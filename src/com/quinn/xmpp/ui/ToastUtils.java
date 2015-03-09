/**
 * 2015-2-2
 * 2015-2-2
 */
package com.quinn.xmpp.ui;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Quinn
 * @date 2015-2-2
 */
public class ToastUtils {
	
	
	public static void toast(Context cont, String msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_LONG).show();
	}

	public static void toast(Context cont, int msg) {
		Toast.makeText(cont, msg, Toast.LENGTH_LONG).show();
	}

	public static void toast(Context cont, String msg, int time) {
		Toast.makeText(cont, msg, time).show();
	}
}
