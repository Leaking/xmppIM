package quinn.xmpp.utils;

import quinn.xmpp.activity.chatroom.ChatRoomActivity;
import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;


public class SystemUtil {
	public static void closeInputMethod(Context context) {
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(((Activity) context)
				.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}
}
