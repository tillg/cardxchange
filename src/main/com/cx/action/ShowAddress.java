package com.cx.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cx.AddressManager;
import com.cx.UserManager;
import com.cx.model.Address;
import com.cx.model.User;

public class ShowAddress extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nextJSP = "/address.jsp";
		String errText = null;

		// get user from session
		HttpSession session = ((HttpServletRequest)req).getSession();
		User user = (User)session.getAttribute("cx.user");
		
		if (user == null) {
			errText = "Für diese Aktion wird ein login benötigt.";
			nextJSP = "/index.jsp";
		}
		
		// load address for logged in user
		if (errText  == null) {
			AddressManager addressManager = AddressManager.getInstance();
			Address busAddress = addressManager.loadBusinessAddress(user);
			Address privateAddress = addressManager.loadPrivateAddress(user);
			
			// put business and private address into request
			req.setAttribute("busAddress", busAddress);
			req.setAttribute("privateAddress", privateAddress);
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(req, resp);
	}



	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

	
}
