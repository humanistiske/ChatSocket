package com.chatsocket.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

import com.chatsocket.helper.ApplicationConstants;

public class ChatUtils
{
	private OutputStreamWriter outStream;
	private InputStreamReader inStream;
	private boolean boolChatStarted = false;
	private ChatListener chatListen;
	private HashMap hshConnection = new HashMap();
	private BufferedWriter out = null; 
	
	public HashMap join(final String nick, final String channel)
	{
		HashMap hshResult = new HashMap();
		
		try
		{
			hshConnection = SocketUtils.getConnection();
			if(hshConnection.containsKey("outStream") && hshConnection.containsKey("inStream"))
			{
				this.outStream = (OutputStreamWriter) hshConnection.get("outStream");
				this.inStream = (InputStreamReader) hshConnection.get("inStream");
				
				out = new BufferedWriter((OutputStreamWriter) hshConnection.get("outStream"));
				
				send(out, "NICK "+nick);
				send(out, "USER bot 0 * :"+nick);

				boolChatStarted = true;		
				hshResult.put("Status", ApplicationConstants.SUCCESS);
			}
		}
		catch(Exception e)
		{
			hshResult.put("Status", ApplicationConstants.FAILURE);
			hshResult.put("message", hshResult.get("message"));
			e.printStackTrace();
		}
		
		return hshResult;
	}
	
	public void getChatData(final String channel)
	{
		try
		{
			if(this.chatListen==null)
			{
				ChatListener chatListen = new ChatListener(this.outStream, this.inStream, channel);
				this.setChatListen(chatListen);	
			}
			this.chatListen.setDaemon(true);		
			this.chatListen.start();
			this.chatListen.interrupt();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void send(final BufferedWriter out, final String msg)
	{
		try 
		{
			out.write(msg+"\r\n");
			System.out.println("From Server >>>> " + msg);
			out.flush();
			if(msg.contains("PVTMSG"))
			{
				
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public ChatListener getChatListen() {
		return chatListen;
	}

	public void setChatListen(final ChatListener chatListen) {
		this.chatListen = chatListen;
	}
	
	public boolean isBoolChatStarted() {
		return boolChatStarted;
	}
}

