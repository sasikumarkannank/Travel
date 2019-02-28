package com.marlabs.testObjects;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.marlabs.core.PageObjeccts;

public class TC06_BestHospitalsSearchObjects extends PageObjeccts 
{
	@FindBy(xpath="//div[@data-js-id='adults-tab']//button")
	WebElement SearchButton;
	
	@FindBy (xpath="//h1")
	WebElement SearchPageHeader;
	
	@FindBy(xpath="(//span//input[@placeholder='Hospital Name (optional)'])[1]")
	WebElement HospitalNameTextField;
	
	@FindBy(xpath="(//span//input[@placeholder='City, State or ZIP'])[1]")
	WebElement ZipCodeTextField;
	
		
	@FindBy(xpath="(//select[@name='specialty_id'])[1]")
	WebElement ClickOnDropDown;
	
	@FindBy(xpath="(//optgroup[@label='Adult Specialties'])[1]/option[1]")
	WebElement SelectSpeciality;
	
	@FindBy(xpath="//div[@class='superhero-content-container flex-row small-center medium-end superhero-content-auto-height']")
    WebElement clickOnPage;
	
	@FindBy(xpath="(//h3)[1]")
	WebElement HospitalCardName;
	
	@FindBy(xpath="//div[@class='tt-suggestion tt-selectable']")
	WebElement AutoSuggestion;
	
	@FindBy(xpath="(//span[@class='bar-tightest'])[2]")
	WebElement ZipCodeButton;
	
	@FindBy(xpath="(//span[@class='bar-tightest'])[3]")
	WebElement SpecialityButton;
	
	protected void clickpage() throws InterruptedException {
		
		toClickElement(clickOnPage);
	}

	protected void clickSearchButton() throws InterruptedException
	{
		toClickElement(SearchButton);
		
	}
	protected String getHospitalName() throws IOException
	{
		
		return DataEntry(HospitalNameTextField, getPropertyValue("HospitalName"));
	}

	protected String getZipCode() throws IOException
	{
		return DataEntry(ZipCodeTextField, getPropertyValue("ZipCode"));
	}
	
	protected void clickOnDropDown() throws InterruptedException
	{
		toClickElement(ClickOnDropDown);
	}
	
	protected void clickOnSpeciality() throws InterruptedException
	{
		toClickElement(SelectSpeciality);
	}
	
	protected void moveToSpeciality() throws InterruptedException
	{
		toFocus(SelectSpeciality);
	}
	
	protected void clickToSpeciality() throws InterruptedException
	{
		toClickElement(SelectSpeciality);
	}
	protected boolean getSearchPageHaeder() throws IOException
	{
		return containsStringElement(getWebElementtext(SearchPageHeader),getPropertyValue("HospitalSearchPageHeader"));
	}
	protected String getCardName() throws IOException
	{
		return getWebElementtext(HospitalCardName);
	}
	
	
	protected String getZipCodeFilter() throws IOException
	{
		return getWebElementtext(ZipCodeButton);
	}
	
	protected String getSpecialityFilter() throws IOException
	{
		return getWebElementtext(SpecialityButton);
	}
	
}
