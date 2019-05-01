package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils extends Constants{
	public XSSFWorkbook workbook;
	
	public XSSFWorkbook getWorkBook(String excelFilePath){
	    workbook = null;
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
			
		 DataFormatter formatter = new DataFormatter();
		 Cell cell = sheet.getRow(rowNum).getCell(cellNum);
		 cellData = formatter.formatCellValue(cell);
		 //cellData = sheet.getRow(rowNum).getCell(cellNum).getStringCellValue();
				/*Method 2: to format Cell type to string
			    Cell cell = sheet.getRow(rowNum).getCell(cellNum);
				cell.setCellType(CellType.STRING);
				cellData = cell.getStringCellValue();*/
		 return cellData;
	}
	
	
	@SuppressWarnings("deprecation")
	public void writeToColumn(Row row,int colNum,String text){
		 Cell cell = row.getCell(colNum);
			 cell.removeCellComment();
		
		  cell = row.createCell(colNum);
		  CellStyle style = workbook.createCellStyle();
		  Font font = workbook.createFont();
		  font.setColor(IndexedColors.RED.getIndex());
		  style.setFont(font);
		cell.setCellStyle(style );
		cell.setCellType(CellType.STRING);
		cell.setCellValue(text);
		    // Write the output to the file
	
	}

}
