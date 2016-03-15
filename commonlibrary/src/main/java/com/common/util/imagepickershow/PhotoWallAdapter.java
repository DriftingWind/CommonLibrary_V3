package com.common.util.imagepickershow;

import java.util.ArrayList;

import android.content.Context;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.common.util.R;
import com.common.util.SDCardImageLoader;
import com.common.util.ScreenUtils;

/**
 * PhotoWall中GridView的适配器
 * 
 * @author hanj
 */

public class PhotoWallAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<String> imagePathList = null;

	private SDCardImageLoader loader;
	//public ArrayList<String> selectedImageList = new ArrayList<String>();
	private SparseArray<String> selectedImageMap;
	// 记录是否被选择
	private SparseIntArray selectionMap;
	

	public PhotoWallAdapter(Context context, ArrayList<String> imagePathList) {
		this.context = context;
		this.imagePathList = imagePathList;

		loader = new SDCardImageLoader(ScreenUtils.getScreenW(),
				ScreenUtils.getScreenH());
		selectionMap = new SparseIntArray();
		selectedImageMap = new SparseArray<String>();
	}

	@Override
	public int getCount() {
		return imagePathList == null ? 0 : imagePathList.size();
	}

	@Override
	public Object getItem(int position) {
		return imagePathList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final String filePath = (String) getItem(position);

		final ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.photo_wall_item, null);
			holder = new ViewHolder();

			holder.imageView = (ImageView) convertView
					.findViewById(R.id.photo_wall_item_photo);
			holder.checkBox = (CheckBox) convertView
					.findViewById(R.id.photo_wall_item_cb);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// tag的key必须使用id的方式定义以保证唯一，否则会出现IllegalArgumentException.
		holder.imageView.setTag(R.id.tag_first, position);
		holder.imageView.setTag(R.id.tag_second, holder.checkBox);
		// holder.checkBox
		// .setOnCheckedChangeListener(new
		// CompoundButton.OnCheckedChangeListener() {
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		// Integer position = (Integer) buttonView
		// .getTag(R.id.tag_first);
		// ImageView image = (ImageView) buttonView
		// .getTag(R.id.tag_second);
		//
		// selectionMap.put(position, isChecked);
		// if (isChecked) {
		// image.setColorFilter(context.getResources()
		// .getColor(R.color.image_checked_bg));
		// } else {
		// image.setColorFilter(null);
		// }
		// }
		// });
		holder.imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Integer position = (Integer) v.getTag(R.id.tag_first);
				CheckBox checkBox = (CheckBox) v.getTag(R.id.tag_second);
				boolean isChecked = checkBox.isChecked();
				if (!isChecked) {
					if(selectionMap.size()>1){
						Toast.makeText(context, "最多只能同时发送"+selectionMap.size()+"张图片", Toast.LENGTH_LONG).show();
						return;
					}
					
					checkBox.setChecked(true);
					isChecked = true;
					checkBox.setVisibility(View.VISIBLE);
					selectionMap.put(position, selectionMap.size()+1);
					selectedImageMap.put(position, filePath);
					// image.setColorFilter(context.getResources().getColor(
					// R.color.image_checked_bg));
				} else {
					// image.setColorFilter(null);
					checkBox.setVisibility(View.GONE);
					isChecked = false;
					checkBox.setChecked(false);
					selectionMap.delete(position);
					selectedImageMap.delete(position);
				}
				
			}
		});
		// holder.checkBox.setChecked(selectionMap.get(position));
		if (selectionMap.get(position)!=0) {
			holder.checkBox.setVisibility(View.VISIBLE);
		} else {
			holder.checkBox.setVisibility(View.GONE);
		}
		holder.imageView.setTag(filePath);
		loader.loadImage(4, filePath, holder.imageView);
		return convertView;
	}

	private class ViewHolder {
		ImageView imageView;
		CheckBox checkBox;
	}

	public SparseIntArray getSelectionMap() {
		return selectionMap;
	}

	public void clearSelectionMap() {
		selectionMap.clear();
	}
	
	public void clearselectedImageMap() {
		selectedImageMap.clear();
	}

	public SparseArray<String> getSelectedImageMap() {
		return selectedImageMap;
	}
}
