package com.marlabs.testCase;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.marlabs.testObjects.TC02_HomePageContentPageObjects;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TC02_HompePageContents extends TC02_HomePageContentPageObjects {
	
	
	
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
	public void HomePage() {
		
		try {
			driver.get(getPropertyValue("BestHospitalURL"));
			
			//Verify User in Best hospital Page
			assertValues(getHospitalPageHeader(), getPropertyValue("HospitalPageHeader"),test, "<b>"+"Home Page Header"+"</b>", fail, driver.getCurrentUrl());
	
			assertValues(HonorSec(), getPropertyValue("HonText"), test, "<b>"+"Home Page Honor roll Section"+"</b>", fail, driver.getCurrentUrl());
		
			assertValues(SpecialtySec(),getPropertyValue("SpecSec"), test,"<b>"+"Home Page Specialty Rankings Section"+"</b>", fail, driver.getCurrentUrl());
		
			assertValues(ChildrenHosp(), getPropertyValue("ChildrenSec"), test, "<b>"+"Home Page Childrens Hospital Section"+"</b>", fail, driver.getCurrentUrl());
		
			assertValues(RightAdvice(), getPropertyValue("LatestAdvice"), test, "<b>"+"Home Page LatestA Advice Section"+"</b>", fail, driver.getCurrentUrl());
		
			assertValues(HospitalProcedure(), getPropertyValue("HospitalPro"),test, "<b>"+"Home Page Childrens Hospital Section"+"</b>", fail, driver.getCurrentUrl());
		
			assertValues(SearchOptions(), getPropertyValue("SearchSection"),test, "<b>"+"Home Page Search Section"+"</b>", fail, driver.getCurrentUrl());
		
			assertValues(RankingSection(), getPropertyValue("FooterRanking"),test, "<b>"+"Home Page Ranking Section"+"</b>", fail, driver.getCurrentUrl());
		
			Linksec(test,fail, getPropertyValue("LatestAdvice"));
		}
		catch(Exception e) {
			
			fail.log(LogStatus.FAIL, "Test Case failed");
			e.printStackTrace();
		}
		
	}
	
	@AfterMethod
	protected void endTest() {
		try {
			report.endTest(test);
			report.endTest(fail);
			System.out.println("Test case 2 executed");
			endReport();
		
		} catch (Exception e) {
			System.out.println("Exception in Ending the test");
	
		}
	}
	
}
