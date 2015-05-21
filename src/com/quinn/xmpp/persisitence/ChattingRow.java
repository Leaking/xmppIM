package com.quinn.xmpp.persisitence;

/**
 * Row in the Table-Chatting
 * 
 * 
 * @author Quinn
 * @date 2015-3-31
 */
public class ChattingRow {
	
	private String fromJID;
	private String toJID;
	private String unReadNum;
	private String lastMSG;
	private String lastTime;
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
	public String getUnReadNum() {
		return unReadNum;
	}
	public void setUnReadNum(String unReadNum) {
		this.unReadNum = unReadNum;
	}
	public String getLastMSG() {
		return lastMSG;
	}
	public void setLastMSG(String lastMSG) {
		this.lastMSG = lastMSG;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	

}


