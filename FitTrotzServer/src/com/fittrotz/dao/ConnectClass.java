package com.fittrotz.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


/*
 *Stellt die Datenbank Verbindung her.
 *Ist nicht von auﬂen zug‰nglich
 */
public class ConnectClass {

	private static DataSource source = null;
	private static Context context = null;

	public static DataSource getDataSource() throws Exception {
		try {
			context = (Context) new InitialContext().lookup("java:comp/env");
			source = (DataSource) context.lookup("jdbc/fittrotz");
			if (source == null) {
				throw new Exception("Verbindung nicht aufgebaut");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return source;
	}


}
