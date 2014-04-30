package com.XMPP.Activity.ChatRoom;



import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

import com.XMPP.R;

public class ChatRoomActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chatroom);
	}



}
