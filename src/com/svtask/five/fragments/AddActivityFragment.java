package com.svtask.five.fragments;

import com.svtask.five.R;
import com.svtask.five.logic.NewContact;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddActivityFragment extends Fragment {
	
	public AddActivityFragment() {
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_add_contact,
				container, false);
		new NewContact(getActivity(), rootView);
		return rootView;
	}
	
}
