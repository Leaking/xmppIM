package com.XMPP.mainview;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.XMPP.R;
import com.XMPP.R.color;
import com.XMPP.smack.Smack;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.IconicIcon;

public class MainviewActivity extends FragmentActivity implements OnClickListener {
	private String username;
	private Smack smack;
	// three kind of the fragment to fill the content part
	private final static int TYPE_CHATTING_FRAGMENT = 1;
	private final static int TYPE_CONTACTS_FRAGMENT = 2;
	private final static int TYPE_SETTING_FRAGMENT = 3;
	// three icon in the footer part
	private ImageView footer_chatting_icon;
	private ImageView footer_contacts_icon;
	private ImageView footer_setting_icon;
	// last_fragment reference to the last choosen fragment
	// current_fragment reference to the current choosen fragment
	private int last_fragment = 1;
	private int current_fragment = 2;
	private final static int LIGHT_UP = 1;
	private final static int LIGHT_DOWN = 0;
	// footer icon
	private IconicFontDrawable footerChatDrawable;
	private IconicFontDrawable footerContactsDrawable;
	private IconicFontDrawable footerSettingsDrawable;
	
	//
	private final static int NUM_PAGES = 3;
	//
	private ViewPager vPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mainview);
		initFooter();
		registIconListerner();
		updateContentFragment(TYPE_CHATTING_FRAGMENT);
		vPager = (ViewPager)findViewById(R.id.mainview_pager);
		vPager.setOffscreenPageLimit(3);
		vPager.setAdapter(new ScreenSlidePagerAdapter(getSupportFragmentManager()));
		
		
		
		// smack = new SmackImpl();
		// smack.refreshConnection();
		// username = smack.getConnection().getUser();
		// Collection<RosterEntry> rosters =
		// smack.getConnection().getRoster().getEntries();
		// System.out.println("我的好友列表�?=======================");
		// for(RosterEntry rosterEntry : rosters){
		// System.out.print("name: "+rosterEntry.getName()+",jid: "+rosterEntry.getUser());
		// System.out.println("");
		// }

	}

	/**
	 * three icon regist onclicklistern on this activity
	 */
	public void registIconListerner() {
		footer_chatting_icon.setOnClickListener(this);
		footer_contacts_icon.setOnClickListener(this);
		footer_setting_icon.setOnClickListener(this);
	}

	/**
	 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects,
	 * in sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0) {
				return new ChattingFragment();
			} else if (position == 1) {
				return new ContactsFragment();
			} else 
				return new SettingFragment();
			
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}
	
	
	
	public void updatefooterIcon(int index, int upOrDown) {
		switch (index) {
		case TYPE_CHATTING_FRAGMENT: {
			if (upOrDown == LIGHT_UP) {
				footerChatDrawable.setIconColor(color.pocket_gold);
			} else if (upOrDown == LIGHT_DOWN) {
				footerChatDrawable.setIconColor(Color.WHITE);
			}
			break;
		}
		case TYPE_CONTACTS_FRAGMENT: {
			if (upOrDown == LIGHT_UP) {
				footerContactsDrawable.setIconColor(color.pocket_gold);
			} else if (upOrDown == LIGHT_DOWN) {
				footerContactsDrawable.setIconColor(Color.WHITE);
			}
			break;
		}
		case TYPE_SETTING_FRAGMENT: {
			if (upOrDown == LIGHT_UP) {
				footerSettingsDrawable.setIconColor(color.pocket_gold);
			} else if (upOrDown == LIGHT_DOWN) {
				footerSettingsDrawable.setIconColor(Color.WHITE);
			}
			break;
		}
		}
	}

	public void updateContentFragment(int fragmentType) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		switch (fragmentType) {
		case TYPE_CHATTING_FRAGMENT:
			last_fragment = current_fragment;
			current_fragment = TYPE_CHATTING_FRAGMENT;
			if (current_fragment != last_fragment) {
				updatefooterIcon(last_fragment, LIGHT_DOWN);
				updatefooterIcon(TYPE_CHATTING_FRAGMENT, LIGHT_UP);
			}
			break;
		case TYPE_CONTACTS_FRAGMENT:
			last_fragment = current_fragment;
			current_fragment = TYPE_CONTACTS_FRAGMENT;
			if(current_fragment != last_fragment){
				updatefooterIcon(last_fragment, LIGHT_DOWN);
				updatefooterIcon(TYPE_CONTACTS_FRAGMENT, LIGHT_UP);	
			}
			break;
		case TYPE_SETTING_FRAGMENT:
			last_fragment = current_fragment;
			current_fragment = TYPE_SETTING_FRAGMENT;
			if(current_fragment != last_fragment){
				updatefooterIcon(last_fragment, LIGHT_DOWN);
				updatefooterIcon(TYPE_SETTING_FRAGMENT, LIGHT_UP);	
			}
			break;
		}

	}

	/**
	 * initial the footer view UI
	 */
	public void initFooter() {
		footerChatDrawable = new IconicFontDrawable(getBaseContext());
		footerChatDrawable.setIcon(IconicIcon.CHAT_INV);
		footerChatDrawable.setIconColor(Color.WHITE);
		footer_chatting_icon = (ImageView) findViewById(R.id.Footer_chattingIcon);
		footer_chatting_icon.setBackground(footerChatDrawable);

		footerContactsDrawable = new IconicFontDrawable(getBaseContext());
		footerContactsDrawable.setIcon(IconicIcon.USER);
		footerContactsDrawable.setIconColor(Color.WHITE);
		footer_contacts_icon = (ImageView) findViewById(R.id.Footer_contactsIcon);
		footer_contacts_icon.setBackground(footerContactsDrawable);

		footerSettingsDrawable = new IconicFontDrawable(getBaseContext());
		footerSettingsDrawable.setIcon(IconicIcon.COG);
		footerSettingsDrawable.setIconColor(Color.WHITE);
		footer_setting_icon = (ImageView) findViewById(R.id.Footer_settingIcon);
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
		switch (v.getId()) {
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
