package actions;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;

//import utils.EventInterface;
import utils.EventListener;
import utils.FileUtils;

public class UserActions extends FileUtils{
	public WebDriver chromeDriver;
	public String webElementName;
	public String testData;
	public String userAction;
	public Row currentRow;
	EventFiringWebDriver driver;
	
	@AfterSuite
	public void closeWorkBook(){
		 FileOutputStream fileOut;
			try {
				fileOut = new FileOutputStream(EXCEL_FILE_PATH);
				 workbook.write(fileOut);
				    fileOut.close();
				    // Closing the workbook
				    workbook.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	public void performUserAction() {
		if(userAction.equals("launchBrowser")){
			launchBrowser();
		}
		else if(userAction.equals("navigate")){
			navigate(testData);
		}
		else if(userAction.equals("waitForPageToLoad")){
			waitForPageToLoad();
		}
		else if(userAction.equals("click")){
			click(webElementName, testData);
		}	
		else if(userAction.equals("switchToFrame")){
			switchToFrame(webElementName);
		}
		else if(userAction.equals("enterCredentials")){
			enterCredentials(webElementName, testData);
		}
		else if(userAction.equals("switchToDefaultFrame")){
			switchToDefaultFrame();
		}	
		else if(userAction.equals("clearAndType")){
			clearAndType(webElementName, testData);
		}
		else if(userAction.equals("hitEnter")){
			hitEnter(webElementName);
		}
		else if(userAction.equals("closeBrowser")){
			closeBrowser();
		}	
		
		}
	
	
	
	public void launchBrowser(){ 
		log.info("######### launchBrowser #########");
		 log.info("launchBrowser WebElement "+webElementName);
		 log.info("launchBrowser TestData "+testData);
		 ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\SONY\\workspace\\KeywordDrivenFramework\\src\\test\\resources\\DriverExecutable\\chromedriver.exe");
			chromeDriver = new ChromeDriver(options);
		    driver = new EventFiringWebDriver(chromeDriver);
			EventListener eventListener = new EventListener();
			// EventInterface eventInterface = new EventInterface();
			driver.register(eventListener);
			// driver.register(eventInterface);
			driver.manage().window().maximize();
			writeToColumn(currentRow, 6, "Passed");
	}
	public  void closeBrowser(){
		log.info("######### closeBrowser #########");
		driver.quit();
	}

	public  void hitEnter(String webElement) {
		log.info("######### hitEnter #########");
		 log.info("hitEnter WebElement "+webElement);
		 log.info("hitEnter TestData "+testData);
		
		try{
			waitForElementToLoad(webElement);
			driver.findElement(By.name(getProperty(webElement))).sendKeys(Keys.ENTER);
		}
		catch(StaleElementReferenceException e){
			log.info("HANDLING STALE ELEMENT");
			driver.findElement(By.name(getProperty(webElement))).sendKeys(Keys.ENTER);
		}
		catch(Exception e){
			log.info("Unable to hit Enter on element - "+webElement);
			e.printStackTrace();
		}
		
	}

	public void navigate(String testData){
		log.info("######### navigate #########");
	 log.info("navigate TestData "+testData);
	 driver.get(testData);
	 waitForPageToLoad();
	 writeToColumn(currentRow, 6, "Passed");
	}

	public  boolean waitForPageToLoad() {
		log.info("######### waitForPageToLoad #########");
		
		log.info("- Waiting for page to load");
		try {
			int waitTime = 0;
			boolean isPageLoadComplete = false;
			do {
				isPageLoadComplete = ((String) ((JavascriptExecutor) driver)
						.executeScript("return document.readyState")).equals("complete");
					Thread.sleep(1000);
				waitTime++;
				if (waitTime > 250) {
					log.info("- Page Load Complete");
					break;
				}
			} while (!isPageLoadComplete);
			{
			}
			writeToColumn(currentRow, 6, "Passed");
		} catch (TimeoutException e) {
			return false;
		}
		catch(InterruptedException e){
			return false;
		}
		return true;
	}

	public  boolean waitForElementToLoad(String webElement){

		try {
			log.info("Waiting until element " + webElement + " is visible in time "
					+ 10 + " secs");
			WebDriverWait wait = new WebDriverWait(driver, 10);
			if(webElement.contains("_ID")){
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id(getProperty(webElement)))));
			}
			else if(webElement.contains("_Class")){
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className(getProperty(webElement)))));
			}
			else if(webElement.contains("_Name")){
				wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.name(getProperty(webElement)))));
			}
			else if(webElement.contains("_Xpath")){
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(getProperty(webElement)))));}
			writeToColumn(currentRow, 6, "Passed");
		} catch (TimeoutException e) {
			log.info("Element " + webElement + " was not visible in time - " + 10);
			e.printStackTrace();
			Assert.fail("Element " + webElement + " was not visible in time - " + 10);
		} catch (NoSuchElementException e) {
			log.info("Element " + webElement + "is not attached to the page document");
			e.printStackTrace();
			writeToColumn(currentRow, 6, "Failed");
			writeToColumn(currentRow, 7,"Element " + webElement + "is not attached to the page document");
			Assert.fail("Element " + webElement + "is not attached to the page document");
		} catch (Exception e) {
			log.info("Some excption occured");
			e.printStackTrace();
			Assert.fail("Failed because "+e.getMessage());
			
		}
		return true;

	}

	public void click(String webElement,String testData){
		log.info("######### click #########");
		 log.info("click WebElement "+webElement);
		 log.info("click TestData "+testData);
		try {
			log.info("Clickable Element Name "+getProperty(webElement));
			waitForElementToLoad(webElement);
			setHighlight(webElement);
			if(webElement.contains("_ID")){
				driver.findElement(By.id(getProperty(webElement))).click();
			}
			else if(webElement.contains("_Class")){
				driver.findElement(By.className(getProperty(webElement))).click();
			}
			else if(webElement.contains("_Name")){
				driver.findElement(By.name(getProperty(webElement))).click();
			}
			else if(webElement.contains("_Xpath")){
			driver.findElement(By.xpath(getProperty(webElement))).click();
			}
			writeToColumn(currentRow, 6, "Passed");
		} catch (StaleElementReferenceException e) {
			log.info("Element " + webElementName + " is not attached to the page document");
			e.printStackTrace();
			Assert.fail("Element " + webElementName + " is not attached to the page document");
		} catch (NoSuchElementException e) {
			log.info("Element " + webElementName + " was not found in DOM");
			e.printStackTrace();
			writeToColumn(currentRow, 6, "Failed");
			writeToColumn(currentRow, 7,"No Such Element found with locator "+webElement);
			Assert.fail("No Such Element found with locator "+webElement);
			
		} catch (Exception e) {
			log.info("- Element " + webElementName + " was not clickable in time-"
					+ 10);
			e.printStackTrace();
			Assert.fail("Failed because "+e.getMessage());
		}
	}

	public  void clearAndType(String webElement, String testData) {
		log.info("######### clearAndType #########");
		 log.info("clearAndType webElement "+webElement);
		 log.info("clearAndType testData "+testData);
		 waitForElementToLoad(webElement);
		  try{
			  setHighlight(webElement);
		  if(webElement.contains("_ID")){
				driver.findElement(By.id(getProperty(webElement))).sendKeys(testData);
			}
			else if(webElement.contains("_Class")){
				driver.findElement(By.className(getProperty(webElement))).sendKeys(testData);
			}
			else if(webElement.contains("_Name")){
				driver.findElement(By.name(getProperty(webElement))).sendKeys(testData);
			}
			else if(webElement.contains("_Xpath")){
			driver.findElement(By.xpath(getProperty(webElement))).sendKeys(testData);
			}
		  }
		  catch (StaleElementReferenceException e) {
				log.info("- Element " + webElementName + " is not attached to the page document");
				e.printStackTrace();
				Assert.fail("Element " + webElementName + " is not attached to the page document");
			} catch (NoSuchElementException e) {
				log.info("- Element " + webElementName + " was not found in DOM");
				e.printStackTrace();
				
				Assert.fail("No Such Element found with locator "+webElement);
				
			} catch (Exception e) {
				log.info("- Element " + webElementName + " was not clickable in time-"
						+ 10);
				e.printStackTrace();
				Assert.fail("Failed because "+e.getMessage());
			}
	}
	
	public void setHighlight(String webElement) {
		  try{
		  if(webElement.contains("_ID")){
			  highlightElement(driver.findElement(By.id(getProperty(webElement))));
			}
			else if(webElement.contains("_Class")){
				highlightElement(driver.findElement(By.className(getProperty(webElement))));
			}
			else if(webElement.contains("_Name")){
				highlightElement(driver.findElement(By.name(getProperty(webElement))));
			}
			else if(webElement.contains("_Xpath")){
				highlightElement(driver.findElement(By.xpath(getProperty(webElement))));
			}
		  }
		  catch(Exception e){
			 log.info(e.getStackTrace());
		  }
	}

	private void highlightElement(WebElement element) {
		try {
			if (true) {
				String attributevalue = "border:3px solid Crimson;";
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, attributevalue);
			}
		} catch (Exception e) {
			log.info("- Element could not be highlighted");
		}
		
	}

	public  void enterCredentials(String webElement,String testData){
		try{
		log.info("######### enterCredentials #########");
		 log.info("enterCredentials webElement "+webElement);
		 log.info("enterCredentials testData "+testData);
		List<String> credentials = Arrays.asList(webElement.split(","));
		List<String> testDataCredentials = Arrays.asList(testData.split(","));
		clearAndType(credentials.get(0),testDataCredentials.get(0));
		clearAndType(credentials.get(1),testDataCredentials.get(1));
		}
			catch(ArrayIndexOutOfBoundsException e){
				log.info(e.getMessage());
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		catch(Exception e){
			log.info("Some exception occured");
			e.printStackTrace();
			Assert.fail("Failed because "+e.getMessage());
		}
		}

	public  void switchToFrame(String webElement){
		try{
		log.info("######### switchToFrame #########");
		log.info("switchToFrame webElement "+webElement);
		driver.switchTo().frame(driver.findElement(By.xpath(getProperty(webElement))));
		writeToColumn(currentRow, 6, "Passed");
		}
	catch(NoSuchFrameException e){
	   log.info("- No frame foun with the locator "+webElementName);
	   e.printStackTrace();
	   Assert.fail("No Such frame with the locator "+webElementName);
	}
		catch(Exception e){
			e.printStackTrace();
			log.info("- Some exception occured");
			Assert.fail("Failed because "+e.getMessage());
		}
	}
	public  void switchToDefaultFrame(){
		try{
		log.info("######### switchToDefaultFrame #########");
		 log.info("switchToDefaultFrame testData "+testData);
		driver.switchTo().defaultContent();
		writeToColumn(currentRow, 6, "Passed");
		}
		catch(Exception e){
			log.info(e.getMessage());
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	
	/*
	 public static WebDriver driver;
	 
	 public static void launchBrowser(){ 
		 ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\SONY\\workspace\\KeywordDrivenFramework\\src\\test\\resources\\DriverExecutable\\chromedriver.exe");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
	 }
	 
		@SuppressWarnings("unused")
		private static void naviagate(String URL) {
			 driver.get(URL);
			 waitForPageToLoad();		
		}
		
		public static boolean waitForPageToLoad() {
			log.info("- Waiting for page to load");
			try {
				int waitTime = 0;
				boolean isPageLoadComplete = false;
				do {
					isPageLoadComplete = ((String) ((JavascriptExecutor) driver)
							.executeScript("return document.readyState")).equals("complete");
						Thread.sleep(1000);
					waitTime++;
					if (waitTime > 250) {
						log.info("- Page Load Complete");
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
		
		public static boolean waitForElementToLoad(WebElement element, int waitTime) {
			try {
				log.info("- Waiting until element " + element + " is visible in time "
						+ waitTime + " secs");
				WebDriverWait wait = new WebDriverWait(driver, waitTime);
				wait.until(ExpectedConditions.visibilityOf(element));
			} catch (TimeoutException e) {
				log.info("- Element " + element + " was not visible in time - " + waitTime);
				e.printStackTrace();
				Assert.fail("- Element " + element + " was not visible in time - " + waitTime);
				return false;
			} catch (NoSuchElementException e) {
				log.info("- Element " + element + "is not attached to the page document"
						+ e.getStackTrace());
				e.printStackTrace();
				Assert.fail("- Element " + element + "is not attached to the page document");
				return false;
			} catch (Exception e) {
				e.printStackTrace();
				Assert.fail("- Unable to find the element " + element);
				return false;
			}
			return true;
		}
		
		public static void click(WebElement element, int optionWaitTime) {
			try {
				waitForElementToLoad(element,optionWaitTime);
				element.click();
			} catch (StaleElementReferenceException e) {
				log.info("- Element " + element + " is not attached to the page document");
				e.printStackTrace();
				Assert.fail("Element " + element + " is not attached to the page document");
			} catch (NoSuchElementException e) {
				log.info("- Element " + element + " was not found in DOM");
				e.printStackTrace();
				Assert.fail("Element " + element + " was not found in DOM");
			} catch (Exception e) {
				log.info("- Element " + element + " was not clickable in time-"
						+ optionWaitTime);
				e.printStackTrace();
				Assert.fail("Element " + element + " was not clickable in time-" + optionWaitTime);
			}
		}
		
		public static void clearAndType(WebElement element, String text, int optionWaitTime) {
			try {
				waitForElementToLoad(element,optionWaitTime);
					element.clear();
					element.sendKeys(text);
					log.info("- Cleared the field and entered -** " + text + " ** in the element - "
							+ element);
				 
			} catch (StaleElementReferenceException e) {
				log.info("- Element for " +element
						+ " is not attached to the page document" + e.getStackTrace());
				Assert.fail("Element for " + element + " is not attached to the page document");
			} catch (NoSuchElementException e) {
				log.info(
						"- Element for " + element + " was not found in DOM" + e.getStackTrace());
				Assert.fail("Element for " + element + " was not found in DOM");
			} catch (Exception e) {
				e.printStackTrace();
				log.info("- Unable to clear and enter '" + text + "' text in field with element -"
						+ element + e.getStackTrace());
				Assert.fail("Unable to clear and enter '" + text + "' text in field with element -" + element);
			}
		}
		
		public static void switchToFrame(WebElement element){
			driver.switchTo().frame(element);
		}
*/}
