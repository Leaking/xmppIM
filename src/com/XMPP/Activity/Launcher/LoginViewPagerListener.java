package com.XMPP.Activity.Launcher;

import android.support.v4.view.ViewPager.OnPageChangeListener;

public class LoginViewPagerListener implements OnPageChangeListener {
	/**
	 * 标志viewPager滑动方向的常量
	 */
	private static final int LEFT_TO_RIGHT = 1;
	private static final int RIGHT_TO_LEFT = 2;
	/**
	 * preValue : 记录正在滑动的view的偏移量（前一个），
	 * proValue : 记录正在滑动的view的偏移量（当前）
	 * pre_pro_choice : 用来标志当前view偏移量应该赋值给preValue还是proValue
	 * lock ：记录是否需要继续更新preValue 和 proValue。（只需记录前两个值，即可判断方向）
	 * direction ： 记录方向，1=由左向右，2=由右向左
	 */
	private float preValue = 0;
	private float proValue = 0;
	private int pre_pro_choice = 1;
	private int lock = 0;
	private int direction = 0;
	
	public int getDirection(){
		
		
		return 0;
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
		
	}

}
