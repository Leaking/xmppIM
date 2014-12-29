package quinn.xmpp.service;

import android.app.Application;

public class MyApplication extends Application {

	public MyApplication(){
		
	}
	
	public static MyApplication getInstance(){
		return new MyApplication();
	}
	private static boolean appVisible = true;

	public boolean isActivityVisible() {
		return appVisible;
	}

	public void appAppear() {
		appVisible = true;
	}

	public static void appDisappear() {
		appVisible = false;
	}

}
