package com.cx.listener;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.cx.util.ConnectionPool;

public class WebDbInitializer implements ServletContextListener {

	

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext ctx = servletContextEvent.getServletContext();
        
		String db_url;
		String db_user;
		String db_pwd;
		
		db_url = ctx.getInitParameter("DB_URL");
		db_user = ctx.getInitParameter("DB_USER");
		db_pwd = ctx.getInitParameter("DB_PASSWORD");
		
		ConnectionPool.initialize(db_url, db_user, db_pwd);
		
	
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		// TODO Auto-generated method stub
		
	}
	
	
}

