package quinn.xmpp.activity.chatroom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import quinn.xmpp.smack.Smack;
import quinn.xmpp.smack.SmackImpl;
import quinn.xmpp.utils.Constants;
import quinn.xmpp.utils.T;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.XMPP.R;
import com.atermenji.android.iconicdroid.IconicFontDrawable;
import com.atermenji.android.iconicdroid.icon.EntypoIcon;
import com.atermenji.android.iconicdroid.icon.FontAwesomeIcon;
import com.atermenji.android.iconicdroid.icon.IconicIcon;

public class FileSenderActivity extends Activity implements
		OnItemClickListener, OnClickListener {

	ListView list_view;
	TextView send_view;
	TextView currentRoot_view;
	Madapter adapter;
	String root;
	private Smack smack;
	private String u_JID;
	private int lastChosenOne = -1;
	private int chosenOne = -1;
	private String chosenPath;
	private List<String> item_view = null;
	private List<String> path_data = null;
	private final static int FOLDER = 1;
	private final static int FILE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_file_sender);
		init();
	}

	public void init() {
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			u_JID = extras.getString("JID");
		}
		smack = SmackImpl.getInstance();
		list_view = (ListView) findViewById(R.id.file_list);
		send_view = (TextView) findViewById(R.id.send_file);
		currentRoot_view = (TextView) findViewById(R.id.current_root);
		root = Environment.getExternalStorageDirectory().getPath();
		send_view.setOnClickListener(this);
		list_view.setOnItemClickListener(this);
		getDir(root);
	}

	private void getDir(String dirPath) {
		currentRoot_view.setText("Location: " + dirPath);
		item_view = new ArrayList<String>();
		path_data = new ArrayList<String>();
		File f = new File(dirPath);
		File[] files = f.listFiles();

		if (!dirPath.equals(root)) {
			// return to the parent
			item_view.add("../");
			path_data.add(f.getParent());
		}

		for (int i = 0; i < files.length; i++) {
			File file = files[i];

			if (!file.isHidden() && file.canRead()) {
				path_data.add(file.getPath());
				if (file.isDirectory()) {
					item_view.add(file.getName() + "/");
				} else {
					item_view.add(file.getName());
				}
			}
		}
		adapter = new Madapter();
		list_view.setAdapter(adapter);
	}

	public class Madapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return item_view.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return item_view.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public int getItemViewType(int position) {
			// Define a way to determine which layout to use, here it's just
			// evens and odds.
			String path = path_data.get(position);
			File f = new File(path);

			if (f.isDirectory())
				return FOLDER;
			else
				return FILE;
		}

		public String getFileSize(int position) {
			String path = path_data.get(position);
			File f = new File(path);
			long fileLength = f.length();
			if (fileLength < 1024)
				return fileLength + "B";
			else {
				long fileSize = fileLength / 1024;
				if (fileSize < 1024)
					return fileSize + "KB";
				else
					return fileSize / 1024 + "MB";
			}
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			switch (getItemViewType(position)) {
			case FOLDER:
				if (convertView == null) {
					convertView = View.inflate(FileSenderActivity.this,
							R.layout.filelist_folder_item, null);
				}
				ImageView folderIcon = (ImageView) convertView
						.findViewById(R.id.folder_icon);
				IconicFontDrawable iconFont = new IconicFontDrawable(
						FileSenderActivity.this);
				iconFont.setIcon(FontAwesomeIcon.FOLDER_CLOSE);
				iconFont.setIconColor(Constants.COLOR_COMMON_GOLD);
				folderIcon.setBackground(iconFont);

				TextView folderpath = (TextView) convertView
						.findViewById(R.id.folder_path);
				folderpath.setText(path_data.get(position));

				ImageView folderIcon1 = (ImageView) convertView
						.findViewById(R.id.folder_arrow_icon);
				IconicFontDrawable iconFont1 = new IconicFontDrawable(
						FileSenderActivity.this);
				iconFont1.setIcon(EntypoIcon.CHEVRON_RIGHT);
				iconFont1.setIconColor(Constants.COLOR_COMMON_BLACK);
				folderIcon1.setBackground(iconFont1);
				break;
			case FILE:
				if (convertView == null) {
					convertView = View.inflate(FileSenderActivity.this,
							R.layout.filelist_file_item, null);
				}
				final ImageView fileIcon = (ImageView) convertView
						.findViewById(R.id.file_icon);

				final IconicFontDrawable iconFont2 = new IconicFontDrawable(
						FileSenderActivity.this);
				if (chosenOne != position || chosenOne == lastChosenOne) {
					iconFont2.setIcon(FontAwesomeIcon.FILE_ALT);
					iconFont2.setIconColor(Constants.COLOR_COMMON_GOLD);
					fileIcon.setBackground(iconFont2);
				} else {
					iconFont2.setIcon(IconicIcon.OK_CIRCLE);
					iconFont2.setIconColor(Constants.COLOR_COMMON_RED);
					fileIcon.setBackground(iconFont2);
				}
				TextView filepath = (TextView) convertView
						.findViewById(R.id.file_path);
				filepath.setText(path_data.get(position));
				TextView fileSize = (TextView) convertView
						.findViewById(R.id.file_size);
				fileSize.setText(getFileSize(position));

				break;
			}
			return convertView;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		// TODO Auto-generated method stub
		// list_view.invalidateViews();
		File file = new File(path_data.get(position));

		if (file.isDirectory()) {
			if (file.canRead()) {
				lastChosenOne = -1;
				chosenOne = -1;
				getDir(path_data.get(position));
			}
		} else {
			if (chosenOne != -1) {
				lastChosenOne = chosenOne;
			}
			chosenPath = path_data.get(position);
			chosenOne = position;
			adapter.notifyDataSetChanged();
			//T.mToast(FileSenderActivity.this, "you choose :" + file.getName());
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		 Intent returnIntent = new Intent();
		 returnIntent.putExtra("result",chosenPath);
		 setResult(RESULT_OK,returnIntent);     
		 finish();
		
		
//		final File file = new File(chosenPath);
//		ServiceDiscoveryManager sdm = ServiceDiscoveryManager
//				.getInstanceFor(smack.getConnection());
//		if (sdm == null)
//			sdm = new ServiceDiscoveryManager(smack.getConnection());
//		sdm.addFeature("http://jabber.org/protocol/disco#info");
//		sdm.addFeature("jabber:iq:privacy");
//
//		FileTransferManager manager = new FileTransferManager(
//				smack.getConnection());
//
//		// Create the outgoing file transfer
//		final OutgoingFileTransfer transfer = manager
//				.createOutgoingFileTransfer(smack.getFullyJID(u_JID));
//
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				try {
//					transfer.sendFile(file, "come on buddy,get it");
//				} catch (XMPPException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//
//				if (transfer.getStatus().equals(Status.refused)
//						|| transfer.getStatus().equals(Status.error)
//						|| transfer.getStatus().equals(Status.cancelled)) {
//					System.out.println(transfer.getStatus() + "  refused cancelled error "
//							+ transfer.getError());
//				} else {
//					System.out.println("Success");
//				}
//			}
//		}).start();
//		// Send the file

	}

}
