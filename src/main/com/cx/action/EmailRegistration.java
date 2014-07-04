package com.cx.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.cx.EmailManager;

public class EmailRegistration extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(EmailRegistration.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		EmailManager emailManager = EmailManager.getInstance(); 
		String codeStr = req.getParameter("code");
		int code = 0;
		try {
			code = Integer.parseInt(codeStr);
			emailManager.emailRegistration(code);
		}
		catch (Exception e) {
			log.error("EmailRegistration called with code: " + codeStr);
		}
		
		
		String nextJSP = "/emailregistered.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(req, resp);
	}
	
	

}
