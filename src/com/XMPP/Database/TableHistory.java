package com.XMPP.Database;

import java.util.ArrayList;

import com.XMPP.Model.BubbleMessage;
import com.XMPP.smack.SmackImpl;
import com.XMPP.util.Constants;
import com.XMPP.util.L;
import com.XMPP.util.MessageType;
import com.XMPP.util.TimeUtil;
import com.XMPP.util.ValueUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class TableHistory {
	private SQLiteDatabase db;

	private TableHistory(Context context) {
		this.db = XMPPSQLiteOpenHelper.getInstance(context);
	}

	public static TableHistory getInstance(Context context) {
		return new TableHistory(context);
	}

	public void insert(RowHistory row) {
		db.execSQL(
				"insert into " + XMPPSQLiteOpenHelper.TABLE_HISTORY
						+ " values(null,?,?,?,?,?)",
				new String[] { row.getMessageTime(), row.getMessageContent(),
						row.getMessageType(), row.getFromJID(), row.getToJID() });
	}

	public ArrayList<RowHistory> select(String jid) {
		jid = ValueUtil.deleteSth(jid, Constants.DELETE_STH);
		String currentUserJID = SmackImpl.getInstance().getConnection().getUser();
		currentUserJID = ValueUtil.deleteSth(currentUserJID, Constants.DELETE_STH);
		ArrayList<RowHistory> rows = new ArrayList<RowHistory>();
		String sql = "select *from " + XMPPSQLiteOpenHelper.TABLE_HISTORY
				+ " where " + "(" + XMPPSQLiteOpenHelper.COLUMN_FROM_JID
				+ " = '" + currentUserJID + "' and " + XMPPSQLiteOpenHelper.COLUMN_TO_JID
				+ " = '" + jid + "') " + "or ("
				+ XMPPSQLiteOpenHelper.COLUMN_FROM_JID + " = '" + jid
				+ "' and " + XMPPSQLiteOpenHelper.COLUMN_TO_JID + " = '" + currentUserJID
				+ "') ";
		L.i("sql " + sql);
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			L.i("sql result");
			String time = cursor.getString(cursor
					.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_TIME));
			String content = cursor.getString(cursor
					.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_CONTENT));
			String type = cursor.getString(cursor
					.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_TYPE));
			String fromJID = cursor.getString(cursor
					.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_FROM_JID));
			String toJID = cursor.getString(cursor
					.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_TO_JID));
			RowHistory row = new RowHistory(time, content, type, fromJID, toJID);
			rows.add(row);
		}
		return rows;
	}

	public ArrayList<BubbleMessage> getBubbleList(String JID) {
		ArrayList<BubbleMessage> bubbleList = new ArrayList<BubbleMessage>();
		ArrayList<RowHistory> historyList = select(JID);
		String rightUser = SmackImpl.getInstance().getConnection().getUser();
		rightUser = ValueUtil.deleteSth(rightUser, Constants.DELETE_STH);
		boolean isMine = false;
		for (int i = 0; i < historyList.size(); i++) {
			RowHistory history = historyList.get(i);
			if (history.getMessageType().equals(Constants.MESSAGE_TYPE_TEXT)) {
				
				if (rightUser.equals(history.getFromJID()))
					isMine = true;
				else
					isMine = false;
				String viewTime = TimeUtil.getViewTime(history.getMessageTime());
				if(i == 0){
					BubbleMessage bubbleMessageTime = new BubbleMessage(
							viewTime, MessageType.TIME, isMine);
					bubbleList.add(bubbleMessageTime);
				}
				if(i != 0){
					boolean longBefore = TimeUtil.isLongBefore(historyList.get(i - 1).getMessageTime(), history.getMessageTime());
					if(longBefore){
						BubbleMessage bubbleMessageTime = new BubbleMessage(
								viewTime, MessageType.TIME, isMine);
						bubbleList.add(bubbleMessageTime);
					}	
				}			
				BubbleMessage bubbleMessageText = new BubbleMessage(
						history.getMessageContent(), MessageType.TEXT, isMine);
				bubbleList.add(bubbleMessageText);
			}
		}

		return bubbleList;

	}

}
