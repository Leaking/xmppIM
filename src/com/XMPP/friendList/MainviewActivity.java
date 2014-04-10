package com.XMPP.friendList;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

import com.XMPP.R;
import com.XMPP.smack.Smack;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoSocialIcon;
import com.atermenji.android.iconicdroid.icon.Icon;
import com.atermenji.android.iconicdroid.icon.IconicIcon;

public class MainviewActivity extends Activity {
	private String username;
	private Smack smack;
	int a;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mainview);
		initFooter();
		
//		smack = new SmackImpl();
//		smack.refreshConnection();
//		username = smack.getConnection().getUser();		
//		Collection<RosterEntry> rosters = smack.getConnection().getRoster().getEntries();  
//		System.out.println("我的好友列表�?=======================");  
//		for(RosterEntry rosterEntry : rosters){  
//		    System.out.print("name: "+rosterEntry.getName()+",jid: "+rosterEntry.getUser());  
//		    System.out.println("");  
//		}  
		
	}

	public void initFooter(){
		IconicFontDrawable footerChatDrawable = new IconicFontDrawable(getBaseContext());
		footerChatDrawable.setIcon(IconicIcon.CHAT_INV);
		footerChatDrawable.setIconColor(Color.WHITE);

		findViewById(R.id.Footer_chattingIcon).setBackground(footerChatDrawable);
	
		IconicFontDrawable footerContactsDrawable = new IconicFontDrawable(getBaseContext());
		footerContactsDrawable.setIcon(IconicIcon.USER);
		footerContactsDrawable.setIconColor(Color.WHITE);

		findViewById(R.id.Footer_contactsIcon).setBackground(footerContactsDrawable);
	
		IconicFontDrawable footerSettingsDrawable = new IconicFontDrawable(getBaseContext());
		footerSettingsDrawable.setIcon(IconicIcon.COG);
		footerSettingsDrawable.setIconColor(Color.WHITE);

		findViewById(R.id.Footer_settingIcon).setBackground(footerSettingsDrawable);
	
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.firends_list, menu);
		return true;
	}

}
