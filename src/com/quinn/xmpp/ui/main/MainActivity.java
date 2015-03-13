package com.quinn.xmpp.ui.main;

import java.lang.reflect.Method;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.quinn.xmpp.R;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.main.MainPagerChangeListener.PagerCallback;
import com.quinn.xmpp.ui.widget.FooterTextIcon;
import com.quinn.xmpp.ui.widget.SlidingTabLayout;

public class MainActivity extends BaseActivity implements PagerCallback {

	private final static int NUM_ITEMS = 3;

	@InjectView(R.id.vPager)
	ViewPager viewpager;

	@InjectView(R.id.sliding_tabs)
	SlidingTabLayout slidingTabLayout;

	private String titles[] = new String[] { "chatting", "contacts", "setting" };

	private MyAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
		mAdapter = new MyAdapter(getSupportFragmentManager(), titles);
		viewpager.setAdapter(mAdapter);
		viewpager.setOnPageChangeListener(new MainPagerChangeListener(this));
		changePageColor(0, 255);
		setOverflowButtonAlways();
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("XMPP");
		setSupportActionBar(toolbar);

		DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);

		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
				mDrawerLayout, toolbar, R.string.setting_leftTxt_openfireIP,
				R.string.setting_leftTxt_openfireIP);
		toggle.syncState();
		mDrawerLayout.setDrawerListener(toggle);

		slidingTabLayout.setViewPager(viewpager);
		slidingTabLayout
				.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
					@Override
					public int getIndicatorColor(int position) {
						return Color.WHITE;
					}
				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void setOverflowButtonAlways() {
		// try
		// {
		// ViewConfiguration config = ViewConfiguration.get(this);
		// Field menuKey = ViewConfiguration.class
		// .getDeclaredField("sHasPermanentMenuKey");
		// menuKey.setAccessible(true);
		// menuKey.setBoolean(config, false);
		// } catch (Exception e)
		// {
		// e.printStackTrace();
		// }
	}

	/**
	 * 设置menu显示icon
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {

		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
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

	public static class MyAdapter extends FragmentPagerAdapter {
		public MyAdapter(FragmentManager fm, String[] titles2) {
			super(fm);
			titles = titles2;
		}

		private String titles[];

		@Override
		public int getCount() {
			return NUM_ITEMS;
		}

		public CharSequence getPageTitle(int position) {
			return titles[position];
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

	}

}
