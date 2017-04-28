package com.share.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.share.service.SendUrlService;

/**
 * Servlet implementation class UrlServlet
 */
@WebServlet("/url")
public class UrlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UrlServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url = (String) request.getParameter("url");
		String code = (String) request.getParameter("code");

		if (url == null || code == null) {
			response.getWriter().print("{\"act\":\"openResult\",\"code\":\"false\"}");
		}else if (url.equals("") || code.equals("")) {
			response.getWriter().print("{\"act\":\"openResult\",\"code\":\"invalid parameter\"}");
		}else {
			SendUrlService sendUrlService = new SendUrlService(url, code);
			boolean result = sendUrlService.send();
			
			if (result) {
				response.getWriter().print("{\"act\":\"openResult\",\"code\":\"true\"}");
			} else {
				response.getWriter().print("{\"act\":\"openResult\",\"code\":\"false\"}");
			}
			
		}
		
	}


}
