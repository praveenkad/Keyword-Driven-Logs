package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

public class EventListener extends AbstractWebDriverEventListener{


	public void beforeNavigateTo(String url, WebDriver driver) {
		FileUtils.log.info("Navigating to "+url);
	}

	
	public void afterNavigateTo(String url, WebDriver driver) {
		FileUtils.log.info("Navigated to "+url);
	}
	
	private String elementName(WebElement element) {
		String chopMe = element.toString();
		/*String part1 = chopMe.substring(0, chopMe.indexOf("["));
		String part2 = chopMe.substring(chopMe.indexOf(">")+2, chopMe.length()-1);*/
		return "- "+chopMe.substring(chopMe.indexOf(">")+2, chopMe.lastIndexOf("]"));
	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
	FileUtils.log.info("Trying to click on "+elementName(element));
	}
	
	public void afterClickOn(WebElement element, WebDriver driver) {
    FileUtils.log.info("Clicked on "+elementName(element));		
	}
	
	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
	FileUtils.log.info("Entering text into "+elementName(element));
	}
	
	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		FileUtils.log.info("Entered text "+element.getAttribute("value")+" into "+elementName(element));
	}

	public void beforeGetText(WebElement element, WebDriver driver) {
	 FileUtils.log.info("$$$$$$$$$$$$$$$$$$$ GET TEXT $$$$$$$$$$$$$$$$$$$$$$");	
	}
	
	public void afterGetText(WebElement element, WebDriver driver, String text) {
		FileUtils.log.info("$$$$$$$$$$$$$$$$$$$ AFTER GET TEXT $$$$$$$$$$$$$$$$$$$$$$");	
	}
	
	/*public void beforeAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterAlertAccept(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeAlertDismiss(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterNavigateTo(String url, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterNavigateBack(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterNavigateForward(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterNavigateRefresh(WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterFindBy(By by, WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeClickOn(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterClickOn(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}

	public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
		// TODO Auto-generated method stub
		
	}

	public void beforeScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterScript(String script, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void beforeSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterSwitchToWindow(String windowName, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void onException(Throwable throwable, WebDriver driver) {
		
		
	}

	public <X> void beforeGetScreenshotAs(OutputType<X> target) {
		// TODO Auto-generated method stub
		
	}

	public <X> void afterGetScreenshotAs(OutputType<X> target, X screenshot) {
		// TODO Auto-generated method stub
		
	}

	public void beforeGetText(WebElement element, WebDriver driver) {
		// TODO Auto-generated method stub
		
	}

	public void afterGetText(WebElement element, WebDriver driver, String text) {
		// TODO Auto-generated method stub
		
	}*/
	 

}
