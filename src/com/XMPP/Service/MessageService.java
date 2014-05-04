package com.XMPP.Service;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.XMPP.Database.RowHistory;
import com.XMPP.Database.TableHistory;
import com.XMPP.smack.ConnectionHandler;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;
import com.XMPP.util.Constants;
import com.XMPP.util.L;
import com.XMPP.util.ValueUtil;

public class MessageService extends Service {

	Smack smack;
	TableHistory tableHistory;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		L.i("come into message service onStartCommand  1 ");
		smack = SmackImpl.getInstance();
		tableHistory = TableHistory.getInstance(this);
		L.i("come into message service onStartCommand 2");
		new Thread(new Runnable() {
			@Override
			public void run() {
				listenIncomeMessage();			
			}
		}).start();
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	public void listenIncomeMessage(){
		final XMPPConnection conn = ConnectionHandler.getConnection();
		conn.getChatManager().addChatListener(
				new ChatManagerListener(){
					@Override
					public void chatCreated(Chat chat, boolean createLocally) {
						// TODO Auto-generated method stub
						if(!createLocally){
							L.i("chat create remote");
							chat.addMessageListener(new MessageListener(){
								/**
								 * in the ChatRoomAcitivity,where chat is built.chat.getParticipant() == ,,,,,,@,,,
								 * but here
								 * chatlistener,chat.getParticipant() == ,,,,,@,,,/Smack
								 * in order to fix this difference, i delete a "/Smack"  here.
								 */
								@Override
								public void processMessage(Chat chat,
										Message message) {
									String fromJID = chat.getParticipant();
									fromJID = ValueUtil.deleteSth(fromJID, Constants.DELETE_STH);
									String toJID = conn.getUser();
									toJID = ValueUtil.deleteSth(toJID, Constants.DELETE_STH);

									String messageType = Constants.MESSAGE_TYPE_TEXT;
									String messageContent = message.getBody();
									String messageTime = (String) message.getProperty("TIME");
									RowHistory historyRow = new RowHistory(messageTime, messageContent, messageType, fromJID, toJID);
									tableHistory.insert(historyRow);
								}							
							});;					
						}else{
							L.i("chat create  locale");
						}
					}
				}				
				);
	}
	
}
