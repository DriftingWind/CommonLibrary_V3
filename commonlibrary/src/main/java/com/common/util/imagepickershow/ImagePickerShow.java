package com.common.util.imagepickershow;

import com.common.util.imagepickershow.PhotoWallActivity.PhotoWallActivityListener;

import android.content.Context;
import android.content.Intent;

public class ImagePickerShow {

	private volatile static ImagePickerShow instance;
	private static Context mContext;

	public static ImagePickerShow getInstance(Context context) {
		if (instance == null) {
			synchronized (ImagePickerShow.class) {
				if (instance == null) {
					instance = new ImagePickerShow(context);
				}
			}
		}
		return instance;
	}

	protected ImagePickerShow(Context context) {
		ImagePickerShow.mContext = context;
	}

	public void imagePicker(PhotoWallActivityListener listener) {
		PhotoWallActivity.setPhotoWallActivityListener(listener);
		Intent intent = new Intent(mContext, PhotoWallActivity.class);
		mContext.startActivity(intent);
	}
}
