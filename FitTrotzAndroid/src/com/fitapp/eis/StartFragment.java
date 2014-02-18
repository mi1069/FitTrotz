package com.fitapp.eis;

import static com.fitapp.helper.HelperClass.SENDER_ID;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitapp.helper.GCMHelper;
import com.google.android.gcm.GCMRegistrar;
/*
Regestriert das Project und Device bei Google*/

public class StartFragment extends Fragment {

	public StartFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_startfragment,
				container, false);

		Context context = container.getContext();

		GCMRegistrar.checkDevice(context);
		GCMRegistrar.checkManifest(context);

		final String regId = GCMRegistrar.getRegistrationId(context);

		GCMHelper.getInstance().setGCMID(regId);

		if (regId.equals("")) {
			GCMRegistrar.register(context, SENDER_ID);
		} else {
			Log.v("-GCM-", "Already registered");
		}

		return rootView;
	}
}
