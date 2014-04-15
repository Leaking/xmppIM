package com.XMPP.service;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.RosterGroup;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.XMPP.smack.ConnectionHandler;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;
import com.XMPP.util.L;

public class Group_FriendService extends Service{

	private Smack smack;
	private ArrayList<RosterGroup> groupList;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		L.d("service onCreate line 26");

	}

	// Binder given to clients
	private final IBinder mBinder = new LocalBinder();
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		L.d("service onBind line 34");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				smack = new SmackImpl();
				smack.setConnection(ConnectionHandler.getConnection());
				groupList = smack.getGroups();
			}
		}).start();
		
		return mBinder;
	}
	
	public class LocalBinder extends Binder {
		public Group_FriendService getService() {
			// Return this instance of LocalService so clients can call public
			// methods
			return Group_FriendService.this;
		}
	}
	
	/** method for clients */
	
	//return all the groups
	public ArrayList<RosterGroup> getGroupList(){
		return groupList;
	}
	
}
