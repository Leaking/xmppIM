/**
 * 2015-2-28
 * 2015-2-28
 */
package com.quinn.xmpp.ui.main;

import android.support.v4.view.ViewPager;
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
	private DirectionFinder finder;
	
	public MainPagerChangeListener(MainActivity mainActivity){
		this.callback  = (PagerCallback)mainActivity;
		finder = new DirectionFinder();
	}
	
	//顺序为1-2-0
	@Override
	public void onPageScrollStateChanged(int state) {
		System.out.println("call 1");
		System.out.println("onPageScrollStateChanged state = "  + state);
		if(state == ViewPager.SCROLL_STATE_DRAGGING || state == ViewPager.SCROLL_STATE_SETTLING){
			finder.enable();
		}else{
			finder.disable();
		}
	}

	//启动就调用该方法
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		System.out.println("positionOffset = " + positionOffset);

		int direction = finder.findDirection(positionOffsetPixels);
		if(positionOffset != 0.0f){
			callback.changePageColor(position+1,  (int)(positionOffset * 255));
			callback.changePageColor(position,  (int)((1-positionOffset) * 255));
		}

		if(direction == DirectionFinder.NONE){
			System.out.println(" Direction NONE");
		}
		
	}

	//将滑动进入新page才会带哦用该方法，，
	//并且是在上面第一个方法 的1-2-0 中的2执行之后执行（即自动滑动到新界面时）
	//如果只是动一下，又回去，，，则不会调用
	@Override
	public void onPageSelected(int position) {
		System.out.println("call 3");

		System.out.println("onPageSelected position = "  + position);

	}
	
	public static class DirectionFinder{
		final static int LEFT_2_RIGHT = 0;
		final static int RIGHT_2_LEFT = 1;
		final static int NONE = 2;
		final static int ORIGIN_VAL = -1;
		int lastVal = ORIGIN_VAL;
		//active为真时,才检测方向
		boolean active = false;
		
		public void disable(){
			active = false;
			lastVal = ORIGIN_VAL;
		}
		public void enable(){
			active = true;
		}
		
		public int findDirection(int currVal){
			if(active == false || currVal == 0)
				return NONE;
			
			//刚开始滑动
			int diffVal = currVal - lastVal;
			if(lastVal == ORIGIN_VAL){
				lastVal = currVal;
				return NONE;
			}else{
				lastVal = currVal;
				return diffVal > 0?LEFT_2_RIGHT:RIGHT_2_LEFT;
			}
		}
		
	}
	

}
