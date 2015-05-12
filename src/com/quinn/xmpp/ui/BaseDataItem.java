package com.quinn.xmpp.ui;

import java.io.Serializable;

/**
 * 
 * 数据结点基类，主要保存结点类型
 * 
 * @author Quinn
 * @date 2015-3-19
 */
public class BaseDataItem implements Serializable{
	
	// item type index of the drawer listview
	public final static int DRAWERITEM_TYPE_HEADER = 0;
	public final static int DRAWERITEM_TYPE_FUNCTION = 1;
	// item type index of the chatting bubble
	public final static int LEFT_BUBBLE_TEXT = 101;
	public final static int RIGHT_BUBBLE_TEXT = 102;
	public final static int LEFT_BUBBLE_IMAGE = 103;
	public final static int RIGHT_BUBBLE_IMAGE = 104;
	public final static int LEFT_BUBBLE_FILE = 105;
	public final static int RIGHT_BUBBLE_FILE = 106;
	
	//
	protected String happenTime;
	
	protected  int itemType;
	protected String jid;

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	public String getHappenTime() {
		return happenTime;
	}

	public void setHappenTime(String happenTime) {
		this.happenTime = happenTime;
	}

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}
	
	
}


