package com.XMPP.util;

import java.util.ArrayList;

import com.XMPP.Database.ContactsRow;
import com.XMPP.Model.ViewEntry;
import com.XMPP.Model.ViewXMPPGroup;
import com.XMPP.smack.Smack;

public class Test {
	public static void output2levelString(String[][] _2levelString){
		L.i("TEST : output 2 Level String");
		int i = 0;
		for(String[] list:_2levelString){
			L.i("dimension : " + i++);
		
			for(String item:list){
				L.i("item: " + item);
			}
		}
	}
	public static void output1levelString(String[] list){
		L.i("TEST : output 1 Level String");
		for(String item:list){
			L.i("item: " + item);
		}
	}
	
	public static void outputConnectedUser(Smack smack){
		L.i("TEST : output current connection info");
		L.i("connect ip " + smack.getConnection().getHost());
		L.i("connect port " + smack.getConnection().getPort());
		L.i("connect username " + smack.getConnection().getUser().toString());
	}
	
	

	
	
	public static void outputCertainString(String iofo,String content){
		L.i("TEST : " + iofo);
		L.i(" = " + content);
	}
	
	
	public static void outputContactsRows (ArrayList<ContactsRow> rows){
		for(int i = 0; i < rows.size(); i++){
			System.out.println(rows.get(i).toString());
		}
	}
	
	public static void outputViewRoster(ArrayList<ViewXMPPGroup> groupList){
		for(int i = 0; i < groupList.size(); i++){
			ViewXMPPGroup group = new ViewXMPPGroup();
			group = groupList.get(i);
			L.i("----------------Group-------------------------------");
			L.i("group name  " + group.getGroupName());
			L.i("online sum   " + String.valueOf(group.getOnlineSum()));
			for(int j = 0; j < groupList.get(i).getEntryList().size();j++){
				ViewEntry entry = new ViewEntry();
				entry = groupList.get(i).getEntryList().get(j);
				L.i("----------friend----------");
				L.i("friendID  " + entry.getFriend_jID());
				L.i("nickname  " + entry.getNickname());
				L.i("online " + entry.getOnline());
				L.i("getPhoto  " + entry.getPhoto());
				L.i("getSignature  " + entry.getSignature());
			}
			
			
		}
		
		
	}
	
}
