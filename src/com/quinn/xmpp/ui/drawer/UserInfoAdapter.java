package com.quinn.xmpp.ui.drawer;

import android.content.Context;
import android.view.LayoutInflater;

/**
 * @author Quinn
 * @date 2015-3-24
 * 
 * Adapter of userInfoRecyclerView
 */
public class UserInfoAdapter {

	private UserVCard userVCard;
	private Context context;
	private LayoutInflater layoutInflater;
	
	public UserInfoAdapter(Context context,UserVCard userVCard){
		this.userVCard = userVCard;
		this.context = context;
		this.layoutInflater = LayoutInflater.from(context);
	}
	
	
}


