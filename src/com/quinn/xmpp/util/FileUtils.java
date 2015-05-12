package com.quinn.xmpp.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

/**
 * @author Quinn
 * @date 2015-3-28
 */
public class FileUtils {

	private final static String AVATAR_PATH = "avatarCache";
	private final static long _30_MB =  30 * 1024 * 1024;


	/**
	 * Generate a image file path according to the current tiemstamp
	 * 
	 * @param context
	 * @return
	 */
	public static String generateImagePath(Context context) {
		File file = getAvatarCacheDir(context);

		String avatarImagePath = file.getPath() + File.separator
				+ generateImageNameByTimeStamp();
		return avatarImagePath;
	}

	/**
	 * Generate a image file according to the current tiemstamp
	 * 
	 * @param context
	 * @return
	 */
	public static File generateImageFile(Context context) {
		String avatarImagePath = generateImagePath(context);
		return new File(avatarImagePath);
	}

	/**
	 * Get Disk Cache Directory SDCard/Android/data/<app-name>/caches/
	 * 
	 * @param context
	 * @return
	 */
	public static File getAvatarCacheDir(Context context) {
		String cachePath;
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable()) {
			cachePath = context.getExternalCacheDir().getPath();
		} else {
			cachePath = context.getCacheDir().getPath();
		}
		File file = new File(cachePath + File.separator + AVATAR_PATH);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * generate a picture file name according to the current timeStamp
	 * 
	 * @return
	 */
	private static String generateImageNameByTimeStamp() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".png";
	}

	/**
	 * Get file path from its uri
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getPathFromUri(Context context, Uri uri) {

		if ("content".equalsIgnoreCase(uri.getScheme())) {
			String[] projection = { "_data" };
			Cursor cursor = null;

			try {
				cursor = context.getContentResolver().query(uri, projection,
						null, null, null);
				int column_index = cursor.getColumnIndexOrThrow("_data");
				if (cursor.moveToFirst()) {
					return cursor.getString(column_index);
				}
			} catch (Exception e) {
				// Eat it
			}
		}

		else if ("file".equalsIgnoreCase(uri.getScheme())) {
			return uri.getPath();
		}

		return null;
	}
	
	/**
	 * 获取文件大小
	 * @param file
	 * @return
	 */
	public static String getFileSizeString(File file) {

		long fileLength = file.length();
		if (fileLength < 1024)
			return fileLength + "B";
		else {
			long fileSize = fileLength / 1024;
			if (fileSize < 1024)
				return fileSize + "KB";
			else
				return fileSize / 1024 + "MB";
		}
	}
	
	/**
	 * 检查文件是否超过指定大小，只能发送30M之内的文件
	 * @param file
	 * @return
	 */
	public static boolean isSendableFile(File file){
		long fileLength = file.length();
		return fileLength <= _30_MB;
	}
	

}
