package br.com.intelipost.support;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationProperties {
	private static Properties properties;
	
	public static void setProperties() {
		try {
			properties = new Properties();
			properties.load(new FileInputStream("./config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperty(String property) {
		return properties.getProperty(property);
	}
}
