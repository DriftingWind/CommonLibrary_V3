package com.common.util.imageshow;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageShow_WeiXin {

	private volatile static ImageShow_WeiXin instance;
	private static Context mContext;

	public static ImageShow_WeiXin getInstance(Context context,Drawable icon) {
		if (instance == null) {
			synchronized (ImageShow_WeiXin.class) {
				if (instance == null) {
					instance = new ImageShow_WeiXin(context);
					// 设置ImageLoader
					init(icon);
				}
			}
		}
		return instance;
	}

	protected ImageShow_WeiXin(Context context) {
		this.mContext = context;
	}

	private static void init(Drawable icon) {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder() //
				.showImageForEmptyUri(icon) //
				.showImageOnFail(icon) //
				.cacheInMemory(true) //
				.cacheOnDisk(true) //
				.build();//
		ImageLoaderConfiguration config = new ImageLoaderConfiguration//
		.Builder(mContext)//
				.defaultDisplayImageOptions(defaultOptions)//
				.discCacheSize(50 * 1024 * 1024)//
				.discCacheFileCount(100)// 缓存一百张图片
				.writeDebugLogs()//
				.build();//
		ImageLoader.getInstance().init(config);
	}
   /**
    *   ArrayList<String> urls = new ArrayList<String>();
	*	String imageUri = "http://site.com/image.png"; // 网络图片  
	*	String imageUri = "file:///mnt/sdcard/image.png"; //SD卡图片  
	*	String imageUri = "content://media/external/audio/albumart/13"; // 媒体文件夹  
	*	String imageUri = "assets://image.png"; // assets  
	*	String imageUri = "drawable://" + R.drawable.image; //  drawable文件   
	*	urls.add("drawable://" + R.drawable.ic_launcher );
    * 	@param position
    * 	@param urls2
    */
	public void imageBrower(int position, ArrayList<String> urls2,boolean NoIndicator) {
		/*Intent intent = new Intent(mContext, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_NOINDICATOR, NoIndicator);
		mContext.startActivity(intent);*/
		imageBrowerFresco(position, urls2, NoIndicator, "ImageLoader");
	}
	public void imageBrowerFresco(int position, ArrayList<String> urls2,boolean NoIndicator,String strTag) {
		Intent intent = new Intent(mContext, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_NOINDICATOR, NoIndicator);
		intent.putExtra(ImagePagerActivity.ACTIVITY_SHOW_TAG, strTag);
		mContext.startActivity(intent);
	}
}
