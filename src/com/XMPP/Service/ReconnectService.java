package com.XMPP.Service;

import java.util.Timer;
import java.util.TimerTask;

import org.jivesoftware.smack.ConnectionListener;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.XMPP.R;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;
import com.XMPP.util.Constants;
import com.XMPP.util.L;

public class ReconnectService extends Service {

	private Smack smack;
	private Timer rTask;
	private long delayTime = 2000;
	private int repeatMaxConnectTimes = 7;
	private int repeat = 0;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		smack = new SmackImpl();
		smack.addConnectionListener(new mConnectionListern());
		return super.onStartCommand(intent, flags, startId);
	}

	class mConnectionListern implements ConnectionListener {

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
			String server = Constants.SERVER_IP;
			int port = Constants.SERVER_PORT;

			// TODO Auto-generated method stub
			smack = new SmackImpl();
			smack.connect(Constants.SERVER_IP, Constants.SERVER_PORT);
			L.i("here---re--connect------authenticated "
					+ smack.getConnection().isAuthenticated());
			String username = "test2";
			String password = "123456";
			L.i("try to connect again");
			++repeat;
			if (repeat >= repeatMaxConnectTimes) {
				System.out.println(" reconnect fail ");
				/**
				 * talk the user to open the wifi or check if the server works.
				 */
				return;

			}
			int result = smack.login(username, password);
			L.i("resule  " + result);
			if (result == Constants.LOGIN_SUCCESS) {
				L.i("here---re--login------authenticated "
						+ smack.getConnection().isAuthenticated());
				repeat = 0;
				smack.turnOnlineToAll();

				smack.addConnectionListener(new mConnectionListern());	
				L.i("reconnect successfully");
			} else {
				rTask.schedule(new ReconnectTask(), delayTime);
			}
		}

	}

}
