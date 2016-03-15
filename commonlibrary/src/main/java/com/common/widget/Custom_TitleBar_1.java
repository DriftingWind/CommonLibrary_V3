package com.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.util.R;

public class Custom_TitleBar_1 extends RelativeLayout {

	public Custom_TitleBar_1(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public interface Custom_TitleBar_1Listener {

		public void left_tv_DO();

		public void right_tv_DO();

	}

	private Custom_TitleBar_1Listener listener;

	public void setCustom_TitleBar_1Listener(Custom_TitleBar_1Listener listener) {
		this.listener = listener;
	}

	private TextView left_tv_custom_titlebar_1;
	private TextView titleDesc_custom_titlebar_1;
	private RelativeLayout relativeLayout_custom_titlebar_1;
	private TextView right_tv_custom_titlebar_1;

	public TextView getLeft_TV_Custom_TitleBar_1() {
		return left_tv_custom_titlebar_1;
	}

	@SuppressLint("NewApi")
	public void setBackground_Left_TV_Custom_TitleBar_1(Drawable background) {
		if (Build.VERSION.SDK_INT >= 16) {
			left_tv_custom_titlebar_1.setBackground(background);
		} else {
			left_tv_custom_titlebar_1.setBackgroundDrawable(background);
		}
	}
	@SuppressLint("NewApi")
	public void setBackground_Right_TV_Custom_TitleBar_1(Drawable background) {
		if (Build.VERSION.SDK_INT >= 16) {
			right_tv_custom_titlebar_1.setBackground(background);
		} else {
			right_tv_custom_titlebar_1.setBackgroundDrawable(background);
		}
	}

	@SuppressLint("NewApi")
	public void setBackground_RelativeLayout_Custom_TitleBar_1(
			Drawable background) {
		if (Build.VERSION.SDK_INT >= 16) {
			relativeLayout_custom_titlebar_1.setBackground(background);
		} else {
			relativeLayout_custom_titlebar_1.setBackgroundDrawable(background);
		}
	}

	public void setText_Left_TV_Custom_TitleBar_1(CharSequence text) {
		left_tv_custom_titlebar_1.setText(text);
	}

	public void setText_Right_TV_Custom_TitleBar_1(CharSequence text) {
		right_tv_custom_titlebar_1.setText(text);
	}

	public void setText_TitleDesc_custom_titlebar_1(CharSequence text) {
		titleDesc_custom_titlebar_1.setText(text);
	}

	public Custom_TitleBar_1(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.custom_titlebar_1, this,
				true);

		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				R.styleable.Custom_TitleBar_1);
		if (typeArray == null) {
			return;
		}
		Drawable background_relativeLayout_custom_titlebar_1 = typeArray
				.getDrawable(R.styleable.Custom_TitleBar_1_background_relativeLayout_custom_titlebar_1);
		relativeLayout_custom_titlebar_1 = (RelativeLayout) findViewById(R.id.relativeLayout_custom_titlebar_1);
		if (background_relativeLayout_custom_titlebar_1 != null) {
			setBackground_RelativeLayout_Custom_TitleBar_1(background_relativeLayout_custom_titlebar_1);
		}
		Drawable background_left_tv_custom_titlebar_1 = typeArray
				.getDrawable(R.styleable.Custom_TitleBar_1_background_left_tv_custom_titlebar_1);
		CharSequence text_left_tv_custom_titlebar_1 = typeArray
				.getText(R.styleable.Custom_TitleBar_1_text_left_tv_custom_titlebar_1);
		left_tv_custom_titlebar_1 = (TextView) findViewById(R.id.left_tv_custom_titlebar_1);
		if (background_left_tv_custom_titlebar_1 != null) {
			setBackground_Left_TV_Custom_TitleBar_1(background_left_tv_custom_titlebar_1);
		}
		if (text_left_tv_custom_titlebar_1 != null) {
			setText_Left_TV_Custom_TitleBar_1(text_left_tv_custom_titlebar_1);
		}

		CharSequence text_right_tv_custom_titlebar_1 = typeArray
				.getText(R.styleable.Custom_TitleBar_1_text_right_tv_custom_titlebar_1);
		right_tv_custom_titlebar_1 = (TextView) findViewById(R.id.right_tv_custom_titlebar_1);
		if (text_right_tv_custom_titlebar_1 != null) {
			setText_Right_TV_Custom_TitleBar_1(text_right_tv_custom_titlebar_1);
		}
		CharSequence text_titleDesc_custom_titlebar_1 = typeArray
				.getText(R.styleable.Custom_TitleBar_1_text_titleDesc_custom_titlebar_1);
		titleDesc_custom_titlebar_1 = (TextView) findViewById(R.id.titleDesc_custom_titlebar_1);
		if (text_titleDesc_custom_titlebar_1 != null) {
			setText_TitleDesc_custom_titlebar_1(text_titleDesc_custom_titlebar_1);
		}
		left_tv_custom_titlebar_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.left_tv_DO();
				}
			}
		});

		right_tv_custom_titlebar_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (listener != null) {
					listener.right_tv_DO();
				}
			}
		});
		typeArray.recycle();
	}
}
