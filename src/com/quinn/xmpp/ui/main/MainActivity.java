package com.quinn.xmpp.ui.main;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.quinn.xmpp.R;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.main.MainPagerChangeListener.PagerCallback;
import com.quinn.xmpp.ui.widget.FooterTextIcon;

public class MainActivity extends BaseActivity implements PagerCallback {

	private final static int NUM_ITEMS = 3;

	@InjectView(R.id.vPager)
	ViewPager viewpager;
	@InjectView(R.id.chattingIcon)
	FooterTextIcon chattingIcon;
	@InjectView(R.id.contactsIcon)
	FooterTextIcon contactsIcon;
	@InjectView(R.id.settingIcon)
	FooterTextIcon settingIcon;

	private MyAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
		mAdapter = new MyAdapter(getSupportFragmentManager());
		viewpager.setAdapter(mAdapter);
		viewpager.setOnPageChangeListener(new MainPagerChangeListener(this));
		changePageColor(0, 255);
		setOverflowButtonAlways();
		getActionBar().setDisplayShowHomeEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void setOverflowButtonAlways()
	{
//		try
//		{
//			ViewConfiguration config = ViewConfiguration.get(this);
//			Field menuKey = ViewConfiguration.class
//					.getDeclaredField("sHasPermanentMenuKey");
//			menuKey.setAccessible(true);
//			menuKey.setBoolean(config, false);
//		} catch (Exception e)
//		{
//			e.printStackTrace();
//		}
	}

	/**
	 * 设置menu显示icon
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu)
	{

		if (featureId == Window.FEATURE_ACTION_BAR && menu != null)
		{
			if (menu.getClass().getSimpleName().equals("MenuBuilder"))
			{
				try
				{
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

		return super.onMenuOpened(featureId, menu);
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



	public static class MyAdapter extends FragmentStatePagerAdapter {
		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new ChattingFragment();
			case 1:
				return new ContactsFragment();
			case 2:
				return new SettingFragment();
			default:
				return new SettingFragment();
			}
		}
	}

	@Override
	public void changePageColor(int index, int alpha) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			chattingIcon.setIconAlpha(alpha);
			break;
		case 1:
			contactsIcon.setIconAlpha(alpha);
			break;
		case 2:
			settingIcon.setIconAlpha(alpha);
			break;
		}
	}
	
	@OnClick(R.id.chattingIcon)
	public void onChattingIcon(){
		changePageColor(0, 255);
		viewpager.setCurrentItem(0, true);
	}
	
	@OnClick(R.id.contactsIcon)
	public void onContactsIcon(){
		changePageColor(1, 255);
		viewpager.setCurrentItem(1, true);

	}
	
	@OnClick(R.id.settingIcon)
	public void onSettingIcon(){
		changePageColor(2, 255);
		viewpager.setCurrentItem(2, true);

	}

}
