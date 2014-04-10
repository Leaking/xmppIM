package com.XMPP.LoginModel;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;


public class LoginListener implements OnClickListener {
	FragmentActivity rootView;
	
	public LoginListener(FragmentActivity rootView){
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
