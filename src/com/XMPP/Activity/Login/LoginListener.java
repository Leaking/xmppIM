package com.XMPP.Activity.Login;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;


public class LoginListener implements OnClickListener {
	Activity rootView;
	
	public LoginListener(Activity rootView){
		this.rootView = rootView;
	}
	public void add(View view){
		view.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		
		// TODO Auto-generated method stub
		System.out.println("dfdf");
		Intent intent = new Intent(rootView,LoginActivity.class);
		rootView.startActivity(intent);
	}
	
}
