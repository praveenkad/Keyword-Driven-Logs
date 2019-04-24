package com.qafox.utils;

import java.io.File;

public class Constants {
	
	public static final String PROPERTY_FILE_PATH = "src/test/java/com/qafox/locators/WebElements.properties";
	public static final String EXCEL_FILE_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator+"test"+File.separator+"java"+File.separator+"com"+File.separator+"qafox"+File.separator+"exceldata"+File.separator+"TestData.xlsx";                               
    public static final String CHROME_DRIVER_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator+"test"+File.separator+"resources"+File.separator+"DriverExecutable"+File.separator+"chromedriver.exe";
    public static final String SHEET_NAME = "Yelp Cases";
    public static final int USER_ACTION_CELL_NUM = 4;
    public static final int TEST_DATA_CELL_NUM = 5;
    public static final int WEBELEMENT_CELL_NUM = 3;
    		//"Reddit Cases";	
    		

}
