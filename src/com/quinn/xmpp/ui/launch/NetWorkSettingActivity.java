package com.quinn.xmpp.ui.launch;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.quinn.xmpp.Intents.Builder;
import com.quinn.xmpp.R;
import com.quinn.xmpp.persisitence.Preference;
import com.quinn.xmpp.ui.BaseActivity;
import com.quinn.xmpp.ui.widget.InputDialog;
import com.quinn.xmpp.ui.widget.InputDialog.MDialogCallback;

public class NetWorkSettingActivity extends BaseActivity implements MDialogCallback{

	private InputDialog inputDialog;
	private View ipView;
	private TextView tv_ip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_net_work_setting);
		ipView = findViewById(R.id.serverIP);
		tv_ip = (TextView) findViewById(R.id.serverIP_tv);
		inputDialog = new InputDialog(this, "IP DDD");
		ipView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				inputDialog.show(
						NetWorkSettingActivity.this.getSupportFragmentManager(), "tag");
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
	
	
	public static Intent createIntent(){
		Builder builder = new Builder("launch.setting.View");
		return builder.toIntent();
	}


	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void confirm(String content) {
		// TODO Auto-generated method stub
		Preference.putString(this, Preference.Key.SERVER_IP, content);
		tv_ip.setText(content);
		app.setServerAddr(content);
	}
}
