package com.XMPP.Activity.ChatRoom;

import java.io.IOException;

import android.media.MediaPlayer;
import android.util.Log;

public class SoundPlayer {
	private MediaPlayer mPlayer = null;
	private String path = null;
	boolean mStartPlaying = true;

	public SoundPlayer(String path) {
		this.path = path;
	}

	private void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
	}

	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
			System.out.println("media path " + path);
			mPlayer.setDataSource(path);
			mPlayer.prepare();
			mPlayer.start();
		} catch (IOException e) {
		}
	}

	private void stopPlaying() {
		mPlayer.release();
		mPlayer = null;
	}

	public void click() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				onPlay(mStartPlaying);
				// i can change the play icon here
				mStartPlaying = !mStartPlaying;
			}
		}).start();

	}
}
