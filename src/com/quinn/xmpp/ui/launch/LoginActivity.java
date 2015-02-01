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
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.quinn.xmpp.MainActivity;
import com.quinn.xmpp.R;
import com.quinn.xmpp.bean.User;
import com.quinn.xmpp.core.launch.LoginTask;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.widget.CleanableEditText;
import com.quinn.xmpp.ui.widget.ClearableAutoCompleteTextView;
import com.quinn.xmpp.ui.widget.LoadingDialog;
import com.quinn.xmpp.ui.widget.TextWatcherCallBack;

/**
 * µÇÂ½½çÃæ
 * 
 * @author Quinn
 * @date 2015-1-24
 */
public class LoginActivity extends BaseActivity implements TextWatcherCallBack {

	private ClearableAutoCompleteTextView accountView;
	private CleanableEditText passwordView;
	private Button login;
	private String account;
	private String password;
	private LoadingDialog loadingDialog;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_login);
		accountView = (ClearableAutoCompleteTextView) findViewById(R.id.et_account);
		passwordView = (CleanableEditText) findViewById(R.id.et_password);
		login = (Button) findViewById(R.id.bt_login);
		loadingDialog = new LoadingDialog(this, "µÇÂ½ÖÐ");
		accountView.setCallBack(this);
		passwordView.setCallBack(this);		
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
		User user = new User(account, password);
		
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				boolean isConnect = smack.connect("192.168.191.3", 5222, "Smack");
//				boolean isLogin = smack.login(account, password);
//				System.out.println("if login = " + isLogin);
//
//			}
//		}).start();
			
		loadingDialog.show(
				this.getSupportFragmentManager(), "tag");
		loadingDialog.setCancelable(false);
		new LoginTask(smack){

			@Override
			protected void onPostExecute(Boolean result) {
				// TODO Auto-generated method stub
				loadingDialog.dismissAllowingStateLoss();
				System.out.println("result = " + result );
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				LoginActivity.this.startActivity(intent);
			}
		}.execute(user);
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
	
}