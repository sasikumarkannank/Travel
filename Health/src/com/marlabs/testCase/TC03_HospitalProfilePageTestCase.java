package com.marlabs.testCase;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.marlabs.testObjects.TC03_HospitalProfileObjects;
import com.relevantcodes.extentreports.ExtentTest;

public class TC03_HospitalProfilePageTestCase extends TC03_HospitalProfileObjects {
	
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
	public void VerifyHospitalProfile() throws Exception {
		
		driver.get(getPropertyValue("HospProfilePageUrl"));
		
		//Verify User in hospital profile page
		assertValues(NameHeader(), getPropertyValue("HospitalNameProfile"),test, "<b>"+"Hospital Name in Header"+"</b>", fail, driver.getCurrentUrl());
		
		assertValues(RankingsSection(),getPropertyValue("RankingSection"),test, "<b>"+"Ranking and recognitions Header"+"</b>", fail, driver.getCurrentUrl());
		
		assertValues(NearHospital(),getPropertyValue("NearHospitalSec"), test,"<b>"+"Near by hospital section verification"+"</b>", fail, driver.getCurrentUrl());
		
		assertValues(AdultSpecSection(),getPropertyValue("SpecialtyHeader"), test,"<b>"+"Adult Specialties Rankings and Ratings section verification"+"</b>", fail, driver.getCurrentUrl());
		
		assertValues(ChildrenSpecSection(),getPropertyValue("ChildrenSpecialtyHeader"), test,"<b>"+"Children's Specialties Rankings and Ratings section verification"+"</b>", fail, driver.getCurrentUrl());
		
		assertValues(ChildrenSpecSection(),getPropertyValue("ChildrenSpecialtyHeader"), test,"<b>"+"Children's Specialties Rankings and Ratings section verification"+"</b>", fail, driver.getCurrentUrl());
				
	}
		@AfterMethod
	protected void endTest() {
		try {
			report.endTest(test);
			report.endTest(fail);
			System.out.println("Test case 3 executed");
			endReport();
		
		} catch (Exception e) {
			System.out.println("Exception in Ending the test");
	
		}
	}
	
}