package quinn.xmpp.activity.chatroom;

import java.io.File;
import java.util.ArrayList;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.ServiceDiscoveryManager;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;

import quinn.xmpp.broadcast.BroadCastUtil;
import quinn.xmpp.model.BubbleMessage;
import quinn.xmpp.smack.Smack;
import quinn.xmpp.smack.SmackImpl;
import quinn.xmpp.utils.Constants;
import quinn.xmpp.utils.L;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;


/**
 * 
 * @author Quinn
 *a task to send a file,and display the progress
 */
public class FileSenderAsyncTask extends AsyncTask<File, Integer, Long> {

	//position the bubble display the stage of the file sending
	private int position;
//
	private Context context;
	//who the user talking to
	private String u_JID;
	
	private Smack smack;
	private String fileType;

	public FileSenderAsyncTask(int position,
			Context context,String u_JID,String fileType) {
		this.position = position;
		this.smack = SmackImpl.getInstance();
		this.context = context;
		this.u_JID = u_JID;
		this.fileType = fileType;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		publishProgress(AsyncTaskContants.NEGOTIATING);
		super.onPreExecute();

	}

	@Override
	protected Long doInBackground(File... params) {
		// TODO Auto-generated method stub
		int progressVal;
		Smack smack = SmackImpl.getInstance();
		//some configuration
		ServiceDiscoveryManager sdm = ServiceDiscoveryManager
				.getInstanceFor(smack.getConnection());
		if (sdm == null)
			sdm = new ServiceDiscoveryManager(smack.getConnection());
		sdm.addFeature("http://jabber.org/protocol/disco#info");
		sdm.addFeature("jabber:iq:privacy");

		final File file = params[0];
		FileTransferManager manager = new FileTransferManager(
				smack.getConnection());

		// Create the outgoing file transfer
		final OutgoingFileTransfer transfer = manager
				.createOutgoingFileTransfer(smack.getFullyJID(u_JID));

		// TODO Auto-generated method stub
		try {
			transfer.sendFile(file, this.fileType);		
			
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while (!transfer.isDone()) {
			if (transfer
					.getStatus()
					.equals(org.jivesoftware.smackx.filetransfer.FileTransfer.Status.error)) {
				publishProgress(AsyncTaskContants.ERROR);
			} else {
				progressVal = (int) (100 * transfer.getProgress());
				publishProgress(progressVal);
				BroadCastUtil.sendBroadCastChatroom(context);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (transfer
				.getStatus()
				.equals(org.jivesoftware.smackx.filetransfer.FileTransfer.Status.refused)) {
			publishProgress(AsyncTaskContants.REJECTED);
			BroadCastUtil.sendBroadCastChatroom(context);
			// just return a value which is not null
			return -1l;
		} else if (transfer.getStatus().equals(
				org.jivesoftware.smackx.filetransfer.FileTransfer.Status.error)) {
			publishProgress(AsyncTaskContants.ERROR);
			BroadCastUtil.sendBroadCastChatroom(context);
			// just return a value which is not null
			return -2l;
		}
		return null;
	}

	@Override
	protected void onPostExecute(Long result) {
		// TODO Auto-generated method stub
		if (result == null)
			publishProgress(AsyncTaskContants.FINISH);
		BroadCastUtil.sendBroadCastChatroom(context);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		L.i("onProgressUpdate");
		switch (values[0]) {
		case AsyncTaskContants.REJECTED:
			smack.getBubbleList(u_JID).get(position).setFileStage(AsyncTaskContants.STR_REJECTED);
			break;
		case AsyncTaskContants.ERROR:
			smack.getBubbleList(u_JID).get(position).setFileStage(AsyncTaskContants.STR_ERROR);
			break;
		case AsyncTaskContants.FINISH:
			smack.getBubbleList(u_JID).get(position).setFileStage(AsyncTaskContants.STR_FINISH);
			break;
		case AsyncTaskContants.NEGOTIATING:
			smack.getBubbleList(u_JID).get(position).setFileStage(AsyncTaskContants.STR_NEGOTIATING);
			break;
		}
		smack.getBubbleList(u_JID).get(position).setFileProgressVal(values[0]);
	}
}
