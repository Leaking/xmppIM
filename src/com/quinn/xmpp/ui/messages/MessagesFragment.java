/**
 * 2015-2-28
 * 2015-2-28
 */
package com.quinn.xmpp.ui.messages;


import com.quinn.xmpp.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Quinn
 * @date 2015-2-28
 */
public class MessagesFragment extends Fragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_messages, container,
				false);
		
		return view;
	}

	
}
