package com.marlabs.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.marlabs.constants.Constants;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public interface GenericMethods extends Constants{
	
	
	default String getPropertyValue(String key) throws IOException {
		
			FileInputStream propertyfileInputStream = new FileInputStream(BestHospitalFilePath);
			Properties propertiesObj = new Properties();
			propertiesObj.load(propertyfileInputStream);
			return propertiesObj.getProperty(key);
		
	}
	
	default void LinkStatus(WebDriver driver, String path, ExtentTest test, ExtentTest fail, String SectionName) throws IOException {
		
		List<WebElement>links = driver.findElements(By.xpath(path));
		
		for(WebElement linkno: links) {
			
			String URL = linkno.getAttribute("href");	
			driver.manage().deleteAllCookies();
			URL baseUrl = new URL(URL);
			HttpURLConnection connection_baseURL = (HttpURLConnection) baseUrl.openConnection();
			int statusCode_baseURL = connection_baseURL.getResponseCode();
			//System.out.println(statusCode_baseURL);
			String msg_baseURl = connection_baseURL.getResponseMessage();			

			if (statusCode_baseURL == 200) {
				test.log(LogStatus.PASS,
						"<span style='font-weight:bold;color:black'>" + SectionName + "</br></span>"
								+ "<span style='font-weight:bold;color:blue'>" + "" + "Actual_URL: " + URL
								+ "</br></span>"
								+ "Response code:<span style='font-weight:bold; font-size:large;color:green'>"
								+ statusCode_baseURL + "</span>");
			}
			else {
				fail.log(LogStatus.FAIL,
						"<span style='font-weight:bold;color:black'>" + SectionName + "</br></span>"
								+ "<span style='font-weight:bold;color:blue'>" + "" + "Actual_URL: " + URL
								+ "</br></span>"
								+ "Response code:<span style='font-weight:bold; font-size:large;color:Red'>"
								+ statusCode_baseURL + "</span>");
			}
		}
		
	}
	
	default void assertValues(String actual ,String Expected, ExtentTest test, String desc, ExtentTest fail, String url) throws Exception {
		
		
		try{
			if(actual.toLowerCase().equals(Expected.toLowerCase())||Expected.toLowerCase().equals(actual.toLowerCase())) {
			
			test.log(LogStatus.PASS,desc +" "+resultPASS(actual, Expected, url));
		}
		else {
			fail.log(LogStatus.FAIL, desc +" "+resultFAIL(actual, Expected, url));
		}
		}
		catch(Exception e) {
			
			fail.log(LogStatus.FAIL, desc +" "+resultFAIL(actual, Expected, url));
		}
	}
	
	default void assertHeader(boolean value,  ExtentTest test, String desc,ExtentTest fail) {
        if(value) {
              test.log(LogStatus.PASS, desc+" Header is Displaying Successfully");
        }else {
     	   fail.log(LogStatus.FAIL, desc+" Header is not Displaying Successfully");
        }
}
	
	default void assertFilterValues(String filterValue ,String HeaderValue, ExtentTest test,ExtentTest fail, String url) throws Exception {
	
	if(HeaderValue.contains(filterValue))
	{
	test.log(LogStatus.PASS, "<b>Filter Selected : </b>"+filterValue+" <br><b>Filtered Page Header: </b>"+HeaderValue+"");
	}
	else
	{
		fail.log(LogStatus.FAIL,resultFAIL(filterValue, HeaderValue, url));
	}
		
		
	}
	
	default void assertImage(boolean value, ExtentTest test, String desc) {
		if(value) {
			test.log(LogStatus.PASS, desc+" is Displaying Successfully");
		}else {
			test.log(LogStatus.FAIL, desc+" is not Displaying Successfully");
		}
		
	}
	
	default void CheckComponentEnabled(boolean value, ExtentTest test, String desc,ExtentTest fail) {
		if(value) {
			test.log(LogStatus.PASS, desc);
		}else {
			fail.log(LogStatus.FAIL, desc);
		}
		
	}
	
	default void assertCompare(WebDriver driver, ExtentTest test, String ActualValue, String ExpectedValue, String desc, String descFail) {

		if(ActualValue.toLowerCase().contains(ExpectedValue.toLowerCase())||ExpectedValue.toLowerCase().contains(ActualValue.toLowerCase())) {
			
			test.log(LogStatus.PASS, desc);
		}
		else {
			test.log(LogStatus.FAIL, descFail);
		}
	}
	
	default void assertSctionVerify(boolean value, ExtentTest test, String ActualValue, String desc, String DescFail){
		
		if(value) {
			test.log(LogStatus.PASS, desc);
		}
		else {
			test.log(LogStatus.FAIL, DescFail);
		}
		
	}
	
	default void assertclickAndVerify(ExtentTest test, String ActualValue, String Expected, String desc, String DescFail) throws InterruptedException {

		if(ActualValue.contains(Expected)) {
		test.log(LogStatus.PASS, desc);
		}
		else {
		test.log(LogStatus.FAIL, DescFail);	
		}
	}

	default void assertSection(boolean value, String Elementext, ExtentTest test, String desc) throws Exception {
        if(value) {
               test.log(LogStatus.PASS, desc+" ");
        }else {
               test.log(LogStatus.FAIL, desc+" ");
        }
        
 }
   
        default void assertHeader(boolean value,  ExtentTest test, String desc) {
               if(value) {
                     test.log(LogStatus.PASS, desc+" Header is Displaying Successfully");
               }else {
                     test.log(LogStatus.FAIL, desc+" Header is not Displaying Successfully");
               }
 }

        public static String resultPASS(String ActualData, String ExpectedData, String URL) throws Exception {

    		    	String text = "<span style='color:black'>"+"</br>" + "<b>URL :</b> " +URL
    	    		+ "</br></span>" +"<span style='color:black'>"+ "<b>Expected :</b> " +ExpectedData
    				+ "</br></span>" + "<span style='color:black'>" + "<b>Actual :</b> " +ActualData
    				+ "</br></span>";
    		return text;
        }    
        
    	public static String resultFAIL(String Actual_data, String Expected_data, String URL) throws Exception {

    		String text = "<span style='color:black'>"+"</br>" + "<b>URL :</b> " +URL
    	    		+ "</br></span>" +"<span style='color:black'>" + "<b>Expected :</b> " +Expected_data
    				+ "</br></span>" + "<span style='color:black'>" + "<b>Actual :</b> " +Actual_data
    				+ "</br></span>";
    		return text;
    	}

   

}
