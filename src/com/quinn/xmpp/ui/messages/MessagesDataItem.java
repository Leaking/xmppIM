package com.quinn.xmpp.ui.messages;

/**
 * @author Quinn
 * @date 2015-3-17
 * 
 */
public class MessagesDataItem {
	private String msgIconURL;
	private String title;
	private String previewWords;
	private String timeStamp;
	
	
	public MessagesDataItem(String msgIconURL,String title,String previewWords,String timeStamp){
		this.msgIconURL = msgIconURL;
		this.title = title;
		this.previewWords = previewWords;
		this.timeStamp = timeStamp;
	}
	public String getMsgIconURL() {
		return msgIconURL;
	}
	public void setMsgIconURL(String msgIconURL) {
		this.msgIconURL = msgIconURL;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPreviewWords() {
		return previewWords;
	}
	public void setPreviewWords(String previewWords) {
		this.previewWords = previewWords;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}


