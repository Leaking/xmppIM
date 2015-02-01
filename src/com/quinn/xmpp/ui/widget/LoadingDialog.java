package com.quinn.xmpp.ui.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.quinn.xmpp.R;

/**
 * 缓冲界面
 * @author Quinn
 *
 */
public class LoadingDialog extends DialogFragment {
	Context context;
	String alertStr;

	public LoadingDialog(Context context,String alertStr) {
		this.context = context;
		this.alertStr = alertStr;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		View view = View.inflate(context, R.layout.loading_dialog, null);
		TextView textview = (TextView)view.findViewById(R.id.alertString);
		textview.setText(this.alertStr);
		ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);
//		progressBar.setIndeterminateDrawable(new NexusRotationCrossDrawable.Builder(
//				this.context).build());

		builder.setView(view);

		return builder.create();
	}
}
