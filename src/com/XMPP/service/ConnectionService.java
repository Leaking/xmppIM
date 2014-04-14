package com.XMPP.service;

import org.jivesoftware.smack.XMPPConnection;

import com.XMPP.smack.ConnectionHandler;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class ConnectionService extends Service{
    private XMPPConnection conn;
	private MyBinder myBinder = new MyBinder();
	public class MyBinder extends Binder{
		//get the XMPP Connection
		public XMPPConnection getConnection(){
			return conn;
		}
		
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		// i can initial some data from the intent arg0, like server name,port
		
		return myBinder;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				conn = ConnectionHandler.connect();
			}
		}).start();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

	

}
