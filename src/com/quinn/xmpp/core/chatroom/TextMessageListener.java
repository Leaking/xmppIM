package com.quinn.xmpp.core.chatroom;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import com.quinn.xmpp.util.LogcatUtils;

/**
 * @author Quinn
 * @date 2015-3-30
 */
public class TextMessageListener implements MessageListener{

	@Override
	public void processMessage(Chat chat, Message message){
		LogcatUtils.v("recevice message from : " + chat.getParticipant());
		LogcatUtils.v("recevice message content : " + message.getBody());
		try {
			chat.sendMessage(message.getBody());
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
	
	public void sendMsgToUIdirectly(){
		
	}
	
	public void sendMsgThroughBroadCast(){
		
	}
	
	
	
	
	
	
	

}


