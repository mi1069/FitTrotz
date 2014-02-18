package com.fitapp.eis;

import static com.fitapp.helper.HelperClass.SERVER_URL;

import com.fitapp.eis.NavigationDrawer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Spinner;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;


/*Erstellt neu Aktivität und übertragt die neu erstellte Aktivität daten an den Server
*/
public class AddSportActivity extends Activity implements
		AdapterView.OnItemSelectedListener, OnClickListener {

	private Button aButton;
	private EditText date;
	private EditText time;
	private EditText place;
	private EditText latitude;
	private EditText longitude;
	private EditText comment;

	private Spinner spinner1;
	TextView name;
	static InputStream is = null;
	private ProgressDialog progDialog;
	private static String url = SERVER_URL + "act";

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_sport);
		this.date = (EditText) findViewById(R.id.adddate);
		this.time = (EditText) findViewById(R.id.addtime);
		this.place = (EditText) findViewById(R.id.addplace);
		this.latitude = (EditText) findViewById(R.id.addlattitude);
		this.longitude = (EditText) findViewById(R.id.addlongitude);
		this.comment = (EditText) findViewById(R.id.addcomment);

		spinner1 = (Spinner) findViewById(R.id.spinner_place);

		ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
				R.array.SportNames,
				android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter);
		spinner1.setOnItemSelectedListener(this);

		// button
		this.aButton = (Button) findViewById(R.id.btnSaveSport);
		this.aButton.setOnClickListener(this);

	}

	/*
	 * inner Class um per Post Daten an den Server zu übertragen
	 */
	private class PostActivityData extends AsyncTask<Void, Void, String> {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progDialog = ProgressDialog.show(AddSportActivity.this,
					"Füge Aktivität ein", "Speichere Daten", true);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#doInBackground(Params[])
		 */
		@Override
		protected String doInBackground(Void... params) {
			List<NameValuePair> param = new ArrayList<NameValuePair>();
			JSONObject jsonobj = new JSONObject();
			try {
				jsonobj.put("name", name.getText().toString());
				jsonobj.put("date", date.getText().toString());
				jsonobj.put("time", time.getText().toString());
				jsonobj.put("place", place.getText().toString());
				jsonobj.put("latitude", latitude.getText().toString());
				jsonobj.put("longitude", longitude.getText().toString());
				jsonobj.put("comment", comment.getText().toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}

			try {
				StringEntity se = new StringEntity(jsonobj.toString());
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(url);
				httppost.setEntity(se);
				httppost.setHeader("Accept", "application/json");
				httppost.setHeader("Content-type", "application/json");

				ResponseHandler responseHandler = new BasicResponseHandler();
				HttpResponse response = httpclient.execute(httppost,
						responseHandler);
				return response.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

			return jsonobj.toString();

		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(String result) {
			url = SERVER_URL + "act";
			progDialog.dismiss();

		}

	}

	@Override
	public void onItemSelected(AdapterView<?> adapterview, View view, int i,
			long l) {
		name = (TextView) view;

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnSaveSport:
			new PostActivityData().execute();
			Intent intent = new Intent(this, NavigationDrawer.class);
			startActivity(intent);
			finish();
			break;
		}
	}

}