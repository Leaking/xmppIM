package com.XMPP.util;

import com.XMPP.Activity.Mainview.ContactsFragment;

import android.content.Context;
import android.widget.Toast;

public class T {
	public static void mToast(Context context,String content){
		Toast.makeText(
				context,
				content,
				Toast.LENGTH_SHORT).show();
	}
}
