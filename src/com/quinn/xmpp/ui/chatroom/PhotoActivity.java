package com.quinn.xmpp.ui.chatroom;



import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.quinn.xmpp.R;
import com.quinn.xmpp.Intents.Builder;
import com.quinn.xmpp.ui.BaseActivity;

/**
 * 
 * @author Quinn
 * @date 2015-4-29
 */
public class PhotoActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.photo, menu);
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
	
	public static Intent createIntent(){
		Builder builder = new Builder("chatroom.Photo.View");
		return builder.toIntent();
	}
	
	
}
