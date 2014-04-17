package com.XMPP.util;

import java.util.ArrayList;

import com.XMPP.service.GroupProfile;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;

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
	
	

	public static void outputGroupList(ArrayList<GroupProfile> list){
		L.i("TEST : if the MainviewActivity receive GroupList");
		if(list == null){
			L.i("test result : GroupProfile is null");
		}
		L.i("group size = " + list.size());
		for(int i = 0; i < list.size(); i++){
			L.i("group name = " + list.get(i).getGroupName());
			for(int j = 0; j < list.get(i).getPersonList().size();j++){
				L.i("person name = " +  list.get(i).getPersonList().get(j).getName());
			}
		}
		
	}
	
	public static void outputCertainString(String iofo,String content){
		L.i("TEST : " + iofo);
		L.i(" = " + content);
	}
	
	
}
