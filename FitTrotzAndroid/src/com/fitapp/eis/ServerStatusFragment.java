package com.fitapp.eis;

import static com.fitapp.helper.HelperClass.SERVER_URL;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*Gibt/Zeigt den Status der server zurück mit Datum und Zeit. 
*/
public class ServerStatusFragment extends Fragment {

	private static final String SERVER_URI = SERVER_URL + "status";
	private static InputStream inputStream = null;
	private View view;
	private TextView textView;

	public ServerStatusFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.show_server_status,
				container, false);
		this.view = rootView;
		this.textView = (TextView) rootView.findViewById(R.id.serverStatus);

		new getProfileData().execute();

		return rootView;
	}

	private class getProfileData extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			String forTextView = null;
			try {

				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(SERVER_URI);
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				inputStream = httpEntity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream));
				forTextView = reader.readLine();

			} catch (Exception e) {
				e.printStackTrace();
			}
			return forTextView;
		}

		@Override
		protected void onPostExecute(String forTextView) {
			textView.setText(forTextView);
		}

	}

}
