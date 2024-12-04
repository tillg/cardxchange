package com.cx.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cx.UserManager;
import com.cx.model.User;

public class ConfirmAddress extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nextJSP = "/home";

		// get the logged in User from the session
		HttpSession session = ((HttpServletRequest)req).getSession();
		User user = (User)session.getAttribute("cx.user");
		
		if (user != null) {
			// Set confirmation date to current date for the user
			user = UserManager.getInstance().confirmData(user);
			session.setAttribute("cx.user", user);
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(req, resp);
	}
	
	

}
