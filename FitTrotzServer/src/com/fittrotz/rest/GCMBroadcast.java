package com.fittrotz.rest;

import com.fittrotz.dao.ConnectClass;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

//should be there
@WebServlet("/GCMBroadcast")
public class GCMBroadcast extends HttpServlet {


	//SENDER_ID here is the "Browser Key" that was generated when I
	// created the API keys for my Google APIs project.
	private static final String SENDER_ID = "AIzaSyDxixvvTmiIseT-OQWGN1rnZOYpYScwIu4";

	// This is a *cheat*  It is a hard-coded registration ID from an Android device
	// that registered itself with GCM using the same project id shown above.
	private static final String DROID_BIONIC = "APA91bGox3-_BSnctAKISgYive03o1LJvf8YN0iS6rf3h9k6JChNXcdBEKbvHScLsgRzKPhOJgoF9WM0XxKggkO8ZQL3U74l1WsQoXHLGofrQaTIBdTWONjQy13gbwDt7tJws7ns5oOZ";

	// This array will hold all the registration ids used to broadcast a message.
	// for this demo, it will only have the DROID_BIONIC id that was captured
	// when we ran the Android client app through Eclipse.
	private List<String> androidTargets = new ArrayList<String>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GCMBroadcast() throws Exception {

		super();

		List<String> gcmlist = new ArrayList<String>();
		PreparedStatement query = null;
		Connection conn = null;

		try{
			conn =  ConnectClass.getDataSource().getConnection();
			query = conn.prepareStatement("select * from GCMIDS");
			ResultSet rs = query.executeQuery();
			while (rs.next()){
				gcmlist.add(rs.getString("GCMID"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(conn != null){
				conn.close();
			}
		}
		// we'll only add the hard-coded *cheat* target device registration id
		// for this demo.
		androidTargets.addAll(gcmlist);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String collapseKey = "";
		String userMessage = "";

		try {
			userMessage = request.getParameter("Message");
			collapseKey = request.getParameter("CollapseKey");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		Sender sender = new Sender(SENDER_ID);

		Message message = new Message.Builder()

		.collapseKey(collapseKey)
		.timeToLive(30)
		.delayWhileIdle(true)
		.addData("message", userMessage)
		.build();

		try {
			MulticastResult result = sender.send(message, androidTargets, 1);

			if (result.getResults() != null) {
				int canonicalRegId = result.getCanonicalIds();
				if (canonicalRegId != 0) {

				}
			} else {
				int error = result.getFailure();
				System.out.println("Broadcast failure: " + error);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("CollapseKey", collapseKey);
		request.setAttribute("Message", userMessage);

		request.getRequestDispatcher("index.jsp").forward(request, response);

	}

}