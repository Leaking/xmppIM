package com.XMPP.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class XMPPSQLiteOpenHelper extends SQLiteOpenHelper {

	private static XMPPSQLiteOpenHelper instance = null;

	private static final int SCHEMA_VERSION = 1;
	private static final String DATABASE_NAME = "XMPP.db";

	public static final String TABLE_CONTACTS = "table_contacts";
	public static final String COLUMN_GROUPS_JID = "jid TEXT,";
	public static final String COLUMN_GROUPS_GROUP = "groupname TEXT,";
	public static final String COLUMN_GROUPS_FRIENDJID = "friendJID TEXT,";
	public static final String COLUMN_GROUPS_NICKNAME = "nickname TEXT,";
	public static final String COLUMN_GROUPS_IFONLINE = "online TEXT,";
	public static final String COLUMN_GROUPS_PHOTO = "photo TEXT,";
	public static final String COLUMN_GROUPS_SIGNATURE = "signature TEXT)";

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

	private static final String TABLE_MESSAGE = "table_message";

	public XMPPSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}

	public static SQLiteDatabase getInstance(Context context) {
		if (instance == null) {
			instance = new XMPPSQLiteOpenHelper(context);
		}
		return instance.getWritableDatabase();
	}

	private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE "
			+ TABLE_CONTACTS + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ COLUMN_GROUPS_JID + COLUMN_GROUPS_GROUP + COLUMN_GROUPS_FRIENDJID
			+ COLUMN_GROUPS_NICKNAME + COLUMN_GROUPS_IFONLINE
			+ COLUMN_GROUPS_PHOTO + COLUMN_GROUPS_SIGNATURE;

	private static final String CREATE_TABLE_HISTORY = "CREATE TABLE "
			+ TABLE_HISTORY + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,  "
			+ COLUMN_TYPE_TIME + COLUMN_TYPE_CONTENT + COLUMN_TYPE_TYPE + COLUMN_TYPE_FROM_JID
			+ COLUMN_TYPE_TO_JID;

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_CONTACTS);
		db.execSQL(CREATE_TABLE_HISTORY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
