package com.qafox.actions;

import java.util.Arrays;
import java.util.List;

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
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qafox.utils.EventListener;
import com.qafox.utils.FileUtils;

public class UserActions extends FileUtils{
	public WebDriver chromeDriver;
	public String webElementName;
	public String testData;
	public String userAction;
	EventFiringWebDriver driver;
	
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
		 System.out.println("launchBrowser WebElement "+webElementName);
		 System.out.println("launchBrowser TestData "+testData);
		 ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_PATH);
			chromeDriver = new ChromeDriver(options);
		    driver = new EventFiringWebDriver(chromeDriver);
			EventListener eventListener = new EventListener();
			// EventInterface eventInterface = new EventInterface();
			driver.register(eventListener);
			// driver.register(eventInterface);
			driver.manage().window().maximize();
	}
	public  void closeBrowser(){
		System.out.println("######### closeBrowser #########");
		driver.quit();
	}

	public  void hitEnter(String webElement) {
		System.out.println("######### hitEnter #########");
		 System.out.println("hitEnter WebElement "+webElement);
		 System.out.println("hitEnter TestData "+testData);
		
		try{
			waitForElementToLoad(webElement);
			driver.findElement(By.name(getProperty(webElement))).sendKeys(Keys.ENTER);
		}
		catch(StaleElementReferenceException e){
			System.out.println("HANDLING STALE ELEMENT");
			driver.findElement(By.name(getProperty(webElement))).sendKeys(Keys.ENTER);
		}
		catch(Exception e){
			System.out.println("Unable to hit Enter on element - "+webElement);
			e.printStackTrace();
		}
		
	}

	public void navigate(String testData){
		System.out.println("######### navigate #########");
	 System.out.println("navigate TestData "+testData);
	 driver.get(testData);
	 waitForPageToLoad();
	}

	public  boolean waitForPageToLoad() {
		System.out.println("######### waitForPageToLoad #########");
		
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

	public  boolean waitForElementToLoad(String webElement){

		try {
			System.out.println("- Waiting until element " + webElement + " is visible in time "
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

	public void click(String webElement,String testData){
		System.out.println("######### click #########");
		 System.out.println("click WebElement "+webElement);
		 System.out.println("click TestData "+testData);
		try {
			System.out.println("Clickable Element Name "+getProperty(webElement));
			waitForElementToLoad(webElement);
			if(webElement.contains("_ID")){
				setHighlight(driver.findElement(By.id(getProperty(webElement))));
				driver.findElement(By.id(getProperty(webElement))).click();
			}
			else if(webElement.contains("_Class")){
				setHighlight(driver.findElement(By.className(getProperty(webElement))));
				driver.findElement(By.className(getProperty(webElement))).click();
			}
			else if(webElement.contains("_Name")){
				setHighlight(driver.findElement(By.name(getProperty(webElement))));
				driver.findElement(By.name(getProperty(webElement))).click();
			}
			else if(webElement.contains("_Xpath")){
				setHighlight(driver.findElement(By.xpath(getProperty(webElement))));
			driver.findElement(By.xpath(getProperty(webElement))).click();
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

	public  void clearAndType(String webElement, String testData) {
		System.out.println("######### clearAndType #########");
		 System.out.println("clearAndType webElement "+webElement);
		 System.out.println("clearAndType testData "+testData);
		 waitForElementToLoad(webElement);
		  try{
			  
		  if(webElement.contains("_ID")){
			  setHighlight(driver.findElement(By.id(getProperty(webElement))));
				driver.findElement(By.id(getProperty(webElement))).sendKeys(testData);
			}
			else if(webElement.contains("_Class")){
				setHighlight(driver.findElement(By.className(getProperty(webElement))));
				driver.findElement(By.className(getProperty(webElement))).sendKeys(testData);
			}
			else if(webElement.contains("_Name")){
				setHighlight(driver.findElement(By.name(getProperty(webElement))));
				driver.findElement(By.name(getProperty(webElement))).sendKeys(testData);
			}
			else if(webElement.contains("_Xpath")){
				setHighlight(driver.findElement(By.xpath(getProperty(webElement))));
			driver.findElement(By.xpath(getProperty(webElement))).sendKeys(testData);
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

	public  void enterCredentials(String webElement,String testData){
		System.out.println("######### enterCredentials #########");
		 System.out.println("enterCredentials webElement "+webElement);
		 System.out.println("enterCredentials testData "+testData);

		List<String> credentials = Arrays.asList(webElement.split(","));
		List<String> testDataCredentials = Arrays.asList(testData.split(","));
		clearAndType(credentials.get(0),testDataCredentials.get(0));
		clearAndType(credentials.get(1),testDataCredentials.get(1));
	}

	public  void switchToFrame(String webElement){
		System.out.println("######### switchToFrame #########");
		System.out.println("switchToFrame webElement "+webElement);

		driver.switchTo().frame(driver.findElement(By.xpath(getProperty(webElement))));
	}
	public  void switchToDefaultFrame(){
		System.out.println("######### switchToDefaultFrame #########");
		 System.out.println("switchToDefaultFrame testData "+testData);
		driver.switchTo().defaultContent();
	}
	
	public void setHighlight(WebElement element) {
		try {
			if (true) {
				String attributevalue = "border:3px solid Crimson;";
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, attributevalue);
				//sleep(1 / 2);
				// String getattrib = element.getAttribute("style");
				// try { Thread.sleep(100); } catch (InterruptedException e) { }
				// executor.executeScript(
				// "arguments[0].setAttribute('style', arguments[1]);", element,
				// getattrib);

			}
		} catch (Exception e) {
			log.info("- Element could not be highlighted");
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
		
		public static boolean waitForElementToLoad(WebElement element, int waitTime) {
			try {
				System.out.println("- Waiting until element " + element + " is visible in time "
						+ waitTime + " secs");
				WebDriverWait wait = new WebDriverWait(driver, waitTime);
				wait.until(ExpectedConditions.visibilityOf(element));
			} catch (TimeoutException e) {
				System.out.println("- Element " + element + " was not visible in time - " + waitTime);
				e.printStackTrace();
				Assert.fail("- Element " + element + " was not visible in time - " + waitTime);
				return false;
			} catch (NoSuchElementException e) {
				System.out.println("- Element " + element + "is not attached to the page document"
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
				System.out.println("- Element " + element + " is not attached to the page document");
				e.printStackTrace();
				Assert.fail("Element " + element + " is not attached to the page document");
			} catch (NoSuchElementException e) {
				System.out.println("- Element " + element + " was not found in DOM");
				e.printStackTrace();
				Assert.fail("Element " + element + " was not found in DOM");
			} catch (Exception e) {
				System.out.println("- Element " + element + " was not clickable in time-"
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
					System.out.println("- Cleared the field and entered -** " + text + " ** in the element - "
							+ element);
				 
			} catch (StaleElementReferenceException e) {
				System.out.println("- Element for " +element
						+ " is not attached to the page document" + e.getStackTrace());
				Assert.fail("Element for " + element + " is not attached to the page document");
			} catch (NoSuchElementException e) {
				System.out.println(
						"- Element for " + element + " was not found in DOM" + e.getStackTrace());
				Assert.fail("Element for " + element + " was not found in DOM");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("- Unable to clear and enter '" + text + "' text in field with element -"
						+ element + e.getStackTrace());
				Assert.fail("Unable to clear and enter '" + text + "' text in field with element -" + element);
			}
		}
		
		public static void switchToFrame(WebElement element){
			driver.switchTo().frame(element);
		}
*/}
