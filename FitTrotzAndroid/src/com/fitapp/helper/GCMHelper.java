package com.fitapp.helper;

public class GCMHelper {

	private String GCMID = null;

	private static GCMHelper instance = null;

	public synchronized static GCMHelper getInstance() {
		if (instance == null) {
			instance = new GCMHelper();
		}
		return instance;
	}

	public String getGCMID() {
		return GCMID;
	}

	public void setGCMID(String gCMID) {
		GCMID = gCMID;
	}

}
