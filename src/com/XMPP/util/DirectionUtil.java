package com.XMPP.util;

public class DirectionUtil {
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

	public void reset(){
		preValue = 0;
		preValue = 0;
		pre_pro_choice = 1;
		lock = 0;
		direction = 0;
	}
	public void refresh(int arg1){
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
	
	public float getPreValue() {
		return preValue;
	}

	public void setPreValue(float preValue) {
		this.preValue = preValue;
	}

	public float getProValue() {
		return proValue;
	}

	public void setProValue(float proValue) {
		this.proValue = proValue;
	}

	public int getPre_pro_choice() {
		return pre_pro_choice;
	}

	public void setPre_pro_choice(int pre_pro_choice) {
		this.pre_pro_choice = pre_pro_choice;
	}

	public int getLock() {
		return lock;
	}

	public void setLock(int lock) {
		this.lock = lock;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

}
