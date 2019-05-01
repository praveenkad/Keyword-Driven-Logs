package testsuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.util.Properties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementChopper {
	static WebDriver driver;
	public static Method method[]; 
	public static String userAction;
	public static WebElement ele;
	public static int wait;
	public static String webElementName;
	public static String testData;
    public static Properties propertyFile=new Properties();
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;

	
	@SuppressWarnings("deprecation")
	public static void main(String[]args){
	
		try {
		File file = new File("C:\\Users\\SONY\\workspace\\KeywordDrivenFramework\\src\\test\\java\\exceldata\\TestData.xlsx");
		FileInputStream fis = new FileInputStream(file);
		 workbook = new XSSFWorkbook(fis);
		 sheet = workbook.getSheet("Reddit Cases");
		 Row row = sheet.getRow(1);
		 Cell cell = row.getCell(6);
		  cell = row.createCell(6);
		  CellStyle style = workbook.createCellStyle();
		  Font font = workbook.createFont();
		  
		  font.setColor(IndexedColors.RED.getIndex());
		  style.setFont(font);
		cell.setCellStyle(style );
		cell.setCellType(CellType.STRING);
		cell.setCellValue("The one that got away");
		 
		 
		    // Write the output to the file
		    FileOutputStream fileOut = new FileOutputStream("C:\\Users\\SONY\\workspace\\KeywordDrivenFramework\\src\\test\\java\\exceldata\\TestData.xlsx");
		 workbook.write(fileOut);
		    fileOut.close();

		    // Closing the workbook
		    workbook.close();
		/*for (Row row: sheet) {
			try{
			userAction = getCellValue(row.getRowNum(), 4);
			testData = getCellValue(row.getRowNum(), 5);
			//executeUserAction();
			}catch(NullPointerException e){
				testData = "temp";
			}
			try{
				webElementName =getCellValue(row.getRowNum(), 3);
			} catch(NullPointerException e){
				webElementName ="temp";
			}
			System.out.println("userAction "+userAction);
			System.out.println("WebElement Name "+webElementName);
			System.out.println("TestData "+testData);
			executeUserAction();
		}*/
	
	} 

	catch(Exception e) {
		e.printStackTrace();
	}

}

}
