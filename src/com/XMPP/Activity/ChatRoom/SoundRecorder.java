package com.XMPP.Activity.ChatRoom;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.XMPP.util.L;

public class SoundRecorder {
	private static final String LOG_TAG = "SoundRecorder";
	private Context mContext;
	private ImageView mView;
	private static String mFileName = null;
	private MediaRecorder mRecorder = null;
	boolean mCancel = false;
	boolean mStartRecording = true;

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

		L.i("come here to start???");
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
		
		mRecorder.stop();
		mRecorder.release();
		mRecorder = null;

		File file = new File(mFileName);
		if (mCancel) {
			// delete the file
			file.delete();
		} else {
			// send the file
			((ChatRoomActivity) mContext).sendSound(file);
		}

	}

	OnTouchListener toucher = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				System.out.println("ACTION_DOWN  ");
				mCancel = false;
				onRecord(mStartRecording);
				mStartRecording = !mStartRecording;
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				System.out.println("ACTION_UP  ");
				onRecord(mStartRecording);
				mStartRecording = !mStartRecording;
			}
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				System.out.println("ACTION_MOVE  ");
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
