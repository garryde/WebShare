package com.share.staticresource;

import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


public class StaticResource {
	//Socket池
	public static ConcurrentHashMap<String, Socket> socketMap = new ConcurrentHashMap<String, Socket>();
}
