package com.cx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.cx.model.Address;
import com.cx.model.User;
import com.cx.util.ConnectionPool;

public class AddressManager {

	public static String BUSINESS_TYPE = "1";
	public static String PRIVATE_TYPE = "2";
	
	
	private static AddressManager theOneAddressManager;
	
	private static Logger log = Logger.getLogger(AddressManager.class);
	
	// singleton
	public static AddressManager getInstance() {
		if (theOneAddressManager == null) {
			try {
				theOneAddressManager = new AddressManager();
			}
			catch (Exception e) {
				log.error("Error creating AddressManager: ", e);
			}
		}
		return theOneAddressManager;
	}
	
	// save address for the user
	public void saveAddress (User user, Address address) {
		// validation
		if (user == null || address == null) {
			return;
		}
		
		Connection con = null;
		try {
			// check if we need INSERT or UPDATE
			long countOfAddress = 0;

			con = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT count(*) FROM cx_address WHERE user_id = ? AND address_type = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setLong(1, user.getUserId());
			stmt.setString(2, address.getAddressType());
			
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				// count of 1 means that the address of the type exists already for the user
				countOfAddress = rs.getLong(1);
			}

			if (countOfAddress == 0) {
				// INSERT address
				sql = "INSERT INTO cx_address (user_id, address_type, street, zip, city, country, state, ";
				sql += " tel_country, tel_city, tel_number, fax_country, fax_city, fax_number, ";
				sql += " mobile_country, mobile_city, mobile_number) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				
				stmt = con.prepareStatement(sql);
				stmt.setLong(1, user.getUserId());
				stmt.setString(2, address.getAddressType());
				stmt.setString(3, address.getStreet());
				stmt.setString(4, address.getZip());
				stmt.setString(5, address.getCity());
				stmt.setString(6, address.getCountry());
				stmt.setString(7, address.getState());
				stmt.setString(8, address.getTelCountry());
				stmt.setString(9, address.getTelCity());
				stmt.setString(10, address.getTelNumber());
				stmt.setString(11, address.getFaxCountry());
				stmt.setString(12, address.getFaxCity());
				stmt.setString(13, address.getFaxNumber());
				stmt.setString(14, address.getMobileCountry());
				stmt.setString(15, address.getMobileCity());
				stmt.setString(16, address.getMobileNumber());
				
				stmt.executeUpdate();
			}
			else {
				// UPDATE address
				sql = "UPDATE cx_address SET street = ?, zip = ?, city = ?, country = ?, state = ?, ";
				sql += " tel_country = ?, tel_city = ?, tel_number = ?, fax_country = ?, fax_city = ?, fax_number = ?, ";
				sql += " mobile_country= ?, mobile_city = ?, mobile_number = ? WHERE user_id = ? AND address_type = ?";

				stmt = con.prepareStatement(sql);
				stmt.setString(1, address.getStreet());
				stmt.setString(2, address.getZip());
				stmt.setString(3, address.getCity());
				stmt.setString(4, address.getCountry());
				stmt.setString(5, address.getState());
				stmt.setString(6, address.getTelCountry());
				stmt.setString(7, address.getTelCity());
				stmt.setString(8, address.getTelNumber());
				stmt.setString(9, address.getFaxCountry());
				stmt.setString(10, address.getFaxCity());
				stmt.setString(11, address.getFaxNumber());
				stmt.setString(12, address.getMobileCountry());
				stmt.setString(13, address.getMobileCity());
				stmt.setString(14, address.getMobileNumber());
				stmt.setLong(15, user.getUserId());
				stmt.setString(16, address.getAddressType());

				stmt.executeUpdate();
			}
		}
		catch (Exception e) {
			log.error("UserManager.getUserByLogin: ", e);
		}
		finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

	// load business address for a user
	public Address loadBusinessAddress (User user) {
		return loadAddress(user, BUSINESS_TYPE);
	}
	// load private address for a user
	public Address loadPrivateAddress (User user) {
		return loadAddress(user, PRIVATE_TYPE);
	}
	
	// -------------  end of public methods ------------- 
	
	// load address from database
	private Address loadAddress(User user, String addressType) {
		Address address = new Address();
		
		// SELECT user by login from database
		Connection con = null;
		try {
			// get a database connection
			con = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT user_id, address_type, street, zip, city, country, state, ";
				sql += " tel_country, tel_city, tel_number, fax_country, fax_city, fax_number, ";
				sql += " mobile_country, mobile_city, mobile_number";
				sql += " FROM cx_address WHERE user_id = ? AND address_type = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setLong(1, user.getUserId());
			stmt.setString(2, addressType);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				// address found
				address.setUserid(user.getUserId());
				address.setAddressType(addressType);
				address.setStreet(rs.getString("street"));
				address.setZip(rs.getString("zip"));
				address.setCity(rs.getString("city"));
				address.setCountry(rs.getString("country"));
				address.setState(rs.getString("state"));
				address.setTelCountry(rs.getString("tel_country"));
				address.setTelCity(rs.getString("tel_city"));
				address.setTelNumber(rs.getString("tel_number"));
				address.setFaxCountry(rs.getString("fax_country"));
				address.setFaxCity(rs.getString("fax_city"));
				address.setFaxNumber(rs.getString("fax_number"));
				address.setMobileCountry(rs.getString("mobile_country"));
				address.setMobileCity(rs.getString("mobile_city"));
				address.setMobileNumber(rs.getString("mobile_number"));
			}
			else {
				// address stays empty
			}
		}
		catch (Exception e) {
			log.error("AddressManager.loadAddress: ", e);
		}
		finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return address;
	}

}
