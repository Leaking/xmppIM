package com.quinn.xmpp.ui.drawer;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.quinn.xmpp.R;
import com.quinn.xmpp.R.id;
import com.quinn.xmpp.R.layout;
import com.quinn.xmpp.R.menu;
import com.quinn.xmpp.ui.BaseActivity;
public class UserInfoActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_info, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
