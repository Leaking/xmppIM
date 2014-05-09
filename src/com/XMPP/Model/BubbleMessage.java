package com.XMPP.Model;

import org.jivesoftware.smackx.filetransfer.FileTransferRequest;

import com.XMPP.util.L;
import com.XMPP.util.MessageType;

/**
 * Message is a Custom Object to encapsulate message information/fields
 * 
 * @author Adil Soomro
 * 
 */
public class BubbleMessage {

	boolean isMine;
	MessageType type;

	String message;
	int pictureID;
	String status;

	//send and revceive file
	String filename;
	String filesize;
	String fileStage;
	int fileProgressVal;
	//receive file
	FileTransferRequest request;
	
	


	public BubbleMessage(){
		
	}
	// receive file 
	public BubbleMessage(FileTransferRequest request,String filename,String filesize){
		this.filename = filename;
		this.filesize = filesize;
		this.request = request;
		this.fileStage = "Negotiating";
		this.type = MessageType.FILE;
		this.fileProgressVal = 0;
		this.isMine = false;
	}
	// send file
	public BubbleMessage(String filename,String filesize ,MessageType type , boolean isMine){
		this.filename =filename;
		this.filesize = filesize;
		this.type = type;
		this.isMine = isMine;
	}
	// message
	public BubbleMessage(String message ,MessageType type , boolean isMine) {
		super();
		this.type = type;
		this.message = message;
		this.isMine = isMine;
	}
	public BubbleMessage(int pictureID,boolean isMine){
		super();
		this.type = MessageType.PICTURE;
		this.pictureID = pictureID;
		this.isMine = isMine;
	}


	public boolean isMine() {
		return isMine;
	}

	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getPictureID() {
		return pictureID;
	}

	public void setPictureID(int pictureID) {
		this.pictureID = pictureID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String string = " Time : "  + this.type + " Content "  + this.getMessage();
		return string;
	}
	public FileTransferRequest getRequest() {
		return request;
	}
	public void setRequest(FileTransferRequest request) {
		this.request = request;
	}
	public String getFileStage() {
		return fileStage;
	}
	public void setFileStage(String fileStage) {
		this.fileStage = fileStage;
	}
	public int getFileProgressVal() {
		return fileProgressVal;
	}
	public void setFileProgressVal(int fileProgressVal) {
		this.fileProgressVal = fileProgressVal;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilesize() {
		return filesize;
	}
	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

}
