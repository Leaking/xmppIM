package com.quinn.xmpp.ui.launch;

import static android.view.KeyEvent.ACTION_DOWN;
import static android.view.KeyEvent.KEYCODE_ENTER;
import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import com.quinn.xmpp.Intents;
import com.quinn.xmpp.Intents.Builder;
import com.quinn.xmpp.R;
import com.quinn.xmpp.core.launch.ConnectTask;
import com.quinn.xmpp.core.launch.SignUpTask;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.ToastUtils;
import com.quinn.xmpp.ui.widget.CleanableEditText;
import com.quinn.xmpp.ui.widget.ClearableAutoCompleteTextView;
import com.quinn.xmpp.ui.widget.SpinnerDialog;
import com.quinn.xmpp.ui.widget.TextWatcherCallBack;

public class SignUpActivity extends BaseActivity implements TextWatcherCallBack{

	
	
	@InjectView(R.id.et_account)
	ClearableAutoCompleteTextView accountView;
	@InjectView(R.id.et_password)
	CleanableEditText passwordView;
	@InjectView(R.id.et_repeatPassword)
	CleanableEditText repeatPasswordView;
	@InjectView(R.id.bt_SignUp)
	Button signUp;
	
	private SpinnerDialog loadingDialog;
	private String account;
	private String password;
	private String repeatPassword;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		ButterKnife.inject(this);
		loadingDialog = new SpinnerDialog(this, getResources().getString(R.string.loading_alert_content_connect_to_server));
		accountView.setCallBack(this);
		passwordView.setCallBack(this);
		repeatPasswordView.setCallBack(this);
		updateEnablement();
		repeatPasswordView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event != null && ACTION_DOWN == event.getAction()
                        && keyCode == KEYCODE_ENTER && signUpEnabled()) {
                	handleSignUp();
                    return true;
                } else
                    return false;
            }
        });

		repeatPasswordView.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                    KeyEvent event) {
                if (actionId == IME_ACTION_DONE && signUpEnabled()) {
                	handleSignUp();
                    return true;
                }
                return false;
            }
        });	
	}

	
	private void updateEnablement() {
		signUp.setEnabled(signUpEnabled());
	}
	
    private boolean signUpEnabled() {
        return !TextUtils.isEmpty(accountView.getText())
                && !TextUtils.isEmpty(passwordView.getText())
                	&& !TextUtils.isEmpty(repeatPasswordView.getText());
    }
	
    @OnClick(R.id.bt_SignUp)
	void handleSignUp(){
    	account = accountView.getText().toString();
		password = passwordView.getText().toString();
		repeatPassword = repeatPasswordView.getText().toString();
		if(password.equals(repeatPassword) == false){
			ToastUtils.toast(this, R.string.toast_content_repeat_password);
			return;
		}
		loadingDialog.show(
				this.getSupportFragmentManager(), "tag");
		new ConnectTask(smack){
			@Override
			protected void onPostExecute(Boolean result) {
				if(result){
					loadingDialog.updateContent(getResources().getString(R.string.loading_alert_content_sign_up));
					SignUpAfterConnect();
				}else{
					ToastUtils.toast(SignUpActivity.this, R.string.toast_content_connect_fail);
					loadingDialog.dismissAllowingStateLoss();
				}
			}
			
		}.execute(app.getServerAddr());
		
	}
    
    public void SignUpAfterConnect(){
    	new SignUpTask(smack){
			@Override
			protected void onPostExecute(Boolean result) {
				loadingDialog.dismissAllowingStateLoss();
				if(result){
					Builder buidler = new Builder();
					buidler.add(Intents.EXTRA_RESULT_ACCOUNT, account)
					.add(Intents.EXTRA_RESULT_PASSWORD, password);
					Intent intent = buidler.toIntent();
					SignUpActivity.this.setResult(RESULT_OK, intent);
					finish();
				}else{
					ToastUtils.toast(SignUpActivity.this, R.string.toast_content_signup_fail);
				}
			}
		}.execute(app.getServerAddr(),account,password);
    }
    
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	
	
	
	public static Intent createIntent(){
		Builder builder = new Builder("launch.SignUp.View");
		return builder.toIntent();
	}

	@Override
	public void handleMoreTextChanged() {
		updateEnablement();
	}
	
	
	
}
