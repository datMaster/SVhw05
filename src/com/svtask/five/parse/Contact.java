package com.svtask.five.parse;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.svtask.five.Constants;

public class Contact {
	
	private String fName = "";
	private String sName = "";
	private String email = "";
	private String tel = "";
	private String id = "";
	private ParseFile image = null;
	
	public Contact() {
	
	}
	
	public Contact toContact(ParseObject object) {
		if(object == null)
			return new Contact();
		tel = object.getString(Constants.PARSE_TEL_COL);
		fName = object.getString(Constants.PARSE_FIRST_NAME_COL);
		sName = object.getString(Constants.PARSE_SECOND_NAME_COL);
		id = object.getObjectId();		
				
		return this;
	}
	
	public Contact createFullContact(ParseObject object) {
		email = object.getString(Constants.PARSE_EMAIL_COL);
		image = (ParseFile) object.get(Constants.PARSE_PHOTO_COL);
		return this;
	}
	
	public String getfName() {
		return fName;
	}
	
	public String getsName() {
		return sName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getTel() {
		return tel;
	}
	
	public ParseFile getImage() {
		return image;
	}
	
	public String getId() {
		return id;
	}
}