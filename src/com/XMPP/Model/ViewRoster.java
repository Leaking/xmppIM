package com.XMPP.Model;

import java.util.ArrayList;

import com.XMPP.Database.ContactsRow;
import com.XMPP.util.L;
import com.XMPP.util.Test;

public class ViewRoster {
	private ArrayList<ViewGroup> groupList;

	public ViewRoster(ArrayList<ContactsRow> rows){
		groupList = new ArrayList<ViewGroup>();
		change(rows);
		Test.outputViewRoster(groupList);
	}
	public void change(ArrayList<ContactsRow> rows){
		int size = rows.size();
		ArrayList<String> groupNames_list = new ArrayList<String>();
		
		for(int i = 0; i < size; i++){
			String groupName = rows.get(i).getGroup();
			if(!groupNames_list.contains(groupName)){
				groupNames_list.add(groupName);
				ViewGroup group = new ViewGroup();
				group.setGroupName(groupName);
				//online,,,,,,,,,,,,,,,
				ViewEntry entry = new ViewEntry();
				entry.setFriend_jID(rows.get(i).getFriend_jID());
				entry.setNickname(rows.get(i).getNickname());
				entry.setOnline(rows.get(i).getOnline());
				entry.setPhoto(rows.get(i).getPhoto());
				entry.setSignature(rows.get(i).getSignature());
				group.add(entry);
				this.add(group);
			}else{
				ViewGroup group = getGroupByName(groupName);
				ViewEntry entry = new ViewEntry();
				entry.setFriend_jID(rows.get(i).getFriend_jID());
				entry.setNickname(rows.get(i).getNickname());
				entry.setOnline(rows.get(i).getOnline());
				entry.setPhoto(rows.get(i).getPhoto());
				entry.setSignature(rows.get(i).getSignature());
				group.add(entry);
			}
		}
	}
	
	
	public ViewGroup getGroupByName(String name){		
		for(int i = 0; i < groupList.size(); i++){
			if(groupList.get(i).getGroupName().equals(name)){
				return groupList.get(i);
			}
		}		
		return null;
	}
	
		
	public void add(ViewGroup group){
		this.groupList.add(group);
	}
	
	
	
	
	
	
	
	
	
	public ArrayList<ViewGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(ArrayList<ViewGroup> groupList) {
		this.groupList = groupList;
	}

	
}
