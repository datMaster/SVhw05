package com.svtask.five;

import com.svtask.five.parse.ParseAPI;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MainActivityPlaceHolder extends Fragment {

	public MainActivityPlaceHolder() {
	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		
//		SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
//        swipeLayout.setOnRefreshListener(this);
//        swipeLayout.setColorScheme(android.R.color.holo_blue_bright, 
//                android.R.color.holo_green_light, 
//                android.R.color.holo_orange_light, 
//                android.R.color.holo_red_light);
		
		ListView listView = (ListView)rootView.findViewById(R.id.listView_names);
		ParseAPI parseApi = new ParseAPI(getActivity());
		parseApi.getContacts(listView);			
		return rootView;
	}
}
