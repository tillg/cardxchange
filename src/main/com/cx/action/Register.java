package com.cx.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import com.cx.EmailManager;
import com.cx.UserManager;
import com.cx.model.User;
import com.cx.util.StringUtil;

public class Register extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(Register.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String nextJSP = "/home";
		
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
		EmailManager emailManager = EmailManager.getInstance();
		if (errText == null && emailManager.existsEmail(email)) {
			errText = "Die Email '" + email + "' ist bereits registriert.";
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
			
			// send an email for activating the entered email address.
			ServletContext ctx = req.getSession().getServletContext(); // read mail hostname from web.xml
			StringBuffer msg = new StringBuffer();
			msg.append("Willkommen bei cardXchange,\n");
			msg.append("\n");
			msg.append("wir freuen uns, dass Sie sich kostenlos bei cardXchange angemeldet haben.\n");
			msg.append("Bitte bestätigen Sie Ihre Email jetzt: ");
			
			msg.append(ctx.getInitParameter("REG.CONFIRM_URL"));
			msg.append("?code=");
			msg.append(StringUtil.getHashCode(email));

			msg.append("\n\n");
			msg.append("Erst durch diese Bestätigung aktivieren Sie Ihre email. Die Bestätigung dient dazu sicherzustellen, dass Sie persönlich Ihre E-Mail-Adresse benutzen und sie nicht unerlaubt von anderen genutzt wurde.\n"); 
			msg.append("\n\n");
			msg.append("Herzlichst,\n");
			msg.append("Ihr Team von cardXchange\n");

			// send email
			try {
				SimpleEmail mail = new SimpleEmail();
				mail.setHostName(ctx.getInitParameter("MAIL.HOSTNAME"));  
				mail.addTo(email, firstname + " " + lastname);
				mail.setFrom(ctx.getInitParameter("MAIL.FROM"), "cardXchange");
				mail.setSubject("cardXchange: Email Bestätigung");
				mail.setMsg(msg.toString());
				mail.setBounceAddress(ctx.getInitParameter("MAIL.BOUNCEADDRESS")); // so bounce message does not go to entered email
				mail.send();
			
				// set flag to show confirmation message that the email has been sent.
				String nlreg_status = "SENT";
				req.setAttribute("nlreg_status", nlreg_status);
			}
			catch (Exception ex) {
				log.error("Register: could not send message: ", ex);
			}
			
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
