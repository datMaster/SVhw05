package com.svtask.five.parse;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;
import com.svtask.five.Constants;
import com.svtask.five.R;
import com.svtask.five.adapters.ContactListAdapter;

public class ParseAPI {		
	
	private static ArrayList<Contact> contactList;
	private static ProgressDialog progressDialog;
	private static Activity activity;
	
	public ParseAPI(Activity activity) {
		this.activity = activity; 
	}
	
	public void getContacts(ListView listView) {
		loadContacts(Constants.PARSE_RECORDS_FROM_BEGIN, listView, null);					
	}
	
	public static void loadContacts(int fromIndex, final ListView listView, final ContactListAdapter adapter) {
		contactList = new ArrayList<Contact>();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Constants.PARSE_TABLE_NAME);
		query.setLimit(Constants.PARSE_LOAD_LIMIT);
		query.setSkip(fromIndex);
				
		if(adapter == null)
			showProgressDialog(Constants.PROGRESS_CONTACT_LIST);
		
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> contacts, ParseException e) {
		        if (e == null) {
		        	for(ParseObject obj : contacts) {
		        		Contact newContact = new Contact();
		        		newContact.toContact(obj);
		        		contactList.add(newContact);
		        	}
		        	if(adapter == null) {		        		
		        		listView.setAdapter(new ContactListAdapter(activity, contactList));
		        		progressDialog.dismiss();
		        	}
		        	else {
		        		adapter.updateContacts(contactList);
		        	}		        	
		        } 
		        else {		        	
		        	progressDialog.dismiss();
		        	Toast.makeText(activity, activity.getString(R.string.no_available_contacts), 
		        			Toast.LENGTH_LONG).show();
		        }
		    }
		});	
	}
	
	private static void showProgressDialog(int select) {		
		switch (select) {
		case Constants.PROGRESS_CONTACT_LIST :
			progressDialog = ProgressDialog.show(activity, 
					activity.getString(R.string.connection), 
					activity.getString(R.string.loading_contacts));
			break;
		case Constants.PROGRESS_FULL_CONTACT_INFO :
			progressDialog = ProgressDialog.show(activity, 
					activity.getString(R.string.connection), 
					activity.getString(R.string.loading_full_info));
			break;
		}
	}
}
