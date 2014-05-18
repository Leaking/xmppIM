package com.XMPP.Activity.Launcher;

import com.XMPP.R;
import com.XMPP.R.layout;
import com.XMPP.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SignUpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

}
