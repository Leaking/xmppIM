package com.quinn.xmpp.ui.contacts;

import org.jivesoftware.smack.RosterEntry;

/**
 * @author Quinn
 * @date 2015-3-16
 * 
 * @Description A data class wrapped the data to show in contacts list children view.
 * {@link name} is maybe a person name or a chatroom name
 */
public class ContactsDataItem {
	
	private String nickname;
	private String jid;
	
	public ContactsDataItem(){
		
	}
	
	public ContactsDataItem(String jid, String nickname){
		this.jid = jid;
		this.nickname = nickname;
	}
	
	
	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	
}


