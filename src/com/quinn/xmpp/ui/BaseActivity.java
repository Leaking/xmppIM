package com.quinn.xmpp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.quinn.xmpp.App;
import com.quinn.xmpp.Smack;


/**
 * 
 * @author Quinn
 * @date 2015-1-27
 */
public class BaseActivity extends FragmentActivity{
	
	protected App app;
	protected Smack smack;
	
	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		if(app == null)
			app = (App) getApplication();
		if(smack == null)
			smack = app.getSmack();
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		app.appAppear();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		app.appAppear();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
}