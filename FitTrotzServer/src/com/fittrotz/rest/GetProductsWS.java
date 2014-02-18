package com.fittrotz.rest;

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

@Path("/getproducts")

//get list of all products ordered by name
public class GetProductsWS {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String returnNamebyPath() throws Exception {

		JSONObject productlist = new JSONObject();
		JSONObject product = new JSONObject();
		List<String> products = new ArrayList<String>();
		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = ConnectClass.getDataSource().getConnection();
			query = conn
					.prepareStatement("select * from PRODUCTS order by NAME");
			ResultSet rs = query.executeQuery();

			while (rs.next()) {
				products.add("{\'Name\' : \'" + rs.getString("NAME")
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
		product.put("product", products);
		product.append("product", products);
		productlist.put("productlist", product);
		String json = new String("{ 'productlist' :{ 'product' : ");

		return json + products + "}}";
	}

	//get product by type (/getproducttype) Query: type 
	@Path("/getproducttype")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String returnTypebyPath(@QueryParam("type") String type)
			throws Exception {
		List<String> productdata = new ArrayList<String>();
		List<String> onedata = new ArrayList<String>();
		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = ConnectClass.getDataSource().getConnection();
			query = conn
					.prepareStatement("select * from PRODUCTS where TYPE = \'"
							+ type + "\' ");;

							ResultSet rs = query.executeQuery();
							while (rs.next()) {
								String nameresult = rs.getString("NAME");
								String[] names = nameresult.split("%");

								String typeresult = rs.getString("TYPE");
								String[] types = typeresult.split("%");

								String calresult = rs.getString("CAL");
								String[] cals = calresult.split("%");



								for (int i = 0; i < types.length; i++) {
									productdata.add("{\'name\' : \'" + names[i] 
											+ "\'\'type\' : \'" + types[i] 
													+ "\'\'cal\' : \'" + cals[i]  +
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

		String json = new String("{ 'productlist' :{ 'product' : ");

		return json + productdata + "}}";
	}


	//get product by Type AND cal ("/getproductcal") query: type&cal

	@Path("/getproductcal")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String returnCalbyPath(@QueryParam("type") String type, @QueryParam("cal") String cal )
			throws Exception {
		List<String> productdata = new ArrayList<String>();
		List<String> onedata = new ArrayList<String>();
		PreparedStatement query = null;
		Connection conn = null;

		try {
			conn = ConnectClass.getDataSource().getConnection();
			query = conn
					.prepareStatement("select * from PRODUCTS where TYPE = \'"
							+ type + "\' AND CAl = \'" + cal + "\' ");;

							ResultSet rs = query.executeQuery();
							while (rs.next()) {
								String nameresult = rs.getString("NAME");
								String[] names = nameresult.split("%");

								String typeresult = rs.getString("TYPE");
								String[] types = typeresult.split("%");

								String calresult = rs.getString("CAL");
								String[] cals = calresult.split("%");


								//adjust depending on preference
								for (int i = 0; i < cals.length; i++) {
									productdata.add("{\'name\' : \'" + names[i] 
											+ "\'\'type\' : \'" + types[i] 
													+ "\'\'cal\' : \'" + cals[i]  +
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

		String json = new String("{ 'productlist' :{ 'product' : ");

		return json + productdata + "}}";
	}
}
