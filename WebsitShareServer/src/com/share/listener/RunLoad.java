package com.share.listener;

import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.share.socket.SocketConnectCheck;
import com.share.socket.SocketAccept;

@WebListener
public class RunLoad implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//启动Socket接收线程
		Executors.newCachedThreadPool().execute(new SocketAccept());
		//启动Socket活性检测线程
		Executors.newCachedThreadPool().execute(new SocketConnectCheck());
	}
}
