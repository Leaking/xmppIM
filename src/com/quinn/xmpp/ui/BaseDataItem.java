package com.quinn.xmpp.ui;

import java.io.Serializable;

/**
 * @author Quinn
 * @date 2015-3-19
 */
public class BaseDataItem implements Serializable{
	
	// item type index of the drawer listview
	public final static int DRAWERITEM_TYPE_HEADER = 0;
	public final static int DRAWERITEM_TYPE_FUNCTION = 1;
	// item type index of the chatting bubble
	
	
	//
	
	protected  int itemType;

	public int getItemType() {
		return itemType;
	}

	public void setItemType(int itemType) {
		this.itemType = itemType;
	}
	
	
}


