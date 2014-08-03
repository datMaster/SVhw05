package com.svtask.five.dialogs;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.svtask.five.R;
import com.svtask.five.parse.Contact;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class FullInfoDialog implements OnClickListener{
	
	private Activity activity;
	private TextView fName;
	private TextView sName;
	private TextView email;
	private TextView emaiLabel;
	private TextView tel;
	private ImageView image;
	private Dialog dialog;	
	
	public FullInfoDialog(Activity activity) {		
		this.activity = activity;
		dialog = new Dialog(this.activity);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.contact_card);
		
		fName = (TextView) dialog.findViewById(R.id.textView_fname_card);
		sName = (TextView) dialog.findViewById(R.id.textView_sname_card);
		email = (TextView) dialog.findViewById(R.id.textView_email_card);
		email.setOnClickListener(this);
		emaiLabel = (TextView) dialog.findViewById(R.id.textView_email_label_card);
		tel = (TextView) dialog.findViewById(R.id.textView_tel_card);
		image = (ImageView) dialog.findViewById(R.id.imageView_image_card);
	}		
	
	public void showContactInfo(Contact contact) {
		fName.setText(contact.getfName());
		tel.setText(contact.getTel());
		
		initFild(sName, contact.getsName(), false);
		initFild(email, contact.getEmail(), true);
		
		if(contact.getImage() != null)
			setImage(contact.getImage());
		else
			image.setVisibility(View.GONE);		
	}
	
	private void initFild(TextView field, String data, boolean isEmail) {
		if(data != null) 
			field.setText(data);					
		else {
			field.setVisibility(View.GONE);
			if(isEmail)
				emaiLabel.setVisibility(View.GONE);
		}
	}
	
	private void setImage(ParseFile data) {
		byte[] imageData = null;
		try {
			imageData = data.getData();
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 1;
			Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length, options);
			image.setImageBitmap(bitmap);
		} 
		catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void show() {
		dialog.show();
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("plain/text");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[] { email.getText().toString() });
		activity.startActivity(intent);
	}
	
}
