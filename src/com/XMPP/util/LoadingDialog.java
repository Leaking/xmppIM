package com.XMPP.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.XMPP.R;

public class LoadingDialog extends DialogFragment {
	Context context;
	String alertStr;

	public LoadingDialog(Context context,String alertStr) {
		this.context = context;
		this.alertStr = alertStr;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(context);

		View view = View.inflate(context, R.layout.loading, null);
		TextView textview = (TextView)view.findViewById(R.id.alertString);
		textview.setText(this.alertStr);
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(view);
		// Create the AlertDialog object and return it
		return builder.create();
	}
}
