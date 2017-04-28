package com.share.socket;

import java.net.Socket;

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
		StaticResource.socketMap.put(code, socket);
	}
	
}
