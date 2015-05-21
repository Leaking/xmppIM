package com.quinn.xmpp.orm;

public class RowChatting {
	
	private String i_JID;
	private String u_JID;
	private String unReadNum;
	private String lastMSG;
	private String lastTime;
	
	public RowChatting(String i_JID,String u_JID,String unReadNum,String lastMSG,String lastTime){
		this.i_JID = i_JID;
		this.u_JID = u_JID;
		this.unReadNum = unReadNum;
		this.lastMSG = lastMSG;
		this.lastTime = lastTime;
	}

	public String getI_JID() {
		return i_JID;
	}

	public void setI_JID(String i_JID) {
		this.i_JID = i_JID;
	}

	public String getU_JID() {
		return u_JID;
	}

	public void setU_JID(String u_JID) {
		this.u_JID = u_JID;
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
