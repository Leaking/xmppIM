package com.XMPP.Service;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.XMPP.smack.ConnectionHandler;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;
import com.XMPP.util.L;
import com.XMPP.util.Test;

public class ChatroomService extends Service {

	Smack smack;
	Chat chat;
	String jid;
	// Binder given to clients
	private final IBinder mBinder = new LocalBinder();

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		Test.outputCertainString("to here", "ChattroomService onBind");
		L.d("service onBind line 34");

		return mBinder;
	}

	public class LocalBinder extends Binder {
		public ChatroomService getService() {
			// Return this instance of LocalService so clients can call public
			// methods
			return ChatroomService.this;
		}
	}

	public Chat getChat(String jid) {
		this.jid = jid;
		Test.outputCertainString("test 2 the chatroom JID", jid);

		// TODO Auto-generated method stub
		Test.outputCertainString("test the chatroom JID", jid);
		smack = new SmackImpl();
		smack.setConnection(ConnectionHandler.getConnection());
		chat = smack.getConnection().getChatManager()
				.createChat(jid, new MessageListener() {

					public void processMessage(Chat chat, Message message) {
						// Print out any messages we get back to
						// standard out.
						System.out.println("Received message: " + message);
						try {
							chat.sendMessage("Howdy!");
						} catch (XMPPException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		return this.chat;
	}

}
