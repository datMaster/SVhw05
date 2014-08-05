package com.svtask.five.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.widget.ListView;

import com.svtask.five.Constants;
import com.svtask.five.R;
import com.svtask.five.parse.ParseAPI;

public class CheckWifi extends Activity{

	private Activity activity;
	private ListView listView;		

	public CheckWifi(Activity activity, ListView listView) {
		this.activity = activity;
		this.listView = listView;		
	}
	

	public void checkWifiState() {
		ConnectivityManager connectManager = (ConnectivityManager) activity
				.getSystemService(activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();
		if (networkInfo == null) {
			turnOnWifiDialog();			
		} else {
			initialization();
		}
	}

	private void initialization() {
		ParseAPI parseApi = new ParseAPI(activity);
		parseApi.getContacts(listView);
	}
	
	private void turnOnWifiDialog() {
		showDialog(activity.getString(R.string.network_info), 
				activity.getString(R.string.network), 
				Constants.TURN_ON_WIFI);
	}
	
	private void refreshDialog() {
		showDialog(activity.getString(R.string.update), 
				activity.getString(R.string.refresh), 
				Constants.REFRESH);
	}
	
	private void showDialog(String title, String message, final int selector) {
		new AlertDialog.Builder(activity)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton(android.R.string.yes,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {
						switch (selector) {
						case Constants.REFRESH :
							checkWifiState();
							break;
						case Constants.TURN_ON_WIFI :								
							activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));														
							new CountDownTimer(1000, 1000) {
								public void onTick(long millisUntilFinished) {								     
								 }

								 public void onFinish() {
								     refreshDialog();
								 }
							}.start();
							break;						
						}						
					}
				}).setIcon(android.R.drawable.ic_dialog_info).show();
	}				
}
