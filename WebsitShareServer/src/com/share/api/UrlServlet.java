package com.share.api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.share.service.ControlService;
import com.share.service.SendUrlService;

@WebServlet("/url")
public class UrlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UrlServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = (String) request.getParameter("url");
		String code = (String) request.getParameter("code");

        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.getWriter().print(new ControlService().handle(url,code));

		
	}


}
