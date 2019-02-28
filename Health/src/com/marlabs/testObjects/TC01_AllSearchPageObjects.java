package com.marlabs.testObjects;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.marlabs.core.PageObjeccts;

public class TC01_AllSearchPageObjects extends PageObjeccts{

	@FindBy(xpath="//div[@class='sidekick-content show-for-medium-up padding-top-flush padding-bottom-flush']/h2")
	WebElement BestHospitalHeader;
	
	@FindBy(xpath="(//label[@class='form-control radio'])[2]")
	WebElement ChilRadioBtn;
	
	@FindBy(xpath="//div[@data-js-id='pediatric-tab']//button")
	WebElement SearchBtn;
	
	@FindBy(xpath="//div[@class='flex-media-content']/h1")
	WebElement SearchPageTxt;
	
	@FindBy(xpath="//div[@data-js-id='pediatric-tab']//input[@placeholder='Hospital Name (optional)']")
	WebElement HospitalName;
	
	//@FindBy(xpath="//ul[@data-js-id='facet-list']//button[@data-field-id='hospital_name']")
	@FindBy(xpath="//div[@class='search-result flex-row']//h3[1]")
	WebElement SearchedHospName;
	
	@FindBy(xpath="//div[@class='superhero-content-container flex-row small-center medium-end superhero-content-auto-height']")
    WebElement clickOnPage;
	
	@FindBy(xpath="//div[@data-js-id='pediatric-tab']//input[@name='city']")
    WebElement Zipcode;
	
	@FindBy(xpath="//ul[@data-js-id='facet-list']/li/button[@data-field-id='city']")
	WebElement ZipcodeFilter;
	
	@FindBy(xpath="//div[@data-js-id='pediatric-tab']//select[@name='specialty_id']")
	WebElement SelSpecialty;
	
	@FindBy(xpath="//ul[@data-js-id='facet-list']/li/button[@data-field-id='specialty_id']")
	WebElement Specialtyfilter;
	
	@FindBy(xpath="//div[@data-js-id='pediatric-tab']//div[@role='presentation']//div[@class='tt-suggestion tt-selectable'][1]")
	WebElement AutoName;
	
	 protected void clickAuto() throws InterruptedException {
         
         toClickElement(AutoName);
  }
	
	protected String SpecialtyInfilter() {
		
		
		return getWebElementtext(Specialtyfilter);	
	}
	
	protected void selfilter(String value) throws InterruptedException {
		toSelectDatafromDropdownByValue(SelSpecialty, value);
	}
	
	protected String ZipCodeInfilter() {
	
		return getWebElementtext(ZipcodeFilter);	
	}
	
	protected void ZipCodeText(String values) {
		sendValues(Zipcode, values);
	}
	
    protected void clickpage() throws InterruptedException {
           
           toClickElement(clickOnPage);
    }

	
	protected String SearchNameCard() {
		
		return getWebElementtext(SearchedHospName);	
	}
	
	protected void HospitalNameArea(String TextValue) throws InterruptedException {

		sendValues(HospitalName,TextValue);
	}
	
	protected String getHospitalPageHeader() {
		return getWebElementtext(BestHospitalHeader);	
	}
	
	protected void ClickChildrenRadioBnt() throws InterruptedException {
		toClickElement(ChilRadioBtn);	
		}
	
	protected void Search() throws InterruptedException {
		toClickElement(SearchBtn);
	}
	
	
	protected String SearchPageText() {
		return getWebElementtext(SearchPageTxt);	
	}
	
	
}
