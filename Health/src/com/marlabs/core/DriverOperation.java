package com.marlabs.core;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.marlabs.reports.ExtentReport;


public class DriverOperation extends ExtentReport implements GenericMethods{
	
	public static WebDriver driver=null;
	public WebDriverWait wait=null;
	
	
	@SuppressWarnings("deprecation")
	protected void initDriver() throws IOException {

		String browserName = getBrowserName();
		
		switch (browserName) {
		
		case "Chrome" : System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"//src//com//marlabs//drivers//chromedriver.exe");
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		
		break;

		case "FireFox":
			
		DesiredCapabilities caps = new DesiredCapabilities();
	    
	    caps.setCapability(CapabilityType.HAS_NATIVE_EVENTS, false);
	    System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"//src//com//marlabs//drivers//geckodriver.exe");
	    driver = new FirefoxDriver(caps);
	    driver.manage().window().maximize();
	    driver.manage().deleteAllCookies();
			
		//driver= new FirefoxDriver();
		break;

		case "Internet Explorer":System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+"//src//com//marlabs//drivers//IEDriverServer.exe");
		driver= new InternetExplorerDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		break;
		
		case "Edge":System.setProperty("webdriver.edge.driver",System.getProperty("user.dir")+"//src//com//marlabs//drivers//MicrosoftWebDriver.exe");
		driver= new EdgeDriver();
		driver.manage().deleteAllCookies();
		break;
		}
		
		//driver.get(getPropertyValue("SearchURL"));
	//	driver.get(getPropertyValue("baseUrl"));

	}
	
	protected WebElement waitForVisibilityofElement(WebElement element) {
		
		wait = new WebDriverWait(driver,30);
	
		return wait.until(ExpectedConditions.visibilityOf(element)); 

	}
	
protected WebElement waitTillElementClicked(WebElement element) {
		
		wait = new WebDriverWait(driver,30);
	
		return wait.until(ExpectedConditions.elementToBeClickable(element)); 

	}
	protected List<WebElement> waitForVisibilityOfAllElements(List<WebElement> element) {

		wait = new WebDriverWait(driver,30);
		return wait.until(ExpectedConditions.visibilityOfAllElements(element)); 

	}
	protected WebElement waitForElementToBeClickable(WebElement element) {
	
		wait = new WebDriverWait(driver,20);
		return wait.until(ExpectedConditions.elementToBeClickable(element)); 

	}
	protected WebElement waitForClickableByLocator(By locator) {

		wait = new WebDriverWait(driver,20);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); 
	}
	
	protected String getBrowserName() throws IOException {
		
		return getPropertyValue("Browser");
		
	}
	
	protected void endDriver() {
		driver.quit();
	}
	
}
