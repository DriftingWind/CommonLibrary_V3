package com.common.util.imageshow;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.common.util.R;
import com.common.util.ScreenUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class ImageFrescoDetailFragment extends Fragment{

	private String mImageUrl;
	private SimpleDraweeView mImageView;
	private PhotoViewAttacher mAttacher;

	public static ImageFrescoDetailFragment newInstance(String imageUrl) {
		final ImageFrescoDetailFragment f = new ImageFrescoDetailFragment();

		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.image_detail_fresco_fragment, container, false);
		mImageView = (SimpleDraweeView) v.findViewById(R.id.image);
		mAttacher = new PhotoViewAttacher(mImageView);

		mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {

			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				getActivity().finish();
			}
		});
		ScreenUtils.initScreen(getActivity());
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setcontrollerUri(mImageView, mImageUrl);
	}
	
	public void setcontrollerUri(final SimpleDraweeView imageView,final String url){
		ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
	            .setResizeOptions(new ResizeOptions((int)(ScreenUtils.getScreenW()*0.75), (int)(ScreenUtils.getScreenH()*0.95)))
	            .build();
		 DraweeController draweeController = Fresco.newDraweeControllerBuilder()
				  .setAutoPlayAnimations(true)//自动播放图片动画
				  .setTapToRetryEnabled(true)//点击重新加载图
				  .setOldController(imageView.getController())
				  .setImageRequest(request)
				  .build();
		imageView.setController(draweeController);
	}

}
