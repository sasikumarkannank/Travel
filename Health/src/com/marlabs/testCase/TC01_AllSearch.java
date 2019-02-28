package com.marlabs.testCase;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.marlabs.testObjects.TC01_AllSearchPageObjects;
import com.relevantcodes.extentreports.ExtentTest;

public class TC01_AllSearch extends TC01_AllSearchPageObjects {
	
	ExtentTest test=null;
	ExtentTest fail=null;
	@BeforeMethod
	public void startTest() {
		try {
			
			String TestcaseName= getClass().getSimpleName();
			startReport(TestcaseName);
			
			test= report.startTest(getClass().getSimpleName());
			fail=report.startTest(getClass().getSimpleName());
			PageFactory.initElements(driver, this);
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in starting the test");
		}
	}
	@Test
	public void VerifyHomePage() {
		
		try {
			driver.get(getPropertyValue("BestHospitalURL"));
			
			//Verify User in Best hospital Page
			assertValues(getHospitalPageHeader(), getPropertyValue("HospitalPageHeader"), test, "<b>"+"Home Page Header"+"</b>", fail, driver.getCurrentUrl());
			
			// Verifing the user in Hospital Search Page
			ClickChildrenRadioBnt();
			Search();
			assertValues(SearchPageText(), getPropertyValue("SearchPageHeader"), test, "<b>"+"Search Page Header"+"</b>", fail, driver.getCurrentUrl());
						
			driver.navigate().back();
			
			//Click on Children hospital radio button
			ClickChildrenRadioBnt();
			
			// Search Using Hospital page
			HospitalNameArea(getPropertyValue("HospName").trim());
			clickpage();
			Search();
			
			assertValues(SearchNameCard(),getPropertyValue("HospName"),test,"<b>"+"Hospital Name In Card"+"</b>", fail, driver.getCurrentUrl() );
			
			//Search Using Zip Code
			driver.navigate().back();
			ClickChildrenRadioBnt();
			ZipCodeText(getPropertyValue("zipcode").trim());
			clickpage();
			Search();
			assertValues(ZipCodeInfilter(), getPropertyValue("zipcode"), test, "<b>"+"Zip Code in Filter"+"</b>", fail, driver.getCurrentUrl());	
		
			//Search Using State Name
			driver.navigate().back();
			ClickChildrenRadioBnt();
			ZipCodeText(getPropertyValue("StateName").trim());
			Search();
			assertValues(ZipCodeInfilter(), getPropertyValue("StateName"), test, "<b>"+"State Name in Filter"+"</b>", fail, driver.getCurrentUrl());	
		
			//Search Using auto suggetion State Name
			driver.navigate().back();
			driver.manage().deleteAllCookies();
			ClickChildrenRadioBnt();
			ZipCodeText(getPropertyValue("StateSuggestion").trim());
			clickAuto();
			Search();
			assertValues(ZipCodeInfilter(), getPropertyValue("StateSuggestion"), test, "<b>"+"State Name Auto suggestion"+"</b>", fail, driver.getCurrentUrl());
			
			//Search Using Specialty
			driver.navigate().back();
			ClickChildrenRadioBnt();
			selfilter(getPropertyValue("specialtyValue").trim());
			Search();
			assertValues(SpecialtyInfilter(), getPropertyValue("specialtyfil"), test, "<b>"+"Specialty in Filter"+"</b>", fail, driver.getCurrentUrl());
			
			System.out.println("TestCase 1 Executed");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	
	}
		@AfterMethod
	protected void endTest() {
		try {
			report.endTest(test);
			report.endTest(fail);
			endReport();
		
		} catch (Exception e) {
			System.out.println("Exception in Ending the test");
	
		}
	}
	
}

