package com.chatsocket.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.chatsocket.helper.Helper;
import com.chatsocket.utils.ChatUtils;
import com.fasterxml.jackson.databind.ObjectMapper;


@Path("chat")
public class ChatController 
{
	private static final long serialVersionUID = 1L;

	@GET
	@Path("/getHistory")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getChatHistory(@Context HttpServletRequest req)
	{	
		String nick, channel;
		String strResponse = "";
		Map<Long, String> hshChat = new HashMap<Long, String>();
		ChatUtils chatUtils = null;
		
		try
		{
			if(!Helper.correctNull(String.valueOf(req.getSession().getAttribute("token"))).equals(""))
			{
				nick = Helper.correctNull(String.valueOf(req.getSession().getAttribute("nick")));
				channel = Helper.correctNull(String.valueOf(req.getSession().getAttribute("channel")));
				
				if(req.getSession().getAttribute("chat")!=null)
				{
					chatUtils = (ChatUtils) req.getSession().getAttribute("chat");
					hshChat = chatUtils.getChatListen().getHshChatHistory();
				}
			}
			
			strResponse = new ObjectMapper().writeValueAsString(hshChat);	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return Response.status(Status.OK).entity(strResponse).build();
	}
}
