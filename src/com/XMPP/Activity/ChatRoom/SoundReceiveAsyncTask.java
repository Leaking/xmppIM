package com.XMPP.Activity.ChatRoom;

import java.io.File;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.XMPP.BroadCast.BroadCastUtil;
import com.XMPP.Model.BubbleMessage;
import com.XMPP.Service.MessageService;
import com.XMPP.smack.SmackImpl;

public class SoundReceiveAsyncTask extends
		AsyncTask<FileTransferRequest, Integer, Long> {

	Context context;
	String fromJID;
	int sumSeconds;
	public SoundReceiveAsyncTask(Context context, int sumSecond,String fromJID){
		this.sumSeconds = sumSecond;
		this.context = context;
		this.fromJID = fromJID;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Long doInBackground(FileTransferRequest... request) {
		// TODO Auto-generated method stub
		
		File mf = Environment.getExternalStorageDirectory();
		File file = new File(mf.getAbsoluteFile() + "/"
				+ request[0].getFileName());
		IncomingFileTransfer transfer = request[0].accept();
		try {
			transfer.recieveFile(file);
			while (!transfer.isDone()) {
				try {
					Thread.sleep(1000L);
				} catch (Exception e) {
					Log.e("", e.getMessage());
				}
			}
			if(transfer.getStatus().equals(org.jivesoftware.smackx.filetransfer.FileTransfer.Status.complete)){
				BubbleMessage bubbleSound = new BubbleMessage(file.getPath(),sumSeconds, false);
				SmackImpl.getInstance().getBubbleList(fromJID).add(bubbleSound);
				BroadCastUtil.sendBroadCastChatroom(context);
				BroadCastUtil.sendBroadCastChatting(context);
			}						
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	
	@Override
	protected void onPostExecute(Long result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}


}
