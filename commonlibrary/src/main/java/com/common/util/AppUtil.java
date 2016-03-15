package com.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AppUtil {
	private static final String TAG = AppUtil.class.getSimpleName();
	// 启动的activiy class
	// private static String
	// lancherActivityClassName=activiy.getClass().getName();
	// private static String lancherActivityClassName;
	private static AppUtil instance;
	private static Context mContext;

	/**
	 * 
	 * @param context
	 * @param welcomeContext
	 *            应用的启动页
	 * @return
	 */
	public synchronized static AppUtil getInstance(Context context) {
		if (instance == null) {
			instance = new AppUtil(context);
		}
		return instance;
	}

	protected AppUtil(Context context) {
		AppUtil.mContext = context;
	}

	public void sendBadgeNumber(int number, Notification notification,
			boolean isNotify) {
	    number = Math.max(0, Math.min(number, 99));
		if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
			sendToXiaoMi(number, notification, isNotify);
		} else if (Build.MANUFACTURER.equalsIgnoreCase("samsung")) {
			sendToSamsumg(number);
		} else if (Build.MANUFACTURER.toLowerCase().contains("sony")) {
			sendToSony(number);
		} else {
			Log.d(TAG, "Not Support number ico");
		}
	}

	private void sendToXiaoMi(int number, Notification notification,
			boolean isNotify) {
		String launcherClassName = getLauncherClassName(mContext);
		if (launcherClassName == null) {
			return;
		}
		NotificationManager nm = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// Notification notification = null;
		boolean isMiUIV6 = true;
		try {
			if (notification == null) {
				NotificationCompat.Builder builder = new NotificationCompat.Builder(
						mContext);
				builder.setContentTitle("您有" + number + "未读消息");
				builder.setTicker("您有" + number + "未读消息");
				builder.setAutoCancel(true);
				builder.setSmallIcon(R.drawable.ic_launcher);
				builder.setDefaults(Notification.DEFAULT_LIGHTS);
				notification = builder.build();
			}
			Class miuiNotificationClass = Class
					.forName("android.app.MiuiNotification");
			Method[] methods = miuiNotificationClass.getDeclaredMethods();
			for (Method method : methods) {
				Log.d(TAG, method + "");
			}
			Object miuiNotification = miuiNotificationClass.newInstance();
			Field field = miuiNotification.getClass().getDeclaredField(
					"messageCount");
			field.setAccessible(true);
			// field.set(miuiNotification, 2);// 设置信息数 ///
			field.set(miuiNotification, number);// 设置信息数
			// ///
			field = notification.getClass().getField("extraNotification");
			field.setAccessible(true);
			field.set(notification, miuiNotification);
			// Toast.makeText(mContext, "Xiaomi=>isSendOk=>1",
			// Toast.LENGTH_LONG)
			// .show();
		} catch (Exception e) {
			e.printStackTrace();
			// miui 6之前的版本
			isMiUIV6 = false;
			Intent localIntent = new Intent(
					"android.intent.action.APPLICATION_MESSAGE_UPDATE");
			localIntent.putExtra(
					"android.intent.extra.update_application_component_name",
					mContext.getPackageName() + "/"
							+ launcherClassName);
			localIntent.putExtra(
					"android.intent.extra.update_application_message_text",
					number);
			mContext.sendBroadcast(localIntent);
		} finally {
			if (notification != null && isMiUIV6 && isNotify) {
				// miui6以上版本需要使用通知发送
				nm.notify(101010, notification);
			}
		}

	}

	private void sendToSony(int number) {
		String launcherClassName = getLauncherClassName(mContext);
		if (launcherClassName == null) {
			return;
		}
		boolean isShow = true;
		if ("0".equals(number)) {
			isShow = false;
		}
		Intent localIntent = new Intent();
		localIntent
				.putExtra(
						"com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE",
						isShow);// 是否显示
		localIntent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
		localIntent.putExtra(
				"com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME",
				launcherClassName);// 启动页
		localIntent.putExtra(
				"com.sonyericsson.home.intent.extra.badge.MESSAGE", number);// 数字
		localIntent.putExtra(
				"com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME",
				mContext.getPackageName());// 包名
		mContext.sendBroadcast(localIntent);
	}

	private void sendToSamsumg(int number) {
		String launcherClassName = getLauncherClassName(mContext);
		if (launcherClassName == null) {
			return;
		}
		Intent localIntent = new Intent(
				"android.intent.action.BADGE_COUNT_UPDATE");
		localIntent.putExtra("badge_count", number);// 数字
		localIntent.putExtra("badge_count_package_name",
				mContext.getPackageName());// 包名
		localIntent.putExtra("badge_count_class_name", launcherClassName); // 启动页
		mContext.sendBroadcast(localIntent);
	}

	private static String getLauncherClassName(Context context) {
		PackageManager packageManager = context.getPackageManager();

		Intent intent = new Intent(Intent.ACTION_MAIN);
		// To limit the components this Intent will resolve to, by setting an
		// explicit package name.
		intent.setPackage(context.getPackageName());
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		// All Application must have 1 Activity at least.
		// Launcher activity must be found!
		ResolveInfo info = packageManager.resolveActivity(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		// get a ResolveInfo containing ACTION_MAIN, CATEGORY_LAUNCHER
		// if there is no Activity which has filtered by CATEGORY_DEFAULT
		if (info == null) {
			info = packageManager.resolveActivity(intent, 0);
		}

		return info.activityInfo.name;
	}
	
	// 版本名
		public static String getVersionName(Context context) {
			return getPackageInfo(context).versionName;
		}

		// 版本号
		public static int getVersionCode(Context context) {
			return getPackageInfo(context).versionCode;
		}

		private static PackageInfo getPackageInfo(Context context) {
			PackageInfo pi = null;

			try {
				PackageManager pm = context.getPackageManager();
				pi = pm.getPackageInfo(context.getPackageName(),
						PackageManager.GET_CONFIGURATIONS);

				return pi;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return pi;
		}

}
