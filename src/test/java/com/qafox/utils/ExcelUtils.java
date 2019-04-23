package com.qafox.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils extends Constants{
	
	
	public XSSFWorkbook getWorkBook(String excelFilePath){
		XSSFWorkbook workbook = null;
		File file = new File(excelFilePath);
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			 workbook = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return workbook;
	}
	
	public XSSFSheet getSheet(String excelPath,String sheetName){
		
		XSSFWorkbook workbook;
		XSSFSheet sheet = null;
		File file = new File(excelPath);
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			 workbook = new XSSFWorkbook(fis);
			 sheet = workbook.getSheet(sheetName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sheet;
		
	}
	
	public String getCellValue(int rowNum, int cellNum,XSSFSheet sheet) {
		 String cellData=null;
		 cellData = sheet.getRow(rowNum).getCell(cellNum).getStringCellValue();
		 return cellData;
	}
	

}
