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

public class ShowProfileFragment extends Fragment implements OnClickListener{

	private TextView name, age, disease, height, weight, bmi, gender;
	private Button btn_edit_profile;
	private String user;
	static InputStream is = null;
	private String url = SERVER_URL + "user";
	private static final String debugTag = "PFshowUserActivity";

	public ShowProfileFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Bundle extras = new Bundle();
		user = extras.getString("user");

		View rootview = inflater.inflate(R.layout.show_profile, container,
				false);

		this.name = (TextView) rootview.findViewById(R.id.username);
		this.age = (TextView) rootview.findViewById(R.id.userage);
		this.disease = (TextView) rootview.findViewById(R.id.userdisease);
		this.height = (TextView) rootview.findViewById(R.id.userheight);
		this.weight = (TextView) rootview.findViewById(R.id.userweight);
		this.bmi = (TextView) rootview.findViewById(R.id.userbmi);

		// button
		btn_edit_profile = (Button) rootview.findViewById(R.id.btn_edit_profile);
		this.btn_edit_profile.setOnClickListener(this);

		new GetUserData().execute();

		return rootview;
	}

	private class GetUserData extends AsyncTask<Void, Void, String> {

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
			url = SERVER_URL + "user";
			String username = new String();
			String userheight = new String();
			String userweight = new String();
			String userage = new String();
			String userdisease = new String();
			String usergender = new String();
			String userbmi = new String();

			try {
				JSONObject respObj = new JSONObject(result);
				username = (String) respObj.get("Name");
				userheight = (String) respObj.get("Height");
				userweight = (String) respObj.get("Weight");
				userage = (String) respObj.get("Age");
				userdisease = (String) respObj.get("Disease");
				usergender = (String) respObj.get("Gender");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			/*
			 * Hier wird die User Krankheit in DiseaseHelper klasse übertragen
			 * später bei abrufen der Ernährungspläne wird dies benötigt.
			 */
			DiseaseHelper.getInstance().setUserD(userdisease);

			name.setText(username);
			height.setText(userheight);
			age.setText(userage);
			weight.setText(userweight);
			disease.setText(userdisease);

			// BMI Calculation

			Double h = Double.parseDouble(userheight);
			Double w = Double.parseDouble(userweight);
			Double bodymasindex = 0.0;
			bodymasindex = (w / ((h / 100) * (h / 100)));
			userbmi = Double.toString(bodymasindex);
			bmi.setText(userbmi);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_edit_profile:
			Intent intent = new Intent(getActivity(), RegisterUserActivity.class);
			startActivity(intent);
			break;

		}
	}

}
