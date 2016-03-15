package com.common.util;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class SoftInputUtil {
	private static final String TAG = SoftInputUtil.class.getSimpleName();
   
	//隐藏输入法
	//Fragment使用  SoftInputUtil.HideKeyboard(getActivity().getCurrentFocus());
	// 隐藏虚拟键盘
	public static void HideKeyboard(View v) {
		Log.d(TAG, "隐藏虚拟键盘");
		InputMethodManager imm = (InputMethodManager) v.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

		}
	}

	// 显示虚拟键盘
	public static void ShowKeyboard(View v) {
		Log.d(TAG, "显示虚拟键盘");
		InputMethodManager imm = (InputMethodManager) v.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);

	}

	// 强制显示或者关闭系统键盘
	public static void KeyBoard(final EditText txtSearchKey, final String status) {

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager m = (InputMethodManager) txtSearchKey
						.getContext().getSystemService(
								Context.INPUT_METHOD_SERVICE);
				if (status.equals("open")) {
					m.showSoftInput(txtSearchKey,
							InputMethodManager.SHOW_FORCED);
				} else {
					m.hideSoftInputFromWindow(txtSearchKey.getWindowToken(), 0);
				}
			}
		}, 300);
	}

	// 通过定时器强制隐藏虚拟键盘
	public static void TimerHideKeyboard(final View v) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) v.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				if (imm.isActive()) {
					imm.hideSoftInputFromWindow(v.getApplicationWindowToken(),
							0);
				}
			}
		}, 10);
	}

	// 输入法是否显示着
	public static boolean KeyBoard(EditText edittext) {
		boolean bool = false;
		InputMethodManager imm = (InputMethodManager) edittext.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			bool = true;
		}
		return bool;

	}

}
