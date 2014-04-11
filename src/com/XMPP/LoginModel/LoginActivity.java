package com.XMPP.LoginModel;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.XMPP.R;
import com.XMPP.mainview.MainviewActivity;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;

public class LoginActivity extends Activity implements OnClickListener {

	private String username;
	private String password;
	private TextView submitLogin;
	private TextView forget;
	private Smack smack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		init();

	}

	public void init() {
		smack = new SmackImpl();
		submitLogin = (TextView) findViewById(R.id.submitLogin);
		forget = (TextView) findViewById(R.id.forget);

		submitLogin.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.submitLogin:
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					username = ((EditText) findViewById(R.id.username))
							.getText().toString();
					password = ((EditText) findViewById(R.id.password))
							.getText().toString();
//					boolean success = smack.login(username, password);
//					smack.turnOnlineToAll();
					boolean success = true;
					if (success) {
						Intent intent = new Intent(LoginActivity.this,
								MainviewActivity.class);
						LoginActivity.this.startActivity(intent);
					}else{
						Toast.makeText(getApplicationContext(), "登陆失败",
							     Toast.LENGTH_SHORT).show();
					}
				}
			}).start();
			break;
		case R.id.forget:

			break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
