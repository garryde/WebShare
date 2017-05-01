package com.share.socket;

import java.net.Socket;
import java.util.concurrent.Executors;

import com.share.staticresource.StaticResource;

public class SocketRegist {
	Socket socket = null;
	String code = null;

	public SocketRegist(Socket socket, String code) {
		super();
		this.socket = socket;
		this.code = code;
		regist();
	}
	
	public void regist() {
		//启动监听线程
		Executors.newCachedThreadPool().execute(new SocketListen(socket));
		//注册Socket
		StaticResource.socketMap.put(code, socket);
	}
	
}
