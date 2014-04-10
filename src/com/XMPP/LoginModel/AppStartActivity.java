package com.XMPP.LoginModel;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.XMPP.R;
import com.XMPP.R.color;
import com.XMPP.LoginModel.LearnMore.LearnMoreFragment_A;
import com.XMPP.LoginModel.LearnMore.LearnMoreFragment_B;
import com.XMPP.LoginModel.LearnMore.LearnMoreFragment_C;
import com.XMPP.util.StatusBarUtil;

public class AppStartActivity extends FragmentActivity implements
		OnPageChangeListener {

	// viewPager中包含的page的数量.
	private static final int NUM_PAGES = 5;
	// viewPager
	private ViewPager mPager;
	// pagerAdapter
	private PagerAdapter mPagerAdapter;
	// 保存items小点的队列
	private List<ImageView> views;
	// 屏幕底部浮现的view
	private View buttomFrame;
	// buttonLogin的高度
	private int buttomViewHeight;
	// 登陆按钮
	private Button loginButton;
	// 注册按钮
	private Button signUpButton;
    // 登陆监听器
	private LoginListener loginListener;
	
	
	/**
	 * 标志viewPager滑动方向的常量
	 */
	private static final int LEFT_TO_RIGHT = 1;
	private static final int RIGHT_TO_LEFT = 2;
	/**
	 * preValue : 记录正在滑动的view的偏移量（前一个）， proValue : 记录正在滑动的view的偏移量（当前）
	 * pre_pro_choice : 用来标志当前view偏移量应该赋值给preValue还是proValue lock
	 * ：记录是否需要继续更新preValue 和 proValue。（只需记录前两个值，即可判断方向） direction ：
	 * 记录方向，1=由左向右，2=由右向左
	 */
	private float preValue = 0;
	private float proValue = 0;
	private int pre_pro_choice = 1;
	private int lock = 0;
	private int direction = 0;
	private int direction_ed = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除标题栏，注意，这条 语句必须在setContentView之前调用
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.app_start_activity);

		buttomViewHeight = (int) getResources().getDimension(
				R.dimen.startActivityButtonViewHeight);

		// 初始化队列并获取item小点
		views = new ArrayList<ImageView>();
		views.add((ImageView) findViewById(R.id.item1));
		views.add((ImageView) findViewById(R.id.item2));
		views.add((ImageView) findViewById(R.id.item3));
		views.add((ImageView) findViewById(R.id.item4));
		views.add((ImageView) findViewById(R.id.item5));

		// 获得底部区域，以及其中的两个按钮
		buttomFrame = findViewById(R.id.buttonLogin);
		loginButton = (Button)findViewById(R.id.buttomLogin);
		signUpButton = (Button)findViewById(R.id.buttomSignUp);
		loginListener = new LoginListener(this);
		regist(loginButton);

		// 初始化viewAdatper和viewPager
		mPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		// 添加监听
		mPager.setOnPageChangeListener(this);
		// 初始化item小点的色彩。
		lightNextDot(0);
	}

	public void regist(View view){
		loginListener.add(view);
	}
	
	// 当用户按下手机的返回按钮时，对应不同pager做出不同反应
	@Override
	public void onBackPressed() {
		if (mPager.getCurrentItem() == 0) {
			// 当pagerView显示的第一个page时，返回按钮的作用是-----返回到手机home界面
			super.onBackPressed();
		} else {
			// 当pagerView显示的第二或者更后的page时，返回按钮的作用是-----返回上一个page
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		}
	}

	// 修改点亮的dot
	
	public void lightNextDot(int position) {
		for (int i = 0; i < NUM_PAGES; i++) {
			// 当在第一个page时，所有dot都不显示颜色
			if (position == 0) {
				views.get(i).setColorFilter(
						getResources().getColor(R.color.white));
				continue;
			}
			// 点亮的dot右移
			if (i == position) {
				views.get(i).setColorFilter(
						getResources().getColor(R.color.dot_big_black));
				// 初始化
				Animation scaleAnimation = new ScaleAnimation(1.0f, 3/2f, 1.0f,
						3/2f, Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				// 设置动画时间
				scaleAnimation.setFillAfter(true);
				scaleAnimation.setDuration(500);
				views.get(i).startAnimation(scaleAnimation);
				if (direction_ed == 1) {
					Animation scaleDownAnimation = new ScaleAnimation(1.0f, 2/3f,
							1.0f, 2/3f, Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					// 设置动画时间
					scaleDownAnimation.setFillAfter(true);
					scaleDownAnimation.setDuration(500);
					views.get(position - 1).startAnimation(scaleDownAnimation);
				}
				if (direction_ed == 2) {
					Animation scaleDownAnimation = new ScaleAnimation(1.0f, 2/3f,
							1.0f, 2/3f, Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					// 设置动画时间
					scaleDownAnimation.setFillAfter(true);
					scaleDownAnimation.setDuration(1000);
					views.get(position + 1).startAnimation(scaleDownAnimation);
				}

			} else
				views.get(i).setColorFilter(
						getResources().getColor(R.color.dot_small_balck));

		}

	}

	/**
	 * A simple pager adapter that represents 5 ScreenSlidePageFragment objects,
	 * in sequence.
	 */
	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
		public ScreenSlidePagerAdapter(android.support.v4.app.FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0) {
				return new StartFragment();
			} else if (position == 1) {
				return new LearnMoreFragment_A();
			} else if (position == 2)
				return new LearnMoreFragment_B();
			else
				return new LearnMoreFragment_C();
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}


	/**
	 * 检测滑动方向
	 */
	public void checkDirection(float arg1) {
		if (pre_pro_choice == 1 && lock < 2) {
			preValue = arg1;
			pre_pro_choice = 2;
			++lock;
		} else if (pre_pro_choice == 2 && lock < 2) {
			proValue = arg1;
			pre_pro_choice = 1;
			++lock;
		}
		if (lock == 2) {
			if (preValue < proValue) {
				direction = 1;
			} else if (preValue > proValue)
				direction = 2;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		if (arg0 == 2 || arg0 == 0) {
			preValue = 0;
			preValue = 0;
			pre_pro_choice = 1;
			lock = 0;
			if(direction == LEFT_TO_RIGHT)
				direction_ed = LEFT_TO_RIGHT;
			else if(direction == RIGHT_TO_LEFT)
				direction_ed = RIGHT_TO_LEFT;
			direction = 0;
		}

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		checkDirection(arg1);
		RelativeLayout.LayoutParams r_layparm = new RelativeLayout.LayoutParams(
				RelativeLayout.LayoutParams.MATCH_PARENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);

		DisplayMetrics mDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
		int screenHeigthPixel = mDisplayMetrics.heightPixels;

		if (direction == LEFT_TO_RIGHT && arg1 != 0 && arg0 == 0) {
			
			for(int i = 0; i < NUM_PAGES; i++){
				views.get(i).setColorFilter(color.dot_small_balck);
				views.get(i).setAlpha(arg1);
			}
			
			r_layparm.topMargin = (int) (screenHeigthPixel
					- StatusBarUtil.getStatusBarHeight(AppStartActivity.this) - buttomViewHeight
					* arg1);
			buttomFrame.setLayoutParams(r_layparm);

		} else if (direction == RIGHT_TO_LEFT && arg1 != 0 && arg0 == 0) {
			for(int i = 0; i < NUM_PAGES; i++){
				views.get(i).setAlpha(arg1);
			}
			r_layparm.topMargin = (int) (screenHeigthPixel
					- StatusBarUtil.getStatusBarHeight(AppStartActivity.this)
					- buttomViewHeight + buttomViewHeight * (1 - arg1));
			buttomFrame.setLayoutParams(r_layparm);

		}

	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		lightNextDot(arg0);

	}
}