package com.svtask.five;

import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;
import com.svtask.five.fragments.MainActivityFragment;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Parse.initialize(this, "z82WClzkPZS8XzRlPhuWL4GgdHQ2N3996ovQ2qLc", "HTZmvhBFybKwNz7KC2BJEjrUysL01CYtGHr3jcPz");
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainActivityFragment()).commit();
		}
		 
		if(ParseUser.getCurrentUser() == null) {						
			ParseFacebookUtils.initialize(getString(R.string.facebook_id));
			ParseLoginBuilder builder = new ParseLoginBuilder(this);
			
			Intent parseLoginIntent = builder.setAppLogo(R.drawable.abook)			    
				    .setFacebookLoginEnabled(true)
				    .setFacebookLoginButtonText("Facebook")
				    .setFacebookLoginPermissions(Arrays.asList("public_profile", "email"))
				    .build();			
			startActivityForResult(parseLoginIntent, 0);						 
		}
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.add_contact :
			startActivity(new Intent(this, AddContactActivity.class));
			break;

		case R.id.profile :
			startActivity(new Intent(this, ProfileActivity.class));
			break;
			
		}
		return true;
	}	
}
