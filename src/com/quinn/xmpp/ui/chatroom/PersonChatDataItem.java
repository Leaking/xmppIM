package com.quinn.xmpp.ui.chatroom;

import com.quinn.xmpp.ui.BaseDataItem;

/**
 * 聊天信息结点
 * 
 * @author Quinn
 * @date 2015-3-31
 */
public class PersonChatDataItem extends BaseDataItem{
	
	private String jid; 
	private String nickname;
	private String textContent;
	private String happenTime;
	private boolean isMyMessage;
	
	
	public boolean isMyMessage() {
		return isMyMessage;
	}
	public void setMyMessage(boolean isMyMessage) {
		this.isMyMessage = isMyMessage;
	}
	public String getJid() {
		return jid;
	}
	public void setJid(String jid) {
		this.jid = jid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getTextContent() {
		return textContent;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	public String getHappenTime() {
		return happenTime;
	}
	public void setHappenTime(String happenTime) {
		this.happenTime = happenTime;
	}
	
	
}


