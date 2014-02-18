package com.fittrotz.rest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import com.fittrotz.dao.ConnectClass;

@Path("/users")
public class UsersWS {
	// GET: list all users
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String returnNamebyPath() throws Exception {

		JSONObject userliste = new JSONObject();
		JSONObject user = new JSONObject();
		List<String> users = new ArrayList<String>();
		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = ConnectClass.getDataSource().getConnection();
			query = conn
					.prepareStatement("select * from FIT_USER order by NAME");
			ResultSet rs = query.executeQuery();

			while (rs.next()) {
				users.add("{\'name\' : \'" + rs.getString("NAME") + "\'}");
			}
			query.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// damit die Datenbankverbindung immer geschlossen wird
			if (conn != null)
				conn.close();
		}
		user.put("user", users);
		user.append("user", users);
		userliste.put("userliste", user);

		String json = new String("{ 'userliste' :{ 'user' : ");

		return json + users + "}}";
	}

	// GET: getuser by ID ("/getuserid") Query: id
	@Path("/getuserid")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getOneUser(@QueryParam("id") String id) throws Exception {
		JSONObject user = new JSONObject();
		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = ConnectClass.getDataSource().getConnection();
			query = conn
					.prepareStatement("select * from FIT_USER where ID = \'"
							+ id + "\' ");
			ResultSet rs = query.executeQuery();

			while (rs.next()) {
				user.put("Id", rs.getString("ID"));
				user.put("Name", rs.getString("NAME"));
				user.put("Gender", rs.getString("GENDER"));
				user.put("Age", rs.getString("AGE"));
				user.put("Id", rs.getString("ID"));
				user.put("Weight", rs.getString("WEIGHT"));
				user.put("Height", rs.getString("HEIGHT"));
				user.put("Disease", rs.getString("DISEASE"));
				user.put("Med", rs.getString("MED"));

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

	// POST: add user into FIT_USER table//
	@Path("/user")
	@POST
	@Consumes("application/json")
	public void buildUserByPath(String json) throws Exception {

		String username = new String();
		String userage = new String();
		String userheight = new String();
		String userweight = new String();

		String usergender = new String();
		String userdisease = new String();
		String usermed = new String();
		String userid = "";
		String GCMID = "";

		try {
			JSONObject respObj = new JSONObject(json);
			username = (String) respObj.get("name");
			usergender = (String) respObj.get("gender");
			userheight = (String) respObj.get("height");
			userweight = (String) respObj.get("weight");
			userage = (String) respObj.get("age");
			userdisease = (String) respObj.get("disease");
			usermed = (String) respObj.get("med");

		} catch (Exception e) {
			e.printStackTrace();
		}

		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = ConnectClass.getDataSource().getConnection();

			query = conn.prepareStatement("INSERT INTO FIT_USER VALUES ( '" + username + "','" + usergender + "','"
					+ userage + "','" + userweight + "','" + userheight + "','"
					+ userdisease + "','" + usermed + "','" + GCMID + "','" + userid + "')");
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