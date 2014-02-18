package com.fittrotz.rest;


import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.ws.rs.Consumes;

import javax.ws.rs.POST;

import javax.ws.rs.Path;

import org.codehaus.jettison.json.JSONObject;

import com.fittrotz.dao.ConnectClass;
@Path("/act")
public class AddActWS {

	// Add Activity data into Activity Table
	@POST
	@Consumes("application/json")
	public void buildActByPath(String json) throws Exception {

		String name = new String();
		String time = new String();
		String date = new String();
		String latitude = new String();
		String place = new String();
		String comment = new String();
		String longitude = new String();
		String GCMID = "";
		String actid = "";


		try {
			JSONObject respObj = new JSONObject(json);
			name = (String) respObj.get("name");
			place = (String) respObj.get("place");
			longitude = (String) respObj.get("longitude");
			latitude = (String) respObj.get("latitude");
			date = (String) respObj.get("date");
			time = (String) respObj.get("time");
			comment = (String) respObj.get("comment");

		} catch (Exception e) {
			e.printStackTrace();
		}

		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = ConnectClass.getDataSource().getConnection();
			query = conn
					.prepareStatement("INSERT INTO FIT_ACTIVITY VALUES (  '"
							+ name + "','" + place + "','" + longitude + "','"
							+ latitude + "','" + date + "','" + time + "','"
							+ comment + "','" + GCMID + "','" + actid + "')");
			query.executeUpdate();
			query.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// damit die Datenbankverbindung immer geschlossen wird
			if (conn != null)
				conn.close();
		}

	}

}