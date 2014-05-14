package com.XMPP.Activity.ChatRoom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

public class ChatTextChangeListener implements TextWatcher{

	Context mContext;
	public ChatTextChangeListener(Context context){
		this.mContext = context;
	}
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		// send a meesage to friend that you are typing
		// u can set a time span to limit the notify frequence;
		
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		if(s.length() == 0){
			//relpace the icon 
			((ChatRoomActivity)mContext).setMicphoneButton();
			
		}else{			
			((ChatRoomActivity)mContext).setSendButton();			
		}
	}

}
