package com.fitapp.eis;

/*Zeigt die Forum*/
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class ForumFragment extends Fragment {

	public ForumFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Bundle extras = new Bundle();

		View rootview = inflater.inflate(R.layout.show_forum, container, false);

		return rootview;
	}

}
