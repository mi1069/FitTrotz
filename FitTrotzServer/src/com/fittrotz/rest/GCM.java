package com.fittrotz.rest;

import javax.ws.rs.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

import org.codehaus.jettison.json.JSONObject;
import com.fittrotz.dao.ConnectClass;

@Path("/gcm")
public class GCM {

	@POST
	@Consumes("application/json")
	public void getGCM(String json) throws Exception {


		String GCMID = new String();

		try {
			JSONObject respObj = new JSONObject(json);
			GCMID = (String) respObj.get("GCM");
		} catch (Exception e) {
			e.printStackTrace();
		}


		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = ConnectClass.getDataSource().getConnection();
			query = conn.prepareStatement("INSERT INTO GCMIDS VALUES ('" + GCMID
					+ "')");
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
