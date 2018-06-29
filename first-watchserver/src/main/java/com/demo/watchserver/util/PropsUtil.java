package com.demo.watchserver.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropsUtil {

	private static Logger logger = LoggerFactory.getLogger(PropsUtil.class);

	public static final String CONFIG_NAME = "application.properties";

	private Properties properties;

	private PropsUtil() {
	}

	public static PropsUtil getInstance() {
		return Singleton.INSTANCE.getInstance();
	}

	private static enum Singleton {
		INSTANCE;

		private PropsUtil singleton;

		// JVM会保证此方法绝对只调用一次
		private Singleton() {
			singleton = new PropsUtil();
		}

		public PropsUtil getInstance() {
			return singleton;
		}
	}

	public Properties loadProperties(String configName) {

		try {
			if (StringUtils.isBlank(configName)) {
				configName = CONFIG_NAME;
			}
			properties = PropertiesLoaderUtils.loadAllProperties(configName);
		} catch (IOException e) {
			logger.error("load properties fail" + e.getMessage(), e);
		}
		return properties;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getValue(String key) {
		if (StringUtils.isBlank(key)) {
			return StringUtils.EMPTY;
		}
		return properties.getProperty(key);
	}

	public void testValueIfChange() {
		while (true) {
			String value = PropsUtil.getInstance().getValue("username");
			try {
				System.out.println(StringUtil.format("value--->{}", value));
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(PropsUtil.getInstance().loadProperties(CONFIG_NAME).get("username"));
	}

}
