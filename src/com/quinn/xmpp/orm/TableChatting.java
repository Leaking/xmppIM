//package com.quinn.xmpp.orm;
//
//import java.util.ArrayList;
//
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
//public class TableChatting {
//	private SQLiteDatabase db;
//
//	
//	//  TABLE_CHATTING
//	public static final String TABLE_CHATTING = "table_chatting";
//	public static final String COLUMN_TYPE_I_JID = "I_JID TEXT,";
//	public static final String COLUMN_TYPE_U_JID = "U_JID TEXT,";
//	public static final String COLUMN_TYPE_UNREADNUM = "unReadNum TEXT,";
//	public static final String COLUMN_TYPE_LASTMSG = "lastMSG TEXT,";
//	public static final String COLUMN_TYPE_LASTTIME = "lastTime TEXT)";
//	public static final String COLUMN_I_JID = "I_JID";
//	public static final String COLUMN_U_JID = "U_JID";
//	public static final String COLUMN_UNREADNUM = "unReadNum";
//	public static final String COLUMN_LASTMSG = "lastMSG";
//	public static final String COLUMN_LASTTIME = "lastTime";
//	
//	
//	public static final String CREATE_TABLE_CHATTING = "CREATE TABLE "
//			+ TABLE_CHATTING + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,  "
//			+ COLUMN_TYPE_I_JID + COLUMN_TYPE_U_JID + COLUMN_TYPE_UNREADNUM + COLUMN_TYPE_LASTMSG
//			+ COLUMN_TYPE_LASTTIME;
//	
//	private TableChatting(Context context) {
//		this.db = XMPPSQLiteOpenHelper.getInstance(context);
//	}
//
//	public static TableChatting getInstance(Context context) {
//		return new TableChatting(context);
//	}
//	
//	public void delete(String i_jid,String u_jid){
//		
//		String sql = "delete from " + TABLE_CHATTING
//				+ " where " + COLUMN_I_JID + " = '" + i_jid + "'"
//				+ " and " + COLUMN_U_JID + " = '" + u_jid + "'";
//		L.i("SQL delete  " + sql);
//
//		db.execSQL(sql);
//	}
//	
//	public void insert(RowChatting row) {
//		db.execSQL(
//				"insert into " + TABLE_CHATTING
//						+ " values(null,?,?,?,?,?)",
//				new String[] { row.getI_JID(), row.getU_JID(),
//						row.getUnReadNum(), row.getLastMSG(), row.getLastTime() });
//	}
//
//	public void reset(String i_JID, String u_JID){
//		 String sql = "update " + TABLE_CHATTING + " set "
//					+ COLUMN_UNREADNUM + " = '0'"
//					+ " where " + COLUMN_I_JID + " = '" + i_JID + "'"
//					+ " and " + COLUMN_U_JID + " = '" + u_JID + "'";
//		db.execSQL(sql);
//		L.i("SQL update  " + sql);
//		
//	}
//	public void update(String i_JID, String u_JID, String unReadNum, String lastMSG, String lastTime) {
//		 String sql = "update " + TABLE_CHATTING + " set "
//					+ COLUMN_LASTMSG + " = '" + lastMSG + "',"
//					+ COLUMN_LASTTIME + " = '" + lastTime + "',"
//					+ COLUMN_UNREADNUM + " = '" + unReadNum + "'"
//					+ " where " + COLUMN_I_JID + " = '" + i_JID + "'"
//					+ " and " + COLUMN_U_JID + " = '" + u_JID + "'";
//		db.execSQL(sql);
//		L.i("SQL update  " + sql);
//		
//	}
//
//	public ArrayList<RowChatting> select(String jid) {
//		jid = ValueUtil.deleteSth(jid, Constants.DELETE_STH);
//
//		ArrayList<RowChatting> rows = new ArrayList<RowChatting>();
//		String sql = "select *from " + TABLE_CHATTING
//				+ " where " + COLUMN_I_JID + " = '" + jid
//				+ "'";
//		L.i("sql " + sql);
//		Cursor cursor = db.rawQuery(sql, null);
//		while (cursor.moveToNext()) {
//			String i_JID = cursor.getString(cursor
//					.getColumnIndex(COLUMN_I_JID));
//			String u_JID = cursor.getString(cursor
//					.getColumnIndex(COLUMN_U_JID));
//			String unReadNum = cursor.getString(cursor
//					.getColumnIndex(COLUMN_UNREADNUM));
//			String lastMsg = cursor.getString(cursor
//					.getColumnIndex(COLUMN_LASTMSG));
//			String lastTime = cursor.getString(cursor
//					.getColumnIndex(COLUMN_LASTTIME));
//			RowChatting row = new RowChatting(i_JID, u_JID, unReadNum, lastMsg,
//					lastTime);
//			rows.add(row);
//		}
//		return rows;
//	}
//
//	
//	public int isExist(String i_JID,String u_JID){
//		String sql = "select "+ COLUMN_UNREADNUM +" from " + TABLE_CHATTING
//				+ " where " + COLUMN_I_JID + " = '" + i_JID
//				+ "' and " + COLUMN_U_JID + " = '" + u_JID + "'";		
//		Cursor cursor = db.rawQuery(sql, null);
//		while (cursor.moveToNext()) {
//			String unReadNum = cursor.getString(cursor
//					.getColumnIndex(COLUMN_UNREADNUM));
//			int num = Integer.parseInt(unReadNum);
//			return num;
//		}
//		return -1;
//	}
//	// check if the row exist, if so, update it ,if not, insert a new one
//	public void insert_update(RowChatting row){
//		String i_JID = SmackImpl.getInstance().getConnection().getUser();
//		i_JID = ValueUtil.deleteSth(i_JID, Constants.DELETE_STH);
//		String u_JID = row.getU_JID();
//		u_JID = ValueUtil.deleteSth(u_JID, Constants.DELETE_STH);
//		String lastMSG = row.getLastMSG();
//		String lastTime = row.getLastTime();	
//		int num = isExist(i_JID, u_JID);
//		int num1 = num + 1;
//		String unReadNum = "" + num1;
//		if(num != -1){
//			update(i_JID,u_JID,unReadNum, lastMSG, lastTime);
//		}else{
//			insert(row);
//		}		
//	}
//	
//}
