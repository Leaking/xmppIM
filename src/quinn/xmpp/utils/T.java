package quinn.xmpp.utils;

import quinn.xmpp.activity.mainview.ContactsFragment;

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
