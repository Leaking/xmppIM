package com.quinn.xmpp.ui.contacts;

/**
 * @author Quinn
 * @date 2015-3-16
 * 
 * @Description A data class wrapped the data to show in contacts list children view.
 * {@link name} is maybe a person name or a chatroom name
 */
public class ContactsDataItem {
	
	private String iconURL;
	private String name;
	
	public ContactsDataItem(String iconURL,String name){
		this.iconURL = iconURL;
		this.name = name;
	}
	
	public String getIconURL() {
		return iconURL;
	}
	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}


