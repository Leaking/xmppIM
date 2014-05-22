package com.XMPP.BroadCast;

import com.XMPP.Activity.ChatRoom.ChatRoomActivity;
import com.XMPP.Activity.Mainview.ChattingFragment;

import android.content.Context;
import android.content.Intent;

public class BroadCastUtil {

	public static final String ACTION_FRESH_CHATROOM_LISTVIEW = "fresh_chatrome_listview";
	public static final String ACTION_FRESH_CHATTING_LISTVIEW = "fresh_chatting_listview";
	
	public static final String ACTION_RECONNECT_RECONNECT = "reconnect_successfully";
	public static void sendBroadCastChatroom(Context context){
		Intent intent = new Intent();
		intent.setAction(ACTION_FRESH_CHATROOM_LISTVIEW);
		context.sendBroadcast(intent);
	}
	public static void sendBroadCastChatting(Context context){
		Intent intent = new Intent();
		intent.setAction(BroadCastUtil.ACTION_FRESH_CHATTING_LISTVIEW);
		context.sendBroadcast(intent);
	}
	public static void sendBroadCastReconnect(Context context){
		Intent intent = new Intent();
		intent.setAction(BroadCastUtil.ACTION_RECONNECT_RECONNECT);
		context.sendBroadcast(intent);
	}
}
