package quinn.xmpp.widget;

import quinn.xmpp.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.widget.EditText;

public class CleanableEditText extends EditText {

	private TextWatcherCallBack mCallback;
	private Drawable mDrawable;
	private Context mContext;

	public void setCallBack(TextWatcherCallBack mCallback) {
		this.mCallback = mCallback;
	}

	public CleanableEditText(Context context) {
		super(context);
		this.mContext = context;
		init();
	}

	public CleanableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		init();
	}

	public CleanableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		init();
	}

	public void init() {
		mCallback = null;
		setFocusable(true);

		mDrawable = mContext.getResources().getDrawable(R.drawable.ic_clear);
		TextWatcher textWatcher = new TextWatcherAdapter() {

			@Override
			public void afterTextChanged(Editable s) {
				if (length() < 1)
					hideClearIcon();
				else
					showClearIcon();
				if (mCallback != null)
					mCallback.handleMoreTextChanged();
			}
		};
		addTextChangedListener(textWatcher);
	}

	public void hideClearIcon() {
		setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

	}

	public void showClearIcon() {
		setCompoundDrawablesWithIntrinsicBounds(null, null, mDrawable, null);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
//		boolean isInTarget = event.getX() > getWidth() - getPaddingRight() - mDrawable.getIntrinsicWidth();
//        if (isInTarget && getCompoundDrawables()[2] != null) {
//        	requestFocus();
//            setText("");
//            return true;
//        }
        return super.onTouchEvent(event);
	}

	@Override
	protected void finalize() throws Throwable {
		mDrawable = null;
		super.finalize();
	}
}
