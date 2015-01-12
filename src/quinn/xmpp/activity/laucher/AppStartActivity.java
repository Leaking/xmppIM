package quinn.xmpp.activity.laucher;

import java.util.ArrayList;
import java.util.List;

import quinn.xmpp.utils.StatusBarUtil;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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

public class AppStartActivity extends FragmentActivity implements
		OnPageChangeListener {

	// viewPager�а��page������.testtest 222
	private static final int NUM_PAGES = 5;
	// viewPager
	private ViewPager mPager;
	// pagerAdapter
	private PagerAdapter mPagerAdapter;
	// ����itemsС��Ķ���?
	private List<ImageView> views;
	// ��Ļ�ײ����ֵ�view
	private View buttomFrame;
	// buttonLogin�ĸ߶�
	private int buttomViewHeight;
	// ��½��ť
	private Button loginButton;
	// ע�ᰴť
	private Button signUpButton;
    // ��½������
	private LoginListener loginListener;
	
	
	/**
	 * ��־viewPager��������ĳ���?
	 */
	private static final int LEFT_TO_RIGHT = 1;
	private static final int RIGHT_TO_LEFT = 2;
	/**
	 * preValue : ��¼���ڻ�����view��ƫ������ǰһ������ proValue : ��¼���ڻ�����view��ƫ��������ǰ��
	 * pre_pro_choice : ������־��ǰviewƫ����Ӧ�ø�ֵ��preValue����proValue lock
	 * ����¼�Ƿ���Ҫ�������preValue �� proValue����ֻ���¼ǰ����ֵ�������жϷ���? direction ��
	 * ��¼����1=�������ң�2=��������
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
		// ȥ���������ע�⣬����? ��������setContentView֮ǰ����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		setContentView(R.layout.app_start_activity);

		buttomViewHeight = (int) getResources().getDimension(
				R.dimen.startActivityButtonViewHeight);

		// ��ʼ�����в���ȡitemС��
		views = new ArrayList<ImageView>();
		views.add((ImageView) findViewById(R.id.item1));
		views.add((ImageView) findViewById(R.id.item2));
		views.add((ImageView) findViewById(R.id.item3));
		views.add((ImageView) findViewById(R.id.item4));
		views.add((ImageView) findViewById(R.id.item5));

		// ��õײ������Լ����е��������?
		buttomFrame = findViewById(R.id.buttonLogin);
		loginButton = (Button)findViewById(R.id.buttomLogin);
		signUpButton = (Button)findViewById(R.id.buttomSignUp);
		loginListener = new LoginListener(this);
		regist(loginButton);

		// ��ʼ��viewAdatper��viewPager
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setOffscreenPageLimit(5);
		mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		mPager.setAdapter(mPagerAdapter);
		// ��Ӽ���?
		mPager.setOnPageChangeListener(this);
		// ��ʼ��itemС���ɫ�ʡ�?
		lightNextDot(0);
	}

	public void regist(View view){
		loginListener.add(view);
	}
	
	// ���û������ֻ�ķ��ذ�ťʱ����Ӧ��ͬpager������ͬ��Ӧ
	@Override
	public void onBackPressed() {
		if (mPager.getCurrentItem() == 0) {
			// ��pagerView��ʾ�ĵ�һ��pageʱ�����ذ�ť��������-----���ص��ֻ�home����
			super.onBackPressed();
		} else {
			// ��pagerView��ʾ�ĵڶ����߸���pageʱ�����ذ�ť��������-----������һ��page
			mPager.setCurrentItem(mPager.getCurrentItem() - 1);
		}
	}

	// �޸ĵ�����dot
	
	public void lightNextDot(int position) {
		for (int i = 0; i < NUM_PAGES; i++) {
			// ���ڵ�һ��pageʱ������dot������ʾ��ɫ
			if (position == 0) {
				views.get(i).setColorFilter(
						getResources().getColor(R.color.white));
				continue;
			}
			// ������dot����
			if (i == position) {
				views.get(i).setColorFilter(
						getResources().getColor(R.color.dot_big_black));
				// ��ʼ��
				Animation scaleAnimation = new ScaleAnimation(1.0f, 3/2f, 1.0f,
						3/2f, Animation.RELATIVE_TO_SELF, 0.5f,
						Animation.RELATIVE_TO_SELF, 0.5f);
				// ���ö���ʱ��
				scaleAnimation.setFillAfter(true);
				scaleAnimation.setDuration(500);
				views.get(i).startAnimation(scaleAnimation);
				if (direction_ed == 1) {
					Animation scaleDownAnimation = new ScaleAnimation(1.0f, 2/3f,
							1.0f, 2/3f, Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					// ���ö���ʱ��
					scaleDownAnimation.setFillAfter(true);
					scaleDownAnimation.setDuration(500);
					views.get(position - 1).startAnimation(scaleDownAnimation);
				}
				if (direction_ed == 2) {
					Animation scaleDownAnimation = new ScaleAnimation(1.0f, 2/3f,
							1.0f, 2/3f, Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					// ���ö���ʱ��
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
				return new More_NetWorkSettingFragment();
			} else if (position == 2)
				return new LearnMoreFragment_B();
			else if(position == 3)
				return new LearnMoreFragment_C();
			else 
				return new LearnMoreFragment_D();
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}


	/**
	 * ��⻬������?
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