package com.XMPP.Activity.ChatRoom;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;

import com.XMPP.BroadCast.BroadCastUtil;
import com.XMPP.smack.SmackImpl;

public class SoundPlayer {
	private MediaPlayer mPlayer = null;
	private String path = null;
	boolean mStartPlaying = true;
	String u_jid;
	int position;
	int sumSecond;
	Context mcontext;

	public SoundPlayer(Context context, String u_jid, int position,
			int sumSecond, String path) {
		this.mcontext = context;
		this.path = path;
		this.u_jid = u_jid;
		this.position = position;
		this.sumSecond = sumSecond;
	}

	private void onPlay(boolean start) {
		if (start) {
			startPlaying();
		} else {
			stopPlaying();
		}
		int i = 0;
		while (i < sumSecond) {
			i += 1;
			try {
				Thread.sleep(1000);
				SmackImpl.getInstance().getBubbleList(u_jid).get(position)
						.setFileProgressVal(100 * i / sumSecond);
				BroadCastUtil.sendBroadCastChatroom(mcontext);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		SmackImpl.getInstance().getBubbleList(u_jid).get(position)
				.setFileProgressVal(100);
		BroadCastUtil.sendBroadCastChatroom(mcontext);
	}

	private void startPlaying() {
		mPlayer = new MediaPlayer();
		try {
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
