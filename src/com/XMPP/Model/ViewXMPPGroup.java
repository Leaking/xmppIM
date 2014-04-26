package com.XMPP.Model;

import java.util.ArrayList;

public class ViewXMPPGroup {
	private String groupName;
	private int onlineSum;
	private ArrayList<ViewEntry> entryList;
	
	
	public ViewEntry getEntry(int index){
		return entryList.get(index);
	}
	public ViewXMPPGroup(){
		this.entryList = new ArrayList<ViewEntry>();
	}
	public int getChildrenCount(){
		return entryList.size();
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
