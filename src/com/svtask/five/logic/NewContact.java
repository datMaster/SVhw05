package com.svtask.five.logic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.svtask.five.R;
import com.svtask.five.holders.NewContactHolder;
import com.svtask.five.parse.ParseAPI;
import com.svtask.five.utils.ByteToBitmap;
import com.svtask.five.utils.OpenFileDialog;
import com.svtask.five.utils.OpenFileDialog.OpenDialogListener;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class NewContact implements OnClickListener, OpenDialogListener{
	
	private Activity activity;
	private View rootView;
	private NewContactHolder newContactHolder;	
	
	public NewContact(Activity activity, View rootView) {
		this.activity = activity;
		this.rootView = rootView;
		initialization();
	}
	
	private void initialization() {
		newContactHolder = new NewContactHolder();
		newContactHolder.fName = (EditText) rootView.findViewById(R.id.editText_fname);
		newContactHolder.sName = (EditText) rootView.findViewById(R.id.editText_sname);
		newContactHolder.email = (EditText) rootView.findViewById(R.id.editText_email);
		newContactHolder.tel = (EditText) rootView.findViewById(R.id.editText_tel);
		newContactHolder.browseButton = (ImageButton) rootView.findViewById(R.id.imageButton_add);
		newContactHolder.image = (ImageView) rootView.findViewById(R.id.imageView_image);
		newContactHolder.saveButton = (Button) rootView.findViewById(R.id.button_save);
		
		newContactHolder.saveButton.setOnClickListener(this);
		newContactHolder.browseButton.setOnClickListener(this);
	}
	
	private void saveToParse() {
		if(newContactHolder.fName.getText().toString().equals("") || newContactHolder.tel.getText().toString().equals(""))
			Toast.makeText(activity, activity.getString(R.string.required), Toast.LENGTH_LONG).show();
		else {
			ParseAPI.saveNewItem(newContactHolder);
			activity.finish();
		}
	}
	
	private void getImage() {
		OpenFileDialog fileDialog = new OpenFileDialog(activity); 
		fileDialog.setOpenDialogListener(this);
		fileDialog.show();
	}
	
	private void setImage() {
		newContactHolder.image.setImageBitmap(ByteToBitmap.getBitmap(newContactHolder.imageData));		
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imageButton_add:
			getImage();
			break;
		case R.id.button_save:
			saveToParse();
			break;		
		}		
	}

	@Override
	public void OnSelectedFile(String fileName) {
		RandomAccessFile file;
		try {
			file = new RandomAccessFile(fileName, "r");			
			try {
				newContactHolder.imageData = new byte[(int)file.length()];
				try {
					file.read(newContactHolder.imageData);	
					setImage();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}		


	}
}
