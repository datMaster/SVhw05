package com.svtask.five.utils;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
	private ImageView bmImage;
	private ProgressBar progressLoading;

	public DownloadImage(ImageView bmImage, ProgressBar progressBar) {
		this.bmImage = bmImage;		
		this.progressLoading = progressBar;
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap mIcon11 = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();
			mIcon11 = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mIcon11;
	}

	protected void onPostExecute(Bitmap result) {
		bmImage.setImageBitmap(result);
		progressLoading.setVisibility(View.GONE);
		bmImage.setVisibility(View.VISIBLE);
	}

}