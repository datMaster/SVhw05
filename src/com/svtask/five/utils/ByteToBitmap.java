package com.svtask.five.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ByteToBitmap {

	public ByteToBitmap() {
		
	}
	
	public static Bitmap getBitmap(byte[] data) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 1;
		Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
		return bitmap;

	}
}
