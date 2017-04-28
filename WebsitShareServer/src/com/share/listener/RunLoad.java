package com.share.listener;

import java.util.concurrent.Executors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.share.service.SocketConnectCheck;
import com.share.socket.SocketAccept;

@WebListener
public class RunLoad implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		Executors.newCachedThreadPool().execute(new SocketAccept());

		Executors.newCachedThreadPool().execute(new SocketConnectCheck());
	}
}
