package com.XMPP.service;

import com.XMPP.service.LoginService.LocalBinder;
import com.XMPP.smack.ConnectionHandler;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class Group_FriendService extends Service{

	private Smack smack;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		smack = new SmackImpl();
		smack.setConnection(ConnectionHandler.getConnection());
	}

	// Binder given to clients
	private final IBinder mBinder = new LocalBinder();
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public class LocalBinder extends Binder {
		public Group_FriendService getService() {
			// Return this instance of LocalService so clients can call public
			// methods
			return Group_FriendService.this;
		}
	}
	
}
