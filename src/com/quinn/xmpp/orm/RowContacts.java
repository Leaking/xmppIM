package com.quinn.xmpp.orm;

public class RowContacts {
	private String jID;
	private String group;
	private String friend_jID;
	private String nickname;
	private String online;
	private String photo;
	private String signature;
	public RowContacts(String jID,String group,String friend_jID,String nickname,String online,String photo,String signature){
		this.jID = jID;
		this.group = group;
		this.friend_jID = friend_jID;
		this.nickname = nickname;
		this.online = online;
		this.photo = photo;
		this.signature = signature;
	}
	public String getjID() {
		return jID;
	}
	public void setjID(String jID) {
		this.jID = jID;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getFriend_jID() {
		return friend_jID;
	}
	public void setFriend_jID(String friend_jID) {
		this.friend_jID = friend_jID;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getOnline() {
		return online;
	}
	public void setOnline(String online) {
		this.online = online;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	@Override
	public String toString(){
		
		String string = 
		"--------------------------"
	   + "jID : " + jID
	   + "group : " + group 
	   + "friend_jID : " + friend_jID
	   + "nickname : " + nickname
	   + "online : " + online
	   + "photo : " + photo
	   + "signature : " + signature
	   + "--------------------------";

		return string;
		
	}

	
}
