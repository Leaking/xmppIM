package quinn.xmpp.model;

import java.util.ArrayList;

import quinn.xmpp.database.RowContacts;
import quinn.xmpp.utils.Constants;
import quinn.xmpp.utils.L;
import quinn.xmpp.utils.Test;


public class ViewRoster {
	private ArrayList<ViewXMPPGroup> groupList;

	public ViewRoster(ArrayList<RowContacts> rows) {
		groupList = new ArrayList<ViewXMPPGroup>();
		change(rows);
	}

	public void change(ArrayList<RowContacts> rows) {
		int size = rows.size();
		ArrayList<String> groupNames_list = new ArrayList<String>();

		for (int i = 0; i < size; i++) {
			String groupName = rows.get(i).getGroup();
			if (!groupNames_list.contains(groupName)) {
				groupNames_list.add(groupName);
				ViewXMPPGroup group = new ViewXMPPGroup();
				group.setGroupName(groupName);
				if (rows.get(i).getFriend_jID() == null) {

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
				ViewXMPPGroup group = getGroupByName(groupName);
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

	public ArrayList<String> getGroupnames(){
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < this.groupList.size();i++){
			list.add(this.groupList.get(i).getGroupName());
		}
		return list;
		
	}
	public ViewXMPPGroup getGroup(int index) {
		return groupList.get(index);
	}

	public ViewEntry getEntry(int i, int j) {
		return groupList.get(i).getEntry(j);
	}

	public int getGroupCount() {
		return groupList.size();
	}

	public ViewXMPPGroup getGroupByName(String name) {
		for (int i = 0; i < groupList.size(); i++) {
			if (groupList.get(i).getGroupName().equals(name)) {
				return groupList.get(i);
			}
		}
		return null;
	}

	public void add(ViewXMPPGroup group) {
		this.groupList.add(group);
	}

	public ArrayList<ViewXMPPGroup> getGroupList() {
		return groupList;
	}

	public void setGroupList(ArrayList<ViewXMPPGroup> groupList) {
		this.groupList = groupList;
	}

	public boolean isExistFriend(String jid){
		for(int i = 0; i < this.groupList.size(); i++){
			ViewXMPPGroup group = groupList.get(i);
			for(int j = 0; j < group.getEntryList().size(); j++){
				ViewEntry entry = group.getEntry(j);
				if(entry.getFriend_jID().equals(jid)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	
}
