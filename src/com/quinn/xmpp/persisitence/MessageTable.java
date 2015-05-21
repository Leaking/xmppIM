package com.quinn.xmpp.persisitence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author Quinn
 * @date 2015-3-31
 */
public class MessageTable {

	private SQLiteDatabase db;

	// TABLE_HISTORY
	public static final String TABLE_HISTORY = "table_history";
	public static final String COLUMN_TYPE_TIME = "messageTime TEXT,";
	public static final String COLUMN_TYPE_CONTENT = "messageContent TEXT,";
	public static final String COLUMN_TYPE_TYPE = "messageType TEXT,";
	public static final String COLUMN_TYPE_FROM_JID = "fromJID TEXT,";
	public static final String COLUMN_TYPE_TO_JID = "toJID TEXT)";
	public static final String COLUMN_TIME = "messageTime";
	public static final String COLUMN_CONTENT = "messageContent";
	public static final String COLUMN_TYPE = "messageType";
	public static final String COLUMN_FROM_JID = "fromJID";
	public static final String COLUMN_TO_JID = "toJID";

	public static final String CREATE_TABLE_HISTORY = "CREATE TABLE "
			+ TABLE_HISTORY + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,  "
			+ COLUMN_TYPE_TIME + COLUMN_TYPE_CONTENT + COLUMN_TYPE_TYPE
			+ COLUMN_TYPE_FROM_JID + COLUMN_TYPE_TO_JID;
	
	private MessageTable(Context context) {
		this.db = XMPPSQLiteOpenHelper.getInstance(context);
	}

	public static MessageTable getInstance(Context context) {
		return new MessageTable(context);
	}
}


