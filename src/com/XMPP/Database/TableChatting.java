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

public class TableChatting {
	private SQLiteDatabase db;

	private TableChatting(Context context) {
		this.db = XMPPSQLiteOpenHelper.getInstance(context);
	}

	public static TableChatting getInstance(Context context) {
		return new TableChatting(context);
	}
	
	public void delete(String i_jid,String u_jid){
		String sql = "delete from " + XMPPSQLiteOpenHelper.TABLE_CHATTING
				+ " where " + XMPPSQLiteOpenHelper.COLUMN_I_JID + " = '" + i_jid + "'"
				+ " and " + XMPPSQLiteOpenHelper.COLUMN_U_JID + " = '" + u_jid + "'";
		db.execSQL(sql);
	}
	
	public void insert(RowChatting row) {
		db.execSQL(
				"insert into " + XMPPSQLiteOpenHelper.TABLE_CHATTING
						+ " values(null,?,?,?,?,?)",
				new String[] { row.getI_JID(), row.getU_JID(),
						row.getUnReadNum(), row.getLastMSG(), row.getLastTime() });
	}

	public void reset(String i_JID, String u_JID){
		 String sql = "update " + XMPPSQLiteOpenHelper.TABLE_CHATTING + " set "
					+ XMPPSQLiteOpenHelper.COLUMN_UNREADNUM + " = '0'"
					+ " where " + XMPPSQLiteOpenHelper.COLUMN_I_JID + " = '" + i_JID + "'"
					+ " and " + XMPPSQLiteOpenHelper.COLUMN_U_JID + " = '" + u_JID + "'";
		db.execSQL(sql);
		L.i("SQL update  " + sql);
		
	}
	public void update(String i_JID, String u_JID, String unReadNum, String lastMSG, String lastTime) {
		 String sql = "update " + XMPPSQLiteOpenHelper.TABLE_CHATTING + " set "
					+ XMPPSQLiteOpenHelper.COLUMN_LASTMSG + " = '" + lastMSG + "',"
					+ XMPPSQLiteOpenHelper.COLUMN_LASTTIME + " = '" + lastTime + "',"
					+ XMPPSQLiteOpenHelper.COLUMN_UNREADNUM + " = '" + unReadNum + "'"
					+ " where " + XMPPSQLiteOpenHelper.COLUMN_I_JID + " = '" + i_JID + "'"
					+ " and " + XMPPSQLiteOpenHelper.COLUMN_U_JID + " = '" + u_JID + "'";
		db.execSQL(sql);
		L.i("SQL update  " + sql);
		
	}

	public ArrayList<RowChatting> select(String jid) {
		jid = ValueUtil.deleteSth(jid, Constants.DELETE_STH);

		ArrayList<RowChatting> rows = new ArrayList<RowChatting>();
		String sql = "select *from " + XMPPSQLiteOpenHelper.TABLE_CHATTING
				+ " where " + XMPPSQLiteOpenHelper.COLUMN_I_JID + " = '" + jid
				+ "'";
		L.i("sql " + sql);
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String i_JID = cursor.getString(cursor
					.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_I_JID));
			String u_JID = cursor.getString(cursor
					.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_U_JID));
			String unReadNum = cursor.getString(cursor
					.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_UNREADNUM));
			String lastMsg = cursor.getString(cursor
					.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_LASTMSG));
			String lastTime = cursor.getString(cursor
					.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_LASTTIME));
			RowChatting row = new RowChatting(i_JID, u_JID, unReadNum, lastMsg,
					lastTime);
			rows.add(row);
		}
		return rows;
	}

	
	public int isExist(String i_JID,String u_JID){
		String sql = "select "+ XMPPSQLiteOpenHelper.COLUMN_UNREADNUM +" from " + XMPPSQLiteOpenHelper.TABLE_CHATTING
				+ " where " + XMPPSQLiteOpenHelper.COLUMN_I_JID + " = '" + i_JID
				+ "' and " + XMPPSQLiteOpenHelper.COLUMN_U_JID + " = '" + u_JID + "'";		
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String unReadNum = cursor.getString(cursor
					.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_UNREADNUM));
			int num = Integer.parseInt(unReadNum);
			return num;
		}
		return -1;
	}
	// check if the row exist, if so, update it ,if not, insert a new one
	public void insert_update(RowChatting row){
		L.i("into--------0");
		String i_JID = SmackImpl.getInstance().getConnection().getUser();
		L.i("into--------1");
		i_JID = ValueUtil.deleteSth(i_JID, Constants.DELETE_STH);
		L.i("into--------2");
		String u_JID = row.getU_JID();
		L.i("into--------3");
		u_JID = ValueUtil.deleteSth(u_JID, Constants.DELETE_STH);
		L.i("into--------4");
		String lastMSG = row.getLastMSG();
		L.i("into--------5");
		String lastTime = row.getLastTime();	
		L.i("into--------6");
		int num = isExist(i_JID, u_JID);
		L.i("into--------7");
		int num1 = num + 1;
		L.i("into--------8");
		String unReadNum = "" + num1;
		L.i("into--------9");
		if(num != -1){
			update(i_JID,u_JID,unReadNum, lastMSG, lastTime);
		}else{
			insert(row);
		}		
	}
	
}
