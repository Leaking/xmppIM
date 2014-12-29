package quinn.xmpp.model;

import com.XMPP.R;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class SettingItemOnTouchListener implements OnTouchListener {

	private int pressResID;
	private int defaultResID;
	private View view;

	public SettingItemOnTouchListener(View view, int pressResID,
			int defaultResID) {
		this.view = view;
		this.pressResID = pressResID;
		this.defaultResID = defaultResID;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			view.setBackgroundResource(pressResID);
			return true;
		} else {
			view.setBackgroundResource(defaultResID);
		}
		return false;
	}

}
