package com.common.util.popwinview;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.common.util.R;

public class PopWin_View_01 {

	private static PopWin_View_01 instance;
	private Context ctx;
	private LinearLayout linearLayout;
	private View popView;
	private static PopupWindow popupWindow;

	// 同步方法，以免单例多线程环境下出现异常
	public synchronized static PopWin_View_01 getInstance(Context context) {
		if (instance == null) {
			instance = new PopWin_View_01(context);
		}
		return instance;
	}

	private PopWin_View_01(Context context) {
		this.ctx = context;
	}

	private void popInit() {
		popView = View.inflate(ctx, R.layout.popwin_view_01, null);
		linearLayout = (LinearLayout) popView.findViewById(R.id.popview_comon);
		popupWindow = new PopupWindow(ctx);
		popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		popupWindow.setFocusable(true);
		popupWindow.setHeight(LayoutParams.MATCH_PARENT);
		popupWindow.setWidth(LayoutParams.MATCH_PARENT);
		popupWindow.setBackgroundDrawable(ctx.getResources().getDrawable(
				R.drawable.shape_transparent));
//		popupWindow.setFocusable(false);
		popupWindow.setContentView(popView);
	}

	/**
	 * 公共样式弹出框
	 * 
	 * @param showView 如图显示
	 * @param parentView 
	 * @return 当前容器
	 */
	public View showPopWin(View showView, View parentView) {
		if (popView==null|| linearLayout == null ||popupWindow == null) {
			popInit();
		}
		popupWindow.dismiss();
		linearLayout.removeAllViewsInLayout();
		linearLayout.addView(showView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
       return popView;
	}

	public void hidePopWin() {
		if (popupWindow != null) {
			popupWindow.dismiss();
		}
	}
}
