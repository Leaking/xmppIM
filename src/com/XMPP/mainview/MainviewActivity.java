package com.XMPP.mainview;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.XMPP.R;
import com.XMPP.smack.Smack;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.IconicIcon;

public class MainviewActivity extends Activity implements OnClickListener{
	private String username;
	private Smack smack;
	//three kind of the fragment to fill the content part
	private final static int TYPE_CHATTING_FRAGMENT = 1;
	private final static int TYPE_CONTACTS_FRAGMENT = 2;
	private final static int TYPE_SETTING_FRAGMENT = 3;
	//three icon in the footer part
	private ImageView footer_chatting_icon;
	private ImageView footer_contacts_icon;
	private ImageView footer_setting_icon;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mainview);
		initFooter();
		registIconListerner();
		updateContentFragment(TYPE_CHATTING_FRAGMENT);
		
		
		
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
	/**
	 * three icon regist onclicklistern on this activity
	 */
	public void registIconListerner(){
		footer_chatting_icon.setOnClickListener(this);
		footer_contacts_icon.setOnClickListener(this);
		footer_setting_icon.setOnClickListener(this);
	}
	public void updateContentFragment(int fragmentType){
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		switch(fragmentType){
		case TYPE_CHATTING_FRAGMENT:
			ChattingFragment chattingFragment = new ChattingFragment();
			fragmentTransaction.replace(R.id.MainviewContent, chattingFragment);			
			break;
		case TYPE_CONTACTS_FRAGMENT:
			ContactsFragment contactsFragment = new ContactsFragment();
			fragmentTransaction.replace(R.id.MainviewContent, contactsFragment);			
			break;
		case TYPE_SETTING_FRAGMENT:
			SettingFragment settingFragment = new SettingFragment();
			fragmentTransaction.replace(R.id.MainviewContent, settingFragment);			
			break;
		}
		fragmentTransaction.commit();	

	}
	/**
	 * initial the footer view UI
	 */
	public void initFooter(){
		IconicFontDrawable footerChatDrawable = new IconicFontDrawable(getBaseContext());
		footerChatDrawable.setIcon(IconicIcon.CHAT_INV);
		footerChatDrawable.setIconColor(Color.WHITE);
		footer_chatting_icon = (ImageView)findViewById(R.id.Footer_chattingIcon);
		footer_chatting_icon.setBackground(footerChatDrawable);
	
		IconicFontDrawable footerContactsDrawable = new IconicFontDrawable(getBaseContext());
		footerContactsDrawable.setIcon(IconicIcon.USER);
		footerContactsDrawable.setIconColor(Color.WHITE);
		footer_contacts_icon = (ImageView)findViewById(R.id.Footer_contactsIcon);
		footer_contacts_icon.setBackground(footerContactsDrawable);
	
		IconicFontDrawable footerSettingsDrawable = new IconicFontDrawable(getBaseContext());
		footerSettingsDrawable.setIcon(IconicIcon.COG);
		footerSettingsDrawable.setIconColor(Color.WHITE);
		footer_setting_icon = (ImageView)findViewById(R.id.Footer_settingIcon);
		footer_setting_icon.setBackground(footerSettingsDrawable);	
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.firends_list, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.Footer_chattingIcon:
			updateContentFragment(TYPE_CHATTING_FRAGMENT);
			System.out.println("fffff111111111111111111");
			break;
		case R.id.Footer_contactsIcon:
			updateContentFragment(TYPE_CONTACTS_FRAGMENT);
			System.out.println("fffff22222222222222");
			break;
		case R.id.Footer_settingIcon:
			updateContentFragment(TYPE_SETTING_FRAGMENT);
			System.out.println("fffff133333333333333");
			break;
		}
	}

}
