package com.share.service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.share.staticresource.StaticResource;
import com.share.util.SocketUtil;

public class SendUrlService {
	String url;
	String code;
	public SendUrlService(String url, String code) {
		super();
		this.url = url;
		this.code = code;
	}
	public SendUrlService() {
		super();
	}

	public boolean send() {
		Socket socket = StaticResource.socketMap.get(code);
		if (socket != null) {
			String jsonString = "{\"act\":\"url\",\"code\":\""+ url +"\"}";
			OutputStream outputStream;
			try {
				outputStream = socket.getOutputStream();
				SocketUtil.writeStr2Stream(jsonString, outputStream);
				//outputStream.close();
				return true;
			} catch (IOException e) {
				StaticResource.socketMap.remove(code);
				return false;
			}
		} else {
			return false;
		}
	}
	
	
}
