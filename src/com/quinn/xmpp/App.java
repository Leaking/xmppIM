package com.quinn.xmpp;

import android.app.Application;

/**
 * 
 * @author Quinn
 * @date 2015-1-28
 */
public class App extends Application{
	
	private String serverAddr;
	private boolean ifAppear;
	private Smack smack;
	
	public Smack getSmack(){
		return smack == null ? new SmackImpl(): smack;
	}
	
	//the App disappear into background
	public void appDisappear(){
		
	}
	
	//the App appear onto foreground
	public void appAppear(){
		
	}
	
	// if the app in to foreground
	public boolean ifAppear(){
		return ifAppear;
	}

	public String getServerAddr() {
		return serverAddr;
	}

	public void setServerAddr(String serverAddr) {
		this.serverAddr = serverAddr;
	}
	
	
	
	
}