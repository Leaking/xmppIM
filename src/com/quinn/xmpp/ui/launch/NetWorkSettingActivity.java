package com.quinn.xmpp.ui.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.quinn.xmpp.Intents.Builder;
import com.quinn.xmpp.R;
import com.quinn.xmpp.persisitence.Preference;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.widget.InputDialog;
import com.quinn.xmpp.ui.widget.InputDialog.MDialogCallback;

/**
 * 
 * @author Quinn
 * @date 2015-1-30
 */
public class NetWorkSettingActivity extends BaseActivity implements
		MDialogCallback {

	private InputDialog inputDialog;
	@InjectView(R.id.serverIP)
	View ipView;
	@InjectView(R.id.serverIP_tv)
	TextView tv_ip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net_work_setting);
		ButterKnife.inject(this);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setTitle("Network");
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		tv_ip.setText(Preference.getString(this, Preference.Key.SERVER_IP));
		inputDialog = new InputDialog(this, "ip address");
		ipView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				inputDialog.show(
						NetWorkSettingActivity.this.getSupportFragmentManager(),
						"tag");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.net_work_setting, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public static Intent createIntent() {
		Builder builder = new Builder("launch.setting.View");
		return builder.toIntent();
	}

	@Override
	public void cancel() {

	}

	@Override
	public void confirm(String content) {
		Preference.putString(this, Preference.Key.SERVER_IP, content);
		tv_ip.setText(content);
		app.setServerAddr(content);
	}
}
