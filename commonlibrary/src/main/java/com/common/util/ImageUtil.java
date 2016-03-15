package com.common.util;

public class ImageUtil {
	/** 判断该文件是否是一个图片。 */
	public static boolean isImage(String fileName) {
		return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")
				|| fileName.endsWith(".png");
	}
}
