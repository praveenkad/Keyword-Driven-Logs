package com.qafox.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class FileUtils extends ExcelUtils{
	
	public static Properties property = new Properties();
	public static Logger log = LogManager.getLogger(FileUtils.class);
	
	public String getProperty(String requiredKey){
		String configFilePath = PROPERTY_FILE_PATH;
		InputStream propFile;
		String requiredKeyValue = null;
		try {
			propFile = new FileInputStream(configFilePath);
			property.load(propFile);
		requiredKeyValue = property.getProperty(requiredKey);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return requiredKeyValue;
	}

}
