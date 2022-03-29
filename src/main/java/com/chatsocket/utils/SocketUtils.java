package com.chatsocket.utils;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;

import com.chatsocket.helper.ApplicationConstants;

public class SocketUtils 
{
	public static HashMap getConnection()
	{
		Socket socket = null;
		HashMap hshResult = new HashMap();
		
		try 
		{
			socket = new Socket(ApplicationConstants.SERVER, ApplicationConstants.PORT);
			OutputStreamWriter outStream = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
			System.out.println("Output Stream captured");
			InputStreamReader inStream = new InputStreamReader(socket.getInputStream(), "UTF-8");
			System.out.println("Input Stream captured");
			
			hshResult.put("outStream", outStream);
			hshResult.put("inStream", inStream);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return hshResult;
	}
}
