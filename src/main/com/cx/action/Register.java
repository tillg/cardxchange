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

public class Register extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String nextJSP = "/home.jsp";
		
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String email = req.getParameter("email");
		String login = req.getParameter("login");
		String pwd = req.getParameter("pwd");

		String errText = null;
		UserManager userManager = UserManager.getInstance();
		
		// validate parameters
		if (null == firstname || firstname.length() < 3) {
			errText = "Bitte geben Sie Ihren Vornamen ein. (mind. 3 Zeichen)";
		}
		if (errText == null && (null == lastname || lastname.length() < 3)) {
			errText = "Bitte geben Sie Ihren Nachnamen ein. (mind. 3 Zeichen)";
		}
		if (errText == null && (null == email || email.length() < 3)) {
			errText = "Bitte geben Sie eine Emailadresse ein. (mind. 5 Zeichen)";
		}
		if (errText == null && (null == login || login.length() < 5)) {
			errText = "Bitte geben Sie ein Login ein. (mind. 5 Zeichen)";
		}
		if (errText == null && (null == pwd || pwd.length() < 5)) {
			errText = "Bitte geben Sie ein Passwort ein. (mind. 5 Zeichen)";
		}
		
		User newUser = new User();
		newUser.setFirstName(firstname);
		newUser.setLastName(lastname);
		newUser.setPrimaryEmail(email);
		newUser.setLogin(login);
		newUser.setPwd(pwd);
		
		if (errText == null) {
			// check if login exists already
			User checkUser = userManager.getUserByLogin(login);
			if (checkUser != null) {
				errText = "Das Login: " + login + " ist bereits vergeben.";
			}
		}
		
		if (errText == null){
			// Register the new User
			newUser = userManager.registerUser(newUser);
			
			// put user into session
			HttpSession session = ((HttpServletRequest)req).getSession();
			session.setAttribute("cx.user", newUser);
			session.setAttribute("cx.loggedin", true);
		}

		// in case of errors return to index.jsp
		if (errText != null) {
			req.setAttribute("errText", errText);
			req.setAttribute("firstname", firstname);
			req.setAttribute("lastname", lastname);
			req.setAttribute("email", email);
			req.setAttribute("login", login);
			nextJSP = "/index.jsp";
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(req, resp);
	}

	
}
