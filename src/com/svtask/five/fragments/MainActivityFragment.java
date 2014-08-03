package com.svtask.five.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.svtask.five.R;
import com.svtask.five.parse.ParseAPI;

public class MainActivityFragment extends Fragment {

	public MainActivityFragment() {
	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		
		ListView listView = (ListView)rootView.findViewById(R.id.listView_names);
		ParseAPI parseApi = new ParseAPI(getActivity());
		parseApi.getContacts(listView);			
		return rootView;
	}
}
