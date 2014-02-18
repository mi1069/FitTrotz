package com.fittrotz.rest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.fittrotz.dao.ConnectClass;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@Path("/getact")
public class GetActWS {

	//get list of activites  ordered by (all) places
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String returnActiviybyPath() throws Exception {

		JSONObject actListe = new JSONObject();
		JSONObject act = new JSONObject();
		List<String> activities = new ArrayList<String>();
		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = ConnectClass.getDataSource().getConnection();
			query = conn
					.prepareStatement("select * from FIT_ACTIVITY order by PLACE");
			ResultSet rs = query.executeQuery();

			while (rs.next()) {
				activities.add("{\'place\' : \'" + rs.getString("PLACE")
						+ "\'}");
			}
			query.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// damit die Datenbankverbindung immer geschlossen wird
			if (conn != null)
				conn.close();
		}
		act.put("act", activities);
		act.append("act", activities);
		actListe.put("actListe", act);
		String json = new String("{ 'actListe' :{ 'act' : ");

		return json + activities + "}}";
	}

	//get latest added activity
	@Path("/oneact")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getOneAct() throws Exception {

		JSONObject act = new JSONObject();
		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = ConnectClass.getDataSource().getConnection();
			query = conn.prepareStatement("select * from FIT_ACTIVITY ORDER by ID");

			ResultSet rs = query.executeQuery();

			while (rs.next()) {
				//act.put("Id", rs.getString("ID"));
				act.put("Activity", rs.getString("ACTIVITY_NAME"));
				act.put("Place", rs.getString("PLACE"));
				act.put("Longitude", rs.getString("LONGITUDE"));
				act.put("Latitude", rs.getString("LATITUDE"));
				act.put("Date", rs.getString("DATUM"));
				act.put("Time", rs.getString("ACTTIME"));
				act.put("Comment", rs.getString("TEXT"));

			}
			query.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// damit die Datenbankverbindung immer geschlossen wird
			if (conn != null)
				conn.close();
		}
		//String GCMID = "APA91bGox3-_BSnctAKISgYive03o1LJvf8YN0iS6rf3h9k6JChNXcdBEKbvHScLsgRzKPhOJgoF9WM0XxKggkO8ZQL3U74l1WsQoXHLGofrQaTIBdTWONjQy13gbwDt7tJws7ns5oOZ";
		//GCM(GCMID);

		return act.toString();
	}

	//get activity by place (/getplace)
	@Path("/getplace")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String returnplacebyPath(@QueryParam("place") String place)
			throws Exception {

		List<String> activitydata = new ArrayList<String>();
		List<String> onedata = new ArrayList<String>();
		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = ConnectClass.getDataSource().getConnection();
			query = conn
					.prepareStatement("select * from FIT_ACTIVITY where PLACE = \'"
							+ place + "\' ");
			ResultSet rs = query.executeQuery();


			while (rs.next()) {
				String placeresult = rs.getString("PLACE");
				String[] places = placeresult.split("%");

				String nameresult = rs.getString("ACTIVITY_NAME");
				String[] names = nameresult.split("%");

				String dateresult = rs.getString("DATUM");
				String[] dates = dateresult.split("%");

				String timeresult = rs.getString("ACTTIME");
				String[] time = dateresult.split("%");

				String latresult = rs.getString("LATITUDE");
				String[] latitudes = latresult.split("%");

				String longresult = rs.getString("LONGITUDE");
				String[] longitudes = longresult.split("%");

				String textresult = rs.getString("TEXT");
				String[] text = dateresult.split("%");

				for (int i = 0; i < places.length; i++) {
					activitydata.add("{\'place\' : \'" + places[i] +","
							+ "\'\'name\' : \'" + names[i]  +","
							+ "\'\'date\' : \'" + dates[i] +","
							+ "\'\'time\' : \'" + time[i] +","
							+ "\'\'latitude\' : \'" + latitudes[i] +","
							+ "\'\'longitude\' : \'" + longitudes[i] +","
							+ "\'\'text\' : \'" + text[i] +
							"\'}");
				}
			}
			query.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// damit die Datenbankverbindung immer geschlossen wird
			if (conn != null)
				conn.close();
		}

		String json = new String("{ 'activitylist' :{ 'activity' : ");

		return json + activitydata + "}}";
	}

	//get activity by place AND name (/getnameplace)
	@Path("/getnameplace")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String returnnameplacebyPath(@QueryParam("place") String place, @QueryParam("name") String name)
			throws Exception {

		List<String> activitydata = new ArrayList<String>();
		List<String> onedata = new ArrayList<String>();
		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = ConnectClass.getDataSource().getConnection();
			query = conn
					.prepareStatement("select * from FIT_ACTIVITY where PLACE = \'"
							+ place + "\' AND ACTIVITY_NAME = \'"
							+ name + "\'");
			ResultSet rs = query.executeQuery();


			while (rs.next()) {
				String placeresult = rs.getString("PLACE");
				String[] places = placeresult.split("%");

				String nameresult = rs.getString("ACTIVITY_NAME");
				String[] names = nameresult.split("%");

				String dateresult = rs.getString("DATUM");
				String[] dates = dateresult.split("%");

				String timeresult = rs.getString("ACTTIME");
				String[] time = dateresult.split("%");

				String latresult = rs.getString("LATITUDE");
				String[] latitudes = latresult.split("%");

				String longresult = rs.getString("LONGITUDE");
				String[] longitudes = longresult.split("%");

				String textresult = rs.getString("TEXT");
				String[] text = dateresult.split("%");

				for (int i = 0; i < places.length; i++) {
					activitydata.add("{\'place\' : \'" + places[i] 
							+ "\'\'name\' : \'" + names[i]  
									+ "\'\'date\' : \'" + dates[i] 
											+ "\'\'time\' : \'" + time[i] 
													+ "\'\'latitude\' : \'" + latitudes[i] 
															+ "\'\'longitude\' : \'" + longitudes[i] 
																	+ "\'\'text\' : \'" + text[i] +
							"\'}");
				}
			}
			query.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// damit die Datenbankverbindung immer geschlossen wird
			if (conn != null)
				conn.close();
		}

		String json = new String("{ 'activitylist' :{ 'activity' : ");

		return json + activitydata + "}}";
	}


	//join activity
	@Path("/joinact")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String joinOneAct() throws Exception {

		JSONObject act = new JSONObject();
		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = ConnectClass.getDataSource().getConnection();
			query = conn.prepareStatement("select * from FIT_ACTIVITY ORDER by ID");

			ResultSet rs = query.executeQuery();

			while (rs.next()) {
				//act.put("Id", rs.getString("ID"));
				act.put("Activity", rs.getString("ACTIVITY_NAME"));
				act.put("Place", rs.getString("PLACE"));
				act.put("Longitude", rs.getString("LONGITUDE"));
				act.put("Latitude", rs.getString("LATITUDE"));
				act.put("Date", rs.getString("DATUM"));
				act.put("Time", rs.getString("ACTTIME"));
				act.put("Comment", rs.getString("TEXT"));

			}
			query.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// damit die Datenbankverbindung immer geschlossen wird
			if (conn != null)
				conn.close();
		}
		String GCMID = "APA91bGox3-_BSnctAKISgYive03o1LJvf8YN0iS6rf3h9k6JChNXcdBEKbvHScLsgRzKPhOJgoF9WM0XxKggkO8ZQL3U74l1WsQoXHLGofrQaTIBdTWONjQy13gbwDt7tJws7ns5oOZ";
		GCM(GCMID);

		return act.toString();
	}

	public void GCM(String GCMID) {
		Sender sender = new Sender("AIzaSyDxixvvTmiIseT-OQWGN1rnZOYpYScwIu4");
		Message message = new Message.Builder().delayWhileIdle(true)
				.addData("message", "Sie Haben ein neue Aktivität Partner")
				.build();
		Result result;
		try {
			result = sender.send(message, GCMID, 1);
			System.out.println(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
