package com.automation.dependencies;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileManager {
	
	static Properties prop = new Properties();
	
	public static String GetProperty(String key) throws IOException {
		InputStream input = new FileInputStream("./project.properties");

		prop.load(input);
		String returnValue = prop.getProperty(key);
		input.close();
		
		return returnValue;
	}

}
