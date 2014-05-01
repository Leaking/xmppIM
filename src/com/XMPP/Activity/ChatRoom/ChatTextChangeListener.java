package com.XMPP.Activity.ChatRoom;

import android.text.Editable;
import android.text.TextWatcher;

import com.XMPP.util.L;

public class ChatTextChangeListener implements TextWatcher{

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		L.i("typing before");

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		// send a meesage to friend that you are typing
		// u can set a time span to limit the notify frequence;
		L.i("typing,typing typing");
	}

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		L.i("typing end");
	}

}
