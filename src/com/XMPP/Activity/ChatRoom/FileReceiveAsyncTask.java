package com.XMPP.Activity.ChatRoom;

import java.io.File;
import java.util.ArrayList;

import org.jivesoftware.smackx.filetransfer.FileTransferRequest;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.XMPP.BroadCast.BroadCastUtil;
import com.XMPP.Model.BubbleMessage;
import com.XMPP.util.L;

/**
 * 
 * @author Quinn
 * Task to receive and store a file,display the stage of the receiving
 */
public class FileReceiveAsyncTask extends
		AsyncTask<FileTransferRequest, Integer, Long> {

	private String savePath;
	private int position;
	private ArrayList<BubbleMessage> messages;
	private Context context;

	public FileReceiveAsyncTask(int position,
			ArrayList<BubbleMessage> messages, Context context) {
		this.position = position;
		this.messages = messages;
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		publishProgress(0);
		BroadCastUtil.sendBroadCastChatroom(context);
		super.onPreExecute();
	}

	public void setSavePath(String path){
		
	}

	@Override
	protected Long doInBackground(FileTransferRequest... request) {
		// TODO Auto-generated method stub
		IncomingFileTransfer transfer = request[0].accept();
		File mf = Environment.getExternalStorageDirectory();
		File file = new File(mf.getAbsoluteFile() + "/"
				+ transfer.getFileName());
		try {
			transfer.recieveFile(file);
			while (!transfer.isDone()) {
				try {
					Thread.sleep(1000L);
				} catch (Exception e) {
					Log.e("", e.getMessage());
				}
				int progressVal = (int) (100 * transfer.getProgress());
				publishProgress(progressVal);
				BroadCastUtil.sendBroadCastChatroom(context);
			}
			if (transfer
					.getStatus()
					.equals(org.jivesoftware.smackx.filetransfer.FileTransfer.Status.cancelled))
				publishProgress(AsyncTaskContants.CANCELLED);
			if (transfer
					.getStatus()
					.equals(org.jivesoftware.smackx.filetransfer.FileTransfer.Status.error))
				publishProgress(AsyncTaskContants.ERROR);
			BroadCastUtil.sendBroadCastChatroom(context);

		} catch (Exception e) {
			Log.e("", e.getMessage());
		}

		return null;
	}

	@Override
	protected void onPostExecute(Long result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		switch (values[0]) {
		case AsyncTaskContants.CANCELLED:
			messages.get(position).setFileStage(AsyncTaskContants.STR_CANCELLED);
			break;
		case AsyncTaskContants.ERROR:
			messages.get(position).setFileStage(AsyncTaskContants.STR_ERROR);
			break;
		case AsyncTaskContants.FINISH:
			messages.get(position).setFileStage(AsyncTaskContants.STR_FINISH);
			break;
		case AsyncTaskContants.NEGOTIATING:
			messages.get(position).setFileStage(AsyncTaskContants.STR_NEGOTIATING);
			break;
		}
		messages.get(position).setFileProgressVal(values[0]);
	}

}