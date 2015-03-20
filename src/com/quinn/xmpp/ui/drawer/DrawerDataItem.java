package com.quinn.xmpp.ui.drawer;

import com.atermenji.android.iconicdroid.icon.Icon;
import com.quinn.xmpp.ui.BaseDataItem;

/**
 * @author Quinn
 * @date 2015-3-19
 * 
 */
public class DrawerDataItem extends BaseDataItem{
	
	
	private Icon functionIconId;
	private String functionName;
	
	
	public DrawerDataItem(Icon functionIconId,String functionName){
		this.functionIconId = functionIconId;
		this.functionName = functionName;
	}
	
	/**
	 * 
	 */
	public DrawerDataItem() {
	}

	public Icon getFunctionIconId() {
		return functionIconId;
	}
	public void setFunctionIconId(Icon functionIconId) {
		this.functionIconId = functionIconId;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	
	
}


