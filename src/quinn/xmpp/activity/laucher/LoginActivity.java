package quinn.xmpp.activity.laucher;

import quinn.xmpp.R;
import quinn.xmpp.activity.common.BaseActivity;
import quinn.xmpp.widget.CleanableEditText;
import quinn.xmpp.widget.ClearableAutoCompleteTextView;
import quinn.xmpp.widget.TextWatcherAdapter;
import quinn.xmpp.widget.TextWatcherCallBack;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

/**
 * µÇÂ½½çÃæ
 * 
 * @author Quinn
 * @date 2015-1-24
 */
public class LoginActivity extends BaseActivity implements TextWatcherCallBack {

	private ClearableAutoCompleteTextView account;
	private CleanableEditText password;

	@Override
	protected void onCreate(Bundle bundle) {
		// TODO Auto-generated method stub
		super.onCreate(bundle);
		setContentView(R.layout.activity_login);
		account = (ClearableAutoCompleteTextView) findViewById(R.id.et_account);
		password = (CleanableEditText) findViewById(R.id.et_password);

	}

	private void updateEnablement() {

	}

	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		return super.onCreateView(name, context, attrs);
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