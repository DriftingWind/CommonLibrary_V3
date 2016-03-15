package com.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

public class DataUtil {
	private static final String TAG = DataUtil.class.getSimpleName();

	public DataUtil() {
	}

	public static int screenWidth, screenHeight;

	public static int getScreenHeight() {
		return screenHeight;
	}

	public static void setScreenHeight(int screenHeight) {
		DataUtil.screenHeight = screenHeight;
	}

	public static void setScreenWidth(int width) {
		screenWidth = width;
	}

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static String getDateTime(long t) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss SSS");
		return sdf.format(Long.valueOf(t));
	}

	public static int readInt(byte data[], int offset) {
		int ret = 0;
		ret = data[offset + 3] & 255;
		ret |= data[offset + 2] << 8 & 65280;
		ret |= data[offset + 1] << 16 & 16711680;
		ret |= data[offset + 0] << 24 & -16777216;
		return ret;
	}

	public static long byteToLong(byte buffer[], int offset) {
		if (buffer == null || buffer.length < 8)
			return 0L;
		long ret = 0L;
		for (int i = 0; i < 8; i++) {
			ret |= buffer[i] & 255;
			if (i < 7)
				ret <<= 8;
		}

		return ret;
	}

	public static byte[] longToByte(long val) {
		byte data[] = new byte[8];
		for (int i = 7; i >= 0; i--) {
			data[i] = (byte) (int) (val & 255L);
			val >>= 8;
		}

		return data;
	}

	public static int RandomInt(int begin, int end) {
		int i = (new Random()).nextInt(end - begin);
		return begin + i;
	}

	public static int hexstring2Int(String hexstring) {
		if (hexstring.substring(0, 2).equalsIgnoreCase("0x"))
			hexstring = hexstring.substring(2);
		hexstring = hexstring.toLowerCase();
		return (int) Long.parseLong(hexstring, 16);
	}

	public static int dip2px(Context context, float dpValue) {
		if (mMetrics == null)
			mMetrics = context.getResources().getDisplayMetrics();
		return (int) TypedValue.applyDimension(1, dpValue, mMetrics);
	}

	public static int dip2px(Context context, int dpValue) {
		if (mMetrics == null)
			mMetrics = context.getResources().getDisplayMetrics();
		return (int) TypedValue.applyDimension(1, dpValue, mMetrics);
	}

	public static float getDisplayMetrics(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		float density_f = 8.0f; // 默认值
		if (dm != null) {
			density_f = dm.density;
		}
		return density_f;
	}

	public static float pixelToDp(Context context, float val) {
		float density = context.getResources().getDisplayMetrics().density;
		return val * density;
	}

	/**
	 * PX转 DIP
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */

	public static int px2dip(Context context, float pxValue) {

		final float scale = context.getResources().getDisplayMetrics().density;

		return (int) (pxValue / scale + 0.5f);

	}

	public static int sp2px(Context context, float spValue) {
		if (mMetrics == null)
			mMetrics = context.getResources().getDisplayMetrics();
		return (int) TypedValue.applyDimension(2, spValue, mMetrics);
	}

	public static int spModify(Context context, float spValue) {
		if (mMetrics == null)
			mMetrics = context.getResources().getDisplayMetrics();
		float size = (spValue * TypedValue.applyDimension(1, 1.0F, mMetrics))
				/ TypedValue.applyDimension(2, 1.0F, mMetrics);
		return (int) size;
	}

	public static ArrayList splitBuffer(byte buffer[], int offset, int length,
			int spsize) {
		ArrayList array = new ArrayList();
		if (spsize <= 0 || length <= 0 || buffer == null
				|| buffer.length < length + offset)
			return array;
		for (int size = 0; size < length - offset;) {
			int left = length - size - offset;
			if (spsize < left) {
				byte sdata[] = new byte[spsize];
				System.arraycopy(buffer, size + offset, sdata, 0, spsize);
				array.add(sdata);
				size += spsize;
			} else {
				byte sdata[] = new byte[left];
				System.arraycopy(buffer, size + offset, sdata, 0, left);
				array.add(sdata);
				size += left;
			}
		}

		return array;
	}

	/**
	 * 半角转为全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 替换、过滤特殊字符
	 * 
	 * @param str
	 * @return
	 */
	public static String stringFilter(String str) {
		str = str.replaceAll("【", "[").replaceAll("】", "]")
				.replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
		String regEx = "[『』]"; // 清除掉特殊字符
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static final int SIZE_1K = 1024;
	public static final String UTF8 = "utf-8";
	public static final String GB2312 = "gb2312";
	public static final String UNICODE = "unicode";
	public static DisplayMetrics mMetrics = null;

	/**
	 * 将字符串写入文件
	 * 
	 * @param context
	 * @param fileName
	 */
	public static void putStringToFile(Context context, String fileName,
			String inputString) {
		try {
			// DebugLog.Log("putStringToFile(), file: " + fileName +
			// ", inputString: " + inputString);
			File file = new File(fileName);
			if (!file.exists()) {
				makeRootDirectory(file.getParent());
				file.createNewFile();
			}
			FileOutputStream fout = new FileOutputStream(file);
			byte[] bytes = inputString.getBytes();
			fout.write(bytes);
			fout.close();

		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "putStream(): " + e.toString());
		}
	}

	/**
	 * 创建文件夹
	 * 
	 * @param filePath
	 */
	public static void makeRootDirectory(String filePath) {
		File file = null;
		try {
			file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 从文件读取字符串
	 * 
	 * @param fileName
	 */
	public static String getStringFromFile(Context context, String fileName) {
		try {
			File file = new File(fileName);
			FileInputStream fin = new FileInputStream(file);
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			String res = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();

			// DebugLog.Log("getStringFromFile(), file: " + fileName + ", res: "
			// + res);
			return res;

		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "getStream(): " + e.toString());
		}
		return null;
	}

}
