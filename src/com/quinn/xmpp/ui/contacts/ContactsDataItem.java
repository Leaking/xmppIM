package com.quinn.xmpp.ui.contacts;

import com.quinn.xmpp.ui.BaseDataItem;

/**
 * @author Quinn
 * @date 2015-3-16
 * 
 * @Description A data class wrapped the data to show in contacts list children view.
 * {@link name} is maybe a person name or a chatroom name
 */
public class ContactsDataItem extends BaseDataItem{
	
	private String service;
	private String nickname;
	private String jid;
	
	
	public ContactsDataItem(){
		
	}
	
	public ContactsDataItem(String jid, String nickname,String service){
		this.jid = jid;
		this.nickname = nickname;
		this.service = service;
	}
	
	
	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
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


