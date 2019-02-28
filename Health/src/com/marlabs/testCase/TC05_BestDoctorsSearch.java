package com.marlabs.testCase;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.marlabs.testObjects.TC05_BestDoctorsSearchObjects;
import com.relevantcodes.extentreports.ExtentTest;

public class TC05_BestDoctorsSearch extends TC05_BestDoctorsSearchObjects 
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
		
		AccessingURL(getPropertyValue("BestDoctors"));
		
		// Verify the wheather distance is enabled or not
		milesDropDown();
		CheckComponentEnabled(milesDropDown(),test,"<b>Distance</b> is disabled",fail);
		
		// Verify Loctaion
		getLocationName();
		pressEnterKey();
		Thread.sleep(5000);
		assertValues(getPropertyValue("ZipCode"), verifyDistance(), test, "<b>Zip Code</b>", fail, driver.getCurrentUrl());
		pressEnterKey();
		
		// Verify the distance 
		milesDropDown();
		CheckComponentEnabled(milesDropDown(),test,"<b>Distance</b> is enabled",fail);
		moveToMiles();
		pressEnterKey();
		assertValues(milesTextOnSearch(),milesText(),test,"<b>Miles</b>",fail,driver.getCurrentUrl());
		toClearFilter();
		
		// Verify the Specaility
		getSpecialityName();
		pressEnterKey();
		assertValues(getPropertyValue("Specilaity"),getSpecialityTextOnCard(),test,"<b>Speciality</b>",fail,driver.getCurrentUrl());
		toClearFilter();
			
		// Verify the Gender
		clickOnPrefferedGender();
		clickOnGender();
		assertValues(getGender(),verifyGender(),test,"<b>Gender</b>",fail,driver.getCurrentUrl());
		toClearFilter();
	
	}
	
	@AfterMethod
	protected void endTest() {
		try {
			report.endTest(test);
			report.endTest(fail);
			System.out.println("Test case 5 executed");
			endReport();
			
		
		} catch (Exception e) {
			System.out.println("Exception in Ending the test");
		}
	}
}