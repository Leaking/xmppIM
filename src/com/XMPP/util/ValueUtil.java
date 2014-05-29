package com.XMPP.util;

import java.io.File;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.XMPP.smack.ConnectionHandler;

public class ValueUtil {
	public static String getItemName(String entryName) {
		String name = new String();
		if (entryName.contains("@") == false)
			return entryName;
		else {
			name = entryName.substring(0, entryName.indexOf("@"));
			return name;
		}
	}

	public static String getID(String name) {
		String jid = new String();
		String host = ConnectionHandler.getConnection().getServiceName();
		jid = name + "@" + host;
		L.i("host jid " + jid);
		return jid;
	}

	public static String getFileSize(File f) {

		long fileLength = f.length();
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

	public static String getFileSize(long fileLength) {
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

	public static String deleteSth(String string, String sth) {
		if (string.indexOf("/") > 0)
			return string.substring(0, string.indexOf("/"));
		else
			return string;
	}

	/**
	 * This method converts dp unit to equivalent pixels, depending on device
	 * density.
	 * 
	 * @param dp
	 *            A value in dp (density independent pixels) unit. Which we need
	 *            to convert into pixels
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent px equivalent to dp depending on
	 *         device density
	 */
	public static float convertDpToPixel(float dp, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	/**
	 * This method converts device specific pixels to density independent
	 * pixels.
	 * 
	 * @param px
	 *            A value in px (pixels) unit. Which we need to convert into db
	 * @param context
	 *            Context to get resources and device specific display metrics
	 * @return A float value to represent dp equivalent to px value
	 */
	public static float convertPixelsToDp(float px, Context context) {
		Resources resources = context.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return dp;
	}
}
