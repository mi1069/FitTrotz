package com.fitapp.eis;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

/*Zeigt Aktivitäten Layout und bestimmt Funktionen die nach der Auswahl ausgeführt werden sollen */

public class ShowActivityFragment extends Fragment implements OnClickListener {

	public ShowActivityFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Bundle extras = new Bundle();

		View rootview = inflater.inflate(R.layout.show_activity_fragment, container, false);

		// buttons
		Button btn_startActivity = (Button) rootview
				.findViewById(R.id.btn_addActivity);
		btn_startActivity.setOnClickListener(this);

		Button btn_pedo = (Button) rootview.findViewById(R.id.btn_joinact);
		btn_pedo.setOnClickListener(this);

		Button btn_user_act = (Button) rootview.findViewById(R.id.btn_user_act);
		btn_user_act.setOnClickListener(this);
		Button btn_search_partner = (Button) rootview
				.findViewById(R.id.btn_search_partner);
		btn_search_partner.setOnClickListener(this);

		return rootview;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_addActivity:
			Intent intent = new Intent(getActivity(), AddSportActivity.class);
			startActivity(intent);
			//getActivity().finish();
			break;

			/*case R.id.btn_pedometer:
			Intent intent2 = new Intent(getActivity(), NavigationDrawer.class);
			startActivity(intent2);
			getActivity().finish();
			break;*/

			case R.id.btn_user_act:
			Intent intent3 = new Intent(getActivity(), ShowUserSportAct.class);
			startActivity(intent3);
			break;

		case R.id.btn_search_partner:
			Intent intent4 = new Intent(getActivity(), ShowUserSportAct.class);
			startActivity(intent4);
			
			break;

		}

	}

}
