package com.marlabs.testCase;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.marlabs.testObjects.TC04_BestHospitalsSearchAllObjects;
import com.relevantcodes.extentreports.ExtentTest;

public class TC04_BestHospitalsSearchAll extends TC04_BestHospitalsSearchAllObjects 
{
	ExtentTest fail=null;
	ExtentTest test=null;

	@BeforeMethod
	protected void startTest() {
		try {
			
			String TestCaseName = getClass().getSimpleName();
			startReport(TestCaseName);
			
			test= report.startTest(getClass().getSimpleName());
			fail=report.startTest(getClass().getSimpleName());
			PageFactory.initElements(driver, this);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception in starting the test");
		}
	}

	@Test
	
	public void BestHospitalsSearch() throws Exception
	{
		
		AccessingURL(getPropertyValue("BestHospitals"));
		
		// Verify Search Page
		clickSearchButton();
		assertHeader(getSearchPageHaeder(),test," Search Page ",fail);
		
		// Verify using hospital name negative scenario
		driver.navigate().back();
		getHospitalName();
		clickpage();
		clickSearchButton();
		assertValues(getPropertyValue("HospitalName"),getCardName(),test,"<b> Hospital Name </b>", fail,driver.getCurrentUrl());
		
				
		// Verify using zipcode 
		driver.navigate().back();
		getZipCode();
		clickSearchButton();
		assertValues(getPropertyValue("ZipCode"),getFilterName(),test,"<b> Zip Code </b>",fail,driver.getCurrentUrl());
		
		// Verify using speciality
		driver.navigate().back();
		clickOnDropDown();
		clickOnSpeciality();
		moveToSpeciality();
		clickToSpeciality();
		clickSearchButton();
		assertValues(getPropertyValue("SpecialitySelected"),getFilterName(),test,"<b> Specialaity </b>",fail,driver.getCurrentUrl());
		
		
		
		//Verify using state name 
		driver.navigate().back();
		getStateName();
		clickOnAutoSuggestion();
		clickSearchButton();
		assertValues(getPropertyValue("StateName"),getFilterName(),test,"<b> State Name </b>",fail,driver.getCurrentUrl());
	
		
		//Verify using city name 
		driver.navigate().back();
		getCityName();
		clickOnAutoSuggestion();
		clickSearchButton();
		assertValues(getPropertyValue("CityName"),getFilterName(),test,"<b> City Name </b>",fail,driver.getCurrentUrl());
			
	
	}
	

	@AfterMethod
	protected void endTest() {
		try {
			report.endTest(test);
			report.endTest(fail);        
			System.out.println("Test case 4 executed");
			endReport();
		
		} catch (Exception e) {
			System.out.println("Exception in Ending the test");
		}
	}
}