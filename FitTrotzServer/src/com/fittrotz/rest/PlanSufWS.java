package com.fittrotz.rest;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.fittrotz.dao.ConnectClass;


// get plan for "Shilddrüssenunterfunktion
@Path("/plansuf")
public class PlanSufWS {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getplanfirst(@QueryParam("type") String type)
			throws Exception {

		JSONObject plan = new JSONObject();
		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = ConnectClass.getDataSource().getConnection();
			query = conn
					.prepareStatement("select * from PLAN_SUF where TYPE = \'"
							+ type + "\' ");
			ResultSet rs = query.executeQuery();

			while (rs.next()) {
				plan.put("Type", rs.getString("TYPE"));
				plan.put("Name", rs.getString("NAME"));
				plan.put("Detail", rs.getString("DETAIL"));
				plan.put("Cal", rs.getString("CAL"));

			}
			query.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// damit die Datenbankverbindung immer geschlossen wird
			if (conn != null)
				conn.close();
		}

		return plan.toString();
	}

}
