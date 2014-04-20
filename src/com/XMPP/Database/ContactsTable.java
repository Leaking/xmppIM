package com.XMPP.Database;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.XMPP.util.L;

public class ContactsTable {
	private SQLiteDatabase db;

	public ContactsTable(SQLiteDatabase db) {
		this.db = db;
	}

	public void insertAll(ArrayList<ContactsRow> rows){
		for(int i = 0; i < rows.size(); i++){
			insert(rows.get(i));
		}
	}
	public void insert(ContactsRow row) {
		L.i(row.getGroup());
		db.execSQL("insert into " + XMPPSQLiteOpenHelper.TABLE_CONTACTS
				+ " values(null,?,?,?,?,?,?,?)",
				new String[] { row.getjID(), row.getGroup(), row.getFriend_jID(),
						row.getNickname(),row.getOnline(), row.getPhoto(), row.getSignature() }
		);
	}
	public ArrayList<ContactsRow> select(String jid){
		ArrayList<ContactsRow> rows = new ArrayList<ContactsRow>();
		String sql = "select *from " + XMPPSQLiteOpenHelper.TABLE_CONTACTS +
				"where jid = " + jid;
		Cursor cursor = db.rawQuery(sql, null);				
		while(cursor.moveToNext()){  
            String jID = cursor.getString(cursor.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_GROUPS_JID));  
            String group = cursor.getString(cursor.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_GROUPS_GROUP));  
            String friend_jid = cursor.getString(cursor.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_GROUPS_FRIENDJID));  
            String nickname = cursor.getString(cursor.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_GROUPS_NICKNAME));
            String online = cursor.getString(cursor.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_GROUPS_IFONLINE));
            String photo = cursor.getString(cursor.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_GROUPS_PHOTO));
            String signature = cursor.getString(cursor.getColumnIndex(XMPPSQLiteOpenHelper.COLUMN_GROUPS_SIGNATURE));
            ContactsRow row = new ContactsRow(jID,group,friend_jid,nickname,online,photo,signature);
            rows.add(row);
		}  		
		return rows;
	}
}
