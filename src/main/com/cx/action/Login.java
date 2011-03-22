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

public class Login extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String nextJSP = "/home.jsp";
		
		String login = req.getParameter("cx_login");
		String pwd = req.getParameter("cx_pwd");

		String errText = null;
		UserManager userManager = UserManager.getInstance();
		
		// validate parameters
		if (errText == null && (null == login || login.length() < 5)) {
			errText = "Bitte geben Sie ein Login ein. (mind. 5 Zeichen)";
		}
		if (errText == null && (null == pwd || pwd.length() < 5)) {
			errText = "Bitte geben Sie ein Passwort ein. (mind. 5 Zeichen)";
		}
		
		User user = userManager.getUserByLogin(login);
		if (errText == null) {
			// check if login exists
			if (user == null) {
				errText = "Unbekanntes Login: " + login;
			}
		}
		
		if (errText == null){
			// Check the password
			if (!pwd.equals(user.getPwd())) {
				errText = "Falsches Passwort.";
			}
		}
		
		if (errText == null) {
			// Successful login: update last login date
			userManager.updateUserLastLogin(user);
			// we put the user without new last login date into session. we show the "last" login date
			
			// Successful login: put user into session
			HttpSession session = ((HttpServletRequest)req).getSession();
			session.setAttribute("cx.user", user);
			session.setAttribute("cx.loggedin", true);
		}

		// in case of errors return to index.jsp
		if (errText != null) {
			req.setAttribute("cx_errText", errText);
			req.setAttribute("cx_login", login);
			nextJSP = "/index.jsp";
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(req, resp);
	}

}
