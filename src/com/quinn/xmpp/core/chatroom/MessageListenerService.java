package com.quinn.xmpp.core.chatroom;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.quinn.xmpp.App;
import com.quinn.xmpp.smack.Smack;
import com.quinn.xmpp.util.LogcatUtils;

/**
 * @author Quinn
 * @date 2015-3-30
 */
public class MessageListenerService extends Service {

	private Smack smack;
	private XMPPConnection connection;
	private ChatManager chatManager;

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("MessageListenerService onCreate");
		
		smack = ((App) getApplication()).getSmack();
		connection = smack.getConnection();
		chatManager = connection.getChatManager();
	}

	private class MessageListenThread extends Thread {

		@Override
		public void run() {
			listenChatMessage();
		}
	}

	public void listenChatMessage() {
		chatManager.addChatListener(new ChatManagerListener() {

			@Override
			public void chatCreated(Chat chat, boolean createLocally) {
				chat.addMessageListener(new MessageListener() {

					@Override
					public void processMessage(Chat chat, Message message) {
						LogcatUtils.v("MessageListenerService Received message: " + message);
					}
					
				});
			}
		});

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("MessageListenerService onStartCommand  ");
		//new MessageListenThread().start();
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

}
