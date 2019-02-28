package com.marlabs.testCase;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.marlabs.testObjects.TC06_BestHospitalsSearchObjects;
import com.relevantcodes.extentreports.ExtentTest;

public class TC06_BestHospitalsSearch extends TC06_BestHospitalsSearchObjects 
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
		getHospitalName();
		clickpage();
		getZipCode();
		clickOnDropDown();
		clickOnSpeciality();
		moveToSpeciality();
		clickToSpeciality();
		clickSearchButton();
		assertHeader(getSearchPageHaeder(),test," Search Page ",fail);
		assertValues(getPropertyValue("HospitalName"),getCardName(),test,"<b> Hospital Name </b>", fail,driver.getCurrentUrl());
		assertValues(getPropertyValue("ZipCode"),getZipCodeFilter(),test,"<b> Zip Code </b>",fail,driver.getCurrentUrl());
		assertValues(getPropertyValue("SpecialitySelected"),getSpecialityFilter(),test,"<b> Specialaity </b>",fail,driver.getCurrentUrl());

	}
		@AfterMethod
		protected void endTest() {
			try {
				report.endTest(test);
				report.endTest(fail);
				System.out.println("Test case 6 executed");
				endReport();
				
				
			} catch (Exception e) {
				System.out.println("Exception in Ending the test");
			}

	
		}
	}
