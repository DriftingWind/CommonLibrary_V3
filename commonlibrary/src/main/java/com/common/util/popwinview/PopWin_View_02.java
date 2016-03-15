package com.common.util.popwinview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.common.util.R;

public class PopWin_View_02 {
	private List<String> itemList = new ArrayList<String>();
	private ListView listView;

	private static PopWin_View_02 instance;
	private Context ctx;
	private static PopupWindow popupWindow;

	// OnItemClickListener listener;

	// 同步方法，以免单例多线程环境下出现异常
	public synchronized static PopWin_View_02 getInstance(Context context) {
		if (instance == null) {
			instance = new PopWin_View_02(context);
		}
		return instance;
	}

	private PopWin_View_02(Context context) {
		this.ctx = context;
	}

	private void popInit() {
		View view = LayoutInflater.from(ctx).inflate(R.layout.popwin_view_02,
				null);
		listView = (ListView) view.findViewById(R.id.popwin_view_02_listView);
		listView.setAdapter(new PopAdapter());
		listView.setFocusableInTouchMode(true);
		listView.setFocusable(true);
		popupWindow = new PopupWindow(view, ctx.getResources()
				.getDimensionPixelSize(R.dimen.popwin_view_02_width),
				LayoutParams.WRAP_CONTENT);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景（很神奇的）
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	public void showPopWin(View parentView, List<String> itemList,
			OnItemClickListener listener) {
		if (listView == null || popupWindow == null) {
			// ToastUtil.showShortToast(ctx, "菜单初始化", true);
			popInit();
		}
		// 设置菜单项的值
		this.itemList = itemList;
		listView.setOnItemClickListener(listener);
		popupWindow.showAsDropDown(parentView,
				10,
				// 保证尺寸是根据屏幕像素密度来的
				ctx.getResources().getDimensionPixelSize(
						R.dimen.popwin_view_02_yoff));
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);
		// 刷新状态
		popupWindow.update();
	}

	// 隐藏菜单
	public void hidePopWin() {
		if (popupWindow != null) {
			popupWindow.dismiss();
		}
	}

	// 适配器
	private final class PopAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return itemList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return itemList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(ctx).inflate(
						R.layout.popwin_view_02_item, null);
				holder = new ViewHolder();

				convertView.setTag(holder);

				holder.groupItem = (TextView) convertView
						.findViewById(R.id.popwin_view_02_textView);

			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.groupItem.setText(itemList.get(position));

			return convertView;
		}

		private final class ViewHolder {
			TextView groupItem;
		}
	}
}
