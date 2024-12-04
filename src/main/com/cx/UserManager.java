package com.cx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.cx.model.Email;
import com.cx.model.User;
import com.cx.util.ConnectionPool;
import com.cx.util.StringUtil;

public class UserManager {

	private static UserManager theOneUserManager;
	
	private static Logger log = Logger.getLogger(UserManager.class);
	
	// singleton
	public static UserManager getInstance() {
		if (theOneUserManager == null) {
			try {
				theOneUserManager = new UserManager();
			}
			catch (Exception e) {
				log.error("Error creating UserManager: ", e);
			}
		}
		return theOneUserManager;
	}
	
	public User getUserByLogin(String userLogin) {
		User loadedUser = null;
		
		// SELECT user by login from database
		Connection con = null;
		try {
			// get a database connection
			con = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT user_id, firstname, lastname, login, pwd, primary_email, last_login, last_confirm FROM cx_user WHERE active='A' AND login = ?";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, userLogin);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				// if active user found, then create User object from result
				loadedUser = new User();
				loadedUser.setUserId(rs.getLong("user_id"));
				loadedUser.setFirstName(rs.getString("firstname"));
				loadedUser.setLastName(rs.getString("lastname"));
				loadedUser.setLogin(rs.getString("login"));
				loadedUser.setPwd(rs.getString("pwd"));
				loadedUser.setPrimaryEmail(rs.getString("primary_email"));
				loadedUser.setLastLogin(rs.getDate("last_login"));
				loadedUser.setLastConfirm(rs.getDate("last_confirm"));
			}
			else {
				// no matching user found
				loadedUser = null;
			}
		}
		catch (Exception e) {
			log.error("UserManager.getUserByLogin: ", e);
		}
		finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		
		return loadedUser;
	}
	
	
	public User registerUser (User newUser) {

		// Validation: we need a User to save
		if (null == newUser) {
			return null;
		}
		
		// save newUser in database
        Connection con = null;
		try {
			String sql = "INSERT INTO cx_user (firstname, lastname, login, pwd, primary_email, last_login) " +
				" VALUES (?, ?, ?, ?, ?, sysdate())";

			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, newUser.getFirstName());
			stmt.setString(2, newUser.getLastName());
			stmt.setString(3, newUser.getLogin());
			stmt.setString(4, newUser.getPwd());
			stmt.setString(5, newUser.getPrimaryEmail());

			if (1 == stmt.executeUpdate()) {
				log.info("UserManager.registerUser(): User created for " + newUser.getFirstName() + " " + newUser.getLastName() + ", Login: " + newUser.getLogin() + ", Email: " + newUser.getPrimaryEmail());
				stmt = con.prepareStatement("SELECT LAST_INSERT_ID()");
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					newUser.setUserId(rs.getLong(1));
					newUser.setLastLogin(Calendar.getInstance().getTime());
				}
			}
			
			log.info("New user created in database with ID: " + newUser.getUserId());
		}
		catch (Exception e) {
			log.error("UserManager.registerUser(): ", e);
		}
		finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		
		// and also save the email
		Email newEmail = new Email();
		newEmail.setUserId(newUser.getUserId());
		newEmail.setEmailType(EmailManager.TYPE_BUSINESS);
		newEmail.setEmail(newUser.getPrimaryEmail());
		newEmail.setActivated(false);
		newEmail.setHashCode(StringUtil.getHashCode(newEmail.getEmail()));
		EmailManager.getInstance().createEmail(newUser, newEmail);
		
		// return saved User (now with userId)
		return newUser;
	}
	
	public User updateUserLastLogin (User user) {
		// Validation: we need a User to update
		if (null == user) {
			return null;
		}
		
		// save newUser in database
        Connection con = null;
		try {
			String sql = "UPDATE cx_user SET last_login = sysdate() WHERE user_id = ?";

			con = ConnectionPool.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setLong(1, user.getUserId());

			if (1 == stmt.executeUpdate()) {
				log.info("UserManager.updateUserLastLogin(): User last_login set for " + user.getFirstName() + " " + user.getLastName() + ", Login: " + user.getLogin() + ", Email: " + user.getPrimaryEmail());
				user.setLastLogin(Calendar.getInstance().getTime());
			}
		}
		catch (Exception e) {
			log.error("UserManager.updateUserLastLogin(): ", e);
		}
		finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		
		// return saved User (now with userId)
		return user;
	}
	
	public long getActiveUserCount() {

		long activeUserCount = 0;
		
		// SELECT user by login from database
		Connection con = null;
		try {
			// get a database connection
			con = ConnectionPool.getInstance().getConnection();
			String sql = "SELECT count(*) FROM cx_user WHERE active='A'";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				activeUserCount = rs.getLong(1);
			}
		}
		catch (Exception e) {
			log.error("UserManager.getActiveUserCount(): ", e);
		}
		finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		
		return activeUserCount;
	}
	
	// Counter has at least MINDIGITS digits (leading zeros).
	// comma character is set as thousand separator.
	// static method for easy access in index.jsp
	public static char[] getActiveUserCountAsStringArray() {
		final int MINDIGITS = 7;
		String strCounter = "";
		
		long counter = UserManager.getInstance().getActiveUserCount();
		long lastcounter = counter;
		int position = 0;
		
		while (lastcounter > 0 || position < MINDIGITS) {
			strCounter = ((Long)(counter % 10)).toString() + strCounter; 
			lastcounter = counter;
			counter = counter / 10;
			
			// set thousand separator as comma
			position ++;
			if (position % 3 == 0) {
				strCounter = "," + strCounter;
			}
		}
		return strCounter.toCharArray();
	}


	public User confirmData(User user) {

		user.setLastConfirm(new Date());
		
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			String sql = "UPDATE cx_user SET last_confirm = sysdate() WHERE user_id = ?";
			PreparedStatement stmt = con.prepareStatement(sql);

			stmt.setLong(1, user.getUserId());

			stmt.executeUpdate();
		}
		catch (Exception e) {
			log.error("UserManager.confirmData(): ", e);
		}
		finally {
			ConnectionPool.getInstance().returnConnection(con);
		}
		return user;
	}
	
	public long getConfirmPos(User user, long statusWidth) {
		if (user == null) {
			return 0;
		}
		
		long ret = statusWidth;
		long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
		long ageInDays = 1;
		
		if (user.getLastConfirm() != null) { 
			ageInDays = ((new Date()).getTime() - user.getLastConfirm().getTime()) / MILLSECS_PER_DAY;
		}
		if (ageInDays <= 30) {
			ret = new Double(ageInDays / 30.0 * statusWidth / 2.0).longValue();
		}
		else {
			ret = new Double(statusWidth / 2.0 + (ageInDays - 30.0)/335.0 * statusWidth / 2.0).longValue(); 
		}
		if (ret > statusWidth) ret = statusWidth;
		
		log.debug("Staus age in days: " + ageInDays + " Status bar right position: " + ret);
			
		return ret;
	}
	
	// ---------------- end of public methods ----------------
	
	
}
