package com.chatsocket.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chatsocket.utils.ChatUtils;

@WebServlet("/login")
public class LoginServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String nick = request.getParameter("nick");
		String channel = request.getParameter("channel");
		
		System.out.println("nick>>>> " + nick + " channel>>>>>> " + channel);
		
		request.getSession().setAttribute("nick", nick);
		request.getSession().setAttribute("channel", channel);
		request.getSession().setAttribute("token", getToken());
		
		ChatUtils chatUtils = new ChatUtils();
		chatUtils.join(nick, channel);
		chatUtils.getChatData(channel);
		request.getSession().setAttribute("chat", chatUtils);
		
		response.sendRedirect("chat.jsp");
	}
	
	public String getToken()
	{
		String token = String.valueOf(System.currentTimeMillis());
		return token;
	}
}
