package quinn.xmpp.activity.laucher;

import quinn.xmpp.smack.Smack;
import quinn.xmpp.smack.SmackImpl;
import quinn.xmpp.utils.Constants;
import quinn.xmpp.utils.L;
import quinn.xmpp.utils.LoadingDialog;
import quinn.xmpp.utils.T;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.XMPP.R;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.IconicIcon;

public class SignUpActivity extends FragmentActivity implements OnClickListener {

	EditText username;
	EditText password;
	EditText repeat_psw;
	Button cancelBtn;
	Button okBtn;
	Smack smack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sign_up);
		initUI();
		setWidget();
		smack = SmackImpl.getInstance();
		okBtn.setOnClickListener(this);
		cancelBtn.setOnClickListener(this);
	}

	public void setWidget() {
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

		if (v.getId() == R.id.sure_signUp) {
			final String usernameStr = username.getText().toString();
			final String passwordStr = password.getText().toString();
			String repeatPswStr = repeat_psw.getText().toString();
			if (usernameStr.length() == 0 || passwordStr.length() == 0
					|| repeatPswStr.length() == 0) {
				T.mToast(this, "please iput essential info");
			}
			if (passwordStr.equals(repeatPswStr) == false) {
				T.mToast(this, "please check the password");
			} else {
				final LoadingDialog loading = new LoadingDialog(
						SignUpActivity.this, "sign in,,");
				loading.show(SignUpActivity.this.getSupportFragmentManager(),
						"tag");
				loading.setCancelable(false);
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						L.i(Constants.SERVICE + " sign in "
								+ Constants.SERVER_IP + " "
								+ Constants.SERVER_PORT);
						smack.connect(Constants.SERVER_IP,
								Constants.SERVER_PORT, Constants.SERVICE);

						final String result = smack.regist(usernameStr, passwordStr);
						L.i("sign in " + result);
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								loading.dismiss();
								if(result.equals("0"))
									T.mToast(SignUpActivity.this, "Error");
								if(result.equals("1"))
									T.mToast(SignUpActivity.this,
											"Sign in successfully");
								if(result.equals("2"))
									T.mToast(SignUpActivity.this, "This user exists");
								if(result.equals("3"))
									T.mToast(SignUpActivity.this, "Error");
							}
						});
						
									
					}
				}).start();

			}

		} else {
			finish();
		}
	}

}
