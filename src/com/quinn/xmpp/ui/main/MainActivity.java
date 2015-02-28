package com.quinn.xmpp.ui.main;

import java.util.Random;

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
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.quinn.xmpp.R;
import com.quinn.xmpp.ui.BaseActivity;

public class MainActivity extends BaseActivity implements OnPageChangeListener {

	private final static int NUM_ITEMS = 3;

	@InjectView(R.id.vPager)
	ViewPager viewpager;
	@InjectView(R.id.chattingIcon)
	ImageView chattingIcon;
	@InjectView(R.id.contactsIcon)
	ImageView contactsIcon;
	@InjectView(R.id.settingIcon)
	ImageView settingIcon;

	private MyAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);
		mAdapter = new MyAdapter(getSupportFragmentManager());
		viewpager.setAdapter(mAdapter);
		viewpager.setOnPageChangeListener(new MainPagerChangeListener());
		
		chattingIcon.setImageBitmap(drawColorBitmap(R.drawable.ic_action_chat,
				Color.GREEN));
		contactsIcon.setImageBitmap(drawColorBitmap(
				R.drawable.ic_action_cc_bcc, Color.GREEN));
		settingIcon.setImageBitmap(drawColorBitmap(
				R.drawable.ic_action_settings, Color.GREEN));
		

		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

	public Bitmap drawColorBitmap(int rsid, int color) {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), rsid);
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(color);
		canvas.drawRect(0, 0, bitmap.getWidth(), bitmap.getHeight(), paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		canvas.drawBitmap(bitmap, 0, 0, paint);
		bitmap.recycle();
		return output;
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
	public void onPageScrollStateChanged(int state) {
		System.out.println("onPageScrollStateChanged state = "  + state);
		//顺序为1-2-0
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		System.out.println("onPageScrolled positio2n = "  + position);
		System.out.println("onPageScrolled positionOff2set = "  + positionOffset);
		System.out.println("onPageScrolled positionOffs4etPixels = "  + positionOffsetPixels);

	}

	@Override
	public void onPageSelected(int position) {
		System.out.println("onPageSelected position = "  + position);

	}

}
