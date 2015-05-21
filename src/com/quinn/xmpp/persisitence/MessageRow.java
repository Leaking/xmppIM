package com.quinn.xmpp.persisitence;

/**
 * Row in the Table-Message
 * 
 * 
 * @author Quinn
 * @date 2015-3-31
 */
public class MessageRow {

	
	private String messageTime;
	private String messageContent;
	private String messageType;
	private String fromJID;
	private String toJID;
	
	
	
	public String getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(String messageTime) {
		this.messageTime = messageTime;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getFromJID() {
		return fromJID;
	}
	public void setFromJID(String fromJID) {
		this.fromJID = fromJID;
	}
	public String getToJID() {
		return toJID;
	}
	public void setToJID(String toJID) {
		this.toJID = toJID;
	}

	
	
	
}


