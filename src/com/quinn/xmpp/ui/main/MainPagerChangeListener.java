/**
 * 2015-2-28
 * 2015-2-28
 */
package com.quinn.xmpp.ui.main;

import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * @author Quinn
 * @date 2015-2-28
 */
public class MainPagerChangeListener implements OnPageChangeListener{

	public interface PagerCallback{
		public void changePageColor(int index, int offset);
	}
	
	private PagerCallback callback;
	
	public MainPagerChangeListener(MainActivity mainActivity){
		this.callback  = (PagerCallback)mainActivity;
	}
	
	//顺序为1-2-0
	@Override
	public void onPageScrollStateChanged(int state) {
		
	}

	//启动就调用该方法
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		if(positionOffset != 0.0f){
			callback.changePageColor(position+1,  (int)(positionOffset * 255));
			callback.changePageColor(position,  (int)((1-positionOffset) * 255));
		}
	}

	//将滑动进入新page才会带哦用该方法，，
	//并且是在上面第一个方法 的1-2-0 中的2执行之后执行（即自动滑动到新界面时）
	//如果只是动一下，又回去，，，则不会调用
	@Override
	public void onPageSelected(int position) {

	}
}
