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
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

/*Registeriert User. Fügt User Daten in Datenbank über den Server
 */
public class RegisterUserActivity extends Activity implements OnClickListener {

	private Button aButton;
	private EditText username;
	private EditText userage;
	private EditText userheight;
	private EditText userweight;

	private RadioGroup radioGenderGroup;
	private RadioButton usergender;

	private RadioGroup radioDiseaseGroup;
	private RadioButton userdisease;

	private RadioGroup radioMedGroup;
	private RadioButton usermed;

	static InputStream is = null;
	private ProgressDialog progDialog;
	private static String url = SERVER_URL + "users/user";

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_profile);

		this.username = (EditText) findViewById(R.id.name);
		this.userage = (EditText) findViewById(R.id.age);
		this.userheight = (EditText) findViewById(R.id.height);
		this.userweight = (EditText) findViewById(R.id.weight);

		this.radioGenderGroup = (RadioGroup) findViewById(R.id.radio_sex);
		this.radioDiseaseGroup = (RadioGroup) findViewById(R.id.radio_disease);
		this.radioMedGroup = (RadioGroup) findViewById(R.id.radio_med);

		// Button
		this.aButton = (Button) findViewById(R.id.Addbutton);
		this.aButton.setOnClickListener(this);

	}

	/*
	 * innere klasse PostUserData übetträgt die Daten an den Server
	 */
	private class PostUserData extends AsyncTask<Void, Void, String> {
		/*
		 * (non-Javadoc)
		 * 
		 * @see android.os.AsyncTask#onPreExecute()
		 */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progDialog = ProgressDialog.show(RegisterUserActivity.this,
					"Füge Userdaten ein", "Speichere Daten", true);
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
				jsonobj.put("name", username.getText().toString());
				jsonobj.put("gender", usergender.getText().toString());
				jsonobj.put("disease", userdisease.getText().toString());
				jsonobj.put("med", usermed.getText().toString());
				jsonobj.put("weight", userweight.getText().toString());
				jsonobj.put("age", userage.getText().toString());
				jsonobj.put("height", userheight.getText().toString());
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
			url = SERVER_URL + "users/user";
			progDialog.dismiss();
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.Addbutton:
			// get selected radio button from radioGroup
			int selectedGender = radioGenderGroup.getCheckedRadioButtonId();
			int selectedDisease = radioDiseaseGroup.getCheckedRadioButtonId();
			int selectedMed = radioMedGroup.getCheckedRadioButtonId();

			// find the radiobutton by returned id
			usergender = (RadioButton) findViewById(selectedGender);
			userdisease = (RadioButton) findViewById(selectedDisease);
			usermed = (RadioButton) findViewById(selectedMed);

			new PostUserData().execute();
			Intent intent = new Intent(this, NavigationDrawer.class);
			startActivity(intent);
			finish();
			break;
		}
	}

}
