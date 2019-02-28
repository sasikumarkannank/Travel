package com.marlabs.core;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;

import com.relevantcodes.extentreports.ExtentTest;

public class PageObjeccts extends Controller{

	// Method to click
	public void toClickElement(WebElement element) throws InterruptedException
	{
		waitForElementToBeClickable(element).click();	
	}	

	// Method to check whether button is enabled or not
	public boolean toChkBtnEnabled(WebElement element) throws InterruptedException
	{
	return element.isEnabled();
	}
	
	//Method to focus on element
	public void toFocus(WebElement element) throws InterruptedException
	{	
					
		new Actions(driver).moveToElement(element).perform();	
		
	}

	// Method to select value from any dropdown 
	public void toSelectDatafromDropdown(WebElement drpdwnElement,String dropdownText) throws InterruptedException
	{
		
		Select selectDropdown = new Select(drpdwnElement);
		selectDropdown.selectByVisibleText(dropdownText);
		
	}

	public void toSelectDatafromDropdownByValue(WebElement drpdwnElement, String dropdownValue) throws InterruptedException
	{
		Select selectDropdown = new Select(drpdwnElement);				
		selectDropdown.selectByValue(dropdownValue);
		
	}

	public String toSelectfromDropdown(By locator, String Data)
	{
		String retval = null;
		List<WebElement> options = driver.findElements(locator);
		try
		{
			for(int i=0; i<options.size(); i++)
			{
				if(options.get(i).getText().equalsIgnoreCase(Data))
				{
					options.get(i).click();
					Thread.sleep(1000);		
				}
				retval=options.get(i).getText();
			}
			
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
		return retval;
	}

	
	public boolean tocheckdisplayed(WebElement element)
	{
		return element.isDisplayed();
	}
	
	public boolean tocheckdisplayed(By locator)
	{
		return driver.findElements(locator).size()>0;
	}



	public void toScrollDown()
	{
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
	}	
	
	public void toScrolltoWebElement(WebElement testElement)
	{
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", testElement);
	}
	
	public void toScrolltoBylocator(By locator)
	{
		WebElement testElement =driver.findElement(locator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", testElement);
	}

	
	public String getLabel(By locator)
	{
		String strValue =  driver.findElement(locator).getText();
		System.out.println("String Value"+strValue);
		return strValue;
	}
	
	public String getLabelbyAttrbute(By locator)
	{
		String strValue =  driver.findElement(locator).getAttribute("value");
		System.out.println("String Value"+strValue);
		return strValue.trim();
	}
	

	public void toClickLink(By locator) throws InterruptedException
	{
		driver.findElement(locator).click();	
		
	}	
	public void toClickWebElement(WebElement element) throws InterruptedException
	{
		element.click();
		
	}

	public String getWebElementtext(WebElement element)
	{
		String strValue = element.getText();
		return strValue;
	}
	
	public String getAttributeValueBylocator(By locator)
	{
		String strValue =  driver.findElement(locator).getAttribute("value");
		return strValue.trim();
	}
	public String getAttributeValueBylocator(WebElement element)
	{
		String strValue =  element.getAttribute("value");
		return strValue.trim();
	}

	// Method to enter the data into text box
	public void toEnterData(By locator,String testValue)
		{
			driver.findElement(locator).sendKeys(testValue);
		}
		
	public String DataEntry(WebElement element, String Value) {
			
			element.sendKeys(Value);
			return Value;
		}
		
	// Method to clear the data from text box
	public void toClearData(By locator) {
			driver.findElement(locator).clear();
		}
		
	// Method to get value from text box
		public String getDataFromTextBox(By locator) {
			return driver.findElement(locator).getAttribute("value");
		}
		public boolean toCheckEmpty(By locator)
		{
			
			String txtbox=driver.findElement(locator).getText().toString();
			if(txtbox.isEmpty())
			{
				return true;
			}
			return false;		  
		}

		public boolean verifyElementIsDisplayed(WebElement element) 
		{
			return waitForVisibilityofElement(element).isDisplayed();
			 
		}


		public boolean verifyElement(By locator, String actual)	
		{
			String Expected= driver.findElement(locator).getText();
			
			if(Expected.equalsIgnoreCase(actual))
			{
				return true;
			}
			return false;

		}
		
		
	    public boolean containsString(By locator, String actual) {
	    	
	        String Expected = driver.findElement(locator).getText();
	        String LwrCase = actual.toUpperCase();
	        if(Expected.contains(LwrCase))
	        {	return true;
	        }
	        return false;
	        
	    }
	    
	    protected String getHeaderValue() {
	    	waitForVisibilityofElement(driver.findElement(By.xpath("//div[@class='flex-media-content']/h1")));
	    	String HeaderText =waitForVisibilityofElement(driver.findElement(By.xpath("//div[@class='flex-media-content']/h1"))).getText();
	   System.out.println("Header Text = "+HeaderText);
	    	
	    	return HeaderText;
	    
	    }
	    
	    protected String getFilterValue() {
	    	
		    return waitForVisibilityofElement(driver.findElement(By.xpath("//ul[@data-js-id='facet-list']/li//span"))).getText();
		    
		    }
	   
	    
	    public void verifyProcedureFilters(ExtentTest test,ExtentTest fail) throws InterruptedException {

	    	int RadioList = driver.findElements(By.xpath("//div[@id='accordion-procedure-or-condition']//label[@class='form-control radio']//span")).size();
	    	System.out.println(RadioList);
	    
	    	for(int i=1; i<=RadioList;i++) {

	    		try {
	    			
	    			WebElement Element = driver.findElement(By.xpath("(//div[@id='accordion-procedure-or-condition']//label[@class='form-control radio'])["+i+"]"));
	    			Element.click();
	    			
	    			//((JavascriptExecutor)driver).executeScript("arguments[0].click();", Element);
				
	    			 
	    			waitForElementToBeClickable(driver.findElement(By.xpath("(//div[@id='accordion-procedure-or-condition']//label[@class='form-control radio'])["+i+"]")));
	    			//driver.findElement(By.xpath("(//div[@id='accordion-procedure-or-condition']//label[@class='form-control radio'])["+i+"]")).click();
	    			String url = driver.getCurrentUrl();
	    			assertFilterValues(getFilterValue(),getHeaderValue(), test,fail,url);
				} catch (Exception e) {
					
					e.printStackTrace();
				}
	    		
	    	}
	    }

  
	    public void verifyFilter(ExtentTest test,List<WebElement> RatioListPath, WebElement VisibleElement,ExtentTest fail) throws InterruptedException {

	    	int RadioList = RatioListPath.size();
	    	System.out.println(RadioList);
	    
	    	for(int i=1; i<=RadioList;i++) {

	    		try {
	    			
	    			
	    			driver.findElement(By.xpath("//div[@id='accordion-specialty']//li["+i+"]/label")).click();
	    			
	    			waitForElementToBeClickable(driver.findElement(By.xpath("//div[@id='accordion-specialty']//li["+i+"]/label")));
	    			
	    			verifyElementIsDisplayed(VisibleElement);
	    			
	    			String url = driver.getCurrentUrl();
	    			assertFilterValues(getFilterValue(),getHeaderValue(), test,fail,url);
				
	    			driver.navigate().refresh();
	    		} catch (Exception e) {
					
					e.printStackTrace();
				}
	    		
	    	}
	    }
	    
	    public void AccessingURL(String URL) {
	    	driver.get(URL);
	    }
	    
	    public Object ScreenShot(WebElement element, WebElement eleName) throws IOException {
	    	
	    	//WebElement ele = driver.findElement(By.xpath(Xpath));

	    	WebElement ele = element;
	    	
	    	// Get entire page screenshot
	    	File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	    	BufferedImage  fullImg = ImageIO.read(screenshot);

	    	// Get the location of element on the page
	    	Point point = ele.getLocation();

	    	// Get width and height of the element
	    	int eleWidth = ele.getSize().getWidth();
	    	int eleHeight = ele.getSize().getHeight();
	    	
	    	  Rectangle rect = new Rectangle(eleWidth, eleHeight);
	    	  
	         
	    	  fullImg = ImageIO.read(screenshot);
	   
	          //Crop Image of drop down web element from the screen shot
	    	  BufferedImage dest = fullImg.getSubimage(point.getX(), point.getY(), rect.width, rect.height);
	          ImageIO.write(dest, "png", screenshot);
	   
	          //Copy Image into particular directory
	          File screenshotLocation = new File("F:\\images\\"+eleName.getText()+"Image.png");
	          FileUtils.copyFile(screenshot, screenshotLocation);
	          return screenshotLocation;    
	    	
	    }
	    
 
 public void sendValues(WebElement hospitalName, String Values) {
		  
		 hospitalName.sendKeys(Values);
		  
	  }
	    
	    public void DietPageVerify(ExtentTest test) {
	    	
	    	int elementSize = driver.findElements(By.xpath("//h3[@class='flex-media-heading text-black heading-large']")).size();
	    	System.out.println("Element Size ="+elementSize);
	    	
	    	for(int i=1;i<=elementSize;i++) {
	    		
	    		
	    		
	    		if(driver.findElement(By.xpath("(//h3[@class='flex-media-heading text-black heading-large'])["+i+"]")).isDisplayed()) {
	    			
	    			
	    			String Headervalue = driver.findElement(By.xpath("(//h3[@class='flex-media-heading text-black heading-large'])["+i+"]")).getText();
	    			assertSctionVerify(driver.findElement(By.xpath("(//h3[@class='flex-media-heading text-black heading-large'])["+i+"]")).isDisplayed(), test, Headervalue, Headervalue,Headervalue+ " Section is not displaying");
	    		}
	    		
	    		
	    	}
	    	
	    }
	    
public void RankingFilter(ExtentTest test,List<WebElement> RankingList,ExtentTest fail ) throws Exception 
	{
		int RankingSize=RankingList.size();
    	    	                            
	    	       for(int j=1;j<=RankingSize;j++) {
	        	     
	    	    	driver.findElement(By.xpath("//div[@id='accordion-rankings']/div["+j+"]/a")).click();
	    	        Thread.sleep(9000);
	    	       
	    	        String ActualText = driver.findElement(By.xpath("//div[@id='accordion-rankings']/div["+j+"]/a")).getText();
	    	                                    
	
	    	        if(driver.findElement(By.xpath("//div[@class='flex-media-content']/h1")).isDisplayed()){
	    	        	
	    	        	String ExpectedText= driver.findElement(By.xpath("//div[@class='flex-media-content']/h1")).getText();
	    	        	ActualText=(ActualText.equals("Best Commercial Diets")) ? "Best Commercial Diet" : ActualText;
	    	        	String url = driver.getCurrentUrl();
	    	        	assertValues(ActualText,ExpectedText,  test, "User in "+ExpectedText+" page",fail,url);
	    	        	}
	    	       }
	}
	     public void DietFilter(ExtentTest test,List<WebElement> DietList,ExtentTest fail  ) throws Exception
	     {
	    	   
	    	           int DietSize=DietList.size();
	    	    	
	    	               
	    	        for(int j=1;j<=DietSize;j++) {
	    	        
	    	        	 driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
	    	       	  
	    	        	driver.findElement(By.xpath("//div[@id='accordion-diet-type']//div["+j+"]/label")).click();
	    	        	driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
	    	      	  
	    	            String ActualText = driver.findElement(By.xpath("(//div[@id='accordion-diet-type']/div[@class='search-field-view'])["+j+"]/label/span[1]")).getText();
	    	            if(driver.findElement(By.xpath("//button[@data-js-id='facet-clear-button']")).isDisplayed()) {
	    	        
	    	                          
	    	          
	    	            String ExpectedText= driver.findElement(By.xpath("//button[@data-js-id='facet-clear-button']")).getText();
	    	            driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
	    	      	  
	    	            driver.findElement(By.xpath("//a[@data-js-id='clear-all']")).click();
	    	            driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
	    	      	  
	    	            String url = driver.getCurrentUrl();
	    	             assertValues(ActualText,ExpectedText,  test, "User in "+ExpectedText+" page",fail,url);
	    	        }
	    	     }
	    	  }
	     public void DietPreferencesFilter(ExtentTest test,List<WebElement> PreferencesList,ExtentTest fail) throws Exception
	     {
	    	    
	    	           int preferences_size=PreferencesList.size(); 
	    	           System.out.println(preferences_size);
	    	         for(int j=1;j<=preferences_size;j++) {
	    	        	 System.out.println("j Value TC08 i==3 "+j);
	    	        	 Thread.sleep(9000);
	    	      
	    	          driver.findElement(By.xpath("//div[@id='accordion-diet-preferences']//div["+j+"]/label")).click();
	    	          
	    	          Thread.sleep(9000);
	    	          
	    	        /*	  ((JavascriptExecutor)driver).executeScript("window.scrollBy(0,200)");
	    					 driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
	    					JavascriptExecutor executor = (JavascriptExecutor)driver;
	    					 driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
	    					*/
	    	         
	    	    
	    	          String ActualText = driver.findElement(By.xpath("(//div[@id='accordion-diet-preferences']//div[@class='search-field-view']/label)["+j+"]")).getText();
	    	          if(driver.findElement(By.xpath("//button[@data-js-id='facet-clear-button']")).isDisplayed()) {
	    	        	 
	    	          String ExpectedText= driver.findElement(By.xpath("//button[@data-js-id='facet-clear-button']")).getText();
	    	        String url = driver.getCurrentUrl();
	    	          assertValues(ActualText, ExpectedText, test, "User in "+ExpectedText+" page",fail,url);
	    	         
	    	          driver.findElement(By.xpath("//a[@data-js-id='clear-all']")).click();
	    	        
	    	    	        
	    	          }
	    	         }
	    	     }
	    	  
	
 
   public void pressEnterKey() throws AWTException, InterruptedException{
   	
   	Robot enter = new Robot();
   	enter.keyPress(KeyEvent.VK_ENTER);
   	Thread.sleep(7000);
   	
   } 
   public boolean containsStringElement(String Expected, String actual) {

       if(Expected.trim().contains(actual.trim())||(actual.trim().contains(Expected.trim())))
       {    
            //System.out.println("Expected");
            return true;
       }
       return false;
       
   }
   
   
  /* public void autosuggest(WebElement element1, List<WebElement> element2 , String values) {

	   element1.sendKeys(values);

	   
	   
	   for(WebElement option : element2){
		   
		   waitForVisibilityofElement(option);
	       
	       if(option.getSize().equals(0)) {
	    	  
	    	  String autovalue = option.getText();
	    	  System.out.println(autovalue);
	    	  option.click(); 
	       }
	       
	      
	   }
	   
   }*/

}
