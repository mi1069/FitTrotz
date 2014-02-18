package com.fittrotz.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.fittrotz.dao.ConnectClass;

@Path("/status")
/**
 * Test for the Databank
 * 
 * @return returnStatus Date and Time of Server
 * @throws Exception
 */
public class StatusWS {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String DatabaseStatus() throws Exception {

		PreparedStatement query = null;
		String myString = null;
		String returnString = null;
		Connection conn = null;
		try {
			conn = ConnectClass.getDataSource().getConnection();
			query = conn
					.prepareStatement("select to_char(sysdate,'YYYY-MM-DD HH24:MI:SS') DATETIME "
							+ "from sys.dual");
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				myString = rs.getString("DATETIME");
			}
			query.close();
			returnString = "<p> Database Status</p> "
					+ "<p> Date/Time return: " + myString + "</p>";
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// damit die Datenbankverbindung immer geschlossen wird
			if (conn != null)
				conn.close();
		}
		return returnString;
	}
}
