package com.common.util.listviewshow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.common.util.R;
import com.common.widget.Custom_TitleBar_1;
import com.common.widget.Custom_TitleBar_1.Custom_TitleBar_1Listener;

public class ListViewActivity extends Activity {
	private static final String TAG = ListViewActivity.class.getSimpleName();

	public ListViewActivity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	private static ListViewActivity instance;
	private static Context mContext;
	private static Drawable selectedImg;

	public interface ListViewActivityListener {

		public void initTitleBar(Custom_TitleBar_1 custom_TitleBar_1);

		public void updateView(String showData);

	}

	public synchronized static ListViewActivity getInstance(Context context) {
		if (instance == null) {
			instance = new ListViewActivity(context);
		}
		return instance;
	}

	protected ListViewActivity(Context context) {
		ListViewActivity.mContext = context;
	}

	private static List<Map<String, Object>> listItems = null;
	private static ListViewActivityListener listener = null;

	public void show_ListView(List<String> listItems, Drawable selectedImg,
			String selectedItem, ListViewActivityListener listener) {

		List<Map<String, Object>> listItems_custom = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < listItems.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			String currentItem = listItems.get(i);
			map.put("leftText", currentItem);
			if (currentItem.equals(selectedItem)) {
				map.put("rightText", selectedImg);
			} else {
				map.put("rightText", null);
			}
			listItems_custom.add(map);
		}
		Intent intent = new Intent(mContext, ListViewActivity.class);
		ListViewActivity.listItems = listItems_custom;
		ListViewActivity.listener = listener;
		ListViewActivity.selectedImg = selectedImg;
		Log.d(TAG, listItems + "");
		mContext.startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view);

		final ListView listView = (ListView) findViewById(R.id.activity_listView);
		if (listItems == null) {
			Toast.makeText(this, "数据列表为空！", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
		// 处理返回键
	    Custom_TitleBar_1 custom_TitleBar_1 = (Custom_TitleBar_1) findViewById(R.id.custom_titlebar_1_activitylistview);
//		custom_TitleBar_1.setActivity(this);
		custom_TitleBar_1.setCustom_TitleBar_1Listener(new Custom_TitleBar_1Listener() {
			
			@Override
			public void right_tv_DO() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void left_tv_DO() {
				// TODO Auto-generated method stub
				finish();
			}
		});
		if (listener != null) {
			listener.initTitleBar(custom_TitleBar_1);
		}
		final ListViewAdapter listViewAdapter = new ListViewAdapter(this,
				listItems);
		listView.setAdapter(listViewAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view,
					int position, long id) {

				if (listener != null) {
					listener.updateView(listItems.get(position).get("leftText")
							.toString());
				}
				 finish();
			}
		});
	}
	private class ListViewAdapter extends BaseAdapter {

		private LayoutInflater mInflater; // 视图容器
		private List<Map<String, Object>> listItems;

		public ListViewAdapter(Context context,
				List<Map<String, Object>> listItems) {
			mInflater = LayoutInflater.from(context); // 创建视图容器并设置上下文
			this.listItems = listItems;
		}

		@SuppressLint("NewApi")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub

			if (convertView == null) {
				convertView = mInflater.inflate(
						R.layout.item_activity_list_view, null);
			}
			// 获取控件对象
			TextView leftTextView = (TextView) convertView
					.findViewById(R.id.leftTextView_activity_list_view);
			TextView rightTextView = (TextView) convertView
					.findViewById(R.id.rightTextView_activity_list_view);
			rightTextView.setCompoundDrawables(null, null, null, null);
			leftTextView.setText(this.listItems.get(position).get("leftText")
					.toString());
			Object rightText_Obj = this.listItems.get(position)
					.get("rightText");
			if (rightText_Obj != null) {
				if (rightText_Obj instanceof Drawable) {
					Drawable rightDrawble = (Drawable) rightText_Obj;
					rightDrawble.setBounds(0, 0,
							rightDrawble.getMinimumWidth(),
							rightDrawble.getMinimumHeight());
					rightTextView.setCompoundDrawables(null, null,
							rightDrawble, null);
				} else if (rightText_Obj instanceof String) {
					rightTextView.setText(rightText_Obj.toString());
				}
			}

			return convertView;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getCount() {
			return this.listItems != null ? this.listItems.size() : 0;
		}

		@Override
		public Object getItem(int position) {
			return this.listItems.get(position);
		}

	}

}
