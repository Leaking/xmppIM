package com.XMPP.Service;

import java.util.Date;
import java.util.Iterator;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smackx.OfflineMessageManager;
import org.jivesoftware.smackx.packet.DelayInformation;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.XMPP.R;
import com.XMPP.Activity.Mainview.ChattingFragment;
import com.XMPP.Activity.Mainview.MainviewActivity;
import com.XMPP.Database.RowChatting;
import com.XMPP.Database.RowHistory;
import com.XMPP.Database.TableChatting;
import com.XMPP.Database.TableHistory;
import com.XMPP.smack.ConnectionHandler;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;
import com.XMPP.util.CircleImage;
import com.XMPP.util.Constants;
import com.XMPP.util.L;
import com.XMPP.util.TimeUtil;
import com.XMPP.util.ValueUtil;

public class MessageService extends Service {

	Smack smack;
	TableHistory tableHistory;
	TableChatting tableChatting;
	String last_uJID;
	String last_Msg;

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
		tableChatting = TableChatting.getInstance(this);
		L.i("come into message service onStartCommand 2");
		new Thread(new Runnable() {
			@Override
			public void run() {
				fetchOfflineMessage();
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

	
	public String getOffLineMsgTime(Message message){
		DelayInformation inf = null;
		try {
			inf = (DelayInformation)message.getExtension("x","jabber:x:delay");
		} catch (Exception e) {
			
		}
		// get offline message timestamp
		Date date;
		date = inf.getStamp();
		L.i("offline message time " + date.toString());
		return TimeUtil.getCertainTime2String(date);
	}
		
	public void fetchOfflineMessage() {
		OfflineMessageManager offlineManager = new OfflineMessageManager(
				smack.getConnection());
		try {
			Iterator<Message> it = offlineManager.getMessages();

			while (it.hasNext()) {
				Message message = it.next();
				L.i("offline message from " + message.getFrom());
				L.i("offline message body " + message.getBody());
									
				String msgTime = getOffLineMsgTime(message);
				String fromJID = ValueUtil.deleteSth(message.getFrom(), "");
				String toJID = smack.getJID();
				String messageType = Constants.MESSAGE_TYPE_TEXT;
				String messageContent = message.getBody();
				
				RowHistory historyRow = new RowHistory(msgTime,
						message.getBody(), messageType, fromJID, toJID);
				tableHistory.insert(historyRow);	
				
				
				RowChatting chattingRow = new RowChatting(toJID,
						fromJID, "1", messageContent, msgTime);
				tableChatting.insert_update(chattingRow);

				Intent intent = new Intent();
				intent.setAction(ChattingFragment.ACTION_FRESH_CHATTING_LISTVIEW);
				sendBroadcast(intent);
				
			}
			
			
			
			offlineManager.deleteMessages();				
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		Presence presence = new Presence(Presence.Type.available);// 姝ゆ椂鍐嶄笂鎶ョ敤鎴风姸鎬�
		smack.getConnection().sendPacket(presence);

	}

	public void listenIncomeMessage() {
		final XMPPConnection conn = ConnectionHandler.getConnection();
		conn.getChatManager().addChatListener(new ChatManagerListener() {
			@Override
			public void chatCreated(Chat chat, boolean createLocally) {
				// TODO Auto-generated method stub
				if (!createLocally) {
					L.i("chat create remote");
					chat.addMessageListener(new MessageListener() {
						/**
						 * in the ChatRoomAcitivity,where chat is
						 * built.chat.getParticipant() == ,,,,,,@,,, but here
						 * chatlistener,chat.getParticipant() == ,,,,,@,,,/Smack
						 * in order to fix this difference, i delete a "/Smack"
						 * here.
						 */
						@Override
						public void processMessage(Chat chat, Message message) {
							String fromJID = chat.getParticipant();
							last_uJID = fromJID;
							fromJID = ValueUtil.deleteSth(fromJID,
									Constants.DELETE_STH);
							String toJID = conn.getUser();
							toJID = ValueUtil.deleteSth(toJID,
									Constants.DELETE_STH);

							String messageType = Constants.MESSAGE_TYPE_TEXT;
							String messageContent = message.getBody();
							last_Msg = messageContent;
							String messageTime = TimeUtil.getCurrentTime2String();
							RowHistory historyRow = new RowHistory(messageTime,
									messageContent, messageType, fromJID, toJID);
							tableHistory.insert(historyRow);

							RowChatting chattingRow = new RowChatting(toJID,
									fromJID, "1", messageContent, messageTime);
							tableChatting.insert_update(chattingRow);

							
							if (!((MyApplication) getApplication())
									.isActivityVisible()) {
								sendNotify();
							}else{
								Intent intent1 = new Intent();
								intent1.setAction(ChattingFragment.ACTION_FRESH_CHATTING_LISTVIEW);
								sendBroadcast(intent1);
							}

						}
					});
					;
				} else {
					L.i("chat create  locale");
				}
			}
		});
	}

	public void sendNotify() {
		Bitmap circleBitmap = CircleImage.toRoundBitmap(BitmapFactory
				.decodeResource(getResources(), R.drawable.channel_qq), true);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setLargeIcon(circleBitmap)
				.setSmallIcon(R.drawable.channel_qq).setContentTitle(last_uJID)
				.setContentText(last_Msg);
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MainviewActivity.class);

		// The stack builder object will contain an artificial back stack for
		// the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainviewActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(1, mBuilder.build());
	}

}
