package com.svtask.five.parse;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
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
	private static Contact tempContact;
	
	public ParseAPI(Activity activity) {
		this.activity = activity; 		
	}
	
	public void getContacts(ListView listView) {
		loadContacts(Constants.PARSE_RECORDS_FROM_BEGIN, listView, null);					
	}
	
	public static void loadContacts(int fromIndex, final ListView listView, final ContactListAdapter adapter) {
		contactList = new ArrayList<Contact>();
		
		if(adapter == null)
			showProgressDialog(Constants.PROGRESS_CONTACT_LIST);		
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Constants.PARSE_TABLE_NAME);
		query.setLimit(Constants.PARSE_LOAD_LIMIT);
		query.setSkip(fromIndex);								
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
		        		dismissProgressDialog();
		        	}
		        	else {		        		
		        		adapter.updateContacts(contactList);		        		
		        	}		        	
		        } 
		        else {		        	
		        	dismissProgressDialog();
		        	Toast.makeText(activity, activity.getString(R.string.loading_error), 
		        			Toast.LENGTH_LONG).show();
		        }
		    }
		});	
	}
	
	
	public static void getFullContactInfo(Contact contact, final ContactListAdapter adapter) {
		tempContact = contact;
		showProgressDialog(Constants.PROGRESS_FULL_CONTACT_INFO);
		ParseQuery<ParseObject> query = ParseQuery.getQuery(Constants.PARSE_TABLE_NAME);
		query.getInBackground(tempContact.getId(), new GetCallback<ParseObject>() {
		  public void done(ParseObject object, ParseException e) {
		    if (e == null) {
		      tempContact.createFullContact(object);
		      adapter.showFullContactInfo(tempContact);
		      dismissProgressDialog();
		    } else {
		    	Toast.makeText(activity, activity.getString(R.string.loading_error_full_contact), 
	        			Toast.LENGTH_LONG).show();		    	
		    	dismissProgressDialog();
		    }
		  }
		});
	}
	
	public static void showProgressDialog(int select) {		
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
		case Constants.PROGRESS_LOAD_MORE :
			progressDialog = ProgressDialog.show(activity, 
					activity.getString(R.string.connection), 
					activity.getString(R.string.loading_more));
			break;
		}
	}
	
	public static void dismissProgressDialog() {
		progressDialog.dismiss();
	}
}
