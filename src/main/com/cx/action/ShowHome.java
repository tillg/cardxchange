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

public class ShowHome extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nextJSP = "/home.jsp";

		// get user from session
		HttpSession session = ((HttpServletRequest)req).getSession();
		User user = (User)session.getAttribute("cx.user");

		if (user == null) {
			nextJSP = "/index.jsp";
		}
		
		// put position for confirmation status
		UserManager umgr = UserManager.getInstance();
		req.setAttribute("posConfirmation", umgr.getConfirmPos(user, 968));
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	
}
