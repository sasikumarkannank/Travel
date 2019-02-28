package com.marlabs.testObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.marlabs.core.PageObjeccts;

public class TC03_HospitalProfileObjects extends PageObjeccts{
	
	@FindBy(xpath="//div[@class='flex-media-content']//h1")
	WebElement HospitalName;
	
	@FindBy(xpath="//h2[contains(text(),'Rankings and Recognitions')]")
	WebElement RankingsSec;
	
	@FindBy(xpath="//h3[contains(text(),'Nearby Hospitals')]")
	WebElement NearHosp;
	
	@FindBy(xpath="//h3[@id='adult-rankings']")
	WebElement AdultSpecSec;
	
	@FindBy(xpath="//h3[@id='children-rankings']")
	WebElement childrenSpecSec;
	
	protected String ChildrenSpecSection(){
		return getWebElementtext(childrenSpecSec);	
	}
	
	protected String AdultSpecSection(){
		return getWebElementtext(AdultSpecSec);	
	}
	
	protected String NearHospital(){
		return getWebElementtext(NearHosp);	
	}
	
	protected String NameHeader(){
		return getWebElementtext(HospitalName);	
	}
	
	protected String RankingsSection(){
		return getWebElementtext(RankingsSec);	
	}

}
