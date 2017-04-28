package com.share.api;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.share.staticresource.StaticResource;

/**
 * Servlet implementation class SocketNum
 */
@WebServlet("/num")
public class SocketNum extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SocketNum() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.getWriter().print("连接客户端数：" + StaticResource.socketMap.size());
		response.getWriter().print("<br />");
        for (Map.Entry<String, Socket> entry : StaticResource.socketMap.entrySet()) {
            response.getWriter().print("ConCode：" + entry.getKey() + "&nbsp&nbsp&nbsp&nbsp" +entry.getValue() + "<br />");
        }
	}

}
