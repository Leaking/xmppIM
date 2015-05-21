package com.quinn.xmpp.persisitence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * @author Quinn
 * @date 2015-3-31
 */
public class XMPPSQLiteOpenHelper extends SQLiteOpenHelper {

	private static XMPPSQLiteOpenHelper instance = null;

	private static final int SCHEMA_VERSION = 1;
	private static final String DATABASE_NAME = "XMPP.db";

	public XMPPSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	}

	public static SQLiteDatabase getInstance(Context context) {
		if (instance == null) {
			instance = new XMPPSQLiteOpenHelper(context);
		}
		return instance.getWritableDatabase();
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(ChattingTable.CREATE_TABLE_CHATTING);
		db.execSQL(MessageTable.CREATE_TABLE_HISTORY);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}


