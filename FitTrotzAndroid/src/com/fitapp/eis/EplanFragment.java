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

import com.fitapp.helper.DiseaseHelper;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/*Zeigt das Ernährungsplan für einen Tag. Holt die Daten aus dem Server*/

public class EplanFragment extends Fragment implements OnClickListener {

	private TextView namebf, detaibf, calbf;
	private TextView namelunch, detaillunch, callunch;
	private TextView namedinner, detaildinner, caldinner;

	private String plan;
	static InputStream is = null;

	private String userdisease = DiseaseHelper.getInstance().getUserD();
	private String urlBf = SERVER_URL + userdisease + "?type=Breakfast";
	private String urlLunch = SERVER_URL + userdisease + "?type=Lunch";
	private String urlDinner = SERVER_URL + userdisease + "?type=Dinner";

	public EplanFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Bundle extras = new Bundle();
		plan = extras.getString("plan");

		View rootview = inflater.inflate(R.layout.show_eplan_tag, container,
				false);

		// for Breakfast

		this.namebf = (TextView) rootview.findViewById(R.id.bMeal1);
		this.detaibf = (TextView) rootview.findViewById(R.id.bMeal2);
		this.calbf = (TextView) rootview.findViewById(R.id.breakCal);

		// for Lunch
		this.namelunch = (TextView) rootview.findViewById(R.id.lMeal1);
		this.detaillunch = (TextView) rootview.findViewById(R.id.lMeal2);
		this.callunch = (TextView) rootview.findViewById(R.id.lunchCal);

		// for Dinner
		this.namedinner = (TextView) rootview.findViewById(R.id.dMeal1);
		this.detaildinner = (TextView) rootview.findViewById(R.id.dMeal2);
		this.caldinner = (TextView) rootview.findViewById(R.id.dinnerCal);

		// button
		Button btn_woche = (Button) rootview.findViewById(R.id.nbt_Woche);
		btn_woche.setOnClickListener(this);

		// execute AsyncTasks
		new GetBreakfast().execute();
		new GetLunch().execute();
		new GetDinner().execute();

		return rootview;
	}

	private class GetBreakfast extends AsyncTask<Void, Void, String> {

		private InputStream inputStream;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {

			String breakfast = null;
			try {
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(urlBf);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				inputStream = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				breakfast = reader.readLine();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return breakfast;

		}

		@Override
		protected void onPostExecute(String result) {

			String mealname = new String();
			String mealcal = new String();
			String mealdetail = new String();

			try {
				JSONObject respObj = new JSONObject(result);
				mealname = (String) respObj.get("Name");
				mealcal = (String) respObj.get("Cal");
				mealdetail = (String) respObj.get("Detail");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			namebf.setText(mealname);
			calbf.setText(mealcal);
			detaibf.setText(mealdetail);

		}

	}

	private class GetLunch extends AsyncTask<Void, Void, String> {

		private InputStream inputStream;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			String lunch = null;
			try {
				DefaultHttpClient httpClient2 = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(urlLunch);
				HttpResponse httpResponse2 = httpClient2.execute(httpGet);
				HttpEntity httpEntity = httpResponse2.getEntity();
				inputStream = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				lunch = reader.readLine();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return lunch;

		}

		@Override
		protected void onPostExecute(String result) {
			urlLunch = SERVER_URL + "getplan2?type=Dinner";
			String mealname2 = new String();
			String mealcal2 = new String();
			String mealdetail2 = new String();

			try {
				JSONObject respObj = new JSONObject(result);
				mealname2 = (String) respObj.get("Name");
				mealcal2 = (String) respObj.get("Cal");
				mealdetail2 = (String) respObj.get("Detail");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			namelunch.setText(mealname2);
			callunch.setText(mealcal2);
			detaillunch.setText(mealdetail2);

		}

	}

	private class GetDinner extends AsyncTask<Void, Void, String> {

		private InputStream inputStream;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			String dinner = null;
			try {
				DefaultHttpClient httpClient2 = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(urlDinner);
				HttpResponse httpResponse2 = httpClient2.execute(httpGet);
				HttpEntity httpEntity2 = httpResponse2.getEntity();
				inputStream = httpEntity2.getContent();
				BufferedReader reader2 = new BufferedReader(
						new InputStreamReader(inputStream));
				dinner = reader2.readLine();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return dinner;

		}

		@Override
		protected void onPostExecute(String result) {
			String dinnername = new String();
			String dinnercal = new String();
			String dinnerdetail = new String();

			try {
				JSONObject respObj = new JSONObject(result);
				dinnername = (String) respObj.get("Name");
				dinnercal = (String) respObj.get("Cal");
				dinnerdetail = (String) respObj.get("Detail");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			namedinner.setText(dinnername);
			caldinner.setText(dinnercal);
			detaildinner.setText(dinnerdetail);

		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.nbt_Woche:
			Intent intent = new Intent(getActivity(), EplanWocheActivity.class);
			startActivity(intent);
			break;

		}

	}

}