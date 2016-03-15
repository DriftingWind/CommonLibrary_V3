package com.common.util.gifandstring;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.TextView;

public class GifAndStringUtil {

	private static Context mContext;

	public GifAndStringUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static GifAndStringUtil instance;

	public synchronized static GifAndStringUtil getInstance(Context context) {
		if (instance == null) {
			instance = new GifAndStringUtil(context);
		}
		return instance;
	}

	protected GifAndStringUtil(Context context) {
		GifAndStringUtil.mContext = context;
	}

	public SpannableStringBuilder handler(final TextView gifTextView,
			String content) {
		SpannableStringBuilder sb = new SpannableStringBuilder(content);
		String regex = "(\\#\\[face/png/f_static_)\\d{3}(.png\\]\\#)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		while (m.find()) {
			String tempText = m.group();
			try {
				String num = tempText.substring(
						"#[face/png/f_static_".length(), tempText.length()
								- ".png]#".length());
				String gif = "face/gif/f" + num + ".gif";
				/**
				 * 如果open这里不抛异常说明存在gif，则显示对应的gif 否则说明gif找不到，则显示png
				 * */
				InputStream is = mContext.getAssets().open(gif);
				sb.setSpan(new AnimatedImageSpan(new AnimatedGifDrawable(is,
						new AnimatedGifDrawable.UpdateListener() {
							@Override
							public void update() {
								gifTextView.postInvalidate();
							}
						})), m.start(), m.end(),
						Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				is.close();
			} catch (Exception e) {
				String png = tempText.substring("#[".length(),
						tempText.length() - "]#".length());
				try {
					sb.setSpan(
							new ImageSpan(mContext, BitmapFactory
									.decodeStream(mContext.getAssets()
											.open(png))), m.start(), m.end(),
							Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		return sb;
	}
}
