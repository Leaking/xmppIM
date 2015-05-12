package com.quinn.xmpp.ui.chatroom;

import com.quinn.xmpp.ui.BaseDataItem;

/**
 * @author Quinn
 * @date 2015-5-12
 */
public class FileDataItem extends BaseDataItem {
	
	private String filename;
	private String filesize;
	private int type;
	private int progress;
	
	
	public FileDataItem(String filename, String filesize,int type){
		this.filename = filename;
		this.filesize = filesize;
		this.type = type;
		
	}
	
	
	/**
	 * 文件状态
	 */
	public interface FileState{
		int REQUEST_STATE = 1; // 发送，未被接收
		int SENDING_STATE = 2; // 发送中
		int REJECTED_STATE = 3; // 被拒绝
		int RECEIVING_STATE = 4; // 接收中
		int COMING_STATE = 5; // 收到文件，未确认 是接受还是拒绝
	}
	
	
	
	
}


