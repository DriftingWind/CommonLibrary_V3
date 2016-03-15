package com.common.util.imagepickershow;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.widget.GridView;
import android.widget.TextView;

import com.common.util.ImageUtil;
import com.common.util.R;
import com.common.util.ScreenUtils;
import com.common.util.ToastUtil;
import com.common.util.imagepickershow.PhotoAlbumActivity.PhotoAlbumActivityListener;
import com.common.widget.Custom_TitleBar_1;
import com.common.widget.Custom_TitleBar_1.Custom_TitleBar_1Listener;

/**
 * 选择照片页面 Created by hanj on 14-10-15.
 */
public class PhotoWallActivity extends Activity {
	private TextView titleTV;

	private ArrayList<String> list;
	private GridView mPhotoWall;
	private PhotoWallAdapter adapter;

	private static PhotoWallActivityListener listener = null;

	public static void setPhotoWallActivityListener(
			PhotoWallActivityListener listener) {
		PhotoWallActivity.listener = listener;
	}

	public interface PhotoWallActivityListener {

		public void initPhotoWallTitleBar(Custom_TitleBar_1 custom_TitleBar_1);

		public void initPhotoAlbumTitleBar(Custom_TitleBar_1 custom_TitleBar_1);

		public void updateView(/*ArrayList<String> paths*/SparseArray<String> paths);

	}

	/**
	 * 当前文件夹路径
	 */
	private String currentFolder = null;
	/**
	 * 当前展示的是否为最近照片
	 */
	private boolean isLatest = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_wall);
		// TODO　修改　ＴＷＴ
		// titleTV = (TextView) findViewById(R.id.topbar_title_tv);
		// titleTV.setText(R.string.latest_image);
		//
		// Button backBtn = (Button) findViewById(R.id.topbar_left_btn);
		// Button confirmBtn = (Button) findViewById(R.id.topbar_right_btn);
		// backBtn.setText(R.string.photo_album);
		// backBtn.setVisibility(View.VISIBLE);
		// confirmBtn.setText(R.string.main_confirm);
		// confirmBtn.setVisibility(View.VISIBLE);
		Custom_TitleBar_1 custom_TitleBar_1 = (Custom_TitleBar_1) findViewById(R.id.custom_titlebar_1_photo_wall);
		PhotoAlbumActivity
				.setPhotoAlbumActivityListener(new PhotoAlbumActivityListener() {

					@Override
					public void initTitleBar(Custom_TitleBar_1 custom_TitleBar_1) {
						// TODO Auto-generated method stub
						listener.initPhotoAlbumTitleBar(custom_TitleBar_1);
					}
				});
		custom_TitleBar_1
				.setCustom_TitleBar_1Listener(new Custom_TitleBar_1Listener() {

					@Override
					public void right_tv_DO() {
						// TODO Auto-generated method stub
						if (listener != null) {
							//ArrayList<String> paths = getSelectImagePaths();
							SparseArray<String> paths = adapter.getSelectedImageMap();
							if(paths!=null && paths.size()>0){
								showProgress("发送中...",false);
								listener.updateView(paths);
								finish();
							}else{
								ToastUtil.showShortToast(PhotoWallActivity.this, "请选择要发送的图片！",false);
							}
						}
					}

					@Override
					public void left_tv_DO() {

						// TODO Auto-generated method stub
						Intent intent = new Intent(PhotoWallActivity.this,
								PhotoAlbumActivity.class);

						intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						// 传递“最近照片”分类信息
						if (firstIn) {
							if (list != null && list.size() > 0) {
								intent.putExtra("latest_count", list.size());
								intent.putExtra("latest_first_img", list.get(0));
							}
							firstIn = false;
						}

						startActivity(intent);
						// 动画
						overridePendingTransition(R.anim.in_from_left,
								R.anim.out_from_right);
						finish();
					}
				});
		if (listener != null) {

			listener.initPhotoWallTitleBar(custom_TitleBar_1);

		}
		// 获取屏幕像素
		ScreenUtils.initScreen(this);
		mPhotoWall = (GridView) findViewById(R.id.photo_wall_grid);
		list = getLatestImagePaths(100);
		adapter = new PhotoWallAdapter(this, list);
		mPhotoWall.setAdapter(adapter);
		// TODO　修改　ＴＷＴ
		// 选择照片完成
		// confirmBtn.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {

		// //选择图片完成,回到起始页面
		// ArrayList<String> paths = getSelectImagePaths();
		//
		// Intent intent = new Intent(PhotoWallActivity.this,
		// MainActivity.class);
		// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// intent.putExtra("code", paths != null ? 100 : 101);
		// intent.putStringArrayListExtra("paths", paths);
		// startActivity(intent);
		// }
		// });

		// 点击返回，回到选择相册页面
		// backBtn.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// backAction();
		// }
		// });
	}

	/**
	 * 第一次跳转至相册页面时，传递最新照片信息
	 */
	private boolean firstIn = true;

	/**
	 * 点击返回时，跳转至相册页面
	 */
	// private void backAction() {
	// Intent intent = new Intent(this, PhotoAlbumActivity.class);
	// intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
	//
	// // 传递“最近照片”分类信息
	// if (firstIn) {
	// if (list != null && list.size() > 0) {
	// intent.putExtra("latest_count", list.size());
	// intent.putExtra("latest_first_img", list.get(0));
	// }
	// firstIn = false;
	// }
	//
	// startActivity(intent);
	// // 动画
	// overridePendingTransition(R.anim.in_from_left, R.anim.out_from_right);
	// }

	// 重写返回键
	// @Override
	// public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// backAction();
	// return true;
	// } else {
	// return super.onKeyDown(keyCode, event);
	// }
	// }

	/**
	 * 根据图片所属文件夹路径，刷新页面
	 */
	private void updateView(int code, String folderPath) {
		list.clear();
		adapter.clearSelectionMap();
		adapter.clearselectedImageMap();
		adapter.notifyDataSetChanged();

		if (code == 100) { // 某个相册
			int lastSeparator = folderPath.lastIndexOf(File.separator);
			String folderName = folderPath.substring(lastSeparator + 1);
			titleTV.setText(folderName);
			list.addAll(getAllImagePathsByFolder(folderPath));
		} else if (code == 200) { // 最近照片
			titleTV.setText(R.string.latest_image);
			list.addAll(getLatestImagePaths(100));
		}

		adapter.notifyDataSetChanged();
		if (list.size() > 0) {
			// 滚动至顶部
			mPhotoWall.smoothScrollToPosition(0);
		}
	}

	/**
	 * 获取指定路径下的所有图片文件。
	 */
	private ArrayList<String> getAllImagePathsByFolder(String folderPath) {
		File folder = new File(folderPath);
		String[] allFileNames = folder.list();
		if (allFileNames == null || allFileNames.length == 0) {
			return null;
		}

		ArrayList<String> imageFilePaths = new ArrayList<String>();
		for (int i = allFileNames.length - 1; i >= 0; i--) {
			if (ImageUtil.isImage(allFileNames[i])) {
				imageFilePaths.add(folderPath + File.separator
						+ allFileNames[i]);
			}
		}

		return imageFilePaths;
	}

	/**
	 * 使用ContentProvider读取SD卡最近图片。
	 */
	private ArrayList<String> getLatestImagePaths(int maxCount) {
		Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		String key_MIME_TYPE = MediaStore.Images.Media.MIME_TYPE;
		String key_DATA = MediaStore.Images.Media.DATA;

		ContentResolver mContentResolver = getContentResolver();
		
		// 只查询jpg和png的图片,按最新修改排序
		Cursor cursor = mContentResolver.query(mImageUri,
				new String[] { key_DATA }, key_MIME_TYPE + "=? or "
						+ key_MIME_TYPE + "=? or " + key_MIME_TYPE + "=?",
				new String[] { "image/jpg", "image/jpeg", "image/png" },
				MediaStore.Images.Media.DATE_MODIFIED);

		ArrayList<String> latestImagePaths = null;
		if (cursor != null) {
			// 从最新的图片开始读取.
			// 当cursor中没有数据时，cursor.moveToLast()将返回false
			if (cursor.moveToLast()) {
				latestImagePaths = new ArrayList<String>();

				while (true) {
					// 获取图片的路径
					String path = cursor.getString(0);
					latestImagePaths.add(path);

					if (latestImagePaths.size() >= maxCount
							|| !cursor.moveToPrevious()) {
						break;
					}
				}
			}
			cursor.close();
		}

		return latestImagePaths;
	}

	// 获取已选择的图片路径
	private ArrayList<String> getSelectImagePaths() {
		SparseIntArray map = adapter.getSelectionMap();
		if (map.size() == 0) {
			return null;
		}
		int b = map.size();
		ArrayList<String> selectedImageList = new ArrayList<String>(b);
		for (int j = 0; j < b; j++) {
			selectedImageList.add(j + "");
		}
		for (int i = 0; i < list.size(); i++) {
			if (map.get(i) != 0) {
				selectedImageList.set(map.get(i) - 1, list.get(i));
			}
		}
		return selectedImageList;
	}

	// 从相册页面跳转至此页
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		// 动画
		overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);

		int code = intent.getIntExtra("code", -1);
		if (code == 100) {
			// 某个相册
			String folderPath = intent.getStringExtra("folderPath");
			if (isLatest
					|| (folderPath != null && !folderPath.equals(currentFolder))) {
				currentFolder = folderPath;
				updateView(100, currentFolder);
				isLatest = false;
			}
		} else if (code == 200) {
			// “最近照片”
			if (!isLatest) {
				updateView(200, null);
				isLatest = true;
			}
		}
	}
	protected ProgressDialog mProgressDlg = null;
	protected ProgressDialog createProgressDlg(String msg, boolean isCancelable) {
		if (msg == null) {
			msg = "请求中，请稍后......";
		}
		if (mProgressDlg == null) {
			mProgressDlg = new ProgressDialog(this);
			mProgressDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDlg.setCancelable(isCancelable);
			mProgressDlg.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					// 终止网络连接
				}

			});
		}
		mProgressDlg.setMessage(msg);
		if (mProgressDlg.isShowing()) {
			mProgressDlg.dismiss();
		}
		return mProgressDlg;
	}

	public void showProgress(String msg, boolean isCancelable) {
		hideProgress();
		mProgressDlg = createProgressDlg(msg, isCancelable);
		mProgressDlg.show();
	}

	public void showProgress() {
		showProgress(null, true);
	}

	public void hideProgress() {
		if (mProgressDlg != null && mProgressDlg.isShowing()) {
			mProgressDlg.dismiss();
		}
	}

}
