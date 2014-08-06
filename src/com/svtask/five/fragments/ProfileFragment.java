package com.svtask.five.fragments;

import com.svtask.five.R;
import com.svtask.five.utils.FacebookWorker;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProfileFragment extends Fragment {
	
	public ProfileFragment() {
	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_profile,
				container, false);			
		new FacebookWorker(getActivity(), rootView);						
		return rootView;
	}		
	
}
