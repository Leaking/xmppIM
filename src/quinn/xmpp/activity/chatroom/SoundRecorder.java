package quinn.xmpp.activity.chatroom;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import quinn.xmpp.activity.laucher.LoginActivity;
import quinn.xmpp.utils.L;
import quinn.xmpp.utils.LoadingDialog;
import quinn.xmpp.utils.RecordingDialog;
import quinn.xmpp.utils.T;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;


public class SoundRecorder {
	private static final String LOG_TAG = "SoundRecorder";
	private Context mContext;
	private ImageView mView;
	private static String mFileName = null;
	private MediaRecorder mRecorder = null;
	boolean mCancel = false;
	boolean mStartRecording = true;
	int startMinute;
	int endMinute;
	int startSecond;
	int endSecond;
	RecordingDialog recording;

	public SoundRecorder(Context context) {
		mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
		mContext = context;
	}

	public void regist(ImageView view) {
		mView = view;
		mView.setOnTouchListener(toucher);
	}

	public void unRegist(ImageView view) {
		mView.setOnTouchListener(null);
	}

	private void onRecord(boolean start) {
		if (start) {
			startRecording();
		} else {
			stopRecording();
		}
	}

	private String getLocation() {
		String str;
		str = Environment.getExternalStorageDirectory().getAbsolutePath();
		long timeMill = Calendar.getInstance().getTime().getTime();
		str += "/record";
		str += timeMill;
		str += ".3gp";
		return str;
	}

	private void startRecording() {
		startMinute = Calendar.getInstance().getTime().getMinutes();
		startSecond = Calendar.getInstance().getTime().getSeconds();
		mFileName = getLocation();
		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		mRecorder.setOutputFile(mFileName);
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

		try {
			mRecorder.prepare();
		} catch (IOException e) {
			Log.e(LOG_TAG, "prepare() failed");
		}

		mRecorder.start();
	}

	private void stopRecording() {
		endMinute = Calendar.getInstance().getTime().getMinutes();
		endSecond = Calendar.getInstance().getTime().getSeconds();
		int diffMinute = endMinute - startMinute;
		int diffSecond;
		if (startSecond > endSecond) {
			diffSecond = endSecond + 60 - startSecond;
			diffMinute -= 1;
		} else {
			diffSecond = endSecond - startSecond;
		}
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;

		if (diffSecond < 3) {
			T.mToast(mContext, "The record is too short");
			mCancel = true;
		}
		File file = new File(mFileName);
		if (mCancel) {
			// delete the file
			file.delete();
		} else {
			// send the file
			((ChatRoomActivity) mContext).sendSound(file, diffMinute,
					diffSecond);
		}

	}

	OnTouchListener toucher = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if (event.getAction() == MotionEvent.ACTION_DOWN) {

				recording = new RecordingDialog(SoundRecorder.this.mContext);
				recording.show(
						((ChatRoomActivity) (SoundRecorder.this.mContext))
								.getSupportFragmentManager(), "tag");
				recording.setCancelable(false);
				mCancel = false;
				onRecord(mStartRecording);
				mStartRecording = !mStartRecording;
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				recording.dismiss();
				onRecord(mStartRecording);
				mStartRecording = !mStartRecording;
			}
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				int size = event.getHistorySize();
				if (size > 0) {
					float pastY = event.getHistoricalY(0);
					float nowY = event.getY();
					float diff = pastY - nowY;
					if (diff > 10) {
						mCancel = true;
					}
				}
			}

			return true;
		}
	};

}
