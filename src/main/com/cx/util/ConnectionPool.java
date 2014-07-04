package com.cx.util;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class ConnectionPool implements DataSource{
	
	private static Logger log = Logger.getLogger(ConnectionPool.class);
	
	private static ConnectionPool theOnePool;
	
	private List<Connection> currentlyUnusedConnections;
	
	/** number of currently instantiated connections. */
	private int connectionCount = 0;


	// the database settings:
	private static String dbUrl;
	private static String dbUser;
	private static String dbPasswd;

	private static final String DB_CONNECTION_DRIVER = "com.mysql.jdbc.Driver";
	private static final int MINIMUM_CONNECTIONS = 2;
	private static final int MAXIMUM_CONNECTIONS = 6;

	public static void initialize(String db_url, String db_user, String db_pwd) {
		dbUrl = db_url;
		dbUser = db_user;
		dbPasswd = db_pwd;
		
		log.debug("Pool initialized: " + db_url + ":" + db_user + ":" + db_pwd);
	}
	
	public static ConnectionPool getInstance() {
		if (theOnePool == null) {
			try {
				theOnePool = new ConnectionPool();
			}
			catch (Exception e) {
				log.error("Error creating ConnectionPool: ", e);
			}
		}
		return theOnePool;
	}

	public synchronized Connection getConnection() throws SQLException {
		Connection ret = this.createConnection();
		while (ret == null) {
			try {
				log.debug("No free connection available. Waiting...");
				this.wait();
			} catch (final InterruptedException ie) {
				log.error("Interrupted while waiting for Connection (Ignored).", ie);
			}
			ret = this.createConnection();
		}
		return ret;
	}

	public synchronized void returnConnection(Connection con) {
		this.currentlyUnusedConnections.add(con);
		this.notifyAll();
	}
	
	public synchronized boolean isClosed() {
		return this.currentlyUnusedConnections == null;
	}

	
	private ConnectionPool() throws SQLException, ClassNotFoundException {

		
/*
 * initialize needs to be called to set these parameters!
 * 		this.dbUrl = DB_URL;
		this.dbUser = DB_USER;
		this.dbPasswd = DB_PASSWORD;
*/
		this.currentlyUnusedConnections = new LinkedList<Connection>();
		
		Class.forName(DB_CONNECTION_DRIVER);
		for (int i = 0; i < MINIMUM_CONNECTIONS; i++) {
			this.currentlyUnusedConnections.add(this.instantiateNewConnection());
		}
		log.info("Successfully created DB ConnectionPool with " + MINIMUM_CONNECTIONS + " connections.");
	}

	/* Creates a new 'real' Connection */
	private synchronized Connection instantiateNewConnection() throws SQLException {
		if (this.connectionCount >= MAXIMUM_CONNECTIONS) {
			throw new SQLException("Unable to create Connection: Maximum amount of connections reached ["
			    + this.connectionCount + "/" + MAXIMUM_CONNECTIONS + "]");
		}
		log.debug("Instantiating new database connection. [" + ConnectionPool.dbUser + " -> " + ConnectionPool.dbUrl + "]");
		final Properties props = new Properties();
		if (ConnectionPool.dbUser != null) {
	        props.setProperty("user", ConnectionPool.dbUser);
        }
		if (ConnectionPool.dbPasswd != null) {
	        props.setProperty("password", ConnectionPool.dbPasswd);
        }
		final Connection con = DriverManager.getConnection(ConnectionPool.dbUrl, props);// this.dbUser,this.dbPasswd);
		this.connectionCount++;
		return con;
	}

	private synchronized Connection createConnection() throws SQLException {
		if (this.isClosed()) {
	        throw new SQLException("Connection pool is closed.");
        }

		if (!this.currentlyUnusedConnections.isEmpty()) {
			final Connection ret = this.currentlyUnusedConnections.remove(0);
			if (!checkConnection(ret)) {
				log.warn("Connection within pool is closed or not working.");
				this.connectionCount--;
				return this.createConnection();
			}
			ret.clearWarnings();
			return ret;
		}
		if (this.connectionCount < MAXIMUM_CONNECTIONS) {
			return this.instantiateNewConnection();
		}
		return null;
	}
	
	// returns false, if simple SQL did not work with the connection
	private boolean checkConnection(Connection con) {
		boolean checked = false;
		
		try {
			if (con.isClosed()) {
				return false;
			}
			String sql = "SELECT count(*) FROM ShopUser";
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				checked = true;
			}
		}
		catch (Exception ex) {
			checked = false;
		}
		return checked;
	}

	public Connection getConnection(final String user, final String passwd) throws SQLException {
		if (ConnectionPool.dbUser.equals(user) && ConnectionPool.dbPasswd.equals(passwd)) {
	        return this.getConnection();
        }
		throw new UnsupportedOperationException("Cannot get connection for '" + user
		    + "'. This data source is restricted to database user '" + ConnectionPool.dbUser + "'.");
	}

	
	public int getLoginTimeout() {
		return DriverManager.getLoginTimeout();
	}

	public PrintWriter getLogWriter() {
		return DriverManager.getLogWriter();
	}

	public void setLogWriter(final PrintWriter writer) {
		DriverManager.setLogWriter(writer);
	}

	public void setLoginTimeout(final int i) {
		DriverManager.setLoginTimeout(i);
	}

	@Override
	public boolean isWrapperFor(Class<?> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> arg0) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
