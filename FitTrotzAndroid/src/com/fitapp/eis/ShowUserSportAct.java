package com.fitapp.eis;

import static com.fitapp.helper.HelperClass.SERVER_URL;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

import static com.fitapp.helper.HelperClass.SERVER_URL;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

/*soll alle erstellten aktivitäten des users zeigen*/

public class ShowUserSportAct extends Activity {
	
	private TextView actname, place, longitude, latitude, date, time;
	private String activities;
	static InputStream is = null;
	private static String url = SERVER_URL + "getact/oneact";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		activities = extras.getString("activity");

		setContentView(R.layout.show_sport);
		this.actname = (TextView) findViewById(R.id.actname);
		this.place = (TextView) findViewById(R.id.place);
		this.longitude = (TextView) findViewById(R.id.longitude);
		this.latitude = (TextView) findViewById(R.id.latitude);
		this.date = (TextView) findViewById(R.id.date);
		this.time = (TextView) findViewById(R.id.time);
		
		actname.setText("nameact");
		latitude.setText("latitudeact");
		place.setText("placeact");
		longitude.setText("longitudeact");
		date.setText("dateact");
		time.setText("timeact");
		new GetActivityData().execute();
	}

	
	private class GetActivityData extends AsyncTask<Void, Void, String> {

		private InputStream inputStream;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			//progDialog = ProgressDialog.show(ShowOneSportActivity.this, "Lade Aktivität", "Lade Daten", true);
		}

		@Override
		protected String doInBackground(Void... params) {
			String json = null;
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				inputStream = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				json = reader.readLine();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return json;

		}


		@Override
		protected void onPostExecute(String result) {
			//url = SERVER_URL + "getact/oneact";
			String nameact = new String();
			String latitudeact = new String();
			String placeact = new String();
			String longitudeact = new String();
			String dateact = new String();
			String timeact = new String();


			try {
				JSONObject respObj = new JSONObject(result);
				nameact = (String) respObj.get("Activity");
				placeact = (String) respObj.get("Place");
				longitudeact = (String) respObj.get("Longitude");
				latitudeact = (String) respObj.get("Latitude");
				
				dateact = (String) respObj.get("Date");
				timeact = (String) respObj.get("Time");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			actname.setText(nameact);
			latitude.setText(latitudeact);
			place.setText(placeact);
			longitude.setText(longitudeact);
			date.setText(dateact);
			time.setText(timeact);
			//progDialog.dismiss();
		}

	}
	
	

}
