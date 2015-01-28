package com.quinn.xmpp.ui.launch;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
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

import com.quinn.xmpp.R;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.widget.CleanableEditText;
import com.quinn.xmpp.ui.widget.ClearableAutoCompleteTextView;
import com.quinn.xmpp.ui.widget.TextWatcherCallBack;

/**
 * 登陆界面
 * 
 * @author Quinn
 * @date 2015-1-24
 */
public class LoginActivity extends BaseActivity implements TextWatcherCallBack {

	private ClearableAutoCompleteTextView accountView;
	private CleanableEditText passwordText;
	private Button login;
	private String account;
	private String password;

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_login);
		accountView = (ClearableAutoCompleteTextView) findViewById(R.id.et_account);
		passwordText = (CleanableEditText) findViewById(R.id.et_password);
		login = (Button) findViewById(R.id.bt_login);
		accountView.setCallBack(this);
		passwordText.setCallBack(this);		
		updateEnablement();	
		passwordText.setOnKeyListener(new OnKeyListener() {
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

		passwordText.setOnEditorActionListener(new OnEditorActionListener() {

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
                && !TextUtils.isEmpty(passwordText.getText());
    }
    
	private void updateEnablement() {
		login.setEnabled(loginEnabled());
	}

	public void handleLogin(){
		account = accountView.getText().toString();
		password = accountView.getText().toString();
		//不要再主线程访问网络
//		boolean isConnect = smack.connect("192.168.1.104", 5222, "Smack");
//		System.out.println("isConnect = " + isConnect);
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
		switch (item.getItemId()) {

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void handleMoreTextChanged() {
		updateEnablement();
	}
	
}