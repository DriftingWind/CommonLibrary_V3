package com.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.util.R;

public class Custom_TextView_1 extends LinearLayout {

	public Custom_TextView_1(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	private TextView leftTextView_custom_textview_1;
	private TextView middleTextView_custom_textview_1;
	private LinearLayout linearLayout;

	public void setLeftTextView(CharSequence leftTextView) {
		leftTextView_custom_textview_1.setText(leftTextView);
	}

	public void setMiddleTextView(CharSequence middleTextView) {
		middleTextView_custom_textview_1.setText(middleTextView);
	}

	public CharSequence getMiddleTextView() {
		return middleTextView_custom_textview_1.getText();
	}
	@SuppressLint("NewApi")
	public void setBg_LinearLayout(Drawable drawable) {
		if (Build.VERSION.SDK_INT >= 16) {
			linearLayout.setBackground(drawable);
		} else {
			linearLayout.setBackgroundDrawable(drawable);
		}
	}

	public Custom_TextView_1(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		LayoutInflater.from(context).inflate(R.layout.custom_textview_1, this,
				true);
		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				R.styleable.Custom_TextView_1);
		if (typeArray == null) {
			return;
		}
		CharSequence leftTextView = typeArray
				.getText(R.styleable.Custom_TextView_1_leftTextView);
		leftTextView_custom_textview_1 = ((TextView) findViewById(R.id.leftTextView_custom_textview_1));
		setLeftTextView(leftTextView);
		leftTextView_custom_textview_1.setClickable(false);
		CharSequence middleTextView = typeArray
				.getText(R.styleable.Custom_TextView_1_middleTextView);
		middleTextView_custom_textview_1 = ((TextView) findViewById(R.id.middleTextView_custom_textview_1));
		setMiddleTextView(middleTextView);
		middleTextView_custom_textview_1.setClickable(false);
		Drawable drawable = typeArray
				.getDrawable(R.styleable.Custom_TextView_1_background_cus);
		if (drawable != null) {
			linearLayout = ((LinearLayout) findViewById(R.id.bg_custom_textview_1));
			setBg_LinearLayout(drawable);
		}
		typeArray.recycle();
	}
}
