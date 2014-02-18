package com.fitapp.eis;

import static com.fitapp.helper.HelperClass.SERVER_URL;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.fitapp.helper.HelperClass.SERVER_URL;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShowOneSportFragment extends Fragment {
	
	
	private TextView actname, place, longitude, latitude, date, time, comment;
	private String activities;
	static InputStream is = null;
	private static String url = SERVER_URL + "getact/oneact";
	
	
	public ShowOneSportFragment(){
		
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Bundle extras = new Bundle();
		activities = extras.getString("activity");
		
		View rootview = inflater.inflate(R.layout.show_sport, container,
				false);
		
		this.actname = (TextView) rootview.findViewById(R.id.actname);
		this.place = (TextView) rootview.findViewById(R.id.place);
		this.longitude = (TextView) rootview.findViewById(R.id.longitude);
		this.latitude = (TextView) rootview.findViewById(R.id.latitude);
		this.date = (TextView) rootview.findViewById(R.id.date);
		this.time = (TextView) rootview.findViewById(R.id.time);
		this.comment = (TextView) rootview.findViewById(R.id.comment);
		

		
		new GetOneActData().execute();

		return rootview;
	}

	private class GetOneActData extends AsyncTask<Void, Void, String> {

		private InputStream inputStream;

		@Override

		protected void onPreExecute() {
			super.onPreExecute();
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
			String commentact = new String();


			try {
				JSONObject respObj = new JSONObject(result);
				nameact = (String) respObj.get("Activity");
				placeact = (String) respObj.get("Place");
				longitudeact = (String) respObj.get("Longitude");
				latitudeact = (String) respObj.get("Latitude");
				
				dateact = (String) respObj.get("Date");
				timeact = (String) respObj.get("Time");
				commentact = (String) respObj.get("Comment");

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
			comment.setText(commentact);
		}

	}

}
