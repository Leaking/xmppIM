package com.XMPP.Model;

import java.util.ArrayList;

import com.XMPP.Database.ContactsRow;
import com.XMPP.util.Constants;
import com.XMPP.util.L;
import com.XMPP.util.Test;

public class ViewRoster {
	private ArrayList<ViewGroup> groupList;

	public ViewRoster(ArrayList<ContactsRow> rows) {
		groupList = new ArrayList<ViewGroup>();
		change(rows);
	}

	public void change(ArrayList<ContactsRow> rows) {
		int size = rows.size();
		ArrayList<String> groupNames_list = new ArrayList<String>();

		for (int i = 0; i < size; i++) {
			String groupName = rows.get(i).getGroup();
			if (!groupNames_list.contains(groupName)) {
				groupNames_list.add(groupName);
				ViewGroup group = new ViewGroup();
				group.setGroupName(groupName);
				// online,,,,,,,,,,,,,,,
				if (rows.get(i).getFriend_jID() == null) {
					System.out.println("empty group  2" + groupName);

					group.setOnlineSum(0);
					this.add(group);
				} else {
					ViewEntry entry = new ViewEntry();
					entry.setFriend_jID(rows.get(i).getFriend_jID());
					entry.setNickname(rows.get(i).getNickname());
					entry.setOnline(rows.get(i).getOnline());
					entry.setPhoto(rows.get(i).getPhoto());
					entry.setSignature(rows.get(i).getSignature());
					group.add(entry);

					if (rows.get(i).getOnline() == Constants.ONLINE)
						group.setOnlineSum(group.getOnlineSum() + 1);
					this.add(group);
				}
			} else {
				ViewGroup group = getGroupByName(groupName);
				ViewEntry entry = new ViewEntry();
				entry.setFriend_jID(rows.get(i).getFriend_jID());
				entry.setNickname(rows.get(i).getNickname());
				entry.setOnline(rows.get(i).getOnline());
				entry.setPhoto(rows.get(i).getPhoto());
				entry.setSignature(rows.get(i).getSignature());

				if (rows.get(i).getOnline() == Constants.ONLINE)
					group.setOnlineSum(group.getOnlineSum() + 1);
				group.add(entry);
			}
		}
	}

	public ViewGroup getGroup(int index) {
		return groupList.get(index);
	}

	public ViewEntry getEntry(int i, int j) {
		return groupList.get(i).getEntry(j);
	}

	public int getGroupCount() {
		return groupList.size();
	}

	public ViewGroup getGroupByName(String name) {
		for (int i = 0; i < groupList.size(); i++) {
			if (groupList.get(i).getGroupName().equals(name)) {
				return groupList.get(i);
			}
		}
		return null;
	}

	public void add(ViewGroup group) {
		this.groupList.add(group);
	}

	public ArrayList<ViewGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(ArrayList<ViewGroup> groupList) {
		this.groupList = groupList;
	}

}
