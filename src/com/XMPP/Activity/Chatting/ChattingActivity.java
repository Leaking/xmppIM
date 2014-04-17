package com.XMPP.Activity.Chatting;

import com.XMPP.R;
import com.XMPP.R.layout;
import com.XMPP.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ChattingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chatting);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chatting, menu);
		return true;
	}

}
