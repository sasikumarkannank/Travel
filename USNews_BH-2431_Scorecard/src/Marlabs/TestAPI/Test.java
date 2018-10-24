package Marlabs.TestAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import Marlabs.Constants.Constants;
import Marlabs.Core.Keywords;
import Marlabs.Core.Xls_Reader;
import Marlabs.Reports.ExtentReport;

public class Test {
	public static void main(String[] args) throws InterruptedException {
		
		Keywords keywords = new Keywords();
		String HOSP_URL = null;

		Xls_Reader getdata_Pages = new Xls_Reader(Constants.TestSuitePath_OE);
		int numOfPages = getdata_Pages.getRowCount(Constants.Sheetname);
		int totalurls=numOfPages;
		System.out.println("No of pages: " +totalurls );
		int Columncount = getdata_Pages.getColCount(Constants.Sheetname);
		ExtentReports extent = ExtentReport.startReport("Result");
		ExtentTest Parent_Pass = extent.startTest("Passed Data");
		ExtentTest Failed_Report = extent.startTest("Failed Data");
		ExtentTest testreportSkip = extent.startTest("Skipped Data");
		for (int k = 2; k <=totalurls; k++) {
			HOSP_URL = getdata_Pages.getCellData(Constants.Sheetname, "Testing URL", k);
			ExtentTest testreport = null;
			String URL = HOSP_URL ;
			System.out.println(k + ": " + "URL : " + URL);

			  LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

	            java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
	            java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

	       

	            WebDriver driver=new HtmlUnitDriver();
	           
				driver.get(HOSP_URL);
				
				driver.findElement(By.name("login")).sendKeys("ITS-MIS@usnews.com");
				driver.findElement(By.name("password")).sendKeys("?93is?*nJhMeyGO");
				driver.findElement(By.name("form.submitted")).click();
				
				 //to choose year 2017-2018
				WebElement element=driver.findElement(By.xpath("(//div[@class='hospitalDrop'])[2]"));
				Actions action = new Actions(driver);
				action.moveToElement(element).moveToElement(driver.findElement(By.xpath("(//a[@class='hospitalDrop_a t'])[3]")));
				action.click().build().perform();
				
				Thread.sleep(2000);
				
				// For getting the column name from excel sheet (Try catch)
				try {

					testreport=extent.startTest("Hospitals Dashboard: " + HOSP_URL);
					String colName = "Honor Roll";//getdata_Pages.getCellData(Constants.Sheetname, "2018 Rank", k);
					String Expected_Value =  getdata_Pages.getCellData(Constants.Sheetname, "2018 Rank", k);
					String Xpath="//*[@data-test-id='honor-roll']";
					String Actual_Value =null;
				
					try {
						
						if(Expected_Value.isEmpty()){
							keywords.dataforskip(colName,testreport, testreportSkip, HOSP_URL);
						}
							
						Actual_Value=driver.findElement(By.xpath(Xpath)).getText();
						keywords.dataComparisonContains(Expected_Value, Actual_Value, colName, testreport,
						Failed_Report, HOSP_URL);
						
					}
						catch (Exception e) {
							
					//String Actual_Value1=driver.findElement(By.xpath(Xpath)).getText();
				keywords.dataComparisonContains(Expected_Value, Actual_Value, colName, testreport,
					Failed_Report, HOSP_URL);
						continue;
					}
					Parent_Pass.appendChild(testreport);
					extent.endTest(testreport);
					extent.endTest(Parent_Pass);
					extent.endTest(Failed_Report);
					extent.endTest(testreportSkip);
					
				}catch(Exception e) {
					e.printStackTrace();
					Parent_Pass.appendChild(testreport);
					extent.endTest(testreport);
					extent.endTest(Parent_Pass);
					extent.endTest(Failed_Report);
					extent.endTest(testreportSkip);
					/*extent.endTest(FatalErrors_Report);*/
					extent.close();
				}
			
			
			
		}

		System.out.println("Test Completed");
		
		extent.flush();
		extent.close();
	}
	
}
