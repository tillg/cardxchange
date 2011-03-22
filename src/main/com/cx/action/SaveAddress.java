package com.cx.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cx.AddressManager;
import com.cx.model.Address;
import com.cx.model.User;

public class SaveAddress extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String nextJSP = "/address.jsp";
		String errText = null;

		// get the logged in User from the session
		HttpSession session = ((HttpServletRequest)req).getSession();
		User user = (User)session.getAttribute("cx.user");
		
		if (user == null) {
			errText = "Zum Speichern der Adresse müssen Sie sich erst einloggen.";
			nextJSP = "/index.jsp";
		}
		
		String addressType = req.getParameter("addressType");
		String street = req.getParameter("street");
		String zip = req.getParameter("zip");
		String city = req.getParameter("city");
		String country = req.getParameter("country");
		String state = req.getParameter("state");
		String telCountry = req.getParameter("tel_country");
		String telCity = req.getParameter("tel_city");
		String telNumber = req.getParameter("tel_number");
		String faxCountry = req.getParameter("fax_country");
		String faxCity = req.getParameter("fax_city");
		String faxNumber = req.getParameter("fax_number");
		String mobileCountry = req.getParameter("mobile_country");
		String mobileCity = req.getParameter("mobile_city");
		String mobileNumber = req.getParameter("mobile_number");
		
		AddressManager addressManager = AddressManager.getInstance();
		
		// validate parameters
		
		Address editAddress = new Address();
		editAddress.setUserid(user.getUserId());
		editAddress.setAddressType(addressType);
		editAddress.setStreet(street);
		editAddress.setZip(zip);
		editAddress.setCity(city);
		editAddress.setCountry(country);
		editAddress.setState(state);
		editAddress.setTelCountry(telCountry);
		editAddress.setTelCity(telCity);
		editAddress.setTelNumber(telNumber);
		editAddress.setFaxCountry(faxCountry);
		editAddress.setFaxCity(faxCity);
		editAddress.setFaxNumber(faxNumber);
		editAddress.setMobileCountry(mobileCountry);
		editAddress.setMobileCity(mobileCity);
		editAddress.setMobileNumber(mobileNumber);

		if (errText == null) {
			// save the address
			addressManager.saveAddress(user, editAddress);
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
		dispatcher.forward(req, resp);
	}

	
}
