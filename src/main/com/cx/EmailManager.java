package com.cx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;

import com.cx.model.Email;
import com.cx.model.User;
import com.cx.util.ConnectionPool;

public class EmailManager {
	private static EmailManager theOneEmailManager;
	
	private static Logger log = Logger.getLogger(EmailManager.class);
	
	public static final String TYPE_BUSINESS = "BUSINESS";
	public static final String TYPE_PRIVATE = "PRIVATE";
	
	// singleton
	public static EmailManager getInstance() {
		if (theOneEmailManager == null) {
			try {
				theOneEmailManager = new EmailManager();
			}
			catch (Exception e) {
				log.error("Error creating EmailManager: ", e);
			}
		}
		return theOneEmailManager;
	}
	
	public boolean existsEmail(String checkEmail) {
		boolean exists = false;
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT count(*) FROM cx_email WHERE email = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, checkEmail);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				exists = (rs.getLong(1) > 0);
			}
		}
		catch (Exception e) {
			log.error("EmailManager.existsEmail: ", e);
		}
		finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return exists;
	}
	
	public void createEmail(User user, Email newEmail) {
		// Validation: we need User and Email to save
		if (null == newEmail || null == user) {
			return;
		}
		
		// save newEmail in database
        Connection con = null;
		try {
			String sql = "INSERT INTO cx_email (user_id, email_type, email, activated, hash_code) " +
				" VALUES (?, ?, ?, 0, ?)";

			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setLong(1, user.getUserId());
			stmt.setString(2, newEmail.getEmailType());
			stmt.setString(3, newEmail.getEmail());
			stmt.setInt(4, newEmail.getHashCode());

			if (1 == stmt.executeUpdate()) {
				log.info("EmailManager.createEmail(): Email created for " + user.getFirstName() + " " + user.getLastName() + ", Login: " + user.getLogin() + ", Email: " + newEmail.getEmail());
			}
		}
		catch (Exception e) {
			log.error("EmailManager.createEmail(): ", e);
		}
		finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}
	
	public void emailRegistration (int code) {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "UPDATE cx_email SET activated = true, activation_time = sysdate() WHERE hash_code = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, code);
			
			if (1 == stmt.executeUpdate()) {
				log.info("EmailManager.emailRegistration() for code: " + code + " successful");
			}
		}
		catch (Exception e) {
			log.error("EmailManager.emailRegistration(): failed, code: " + code, e);
		}
		finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
	}

}
