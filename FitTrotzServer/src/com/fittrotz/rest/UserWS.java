package com.fittrotz.rest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.fittrotz.dao.ConnectClass;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@Path("/user")
//get latest added user
public class UserWS {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getUser() throws Exception {

		JSONObject user = new JSONObject();
		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = ConnectClass.getDataSource().getConnection();
			query = conn.prepareStatement("select * from FIT_USER ORDER BY ID");

			ResultSet rs = query.executeQuery();

			while (rs.next()) {
				user.put("Name", rs.getString("NAME"));
				user.put("Gender", rs.getString("GENDER"));
				user.put("Height", rs.getString("HEIGHT"));
				user.put("Age", rs.getString("AGE"));
				user.put("Weight", rs.getString("WEIGHT"));
				user.put("Disease", rs.getString("DISEASE"));

			}
			query.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// damit die Datenbankverbindung immer geschlossen wird
			if (conn != null)
				conn.close();
		}

		return user.toString();
	}

}
