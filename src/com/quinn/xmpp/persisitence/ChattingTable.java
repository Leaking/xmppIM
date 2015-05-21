package com.quinn.xmpp.persisitence;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quinn.xmpp.App;
import com.quinn.xmpp.orm.RowChatting;
import com.quinn.xmpp.util.LogcatUtils;

/**
 * @author Quinn
 * @date 2015-3-31
 */
public class ChattingTable {
	private SQLiteDatabase db;
	private Context context;

	// TABLE_CHATTING
	public static final String TABLE_CHATTING = "table_chatting";
	

	//列名
	public static final String COLUMN_I_JID = "I_JID";
	public static final String COLUMN_U_JID = "U_JID";
	public static final String COLUMN_UNREADNUM = "unReadNum";
	public static final String COLUMN_LASTMSG = "lastMSG";
	public static final String COLUMN_LASTTIME = "lastTime";

	// 各列的类型，以及建表SQL语句
	public static final String COLUMN_TYPE_I_JID = COLUMN_I_JID + " TEXT,";
	public static final String COLUMN_TYPE_U_JID = COLUMN_U_JID + " TEXT,";
	public static final String COLUMN_TYPE_UNREADNUM = COLUMN_UNREADNUM + " TEXT,";
	public static final String COLUMN_TYPE_LASTMSG = COLUMN_LASTMSG + " TEXT,";
	public static final String COLUMN_TYPE_LASTTIME = COLUMN_LASTTIME + " TEXT)";
	public static final String CREATE_TABLE_CHATTING = "CREATE TABLE "
			+ TABLE_CHATTING + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,  "
			+ COLUMN_TYPE_I_JID + COLUMN_TYPE_U_JID + COLUMN_TYPE_UNREADNUM
			+ COLUMN_TYPE_LASTMSG + COLUMN_TYPE_LASTTIME;

	private ChattingTable(Context context) {
		this.db = XMPPSQLiteOpenHelper.getInstance(context);
	}

	public static ChattingTable getInstance(Context context) {
		return new ChattingTable(context);
	}

	public void delete(String i_jid, String u_jid) {
		String sql = "delete from " + TABLE_CHATTING + " where " + COLUMN_I_JID
				+ " = '" + i_jid + "'" + " and " + COLUMN_U_JID + " = '"
				+ u_jid + "'";
		LogcatUtils.i("SQL delete  " + sql);
		db.execSQL(sql);
	}

	public void insert(RowChatting row) {
		db.execSQL(
				"insert into " + TABLE_CHATTING + " values(null,?,?,?,?,?)",
				new String[] { row.getI_JID(), row.getU_JID(),
						row.getUnReadNum(), row.getLastMSG(), row.getLastTime() });
	}

	public void reset(String i_JID, String u_JID) {
		String sql = "update " + TABLE_CHATTING + " set " + COLUMN_UNREADNUM
				+ " = '0'" + " where " + COLUMN_I_JID + " = '" + i_JID + "'"
				+ " and " + COLUMN_U_JID + " = '" + u_JID + "'";
		db.execSQL(sql);
		LogcatUtils.i("SQL update  " + sql);

	}

	public void update(String i_JID, String u_JID, String unReadNum,
			String lastMSG, String lastTime) {
		String sql = "update " + TABLE_CHATTING + " set " + COLUMN_LASTMSG
				+ " = '" + lastMSG + "'," + COLUMN_LASTTIME + " = '" + lastTime
				+ "'," + COLUMN_UNREADNUM + " = '" + unReadNum + "'"
				+ " where " + COLUMN_I_JID + " = '" + i_JID + "'" + " and "
				+ COLUMN_U_JID + " = '" + u_JID + "'";
		db.execSQL(sql);
		LogcatUtils.i("SQL update  " + sql);

	}

	public ArrayList<RowChatting> select(String jid) {
		//Sjid = ValueUtil.deleteSth(jid, Constants.DELETE_STH);

		ArrayList<RowChatting> rows = new ArrayList<RowChatting>();
		String sql = "select *from " + TABLE_CHATTING + " where "
				+ COLUMN_I_JID + " = '" + jid + "'";
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String i_JID = cursor
					.getString(cursor.getColumnIndex(COLUMN_I_JID));
			String u_JID = cursor
					.getString(cursor.getColumnIndex(COLUMN_U_JID));
			String unReadNum = cursor.getString(cursor
					.getColumnIndex(COLUMN_UNREADNUM));
			String lastMsg = cursor.getString(cursor
					.getColumnIndex(COLUMN_LASTMSG));
			String lastTime = cursor.getString(cursor
					.getColumnIndex(COLUMN_LASTTIME));
			RowChatting row = new RowChatting(i_JID, u_JID, unReadNum, lastMsg,
					lastTime);
			rows.add(row);
		}
		return rows;
	}

	public int isExist(String i_JID, String u_JID) {
		String sql = "select " + COLUMN_UNREADNUM + " from " + TABLE_CHATTING
				+ " where " + COLUMN_I_JID + " = '" + i_JID + "' and "
				+ COLUMN_U_JID + " = '" + u_JID + "'";
		Cursor cursor = db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String unReadNum = cursor.getString(cursor
					.getColumnIndex(COLUMN_UNREADNUM));
			int num = Integer.parseInt(unReadNum);
			return num;
		}
		return -1;
	}

	// check if the row exist, if so, update it ,if not, insert a new one
	public void insert_update(RowChatting row) {
		App app = (App) context.getApplicationContext();
		String i_JID = app.getSmack().getUserVCard().getJid();
		String u_JID = row.getU_JID();
		String lastMSG = row.getLastMSG();
		String lastTime = row.getLastTime();
		int num = isExist(i_JID, u_JID);
		int num1 = num + 1;
		String unReadNum = "" + num1;
		if (num != -1) {
			update(i_JID, u_JID, unReadNum, lastMSG, lastTime);
		} else {
			insert(row);
		}
	}

}
