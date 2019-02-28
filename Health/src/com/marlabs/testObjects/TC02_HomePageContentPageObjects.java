package com.marlabs.testObjects;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.marlabs.core.PageObjeccts;
import com.relevantcodes.extentreports.ExtentTest;

public class TC02_HomePageContentPageObjects extends PageObjeccts {

	@FindBy(xpath="//div[@class='sidekick-content show-for-medium-up padding-top-flush padding-bottom-flush']/h2")
	WebElement BestHospitalHeader;
	
	@FindBy(xpath="(//h4[contains(text(),'Honor Roll')])[1]")
	WebElement Honor;
	
	@FindBy(xpath="//h3[contains(text(),'Specialty Rankings')]")
	WebElement Specialty;
	
	@FindBy(xpath="//h3[contains(text(),\"2018-19 Best Children's Hospitals\")]")
	WebElement ChildHosp;
	
	@FindBy(xpath="//h3[@id='latest-hospital-advice']")
	WebElement Advice;
	
	@FindBy(xpath="//h3[contains(text(),\"2018-19 Best Hospitals by Procedure\")]")
	WebElement HospProc;
	
	@FindBy(xpath="//span[contains(text(),'Search Best Hospitals')]")
	WebElement SearchSec;
	
	@FindBy(xpath="//h3[contains(text(),'Health Care Rankings')]")
	WebElement RankingSec;
	
	
	String LinkData ="//div[@class='flex-small-12 flex-large-4']//a";
	
	protected void Linksec(ExtentTest test,ExtentTest fail, String SectionName ) throws IOException {
		
		LinkStatus(driver, LinkData, test, fail, SectionName);
	}
	
	
	protected String RankingSection(){
		return getWebElementtext(RankingSec);	
	}
	
	protected String SearchOptions(){
		return getWebElementtext(SearchSec);	
	}
	
	
	protected String HospitalProcedure(){
		return getWebElementtext(HospProc);	
	}
	
	protected String RightAdvice(){
		return getWebElementtext(Advice);	
	}
	
	protected String ChildrenHosp(){
		return getWebElementtext(ChildHosp);	
	}
	
	protected String SpecialtySec() {
		return getWebElementtext(Specialty);	
	}
	
	protected String HonorSec() {
		return getWebElementtext(Honor);	
	}
	
	protected String getHospitalPageHeader() {
		return getWebElementtext(BestHospitalHeader);	
	}
}
