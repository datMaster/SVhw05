package com.svtask.five.adapters;

import java.util.ArrayList;

import com.svtask.five.Constants;
import com.svtask.five.R;
import com.svtask.five.holders.ContactHolder;
import com.svtask.five.parse.Contact;
import com.svtask.five.parse.ParseAPI;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactListAdapter extends BaseAdapter 
implements OnClickListener, OnRefreshListener, OnScrollListener{

	private LayoutInflater inflater;
	private Activity activity;
	private ArrayList<Contact> contactsList;
	private ArrayList<ContactHolder> contactHolderList;
	private SwipeRefreshLayout swipeLayout;
	private ListView listView;
	private View footerView;
	private boolean loadingMore = false;
	private int totalCount = 0; 
	
	public ContactListAdapter(Activity activity, ArrayList<Contact> contacts) {
		this.activity = activity;
		this.contactsList = contacts;
		inflater = (LayoutInflater) this.activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		contactHolderList = new ArrayList<ContactHolder>();		
					
		swipeLayout = 
				(SwipeRefreshLayout)this.activity.getWindow().getDecorView().findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorScheme(android.R.color.white, 
                android.R.color.holo_green_light, 
                android.R.color.white, 
                android.R.color.holo_green_light);
        
      
        footerView = ((LayoutInflater)this.activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer, null, false);         
        listView = (ListView) this.activity.getWindow().getDecorView().findViewById(R.id.listView_names); 
        listView.setOnScrollListener(this);
        updateHolderList();
	}
	
	@Override
	public int getCount() {		
		return contactHolderList.size();
	}

	@Override
	public Object getItem(int position) {
		return contactHolderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null)
			convertView = inflater.inflate(R.layout.contact_item, null);
		ContactHolder holder = contactHolderList.get(position);
		holder.fName = (TextView)convertView.findViewById(R.id.textView_fname);
		holder.sName = (TextView)convertView.findViewById(R.id.textView_sname);
		holder.tel = (TextView)convertView.findViewById(R.id.textView_tel);
		
		holder.fName.setText(contactsList.get(position).getfName());
		holder.sName.setText(contactsList.get(position).getsName());
		holder.tel.setText(contactsList.get(position).getTel());
		
		holder.layout = (LinearLayout)convertView.findViewById(R.id.linearLayout_contact);
		holder.layout.setTag(position);
		holder.layout.setOnClickListener(this);
		return convertView;
	}
	
	private void updateHolderList() {
		int count = contactsList.size() - contactHolderList.size();
		for(int i = 0; i < count; i ++) {
			ContactHolder newContactHolder = new ContactHolder();
			contactHolderList.add(newContactHolder);
		}
		totalCount = contactHolderList.size();
	}

	@Override
	public void onClick(View v) {
		int position = Integer.parseInt(v.getTag().toString());
		Toast.makeText(activity, contactHolderList.get(position).fName.getText(), Toast.LENGTH_LONG).show();		
	}
	
	public void addNewItems(ArrayList<Contact> newContacts) {
		for(Contact obj : newContacts)
			contactsList.add(obj);
		updateHolderList();
	}
	
	public void updateContacts(ArrayList<Contact> newContacts) {		
		if(contactsList.size() == 0) {
			contactsList = newContacts;
			swipeLayout.setRefreshing(false);
		}
		else {
			addNewItems(newContacts);
			loadingMore = false;
			listView.removeFooterView(footerView);
		}		
		updateHolderList();		
		notifyDataSetChanged();		
	}	
	
	@Override
	public void onRefresh() {
		contactsList.clear();
		contactHolderList.clear();
		ParseAPI.loadContacts(Constants.PARSE_RECORDS_FROM_BEGIN, null, this);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		int lastInScreen = firstVisibleItem + visibleItemCount;
		if(((lastInScreen % Constants.PARSE_LOAD_LIMIT) == 0) && (loadingMore == false)) {
			listView.addFooterView(footerView);
			ParseAPI.loadContacts(contactsList.size(), null, this);
			loadingMore = true;
		}
	}	
}
