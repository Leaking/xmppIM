package com.quinn.xmpp.ui.launch;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import android.content.Intent;
import android.os.Bundle;
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

import com.quinn.xmpp.MainActivity;
import com.quinn.xmpp.R;
import com.quinn.xmpp.bean.User;
import com.quinn.xmpp.core.launch.LoginTask;
import com.quinn.xmpp.persisitence.Preference;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.widget.CleanableEditText;
import com.quinn.xmpp.ui.widget.ClearableAutoCompleteTextView;
import com.quinn.xmpp.ui.widget.SpinnerDialog;
import com.quinn.xmpp.ui.widget.TextWatcherCallBack;

/**
 * ��½����
 * 
 * @author Quinn
 * @date 2015-1-24
 */
public class LoginActivity extends BaseActivity implements TextWatcherCallBack, OnClickListener {

	private ClearableAutoCompleteTextView accountView;
	private CleanableEditText passwordView;
	private Button login;
	private String account;
	private String password;
	private SpinnerDialog loadingDialog;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_login);
		accountView = (ClearableAutoCompleteTextView) findViewById(R.id.et_account);
		passwordView = (CleanableEditText) findViewById(R.id.et_password);
		login = (Button) findViewById(R.id.bt_login);
		loadingDialog = new SpinnerDialog(this, "Login,,,");
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

	public void handleLogin(){
		account = accountView.getText().toString();
		password = passwordView.getText().toString();
		loadingDialog.show(
				this.getSupportFragmentManager(), "tag");
		new LoginTask(smack){

			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				loadingDialog.dismissAllowingStateLoss();
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				LoginActivity.this.startActivity(intent);
			}
		}.execute(app.getServerAddr(),account,password);
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
		case R.id.action_settings:{
			Intent intent = NetWorkSettingActivity.createIntent();
			startActivity(intent);
			return true;
		}
		case R.id.action_newAccount:{
			Intent intent = SignUpActivity.createIntent();
			startActivity(intent);
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

	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		handleLogin();
	}
	
}