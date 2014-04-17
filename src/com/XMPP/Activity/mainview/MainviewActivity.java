package com.XMPP.Activity.mainview;

import java.util.ArrayList;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterGroup;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

import com.XMPP.R;
import com.XMPP.service.GroupProfile;
import com.XMPP.service.Group_FriendService;
import com.XMPP.smack.ConnectionHandler;
import com.XMPP.smack.Smack;
import com.XMPP.smack.SmackImpl;
import com.XMPP.util.L;
import com.XMPP.util.Test;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.IconicIcon;

public class MainviewActivity extends FragmentActivity implements
		OnPageChangeListener, OnClickListener,ContactsFragment.RosterGroupCallback{
	private String username;
	private Smack smack;
	// three kind of the fragment to fill the content part
	private final static int TYPE_CHATTING_FRAGMENT = 0;
	private final static int TYPE_CONTACTS_FRAGMENT = 1;
	private final static int TYPE_SETTING_FRAGMENT = 2;
	// three icon in the footer part
	private ImageView footer_chatting_icon;
	private ImageView footer_contacts_icon;
	private ImageView footer_setting_icon;
	// last_fragment reference to the last choosen fragment
	// current_fragment reference to the current choosen fragment
	private int last_fragment = 0;
	private int current_fragment = 1;
	private final static int LIGHT_UP = 1;
	private final static int LIGHT_DOWN = 0;
	// footer icon
	private IconicFontDrawable footerChatDrawable;
	private IconicFontDrawable footerContactsDrawable;
	private IconicFontDrawable footerSettingsDrawable;
	// color of the three icon in the footer view32bcb6
	private final static int LIGHT_UP_COLOR = Color.rgb(0xff, 0xff, 0xff);
	private final static int LIGHT_DOWN_COLOR = Color.rgb(0x32, 0xBC, 0xcc);
	// sum of the fragments in the viewpager
	private final static int NUM_PAGES = 3;
	// viewpager
	private ViewPager vPager;
	//service
	Group_FriendService mService;
	boolean mBound = false;
	//
	ArrayList<RosterGroup> groupList;
	
	//
	ArrayList<GroupProfile> gP_List;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mainview);

		vPager = (ViewPager) findViewById(R.id.mainview_pager);
		vPager.setOffscreenPageLimit(3);
		vPager.setAdapter(new ScreenSlidePagerAdapter(
				getSupportFragmentManager()));
		vPager.setOnPageChangeListener(this);
		initFooter();
		registIconListerner();
		updateContentFragment(TYPE_CHATTING_FRAGMENT);
		L.d("onCreate  line 87");

		Intent intent = getIntent();
		gP_List = (ArrayList<GroupProfile>) intent.getSerializableExtra("GroupList");
		Test.outputGroupList(gP_List);
		smack = new SmackImpl();
		smack.setConnection(ConnectionHandler.getConnection());

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
				footerChatDrawable.setIconColor(LIGHT_UP_COLOR);

			} else if (upOrDown == LIGHT_DOWN) {
				footerChatDrawable.setIconColor(LIGHT_DOWN_COLOR);

			}
			break;
		}
		case TYPE_CONTACTS_FRAGMENT: {
			if (upOrDown == LIGHT_UP) {
				footerContactsDrawable.setIconColor(LIGHT_UP_COLOR);
			} else if (upOrDown == LIGHT_DOWN) {
				footerContactsDrawable.setIconColor(LIGHT_DOWN_COLOR);
			}
			break;
		}
		case TYPE_SETTING_FRAGMENT: {
			if (upOrDown == LIGHT_UP) {
				footerSettingsDrawable.setIconColor(LIGHT_UP_COLOR);
			} else if (upOrDown == LIGHT_DOWN) {
				footerSettingsDrawable.setIconColor(LIGHT_DOWN_COLOR);
			}
			break;
		}
		}
	}

	public void updateContentFragment(int fragmentType) {
		switch (fragmentType) {
		case TYPE_CHATTING_FRAGMENT:
			vPager.setCurrentItem(TYPE_CHATTING_FRAGMENT);
			last_fragment = current_fragment;
			current_fragment = TYPE_CHATTING_FRAGMENT;
			if (current_fragment != last_fragment) {
				updatefooterIcon(last_fragment, LIGHT_DOWN);
				updatefooterIcon(TYPE_CHATTING_FRAGMENT, LIGHT_UP);
			}
			break;
		case TYPE_CONTACTS_FRAGMENT:
			vPager.setCurrentItem(TYPE_CONTACTS_FRAGMENT);
			last_fragment = current_fragment;
			current_fragment = TYPE_CONTACTS_FRAGMENT;
			if (current_fragment != last_fragment) {
				updatefooterIcon(last_fragment, LIGHT_DOWN);
				updatefooterIcon(TYPE_CONTACTS_FRAGMENT, LIGHT_UP);
			}
			break;
		case TYPE_SETTING_FRAGMENT:
			vPager.setCurrentItem(TYPE_SETTING_FRAGMENT);
			last_fragment = current_fragment;
			current_fragment = TYPE_SETTING_FRAGMENT;
			if (current_fragment != last_fragment) {
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
		footerChatDrawable.setIconColor(LIGHT_UP_COLOR);
		footer_chatting_icon = (ImageView) findViewById(R.id.Footer_chattingIcon);
		footer_chatting_icon.setBackground(footerChatDrawable);

		footerContactsDrawable = new IconicFontDrawable(getBaseContext());
		footerContactsDrawable.setIcon(IconicIcon.USER);
		footerContactsDrawable.setIconColor(LIGHT_DOWN_COLOR);
		footer_contacts_icon = (ImageView) findViewById(R.id.Footer_contactsIcon);
		footer_contacts_icon.setBackground(footerContactsDrawable);

		footerSettingsDrawable = new IconicFontDrawable(getBaseContext());
		footerSettingsDrawable.setIcon(IconicIcon.COG);
		footerSettingsDrawable.setIconColor(LIGHT_DOWN_COLOR);
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
			break;
		case R.id.Footer_contactsIcon:
			updateContentFragment(TYPE_CONTACTS_FRAGMENT);
			break;
		case R.id.Footer_settingIcon:
			updateContentFragment(TYPE_SETTING_FRAGMENT);
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		System.out.println("choosen   " + arg0);
		last_fragment = current_fragment;
		current_fragment = arg0;
		if (current_fragment != last_fragment) {
			updatefooterIcon(last_fragment, LIGHT_DOWN);
			updatefooterIcon(arg0, LIGHT_UP);
		}

	}

	/** Defines callbacks for service binding, passed to bindService() */
	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			// We've bound to LoginService, cast the IBinder and get
			// LoginService instance
			Group_FriendService.LocalBinder binder = (Group_FriendService.LocalBinder) service;
			L.d("onServiceConnected  line 276");
			mService = binder.getService();
			mBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			mBound = false;
		}
	};

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// Unbind from the service
		if (mBound) {
			unbindService(mConnection);
			mBound = false;
		}
	}

	@Override
	public ArrayList<GroupProfile> getGroupList() {
		// TODO Auto-generated method stub
		return gP_List;
	}
}
