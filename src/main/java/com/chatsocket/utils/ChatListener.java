package com.chatsocket.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.chatsocket.helper.Helper;

public class ChatListener extends Thread
{
	private OutputStreamWriter outStream;
	private InputStreamReader inStream;
	private String channel;
	private HashMap<Long, String> hshChatHistory = new HashMap<Long, String>();
	private List<String> listUsers = new ArrayList<String>();
	private long msgCount = 0;
	
	public HashMap<Long, String> getHshChatHistory() 
	{
		return hshChatHistory;
	}

	ChatListener(final OutputStreamWriter outStream, final InputStreamReader inStream, final String channel)
	{
		this.outStream = outStream;
		this.inStream = inStream;
		this.channel = channel;
	}
	
	@Override
	public void run()
	{
		System.out.println("********** Server started listening *************");
		
		BufferedWriter out = new BufferedWriter(this.outStream);
		BufferedReader in = new BufferedReader(this.inStream);
		
		String line = null;
		try
		{
			while((line = in.readLine()) != null)
			{
				line = Helper.replaceNonParseableChar(line);
				System.out.println(line);
				if(line.contains("PING"))
				{
					ChatUtils.send(out, line.replace("PING", "PONG"));
					System.out.println("From Server >>>> " + line.replace("PING", "PONG"));
				}
				if(line.contains("MODE"))
				{
					ChatUtils.send(out, "JOIN "+this.channel);
					System.out.println("From Server >>>> " + "JOIN "+this.channel);
				}
				if(line.contains("PRIVMSG") && line.contains(this.channel)) 
				{
					msgCount++;
					String strUserName = line.substring(1, line.indexOf("!"));
					String strMessage = line.substring(line.indexOf(this.channel) + this.channel.length()+2);
					String strUsrMsg = strUserName + ">>>" + strMessage;
					
					System.out.println(strUsrMsg);
					hshChatHistory.put(msgCount, strUsrMsg);
				}
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("********** Server stopped listening *************");
	}
}
