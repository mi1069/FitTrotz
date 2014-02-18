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







import com.fitapp.helper.GCMHelper;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/*Schickt gcm nachricht an den ersteller der Aktivität*/
public class JoinSelectedSport extends Activity implements OnClickListener {



	private TextView actname, place, longitude, latitude, date, time;
	private String activities;
	static InputStream is = null;
	private String GCMID = GCMHelper.getInstance().getGCMID();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();

		setContentView(R.layout.show_sport);

		//button
				Button btn_joinact = (Button) findViewById(R.id.btn_joinact);
				btn_joinact.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String url = SERVER_URL + "getact/oneact";
		
		
	}


	

}

	


