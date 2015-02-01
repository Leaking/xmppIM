package com.quinn.xmpp.ui.launch;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.quinn.xmpp.Intents.Builder;
import com.quinn.xmpp.R;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.widget.CleanableEditText;
import com.quinn.xmpp.ui.widget.ClearableAutoCompleteTextView;

public class SignUpActivity extends BaseActivity {

	
	
	private ClearableAutoCompleteTextView accountView;
	private CleanableEditText passwordText;
	private CleanableEditText repeatPasswordText;
	private Button signUp;
	private String account;
	private String password;
	private String repeatPassword;

	
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
		Builder builder = new Builder("launch.SignUp.View");
		return builder.toIntent();
	}
	
}
