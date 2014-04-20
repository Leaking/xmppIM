package com.XMPP.Model;

import java.util.ArrayList;

public class ViewGroup {
	private String groupName;
	private int onlineSum;
	private ArrayList<ViewEntry> entryList;
	
	
	
	public ViewGroup(){
		this.entryList = new ArrayList<ViewEntry>();
	}
	
	public void add(ViewEntry entry){
		entryList.add(entry);
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getOnlineSum() {
		return onlineSum;
	}
	public void setOnlineSum(int onlineSum) {
		this.onlineSum = onlineSum;
	}
	public ArrayList<ViewEntry> getEntryList() {
		return entryList;
	}
	public void setEntryList(ArrayList<ViewEntry> entryList) {
		this.entryList = entryList;
	}

}
