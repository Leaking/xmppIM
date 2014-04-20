package com.XMPP.Model;

public class ViewEntry {
	private String friend_jID;
	private String nickname;
	private String online;
	private String photo;
	private String signature;
	
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
}
