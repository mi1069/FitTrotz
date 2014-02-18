package com.fitapp.eis;

/*Zeigt das Graph layout */
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class GraphFragment extends Fragment {

	public GraphFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Bundle extras = new Bundle();

		View rootview = inflater.inflate(R.layout.show_graph, container, false);

		return rootview;
	}

}
