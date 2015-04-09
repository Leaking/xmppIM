package com.quinn.xmpp.core.contacts;

import java.util.ArrayList;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.Presence;

import com.quinn.xmpp.smack.Smack;
import com.quinn.xmpp.ui.contacts.ContactsDataItem;
import com.quinn.xmpp.util.LogcatUtils;

import android.os.AsyncTask;

/**
 * @author Quinn
 * @date 2015-3-16
 * @description A task to load Roster information
 */
public class ContactsLoadingTask extends AsyncTask<Void, Integer, ArrayList<ContactsDataItem>>{


	private Smack smack;
	
	public ContactsLoadingTask(Smack smack){
		this.smack = smack;
	}
	
	
	@Override
	protected ArrayList<ContactsDataItem> doInBackground(Void... params) {
		ArrayList<ContactsDataItem> contactsDataItems = new ArrayList<ContactsDataItem>();
		for (RosterEntry rosterEntry : smack.getAllRosterEntry()) {
			String jid = rosterEntry.getUser();
			Presence presence = smack.getConnection().getRoster().getPresence(jid);
			ContactsDataItem item = smack.getContactData(jid);
			item.setService(getServiceFromFullJID(presence.getFrom()));
			contactsDataItems.add(item);
		}
		return contactsDataItems;
	}
	
	/**
	 * 获取service 后面修改代码，用stringbuffer
	 * @param fullJID
	 * @return
	 */
	public String getServiceFromFullJID(String fullJID){
		String service = null;
		service = fullJID.substring(fullJID.lastIndexOf("/") + 1);
		LogcatUtils.i("Get service from fullJID = " + service);
		return service;
	}
	
}


