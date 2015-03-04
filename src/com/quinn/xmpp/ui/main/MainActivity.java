package com.quinn.xmpp.ui.main;

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
import butterknife.ButterKnife;
import butterknife.InjectView;

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

		

		int color = Color.argb(255, 0, 255, 00);

//		final Handler handler = new Handler() {
//
//			@Override
//			public void handleMessage(Message msg) {
//				// TODO Auto-generated method stub
//				int alpha = (Integer) msg.obj;
//				int color = Color.argb(alpha, 0, 255, 00);
//				settingIcon.setImageBitmap(drawColorBitmap(
//						R.drawable.ic_action_settings, color));
//			}
//
//		};
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// // TODO Auto-generated method stub
		// int alpha = 10;
		// while(true){
		// try {
		// Thread.sleep(100);
		// alpha = (alpha + 10)%255;
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// Message msg = new Message();
		// msg.obj = alpha;
		// handler.sendMessage(msg);
		//
		// }
		// }
		// }).start();

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

		canvas.drawBitmap(drawSrcGrayColorBitmap(rsid, Color.YELLOW), 0, 0,
				paint);
		bitmap.recycle();
		return output;
	}

	public Bitmap drawSrcGrayColorBitmap(int rsid, int color) {
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

}
