package com.qafox.testsuite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElementChopper {
	
	public static Logger log;
	
	public static void main(String[]args){
		
		log = LogManager.getLogger(ElementChopper.class);
		log.info("This is Info Message");
		log.warn("This is warn Message");
		log.debug("This is debug message");
		log.error("This is error message");
		log.fatal("This is fatal message");
		/*
		String chopMe = "[[ChromeDriver: chrome on XP (cb800a48e144fb02cfc1b6f23ec4b119)] -> xpath: //a[text()='log in']]";
		System.out.println("Selector: "+chopMe.substring(chopMe.indexOf(">")+2, chopMe.lastIndexOf("]")));
	*/}

}
