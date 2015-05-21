package com.quinn.xmpp.core.chatroom;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.filetransfer.FileTransferListener;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.quinn.xmpp.App;
import com.quinn.xmpp.smack.Smack;
import com.quinn.xmpp.ui.chatroom.FileDataItem;
import com.quinn.xmpp.util.FileUtils;
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
		smack = ((App) getApplication()).getSmack();
		connection = smack.getConnection();
		chatManager = connection.getChatManager();
	}

	private class MessageListenThread extends Thread {

		@Override
		public void run() {
			listenChatMessage();
			listenFileMessage();
		}
	}
	
	public void listenFileMessage(){
		final FileTransferManager manager = new FileTransferManager(connection);
		manager.addFileTransferListener(new FileTransferListener() {
			public void fileTransferRequest(FileTransferRequest request) {
				LogcatUtils.i("receive file request " + request);
				FileDataItem dataitem = FileDataItem.generatteFromRequest(request);
				//request.getRequestor();
//			if(true) {
//				IncomingFileTransfer transfer = request.accept();
//				try {
//					transfer.recieveFile(new File("shakespeare_complete_works.txt"));
//				} catch (XMPPException e) {
//					e.printStackTrace();
//				}
//			} else {
//				request.reject();
//			}
		}
		});
	}

	public void listenChatMessage() {
		chatManager.addChatListener(new ChatManagerListener() {

			@Override
			public void chatCreated(Chat chat, boolean createLocally) {
				LogcatUtils.i("createLocally = " + createLocally);
				if(createLocally)
					return;
				chat.addMessageListener(new MessageListener() {

					@Override
					public void processMessage(Chat chat, Message message) {
						LogcatUtils.v("MessageListenerService Received message: " + message.getBody());
					}
					
				});
			}
		});

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LogcatUtils.i("启动MessageListenerService");
		new MessageListenThread().start();
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
