package testrunner;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestRunner {
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
	public static void main(String[] args) {
	
		try {
		File file = new File("C:\\Users\\SONY\\workspace\\KeywordDrivenFramework\\src\\test\\java\\exceldata\\TestData.xlsx");
		FileInputStream locatorsProperties = new FileInputStream("src/test/java/locators/WebElements.properties");
		propertyFile.load(locatorsProperties);
		FileInputStream fis = new FileInputStream(file);
		 workbook = new XSSFWorkbook(fis);
		 sheet = workbook.getSheet("Reddit Cases");
		for (Row row: sheet) {
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
		}
	
	} 

	catch(Exception e) {
		e.printStackTrace();
	}

}
	

private static String getCellValue(int rowNum, int cellNum) {
	 String cellData=null;
	 cellData = sheet.getRow(rowNum).getCell(cellNum).getStringCellValue();
	 return cellData;
}



public static void executeUserAction() {
	 method = new TestRunner().getClass().getMethods();
		for (Method meth : method) {
			if(meth.getName().equals(userAction)){
				try {
					meth.invoke(new TestRunner(),webElementName,testData);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				break;
			}
		}
	
}
public static void launchBrowser(String webElementName,String testData){ 
	 System.out.println("######### launchBrowser #########");
	 System.out.println("launchBrowser WebElement "+webElementName);
	 System.out.println("launchBrowser TestData "+testData);
	 ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\SONY\\workspace\\KeywordDrivenFramework\\src\\test\\resources\\DriverExecutable\\chromedriver.exe");
		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
}

public static void closeBrowser(String webElementName,String testData){
	System.out.println("######### Close Browser #########");
	driver.quit();
}

public static void hitEnter(String webElement,String testData) {
	System.out.println("######### hitEnter #########");
	 System.out.println("hitEnter WebElement "+webElement);
	 System.out.println("hitEnter TestData "+testData);
	
	try{
		waitForElementToLoad(webElement,testData);
		driver.findElement(By.name(propertyFile.getProperty(webElement))).sendKeys(Keys.RETURN);
	}catch(Exception e){
		System.out.println("Unable to hit Enter on element - "+webElement);
		System.out.println(e.getStackTrace());
	}
	
}

public static void navigate(String webElement,String testData) {
		System.out.println("######### navigate #########");
	 System.out.println("navigate WebElement "+webElement);
	 System.out.println("navigate TestData "+testData);
	 driver.get(testData);
	 waitForPageToLoad(webElement,testData);	
}

public static boolean waitForPageToLoad(String webElement,String testData) {
	System.out.println("######### waitForPageToLoad #########");
	 System.out.println("waitForPageToLoad WebElement "+webElement);
	 System.out.println("waitForPageToLoad TestData "+testData);
	
	System.out.println("- Waiting for page to load");
	try {
		int waitTime = 0;
		boolean isPageLoadComplete = false;
		do {
			isPageLoadComplete = ((String) ((JavascriptExecutor) driver)
					.executeScript("return document.readyState")).equals("complete");
				Thread.sleep(1000);
			waitTime++;
			if (waitTime > 250) {
				System.out.println("- Page Load Complete");
				break;
			}
		} while (!isPageLoadComplete);
		{
		}
	} catch (TimeoutException e) {
		return false;
	}
	catch(InterruptedException e){
		return false;
	}
	return true;
}

public static boolean waitForElementToLoad(String webElement,String testData){
	System.out.println("######### waitForElementToLoad #########");
	 System.out.println("waitForElementToLoad WebElement "+webElement);
	 System.out.println("waitForElementToLoad TestData "+testData);

	try {
		System.out.println("- Waiting until element " + webElement + " is visible in time "
				+ 10 + " secs");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		if(webElement.contains("_ID")){
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(propertyFile.getProperty(webElement)))));
		}
		else if(webElement.contains("_Class")){
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className(propertyFile.getProperty(webElement)))));
		}
		else if(webElement.contains("_Name")){
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name(propertyFile.getProperty(webElement)))));
		}
		else if(webElement.contains("_Xpath")){
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(propertyFile.getProperty(webElement)))));}
	} catch (TimeoutException e) {
		System.out.println("- Element " + webElement + " was not visible in time - " + 10);
		e.printStackTrace();
		throw e;
	} catch (NoSuchElementException e) {
		System.out.println("- Element " + webElement + "is not attached to the page document"
				+ e.getStackTrace());
		e.printStackTrace();
		
		throw e;
	} catch (Exception e) {
		try {
			throw e;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	return true;

}

public static void click(String webElement,String testData){
	System.out.println("######### click #########");
	 System.out.println("click WebElement "+webElement);
	 System.out.println("click TestData "+testData);
	try {
		System.out.println("Clickable Element Name "+propertyFile.getProperty(webElement));
		waitForElementToLoad(webElement,testData);
		if(webElement.contains("_ID")){
			driver.findElement(By.id(propertyFile.getProperty(webElement))).click();
		}
		else if(webElement.contains("_Class")){
			driver.findElement(By.className(propertyFile.getProperty(webElement))).click();
		}
		else if(webElement.contains("_Name")){
			driver.findElement(By.name(propertyFile.getProperty(webElement))).click();
		}
		else if(webElement.contains("_Xpath")){
		driver.findElement(By.xpath(propertyFile.getProperty(webElement))).click();
		}
	} catch (StaleElementReferenceException e) {
		System.out.println("- Element " + webElementName + " is not attached to the page document");
		e.printStackTrace();
		throw e;
	} catch (NoSuchElementException e) {
		System.out.println("- Element " + webElementName + " was not found in DOM");
		e.printStackTrace();
		throw e;
		
	} catch (Exception e) {
		System.out.println("- Element " + webElementName + " was not clickable in time-"
				+ 10);
		e.printStackTrace();
		try {
			throw e;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}

public static void clearAndType(String webElement, String testData) {
	System.out.println("######### clearAndType #########");
	 System.out.println("clearAndType webElement "+webElement);
	 System.out.println("clearAndType testData "+testData);
	  waitForElementToLoad(webElement,testData);
	  try{
	  if(webElement.contains("_ID")){
			driver.findElement(By.id(propertyFile.getProperty(webElement))).sendKeys(testData);
		}
		else if(webElement.contains("_Class")){
			driver.findElement(By.className(propertyFile.getProperty(webElement))).sendKeys(testData);
		}
		else if(webElement.contains("_Name")){
			driver.findElement(By.name(propertyFile.getProperty(webElement))).sendKeys(testData);
		}
		else if(webElement.contains("_Xpath")){
		driver.findElement(By.xpath(propertyFile.getProperty(webElement))).sendKeys(testData);
		}
	  }
	  catch(Exception e){
		 System.out.println(e.getStackTrace());
		 try {
			throw e;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	  }
}

public static void enterCredentials(String webElement,String testData){
	System.out.println("######### enterCredentials #########");
	 System.out.println("enterCredentials webElement "+webElement);
	 System.out.println("enterCredentials testData "+testData);

	List<String> credentials = Arrays.asList(webElement.split(","));
	List<String> testDataCredentials = Arrays.asList(testData.split(","));
	clearAndType(credentials.get(0),testDataCredentials.get(0));
	clearAndType(credentials.get(1),testDataCredentials.get(1));
}

public static void switchToFrame(String webElement,String testData){
	System.out.println("######### switchToFrame #########");
	System.out.println("switchToFrame webElement "+webElement);
	 System.out.println("switchToFrame testData "+testData);

	driver.switchTo().frame(driver.findElement(By.xpath(propertyFile.getProperty(webElement))));
}
public static void switchToDefaultFrame(String webElement,String testData){
	System.out.println("######### switchToDefaultFrame #########");
	System.out.println("switchToDefaultFrame webElement "+webElement);
	 System.out.println("switchToDefaultFrame testData "+testData);
	driver.switchTo().defaultContent();
}

}