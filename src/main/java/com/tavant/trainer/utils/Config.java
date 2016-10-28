package com.tavant.trainer.utils;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Config implements ServletContextListener {
	
	
	
	
	private static final String ATTRIBUTE_NAME = "config";
	private static Properties config = new Properties();
	// private static final Config _INSTANCE=new Config();

	static{
		
		try {
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
			System.out.println("config loaded");
		} catch (Exception e) {
			System.out.println("problem in config file loading");
			e.printStackTrace();
			System.exit(0);
		}
		// event.getServletContext().setAttribute(ATTRIBUTE_NAME, this);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// NOOP.
	}

	/*
	 * public static Config getInstance(ServletContext context) { return
	 * (Config) context.getAttribute(ATTRIBUTE_NAME); }
	 */

	public static String getProperty(String key) {
		return config.getProperty(key);
	}

}
