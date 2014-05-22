package com.XMPP.Activity.Launcher;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.XMPP.R;
import com.XMPP.util.Constants;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.IconicIcon;

public class SignUpActivity extends Activity implements OnClickListener {

	EditText username;
	EditText password;
	EditText repeat_psw;
	Button cancelBtn;
	Button okBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sign_up);
		initUI();
		setWidget();
		okBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
	}
	
	
	public void setWidget(){
		username = (EditText) findViewById(R.id.username_edit);
		password = (EditText) findViewById(R.id.password_edit);
		repeat_psw = (EditText) findViewById(R.id.re_password_edit);
		cancelBtn = (Button) findViewById(R.id.cancel_signUp);
		okBtn = (Button) findViewById(R.id.sure_signUp);
	}
	public void initUI() {
		ImageView image_user = (ImageView) findViewById(R.id.user_icon);
		ImageView image_psw = (ImageView) findViewById(R.id.psw_icon);
		ImageView image_re_psw = (ImageView) findViewById(R.id.re_psw_icon);

		IconicFontDrawable iconDraw_user = new IconicFontDrawable(this);
		IconicFontDrawable iconDraw_psw = new IconicFontDrawable(this);
		IconicFontDrawable iconDraw_re_psw = new IconicFontDrawable(this);

		iconDraw_user.setIcon(IconicIcon.USER);
		iconDraw_user.setIconColor(Constants.COLOR_COMMON_BLUE);
		image_user.setBackground(iconDraw_user);

		iconDraw_psw.setIcon(IconicIcon.LOCK);
		iconDraw_psw.setIconColor(Constants.COLOR_COMMON_BLUE);
		image_psw.setBackground(iconDraw_psw);

		iconDraw_re_psw.setIcon(IconicIcon.LOCK);
		iconDraw_re_psw.setIconColor(Constants.COLOR_COMMON_RED);
		image_re_psw.setBackground(iconDraw_re_psw);
	}


	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
