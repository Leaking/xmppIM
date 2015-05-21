//package com.quinn.xmpp.orm;
//
//import java.util.ArrayList;
//
//import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
//
//import com.XMPP.Activity.ChatRoom.AsyncTaskContants;
//import com.XMPP.Model.BubbleMessage;
//import com.XMPP.smack.SmackImpl;
//import com.XMPP.util.Constants;
//import com.XMPP.util.L;
//import com.XMPP.util.MessageType;
//import com.XMPP.util.TimeUtil;
//import com.XMPP.util.ValueUtil;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//
//public class TableHistory {
//	private SQLiteDatabase db;
//
//	// TABLE_HISTORY
//	public static final String TABLE_HISTORY = "table_history";
//	public static final String COLUMN_TYPE_TIME = "messageTime TEXT,";
//	public static final String COLUMN_TYPE_CONTENT = "messageContent TEXT,";
//	public static final String COLUMN_TYPE_TYPE = "messageType TEXT,";
//	public static final String COLUMN_TYPE_FROM_JID = "fromJID TEXT,";
//	public static final String COLUMN_TYPE_TO_JID = "toJID TEXT)";
//	public static final String COLUMN_TIME = "messageTime";
//	public static final String COLUMN_CONTENT = "messageContent";
//	public static final String COLUMN_TYPE = "messageType";
//	public static final String COLUMN_FROM_JID = "fromJID";
//	public static final String COLUMN_TO_JID = "toJID";
//
//	public static final String CREATE_TABLE_HISTORY = "CREATE TABLE "
//			+ TABLE_HISTORY + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,  "
//			+ COLUMN_TYPE_TIME + COLUMN_TYPE_CONTENT + COLUMN_TYPE_TYPE
//			+ COLUMN_TYPE_FROM_JID + COLUMN_TYPE_TO_JID;
//
//	private TableHistory(Context context) {
//		this.db = XMPPSQLiteOpenHelper.getInstance(context);
//	}
//
//	public static TableHistory getInstance(Context context) {
//		return new TableHistory(context);
//	}
//
//	public void insert(RowHistory row) {
//		db.execSQL(
//				"insert into " + TABLE_HISTORY + " values(null,?,?,?,?,?)",
//				new String[] { row.getMessageTime(), row.getMessageContent(),
//						row.getMessageType(), row.getFromJID(), row.getToJID() });
//	}
//
//	public ArrayList<RowHistory> select(String jid) {
//		jid = ValueUtil.deleteSth(jid, Constants.DELETE_STH);
//		String currentUserJID = SmackImpl.getInstance().getConnection()
//				.getUser();
//		currentUserJID = ValueUtil.deleteSth(currentUserJID,
//				Constants.DELETE_STH);
//		ArrayList<RowHistory> rows = new ArrayList<RowHistory>();
//		String sql = "select *from " + TABLE_HISTORY + " where " + "("
//				+ COLUMN_FROM_JID + " = '" + currentUserJID + "' and "
//				+ COLUMN_TO_JID + " = '" + jid + "') " + "or ("
//				+ COLUMN_FROM_JID + " = '" + jid + "' and " + COLUMN_TO_JID
//				+ " = '" + currentUserJID + "') ";
//		L.i("sql " + sql);
//		Cursor cursor = db.rawQuery(sql, null);
//		while (cursor.moveToNext()) {
//			L.i("sql result");
//			String time = cursor.getString(cursor.getColumnIndex(COLUMN_TIME));
//			String content = cursor.getString(cursor
//					.getColumnIndex(COLUMN_CONTENT));
//			String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
//			String fromJID = cursor.getString(cursor
//					.getColumnIndex(COLUMN_FROM_JID));
//			String toJID = cursor.getString(cursor
//					.getColumnIndex(COLUMN_TO_JID));
//			RowHistory row = new RowHistory(time, content, type, fromJID, toJID);
//			rows.add(row);
//		}
//		return rows;
//	}
//
//	public ArrayList<BubbleMessage> getBubbleList(String u_JID) {
//		ArrayList<BubbleMessage> bubbleList = new ArrayList<BubbleMessage>();
//		ArrayList<RowHistory> historyList = select(u_JID);
//		String rightUser = SmackImpl.getInstance().getConnection().getUser();
//		rightUser = ValueUtil.deleteSth(rightUser, Constants.DELETE_STH);
//		boolean isMine = false;
//		for (int i = 0; i < historyList.size(); i++) {
//			RowHistory history = historyList.get(i);
//			if (rightUser.equals(history.getFromJID()))
//				isMine = true;
//			else
//				isMine = false;
//			String viewTime = TimeUtil.getViewTime(history.getMessageTime());
//			if (i == 0) {
//				BubbleMessage bubbleMessageTime = new BubbleMessage(viewTime,
//						MessageType.TIME, isMine);
//				bubbleList.add(bubbleMessageTime);
//			}
//			if (i != 0) {
//				boolean longBefore = TimeUtil.isLongBefore(
//						historyList.get(i - 1).getMessageTime(),
//						history.getMessageTime());
//				if (longBefore) {
//					BubbleMessage bubbleMessageTime = new BubbleMessage(
//							viewTime, MessageType.TIME, isMine);
//					bubbleList.add(bubbleMessageTime);
//				}
//			}
//			if (history.getMessageType().equals(Constants.MESSAGE_TYPE_TEXT)) {
//				BubbleMessage bubbleMessageText = new BubbleMessage(
//						history.getMessageContent(), MessageType.TEXT, isMine);
//				bubbleList.add(bubbleMessageText);
//			} else if (history.getMessageType().equals(
//					Constants.MESSAGE_TYPE_FILE)) {
//				// content = "filename@filesize@filestage"
//				String filename;
//				String filesize;
//				String filestage;
//				String content = history.getMessageContent();
//				int first_at = content.indexOf("@");
//				int second_at = content.lastIndexOf("@");
//
//				MessageType type = MessageType.FILE;
//				filename = content.substring(0, first_at);
//				filesize = content.substring(first_at + 1, second_at);
//				filestage = content.substring(second_at + 1);
//
//				BubbleMessage bubbleMessageFile = new BubbleMessage(filename,
//						filesize);
//				bubbleMessageFile.setFileStage(AsyncTaskContants.STR_FINISH);
//				bubbleMessageFile.setFileProgressVal(101);
//				bubbleMessageFile.setMine(isMine);
//				bubbleList.add(bubbleMessageFile);
//			} else if (history.getMessageType().equals(
//					Constants.MESSAGE_TYPE_SOUND)) {
//				String content = history.getMessageContent();
//				int first_at = content.indexOf("@");
//				String path = content.substring(0, first_at);
//				String seconds = content.substring(first_at + 1);
//				int sumSecond = Integer.parseInt(seconds);
//				BubbleMessage bubbleMessageSound = new BubbleMessage(path,
//						sumSecond, isMine);
//				bubbleList.add(bubbleMessageSound);
//			}
//		}
//
//		return bubbleList;
//
//	}
//
//}
