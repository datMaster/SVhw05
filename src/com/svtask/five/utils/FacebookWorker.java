package com.svtask.five.utils;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;
import com.svtask.five.MainActivity;
import com.svtask.five.R;
import com.svtask.five.Constants;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FacebookWorker implements OnClickListener {

	private TextView textContent;
	private ImageView image;
	private String userId;
	private Button buttonLogout;
	private Activity activity;
	private ProgressBar progressImageLoading;

	public FacebookWorker(Activity activity, View rootView) {

		this.activity = activity;

		buttonLogout = (Button) rootView.findViewById(R.id.button_logout);
		buttonLogout.setOnClickListener(this);
		image = (ImageView) rootView.findViewById(R.id.imageView_avatar_fb);
		textContent = (TextView) rootView
				.findViewById(R.id.textView_user_profile);
		image.setVisibility(View.GONE);
		progressImageLoading = (ProgressBar) rootView.findViewById(R.id.progressBar_fb_image_loading);

		getUserInformation();
	}

	private void getUserInformation() {
		ParseFacebookUtils.initialize(activity.getString(R.string.facebook_id));
		if (ParseFacebookUtils.getSession().getState().isOpened()) {
			Session sesion = ParseFacebookUtils.getSession();
			Request.newMeRequest(sesion, new Request.GraphUserCallback() {
				@Override
				public void onCompleted(GraphUser fbUser, Response response) {
					if (fbUser != null) {
						textContent.setText(buildUserInfoDisplay(fbUser));
					}
				}
			}).executeAsync();
		}
	}

	private String buildUserInfoDisplay(GraphUser user) {
		StringBuilder userInfo = new StringBuilder("");

		userInfo.append(String.format("Name: %s\n", user.getName()));
		userInfo.append(String.format("Email: %s\n", user.getProperty("email")));

		userId = user.getProperty("id").toString();
		loadImage();

		return userInfo.toString();
	}

	private void loadImage() {
		String imageUri = "https://graph.facebook.com/" + userId
				+ "/picture?type=large";
		new DownloadImage(image, progressImageLoading).execute(imageUri);
	}

	@Override
	public void onClick(View v) {
		ParseUser.getCurrentUser().logOut();
		activity.setResult(Constants.LOGOUT);
		activity.finish();
	}

}
