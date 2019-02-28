package com.marlabs.testObjects;

import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.marlabs.core.PageObjeccts;//div//h1

public class TC05_BestDoctorsSearchObjects extends PageObjeccts {
	
	@FindBy(xpath="//div[@id='City,StateorZIP']//input[@placeholder='Location']")
	WebElement LoctaionTextField;
	
	@FindBy(xpath="(//select[@id='label']/option)[2]")
	WebElement Distance;
	
	@FindBy (xpath="//fieldset[@class='FilterMenuItem__FieldSet-s1fivikr-0 hGurNA']//div[@name='distance']")
	WebElement miles; 
	
	@FindBy(xpath="//select[@id='label']/option[2]")
	WebElement milesOption;
	
	@FindBy(xpath="(//div[@id='react-autowhatever-1'])[3]")
	WebElement AutoSuggestion;
	
	@FindBy(xpath="//div[@name='gender']/select/option[3]")
	WebElement Gender;
	
	@FindBy (xpath="//div[@id='Language']//div//input[@placeholder='Language Name']")
	WebElement language;
	
	@FindBy (xpath="//div[@class='react-autosuggest__container']//input[@placeholder='Hospital Name']")
	WebElement Affliation;
	
	@FindBy(xpath="(//div[@class='jpvx17-0-Box-cwadsP ghLyLW'])[1]")
	WebElement Filter;
	
	@FindBy(xpath="//div[@class='jpvx17-0-Box-cwadsP jYDApO']")
	WebElement ClearButton;
	
	@FindBy(xpath="//div[contains(text(),'20001')]")
	WebElement DistanceData;
	
	@FindBy(xpath="//div[@class='jpvx17-0-Box-cwadsP ghLyLW'][contains(text(),'Abilene Regional Medical Center')]")
	WebElement AffliationSelected;
	
	@FindBy(xpath="(//div[@class='Hide-s18sxv0v-0 bxLBHg jpvx17-0-Box-cwadsP geVZvN'])[3]")
	WebElement click;
	
	@FindBy(xpath="(//div[@id='react-autowhatever-1'])[4]")
	WebElement AffliationAutoSuggestion;

	@FindBy(xpath="(//div[@id='react-autowhatever-1']//span[@class='aabx0k-0-Span-juOiwt fuWRjB'])[1]")
	WebElement AffliationAuto;
	
	@FindBy(xpath="(//div[@class='DetailCardRankingText Box-cwadsP iRFqFI'])[1]")
	WebElement SpecialityDataOnCard;
	
	@FindBy(xpath="//input[@placeholder='Specialty']")
	WebElement Specialiyt;
	
	@FindBy(xpath="//select[@class='Select__SelectField-k6jz66-0 iIQooh']")
	WebElement GenderField;
	
	@FindBy(xpath="//div[@class='jpvx17-0-Box-cwadsP ghLyLW'][contains(text(),'Female')]")
	WebElement SelectedGender;
	
	protected String getLocationName() throws IOException
	{
		
		return DataEntry(LoctaionTextField, getPropertyValue("ZipCode"));
	}
		
	protected String verifyDistance()
	{
		return getWebElementtext(DistanceData);
		
		
	}
	protected void moveToDistance() throws InterruptedException
	{
		toFocus(Distance);
		Thread.sleep(3000);
	}
	
		
	protected void clickOnAutoSuggestion() throws InterruptedException
	{
		toClickElement(AutoSuggestion);
	}
	
	public boolean milesDropDown() throws InterruptedException
	{
		return toChkBtnEnabled(miles);
	}
	
	protected void moveToMiles() throws InterruptedException
	{
		toFocus(milesOption);
	}
	
	protected String milesText()
	{
		return getWebElementtext(milesOption);
	}
	
	protected String milesTextOnSearch()
	{
		return getWebElementtext(Distance);
	}
	
	protected void toClearFilter() throws InterruptedException
	{
		toClickElement(ClearButton);
		//Thread.sleep(3000);
	}

	
	protected String getSpecialityName() throws IOException
	{
		
		return DataEntry(Specialiyt, getPropertyValue("Specilaity"));
	}
	
	protected String getSpecialityTextOnCard()
	{
		return getWebElementtext(SpecialityDataOnCard);
	}
	
	protected String getAffliationName() throws IOException
	{
		
		return DataEntry(Affliation, getPropertyValue("Affliation"));
	}

	protected String verifyFilterselected()
	{
		return getWebElementtext(AffliationSelected);
	}
	
	protected void moveToAffliationAutoSuggestion() throws InterruptedException
	{
		toFocus(AffliationAutoSuggestion);
	}
	
	protected void clickToAffliationAutoSuggestion() throws InterruptedException
	{
		toClickElement(AffliationAuto);
	}
	
	protected void clickOnPrefferedGender() throws InterruptedException
	{
		toClickElement(GenderField);
		//Thread.sleep(2000);
	}
	
	protected void clickOnGender() throws InterruptedException
	{
		toClickElement(Gender);
	}
	
	protected String getGender()
	{
		return getWebElementtext(Gender);
	}
	
	protected String verifyGender()
	{
		return getWebElementtext(Gender);
	}
	
	

}

