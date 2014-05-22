package com.XMPP.Activity.Launcher;

import com.XMPP.R;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class LoginListener implements OnClickListener {
	Activity rootView;

	public LoginListener(Activity rootView) {
		this.rootView = rootView;
	}

	public void add(View view) {
		view.setOnClickListener(this);
	}

	public void onClick(View v) {

		// TODO Auto-generated method stub
		if (v.getId() == R.id.Login_loginButton) {
			Intent intent = new Intent(rootView, LoginActivity.class);
			rootView.startActivity(intent);
		}
		if(v.getId() == R.id.Login_SignUpButton){
			Intent intent = new Intent(rootView, SignUpActivity.class);
			rootView.startActivity(intent);
		}
	}

}
