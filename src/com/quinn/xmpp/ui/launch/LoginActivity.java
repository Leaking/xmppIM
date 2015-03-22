package com.quinn.xmpp.ui.launch;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.quinn.xmpp.Intents;
import com.quinn.xmpp.R;
import com.quinn.xmpp.core.launch.ConnectTask;
import com.quinn.xmpp.core.launch.LoginTask;
import com.quinn.xmpp.persisitence.Preference;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.ToastUtils;
import com.quinn.xmpp.ui.main.MainActivity;
import com.quinn.xmpp.ui.widget.CleanableEditText;
import com.quinn.xmpp.ui.widget.ClearableAutoCompleteTextView;
import com.quinn.xmpp.ui.widget.SpinnerDialog;
import com.quinn.xmpp.ui.widget.TextWatcherCallBack;

/**
 * login activity
 * 
 * @author Quinn
 * @date 2015-1-24
 */
public class LoginActivity extends BaseActivity implements TextWatcherCallBack,
		OnClickListener {

	@InjectView(R.id.et_account)
	ClearableAutoCompleteTextView accountView;
	@InjectView(R.id.et_password)
	CleanableEditText passwordView;
	@InjectView(R.id.bt_login)
	Button login;

	private String account;
	private String password;
	private SpinnerDialog loadingDialog;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_login);
		ButterKnife.inject(this);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("Login");
		setSupportActionBar(toolbar);

		loadingDialog = new SpinnerDialog(this, getResources().getString(
				R.string.loading_alert_content_connect_to_server));
		accountView.setCallBack(this);
		passwordView.setCallBack(this);
		app.setServerAddr(Preference.getString(this, Preference.Key.SERVER_IP));
		updateEnablement();
		passwordView.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event != null && ACTION_DOWN == event.getAction()
						&& keyCode == KEYCODE_ENTER && loginEnabled()) {
					handleLogin();
					return true;
				} else
					return false;
			}
		});

		passwordView.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == IME_ACTION_DONE && loginEnabled()) {
					handleLogin();
					return true;
				}
				return false;
			}
		});
		login.setOnClickListener(this);
	}

	private boolean loginEnabled() {
		return !TextUtils.isEmpty(accountView.getText())
				&& !TextUtils.isEmpty(passwordView.getText());
	}

	private void updateEnablement() {
		login.setEnabled(loginEnabled());
	}

	@OnClick(R.id.bt_login)
	void handleLogin() {
	   
		loadingDialog.show(this.getSupportFragmentManager(), "tag");
		new ConnectTask(smack) {
			@Override
			protected void onPostExecute(Boolean result) {
				if (result) {
					loadingDialog.updateContent(getResources().getString(
							R.string.loading_alert_content_log_in));
					loginAfterConnect();
				} else {
					ToastUtils.toast(LoginActivity.this,
							R.string.toast_content_connect_fail);
					loadingDialog.dismissAllowingStateLoss();
				}
			}

		}.execute(app.getServerAddr());

	}

	public void loginAfterConnect() {
		account = accountView.getText().toString();
		password = passwordView.getText().toString();
		new LoginTask(getResources(),smack) {
			@Override
			protected void onPostExecute(Boolean result) {
				loadingDialog.dismissAllowingStateLoss();
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				LoginActivity.this.startActivity(intent);
			}
		}.execute(account, password);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.login, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		int id = item.getItemId();
		switch (id) {
		case R.id.action_settings: {
			Intent intent = NetWorkSettingActivity.createIntent();
			startActivity(intent);
			return true;
		}
		case R.id.action_newAccount: {
			Intent intent = SignUpActivity.createIntent();
			startActivityForResult(intent, Intents.RESULT_CODE_SUCCESS);
			return true;
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void handleMoreTextChanged() {
		updateEnablement();
	}

	@Override
	public void onClick(View v) {
		handleLogin();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK
				&& requestCode == Intents.RESULT_CODE_SUCCESS) {
			Bundle bundle = data.getExtras();
			accountView.setText(bundle.getString(Intents.EXTRA_RESULT_ACCOUNT));
			passwordView.setText(bundle
					.getString(Intents.EXTRA_RESULT_PASSWORD));
			ToastUtils.toast(this, R.string.toast_content_signup_successfully);
		}

	}

}