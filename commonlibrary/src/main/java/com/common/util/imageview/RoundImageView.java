package com.common.util.imageview;

import com.common.util.DataUtil;
import com.common.util.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class RoundImageView extends ImageView {
	private static final String TAG = RoundImageView.class.getSimpleName();
	// 描边的颜色值
	private int stroke_Color = 0xff9bd3d8;// c9c8ae
	// dp值
	private float rect_radius = 10;
	private float border_width = 2;
	private final RectF roundRect = new RectF();

	private final Paint maskPaint = new Paint();
	private final Paint zonePaint = new Paint();
	private final Paint strokePaint = new Paint();

	// 设置颜色
	public void setColour(int color) {
		stroke_Color = color;
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// init();
		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				R.styleable.RoundImageView);
		if (typeArray == null) {
			return;
		}
		rect_radius = typeArray.getDimension(
				R.styleable.RoundImageView_rect_radius, 10);
		border_width = typeArray.getDimension(
				R.styleable.RoundImageView_border_width, 2);
		stroke_Color = typeArray.getColor(
				R.styleable.RoundImageView_stroke_Color, 0xff9bd3d8);
		Log.d(TAG, "before:" + rect_radius);
		rect_radius = DataUtil.dip2px(getContext(), rect_radius);
		border_width = DataUtil.dip2px(getContext(), border_width);
		Log.d(TAG, "after" + rect_radius);
		maskPaint.setAntiAlias(true);
		maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

		zonePaint.setAntiAlias(true);

		strokePaint.setAntiAlias(true);
		// 描边的颜色值
		strokePaint.setColor(stroke_Color);
		strokePaint.setStyle(Paint.Style.STROKE);
		strokePaint.setStrokeWidth(border_width);
		typeArray.recycle();
	}

	public RoundImageView(Context context) {
		super(context);
	}

	public void setRectAdius(float radius) {
		rect_radius = radius;
		strokePaint.setStrokeWidth(rect_radius / 2);
		invalidate();
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		int w = getWidth();
		int h = getHeight();
		roundRect.set(0, 0, w, h);
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
		canvas.drawRoundRect(roundRect, rect_radius, rect_radius, zonePaint);

		canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
		super.draw(canvas);
		canvas.drawRoundRect(roundRect, rect_radius, rect_radius, strokePaint);
		canvas.restore();

	}
}
