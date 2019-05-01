package utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;




public class FileUtils extends ExcelUtils{
	
	public static Properties property = new Properties();
	public static Logger log = Logger.getLogger("");
	
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
	
	public void loadLogs(){
		PropertyConfigurator.configure(LOG4J_PROP_FILE_PATH);
		log.info("Loaded log4j config file");
	}

}
