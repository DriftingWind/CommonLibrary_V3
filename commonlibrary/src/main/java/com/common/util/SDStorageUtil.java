package com.common.util;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

public class SDStorageUtil {
	private static final int ERROR = -1;
	public static int save_dir = 1;
	//应用所需sd卡最小空间
	public static final int FREE_SD_SPACE_NEEDED_MIN = 20;

	// 判断是否已经安装SD卡
	public static boolean isSDCardExist() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}

	// 内存剩余空间
	public static long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	// 内存总空间
	public static long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}

	// SD卡剩余空间
	public static long getAvailableExternalMemorySize() {
		if (isSDCardExist()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long availableBlocks = stat.getAvailableBlocks();
			return availableBlocks * blockSize;
		} else {
			return ERROR;
		}
	}

	private static final int MB = 1024 * 1024;

	/** 计算sdcard上的剩余空间 **/
	public static int freeSpaceOnSd() {
		StatFs stat = new StatFs(Environment.getExternalStorageDirectory()
				.getPath());
		double sdFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
				.getBlockSize()) / MB;
		return (int) sdFreeMB;
	}

	// SD卡总空间
	public static long getTotalExternalMemorySize() {
		if (isSDCardExist()) {
			File path = Environment.getExternalStorageDirectory();
			StatFs stat = new StatFs(path.getPath());
			long blockSize = stat.getBlockSize();
			long totalBlocks = stat.getBlockCount();
			return totalBlocks * blockSize;
		} else {
			return ERROR;
		}
	}

	// 判断SD卡下external_sd文件夹的总大小
	public static long getTotalExternal_SDMemorySize() {
		if (isSDCardExist()) {
			File path = Environment.getExternalStorageDirectory();
			File externalSD = new File(path.getPath() + "/external_sd");
			if (externalSD.exists() && externalSD.isDirectory()) {
				StatFs stat = new StatFs(path.getPath() + "/external_sd");
				long blockSize = stat.getBlockSize();
				long totalBlocks = stat.getBlockCount();
				if (getTotalExternalMemorySize() != -1
						&& getTotalExternalMemorySize() != totalBlocks
								* blockSize) {
					return totalBlocks * blockSize;
				} else {
					return ERROR;
				}
			} else {
				return ERROR;
			}

		} else {
			return ERROR;
		}
	}

	// 判断SD卡下external_sd文件夹的可用大小
	public static long getAvailableExternal_SDMemorySize() {
		if (isSDCardExist()) {
			File path = Environment.getExternalStorageDirectory();
			File externalSD = new File(path.getPath() + "/external_sd");
			if (externalSD.exists() && externalSD.isDirectory()) {
				StatFs stat = new StatFs(path.getPath() + "/external_sd");
				long blockSize = stat.getBlockSize();
				long availableBlocks = stat.getAvailableBlocks();
				if (getAvailableExternalMemorySize() != -1
						&& getAvailableExternalMemorySize() != availableBlocks
								* blockSize) {
					return availableBlocks * blockSize;
				} else {
					return ERROR;
				}

			} else {
				return ERROR;
			}

		} else {
			return ERROR;
		}
	}
	
	 /**
     * 判断SD卡是否可用
     */
    public static boolean isSDcardOK() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡跟路径。SD卡不可用时，返回null
     */
    public static String getSDcardRoot() {
        if (isSDcardOK()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }

        return null;
    }

   
}