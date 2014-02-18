package com.fitapp.helper;

public class DiseaseHelper {private String userDisease = null;

private static DiseaseHelper instance = null;

public synchronized static DiseaseHelper getInstance() {
	if (instance == null) {
		instance = new DiseaseHelper();
	}
	return instance;
}

public String getUserD() {

	String schild = "Schildruesenunterfunktion";
	String stein = "Stein-Leventhal";
	String userD = null;

	if (schild.equals(userDisease)) {
		userD = "plansuf";
	}
	if (stein.equals(userDisease)) {
		userD = "plansls";
	}

	return userD;

}

public void setUserD(String userD) {
	userDisease = userD;
}

}