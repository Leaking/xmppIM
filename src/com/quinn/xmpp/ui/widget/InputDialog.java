package com.quinn.xmpp.ui.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.quinn.xmpp.R;

/**
 * @author Quinn
 * @date 2015-2-1
 */
public class InputDialog extends DialogFragment{

	private Context context;
	private String title;
	private InputDialogCallback callback;
	
	public interface InputDialogCallback{
		public void cancel();
		public void confirm(String string);
	}
	
	public InputDialog(Context context, String title){
		this.context  = context;
		this.title = title;
		this.callback = (InputDialogCallback)context;
	}
	
	public InputDialog(Context context){
		this.context = context;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setCallback(InputDialogCallback callback){
		this.callback = callback;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View view = View.inflate(context, R.layout.input_dialog, null);
		TextView titleView = (TextView) view.findViewById(R.id.popupDialogTitle);
		titleView.setText(title);
		final ClearableAutoCompleteTextView contentTv = (ClearableAutoCompleteTextView) view.findViewById(R.id.et_dialogContent);
		Button confirmBtn = (Button) view.findViewById(R.id.inputDialogConfirm);
		Button cancelBtn = (Button) view.findViewById(R.id.inputDialogCancel);
		confirmBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				callback.confirm(contentTv.getText().toString());
				InputDialog.this.dismiss();
			}
		});
		cancelBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputDialog.this.dismiss();
			}
		});
		builder.setView(view);
		return builder.create();
	}

	
}
