package com.XMPP.Service;

import android.app.Application;

public class MyApplication extends Application {

	public MyApplication(){
		
	}
	
	public static MyApplication getInstance(){
		return new MyApplication();
	}
	private static boolean appVisible;

	public static boolean isActivityVisible() {
		return appVisible;
	}

	public static void appAppear() {
		appVisible = true;
	}

	public static void appDisappear() {
		appVisible = false;
	}

}
