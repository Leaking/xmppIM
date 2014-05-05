package com.XMPP.Activity.ChatRoom;

import java.util.ArrayList;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.XMPP.R;
import com.XMPP.Activity.Mainview.ChattingFragment;
import com.XMPP.Activity.Mainview.MainviewActivity;
import com.XMPP.Database.RowChatting;
import com.XMPP.Database.RowHistory;
import com.XMPP.Database.TableChatting;
import com.XMPP.Database.TableContacts;
import com.XMPP.Database.TableHistory;
import com.XMPP.Model.BubbleMessage;
import com.XMPP.Model.IconOnTouchListener;
import com.XMPP.Service.MyApplication;
import com.XMPP.smack.ConnectionHandler;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;
import com.XMPP.util.Constants;
import com.XMPP.util.L;
import com.XMPP.util.MessageType;
import com.XMPP.util.SystemUtil;
import com.XMPP.util.T;
import com.XMPP.util.Test;
import com.XMPP.util.TimeUtil;
import com.XMPP.util.ValueUtil;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;
import com.atermenji.android.iconicdroid.icon.FontAwesomeIcon;
import com.atermenji.android.iconicdroid.icon.IconicIcon;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

public class ChatRoomActivity extends FragmentActivity implements
		EmojiconGridFragment.OnEmojiconClickedListener,
		EmojiconsFragment.OnEmojiconBackspaceClickedListener {
	RosterEntry entry;
	String u_JID;
	XMPPConnection conn;
	Smack smack;
	Chat chat;
	TextView room;
	ImageView face;
	ImageView plus;
	EditText input;
	ImageView send;
	LinearLayout rootLayout;
	//
	LinearLayout plusLayout;
	View faceLayout;
	boolean open_plus = false;
	boolean open_face = false;
	//
	ListView bubbleList;
	Plus_appear_Listener plus_appear_listener;
	Plus_disappear_Listener plus_disappear_listener;
	Face_appear_Listener face_appear_listener;
	Face_disappear_Listener face_disappear_listener;
	ArrayList<BubbleMessage> messages;
	BubbleAdapter adapter;
	AdapterRefreshReceiver aReceiver;
	IntentFilter filter;

	//
	private static final String ACTION_FRESH_CHATROOM_LISTVIEW = "fresh_chatrome_listview";
	
	// time
	private static final String format = "MM-dd HH:mm";
	private String pastTimeStr;
	private String nowTimeStr;
	//DB
	TableHistory tableHistory;
	TableChatting tableChatting;
	
	// notify
	String last_uJID;
	String last_Msg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chatroom);
		init();
		bubbleList = (ListView) findViewById(R.id.bubbleList);
		messages = new ArrayList<BubbleMessage>();
		adapter = new BubbleAdapter(this, messages);
		bubbleList.setAdapter(adapter);
		bubbleList.setSelection(messages.size() - 1);
		aReceiver = new AdapterRefreshReceiver();
		filter = new IntentFilter();
		filter.addAction(ChatRoomActivity.ACTION_FRESH_CHATROOM_LISTVIEW);
		registerReceiver(aReceiver, filter);
	}

	

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		// move these operation into a seperate service
		registerReceiver(aReceiver, filter);
		
		if(messages.size() == 0){
			messages = new ArrayList<BubbleMessage>();
			tableHistory = TableHistory.getInstance(this);
			messages = tableHistory.getBubbleList(chat.getParticipant());
			Test.outputMessageBubbleList(messages);
			adapter = new BubbleAdapter(this, messages);
			bubbleList.setAdapter(adapter);	
			bubbleList.setSelection(messages.size() - 1);
		}
		
		
		
	}



	public void init() {
		smack = SmackImpl.getInstance();
		conn = ConnectionHandler.getConnection();
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			u_JID = extras.getString("JID");
			L.i("test the u_JID style " + u_JID);
			entry = conn.getRoster().getEntry(u_JID);
		}
		
		room = (TextView) findViewById(R.id.room_Friend);
		room.setText(smack.getNickname(u_JID));
		face = (ImageView) findViewById(R.id.face);
		plus = (ImageView) findViewById(R.id.plus);
		input = (EditText) findViewById(R.id.input);
		send = (ImageView) findViewById(R.id.send);
		rootLayout = (LinearLayout) findViewById(R.id.root);
		send.setOnClickListener(new Send_Listener());

		plusLayout = getPlusLayout();
		faceLayout = getFaceView();

		IconicFontDrawable icon_face = new IconicFontDrawable(this);
		icon_face.setIcon(FontAwesomeIcon.GITHUB_ALT);
		icon_face.setIconColor(Constants.COLOR_COMMON_BLUE);
		face.setBackground(icon_face);

		IconicFontDrawable icon_plus = new IconicFontDrawable(this);
		icon_plus.setIcon(FontAwesomeIcon.RESIZE_FULL);
		icon_plus.setIconColor(Constants.COLOR_COMMON_BLUE);
		plus.setBackground(icon_plus);

		IconicFontDrawable icon_send = new IconicFontDrawable(this);
		icon_send.setIcon(FontAwesomeIcon.PLAY);
		icon_send.setIconColor(Constants.COLOR_COMMON_BLUE);
		send.setBackground(icon_send);

		face_appear_listener = new Face_appear_Listener();
		face_disappear_listener = new Face_disappear_Listener();
		plus_appear_listener = new Plus_appear_Listener();
		plus_disappear_listener = new Plus_disappear_Listener();

		// input.setOnFocusChangeListener(new EditTextFocusChangeListener());
		input.setOnTouchListener(new EditOnTouchListener());
		input.addTextChangedListener(new ChatTextChangeListener());
		face.setOnTouchListener(new IconOnTouchListener(icon_face, face));
		plus.setOnTouchListener(new IconOnTouchListener(icon_plus, plus));
		face.setOnClickListener(face_appear_listener);
		plus.setOnClickListener(plus_appear_listener);

		//
		receiveMessage();
	}

	public void sendMessage(final Message message) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					chat.sendMessage(message);
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}


	public void receiveMessage() {

		// TODO Auto-generated method stub
		chat = conn.getChatManager().createChat(u_JID, new MessageListener() {

			public void processMessage(Chat chat, Message message) {
				BubbleMessage bubbleMessage = new BubbleMessage();

				if (message.getProperty("TYPE").toString()
						.equals(Constants.MESSAGE_TYPE_TEXT)) {
					//store the data 
					/**
					 * here ,chat.getParticipant() == ,,,,,,@,,,
					 * in the other hand
					 * in the chatlistener,chat.getParticipant() == ,,,,,@,,,/Smack
					 * in order to fix this difference, i delete a "/Smack"  in the chatlistener.
					 */
					String toJID = conn.getUser();
					toJID = ValueUtil.deleteSth(toJID, Constants.DELETE_STH);
					String fromJID = chat.getParticipant();
					fromJID = ValueUtil.deleteSth(fromJID, Constants.DELETE_STH);
					
					String MsgType = Constants.MESSAGE_TYPE_TEXT;
					String strBody = message.getBody();
					String strDate = (String) message.getProperty("TIME");
					RowHistory historyRow = new RowHistory(strDate, strBody, MsgType, fromJID, toJID);
					restoreMessage(historyRow);	
					
					last_uJID = smack.getNickname(last_uJID);
					last_Msg = message.getBody();
					
					//get the string of time to show
					if(pastTimeStr == null)
						pastTimeStr = strDate;
					else {
						pastTimeStr = nowTimeStr;
					}
					nowTimeStr = strDate;
					// add time bubble
					if(TimeUtil.isLongBefore(pastTimeStr, nowTimeStr)){
						String viewTime = TimeUtil.getViewTime(nowTimeStr);
						bubbleMessage = new BubbleMessage(viewTime,
								MessageType.TIME, false);
						messages.add(bubbleMessage);
					}
					// add content bubble
					bubbleMessage = new BubbleMessage(message.getBody(),
							MessageType.TEXT, false);
					messages.add(bubbleMessage);
										
					
					RowChatting chattingRow = new RowChatting(toJID, fromJID, "1", message.getBody(), nowTimeStr);
					tableChatting = TableChatting.getInstance(ChatRoomActivity.this);
					tableChatting.insert_update(chattingRow);

					if(!((MyApplication)getApplication()).isActivityVisible()){
						sendNotify();
					}
			
					

					
				} else if (message.getProperty("TYPE").toString().equals(Constants.MESSAGE_TYPE_FILE)){
					/**
					 * file
					 */
										
				}					
				Intent intent1 = new Intent();
				intent1.setAction(ChattingFragment.ACTION_FRESH_CHATTING_LISTVIEW);
				sendBroadcast(intent1);
				Intent intent2 = new Intent();
				intent2.setAction(ChatRoomActivity.ACTION_FRESH_CHATROOM_LISTVIEW);
				sendBroadcast(intent2);
			}
		});
	}
	class Send_Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//check the input content
			String inputContent = input.getText().toString();
			if(inputContent == null || inputContent.length() == 0){
				T.mToast(ChatRoomActivity.this, "oops, you forget to input something");
				return;
			}else if(inputContent.length() > 300){
				T.mToast(ChatRoomActivity.this, "oops，you input too much");
			}
			//get the string of time
			String strDate = TimeUtil.getCurrentTime2String();
			if(pastTimeStr == null)
				pastTimeStr = strDate;
			else {
				pastTimeStr = nowTimeStr;
			}
			nowTimeStr = strDate;
			//add a bubble
			if(TimeUtil.isLongBefore(pastTimeStr, nowTimeStr)){
				String viewTime = TimeUtil.getCurrentViewTime();
				BubbleMessage bubbleMessageTime = new BubbleMessage(viewTime,
					MessageType.TIME, true);
				messages.add(bubbleMessageTime);
			}
			BubbleMessage bubbleMessageText = new BubbleMessage(inputContent,
					MessageType.TEXT, true);
			messages.add(bubbleMessageText);
			// restore to DB
			String fromJID = conn.getUser();
			String toJID = chat.getParticipant();
			toJID = ValueUtil.deleteSth(toJID, Constants.DELETE_STH);
			fromJID = ValueUtil.deleteSth(fromJID, Constants.DELETE_STH);

			String MsgType = Constants.MESSAGE_TYPE_TEXT;
			String strBody = inputContent;
			RowHistory historyRow = new RowHistory(strDate, strBody, MsgType, fromJID, toJID);
			restoreMessage(historyRow);
						
			//send Message
			Message messageText = new Message();
			messageText.setBody(inputContent);
			messageText.setProperty("TYPE", Constants.MESSAGE_TYPE_TEXT);
			messageText.setProperty("TIME", strDate);			
			sendMessage(messageText);
			
			//send a broadcast to renew the UI
			Intent intent = new Intent();
			intent.setAction(ChatRoomActivity.ACTION_FRESH_CHATROOM_LISTVIEW);
			sendBroadcast(intent);
			//
			input.setText(null);
		}

	}

	public void restoreMessage(final RowHistory historyRow){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				TableHistory.getInstance(ChatRoomActivity.this).insert(historyRow);
			}
		}).start();
	}
	
	class EditOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			close_Face();
			close_Plus();
			return false;
		}

	}
	class Plus_appear_Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			IconicFontDrawable icon_close = new IconicFontDrawable(
					ChatRoomActivity.this);
			icon_close.setIcon(FontAwesomeIcon.RESIZE_SMALL);
			icon_close.setIconColor(Constants.COLOR_COMMON_BLUE);
			plus.setBackground(icon_close);
			plus.setOnTouchListener(new IconOnTouchListener(icon_close, plus));

			SystemUtil.closeInputMethod(ChatRoomActivity.this);
			close_Face();
			open_plus = true;
			rootLayout.addView(plusLayout);
			plus.setOnClickListener(plus_disappear_listener);
		}

	}

	class Plus_disappear_Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			IconicFontDrawable icon_open = new IconicFontDrawable(
					ChatRoomActivity.this);
			icon_open.setIcon(FontAwesomeIcon.RESIZE_FULL);
			icon_open.setIconColor(Constants.COLOR_COMMON_BLUE);
			plus.setBackground(icon_open);
			plus.setOnTouchListener(new IconOnTouchListener(icon_open, plus));

			open_plus = false;
			rootLayout.removeView(plusLayout);
			plus.setOnClickListener(plus_appear_listener);

		}

	}

	class Face_appear_Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			SystemUtil.closeInputMethod(ChatRoomActivity.this);
			close_Plus();
			open_face = true;
			rootLayout.addView(faceLayout);
			face.setOnClickListener(new Face_disappear_Listener());
		}

	}

	class Face_disappear_Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			open_face = false;
			rootLayout.removeView(faceLayout);
			face.setOnClickListener(new Face_appear_Listener());
		}

	}

	public View getFaceView() {
		View faceView = new View(ChatRoomActivity.this);

		faceView = (View) View.inflate(this, R.layout.face_layout, null);
		return faceView;
	}

	public LinearLayout getPlusLayout() {
		LinearLayout footLayout = new LinearLayout(ChatRoomActivity.this);

		int plusHeight = (int) ValueUtil.convertDpToPixel(50,
				ChatRoomActivity.this);

		footLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				plusHeight));
		footLayout.setOrientation(0);
		footLayout.setPadding(20, 20, 20, 20);

		Button plus_picture = new Button(ChatRoomActivity.this);
		Button plus_camera = new Button(ChatRoomActivity.this);
		Button plus_file = new Button(ChatRoomActivity.this);
		Button plus_video = new Button(ChatRoomActivity.this);
		Button plus_locate = new Button(ChatRoomActivity.this);
		int size = (int) ValueUtil.convertDpToPixel(5, ChatRoomActivity.this);
		plus_picture.setLayoutParams(new LayoutParams(size, size));
		plus_camera.setLayoutParams(new LayoutParams(size, size));
		plus_file.setLayoutParams(new LayoutParams(size, size));
		plus_video.setLayoutParams(new LayoutParams(size, size));
		plus_locate.setLayoutParams(new LayoutParams(size, size));

		IconicFontDrawable icon_picture = new IconicFontDrawable(
				ChatRoomActivity.this);
		icon_picture.setIcon(EntypoIcon.PICTURE);
		icon_picture.setIconColor(Constants.COLOR_COMMON_BLUE);
		plus_picture.setBackground(icon_picture);
		plus_picture.setOnTouchListener(new IconOnTouchListener(icon_picture,
				plus_picture));

		IconicFontDrawable icon_camera = new IconicFontDrawable(
				ChatRoomActivity.this);
		icon_camera.setIcon(EntypoIcon.CAMERA);
		icon_camera.setIconColor(Constants.COLOR_COMMON_BLUE);
		plus_camera.setBackground(icon_camera);
		plus_camera.setOnTouchListener(new IconOnTouchListener(icon_camera,
				plus_camera));

		IconicFontDrawable icon_video = new IconicFontDrawable(
				ChatRoomActivity.this);
		icon_video.setIcon(FontAwesomeIcon.FACETIME_VIDEO);
		icon_video.setIconColor(Constants.COLOR_COMMON_BLUE);
		plus_video.setBackground(icon_video);
		plus_video.setOnTouchListener(new IconOnTouchListener(icon_video,
				plus_video));

		IconicFontDrawable icon_file = new IconicFontDrawable(
				ChatRoomActivity.this);
		icon_file.setIcon(FontAwesomeIcon.FOLDER_OPEN);
		icon_file.setIconColor(Constants.COLOR_COMMON_BLUE);
		plus_file.setBackground(icon_file);
		plus_file.setOnTouchListener(new IconOnTouchListener(icon_file,
				plus_file));

		IconicFontDrawable icon_locate = new IconicFontDrawable(
				ChatRoomActivity.this);
		icon_locate.setIcon(IconicIcon.LOCATION);
		icon_locate.setIconColor(Constants.COLOR_COMMON_BLUE);
		plus_locate.setBackground(icon_locate);
		plus_locate.setOnTouchListener(new IconOnTouchListener(icon_locate,
				plus_locate));

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.rightMargin = 7;
		plus_picture.setLayoutParams(layoutParams);
		plus_camera.setLayoutParams(layoutParams);
		plus_file.setLayoutParams(layoutParams);
		plus_video.setLayoutParams(layoutParams);
		plus_locate.setLayoutParams(layoutParams);

		footLayout.addView(plus_picture);
		footLayout.addView(plus_camera);
		footLayout.addView(plus_file);
		footLayout.addView(plus_video);
		footLayout.addView(plus_locate);

		return footLayout;
	}

	public void close_Plus() {
		if (open_plus)
			rootLayout.removeView(plusLayout);
	}

	public void close_Face() {
		if (open_face)
			rootLayout.removeView(faceLayout);
	}

	@Override
	public void onEmojiconBackspaceClicked(View v) {
		// TODO Auto-generated method stub
		EmojiconsFragment.backspace(input);
	}

	@Override
	public void onEmojiconClicked(Emojicon emojicon) {
		// TODO Auto-generated method stub
		EmojiconsFragment.input(input, emojicon);
	}

	class AdapterRefreshReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			L.i("BroadcastReceiver AdapterRefreshReceiver ChatRoomActivity");
			adapter.notifyDataSetChanged();
			bubbleList.setSelection(messages.size() - 1);
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		unregisterReceiver(aReceiver);
		tableChatting = TableChatting.getInstance(this);
		tableChatting.reset(smack.getJID(), u_JID);
		Intent intent = new Intent();
		intent.setAction(ChattingFragment.ACTION_FRESH_CHATTING_LISTVIEW);
		sendBroadcast(intent);
	}
	
	public void sendNotify(){
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(this)
		        .setSmallIcon(R.drawable.channel_qq)
		        .setContentTitle(last_uJID)
		        .setContentText(last_Msg);
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(this, MainviewActivity.class);

		// The stack builder object will contain an artificial back stack for the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(MainviewActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(1, mBuilder.build());
	}

}
