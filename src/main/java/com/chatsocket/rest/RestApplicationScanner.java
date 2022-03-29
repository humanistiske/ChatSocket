package com.chatsocket.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class RestApplicationScanner extends Application
{
	private Set<Object> singletons = new HashSet<Object>();
	
	public RestApplicationScanner()
	{
		System.out.println("==========RestApplicationScanner Initiated==========");
		singletons.add(new ChatController());
	}
	
	@Override
	public Set<Object> getSingletons()
	{
		return singletons;
	}
}
