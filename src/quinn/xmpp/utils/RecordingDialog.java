package quinn.xmpp.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.XMPP.R;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;

public class RecordingDialog extends DialogFragment {
	Context mContext;
	private final static String dialogStr = "手指上滑取消录音";
	

	public RecordingDialog(Context context) {
		this.mContext = context;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

		View view = View.inflate(mContext, R.layout.recording, null);
		TextView textview = (TextView)view.findViewById(R.id.recordStr);
		textview.setText(this.dialogStr);
		ImageView image = (ImageView)view.findViewById(R.id.recordView);
		IconicFontDrawable iconDrawable = new IconicFontDrawable(mContext);
		iconDrawable.setIcon(EntypoIcon.MIC);
		iconDrawable.setIconColor(Constants.COLOR_COMMON_BLUE);
		image.setBackground(iconDrawable);
		// Inflate and set the layout for the dialog
		// Pass null as the parent view because its going in the dialog layout
		builder.setView(view);
		// Create the AlertDialog object and return it
		return builder.create();
	}
}
