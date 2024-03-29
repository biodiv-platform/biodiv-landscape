package com.strandls.landscape;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LandscapeConfig {
	
	private LandscapeConfig() {}

	private static final Logger logger = LoggerFactory.getLogger(LandscapeConfig.class);
	
	private static Properties properties;

	static {
		InputStream in = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("config.properties");

		properties = new Properties();
		try {
			properties.load(in);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
	
	public static String getString(String key) {
		return properties.getProperty(key);
	}
	
	public static int getInt(String key) {
		return Integer.parseInt(getString(key));
	}
}
