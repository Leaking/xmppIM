package com.XMPP.Service;

import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.ConnectionListener;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;

import com.XMPP.Activity.Launcher.LoginActivity;
import com.XMPP.BroadCast.BroadCastUtil;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;
import com.XMPP.util.Constants;
import com.XMPP.util.L;
import com.XMPP.util.LoadingDialog;
import com.XMPP.util.T;

public class ReconnectService extends Service {

	private Smack smack;
	private Timer rTask;
	private long delayTime = 2000;


	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		smack = SmackImpl.getInstance();
		smack.addConnectionListener(new mConnectionListern());
		return super.onStartCommand(intent, flags, startId);
	}

	class mConnectionListern implements ConnectionListener {
		LoadingDialog loading;
		@Override
		public void connectionClosed() {
			// TODO Auto-generated method stub
			L.i("---------connectionClosed----------");
		}

		/**
		 * Notification that the connection was closed due to an exception. for
		 * example,you close the wifi or GPRS ,you close the server
		 */
		@Override
		public void connectionClosedOnError(Exception arg0) {
			// TODO Auto-generated method stub
			L.i("---------connectionClosedOnError----------");
			smack.setConnect(false);
			rTask = new Timer();
			rTask.schedule(new ReconnectTask(), delayTime);
		}

		@Override
		public void reconnectingIn(int arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void reconnectionFailed(Exception arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void reconnectionSuccessful() {
			// TODO Auto-generated method stub

		}

	}

	class ReconnectTask extends TimerTask {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			// sooner or later ,the username and password needs to be saved in
			// the sys
			smack.disconnect();

			// TODO Auto-generated method stub
			smack = SmackImpl.getInstance();;
			smack.connect(Constants.SERVER_IP, Constants.SERVER_PORT,Constants.SERVICE);
			String username = smack.getUsername();
			String password = smack.getPassword();
			L.i("try to connect again");

			int result = smack.login(username, password);
			if (result == Constants.LOGIN_SUCCESS) {
				
				BroadCastUtil.sendBroadCastReconnect(ReconnectService.this);
				smack.turnOnlineToAll();
				smack.addConnectionListener(new mConnectionListern());	
				smack.setConnect(true);
				L.i("reconnect successfully");
			} else {
				rTask.schedule(new ReconnectTask(), delayTime);
			}
		}

	}

}
