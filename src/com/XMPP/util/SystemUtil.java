package com.XMPP.util;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.XMPP.Activity.ChatRoom.ChatRoomActivity;

public class SystemUtil {
	public static void closeInputMethod(Context context) {
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(((Activity) context)
				.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
