package Marlabs.TestAPI;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;

import com.gargoylesoftware.htmlunit.UnexpectedPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Marlabs.Constants.Constants;
import Marlabs.Core.BaseClass;
import Marlabs.Core.Keywords;
import Marlabs.Core.Xls_Reader;
import Marlabs.Reports.ExtentReport;

public class TestRegionalRecognisition extends Keywords {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException,ClassNotFoundException, IOException {
		// try{
		Keywords keywords = new Keywords();
		String HOSP_URL = null;

		Xls_Reader getdata_Pages = new Xls_Reader(Constants.TestSuitePath_OE);
		int numOfPages = getdata_Pages.getRowCount(Constants.Sheetname);
		int totalurls=numOfPages;
		System.out.println("No of pages: " +totalurls );
		int Columncount = getdata_Pages.getColCount(Constants.Sheetname);
		System.out.println("Column Count = "+Columncount);
		BaseClass.storeObjectOnlineEducation();

		 
		  LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

          java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
          java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
		
         WebDriver driver=new HtmlUnitDriver();
		
		driver.get("https://hospitaldashboard-uat3.usnews.com/login");
	
		System.out.println("Url Accessed");
		
		driver.findElement(By.name("login")).sendKeys("ITS-MIS@usnews.com");
		driver.findElement(By.name("password")).sendKeys("?93is?*nJhMeyGO");
		driver.findElement(By.name("form.submitted")).click();
		
		ExtentReports extent = ExtentReport.startReport("Result");
		ExtentTest Parent_Pass = extent.startTest("Passed Data");
		ExtentTest Failed_Report = extent.startTest("Failed Data");
		ExtentTest testreportSkip = extent.startTest("Skipped Data");
		ExtentTest FatalErrors_Report = extent.startTest("Fatal Errors");
		String colName= "Region_name";	
		// Loop for Online Education colleges URL (numOfPages)
		for (int k = 2001; k <4000; k++) {
			
			HOSP_URL = getdata_Pages.getCellData(Constants.Sheetname, "Testing URL", k);
			ExtentTest testreport = null;

			ArrayList<String> dataValue = BaseClass.getObjectForDataValueOnlineEducation(HOSP_URL);

			String URL = HOSP_URL ;
			System.out.println(k + ": " + "URL : " + URL);

			//to remove console warnings of htmlunit
            LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

            java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
            java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

           try {
			
			URL baseUrl = new URL(HOSP_URL);
			HttpURLConnection connection_baseURL = (HttpURLConnection) baseUrl.openConnection();
			int statusCode_baseURL = connection_baseURL.getResponseCode();
			//System.out.println(statusCode_baseURL);
			
			if(statusCode_baseURL==200) {
		
			driver.get(HOSP_URL);
			
			Thread.sleep(2000);
			
			// For getting the column name from excel sheet (Try catch)
			try {
				
				Marlabs.Core.Xls_Reader excel = new Marlabs.Core.Xls_Reader(Constants.TestSuitePath_OE);
				ArrayList al = new ArrayList();

				if(statusCode_baseURL==200) {
					
				if(driver.findElements(By.xpath("//span[contains(text(),'Regional Rankings')]/ancestor::div[@class='info']//li//p[contains(text(),'Recognized')]")).size()>0) {
					
					testreport=extent.startTest("Hospitals Dashboard: " + HOSP_URL);
			
					
					
					//String Expected_Value = dataValue.get(z).trim();
					String Expected_Value = getdata_Pages.getCellData(Constants.Sheetname, "region_name", k);
					Expected_Value = "Recognized in "+Expected_Value;
					//System.out.println("Expected Value = "+Expected_Value);
					
					String Xpath="//span[contains(text(),'Regional Rankings')]/ancestor::div[@class='info']//li//p[contains(text(),'Recognized')]";
					String Actual_Value =null;
				
					try {
						
						if(Expected_Value.isEmpty()){
							keywords.dataforskip(colName,testreport, testreportSkip, HOSP_URL);
						}
							
						Actual_Value=driver.findElement(By.xpath(Xpath)).getText();
						//System.out.println("Actual_Value ="+Actual_Value);
						
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
				extent.endTest(FatalErrors_Report);
				}else {
					testreportSkip.log(LogStatus.SKIP, "<b>No Regional Recognition for this Hospital</b><br> Hospital Dashboard URL : " + HOSP_URL);
				}
				
				}
			} catch (Exception e) {
				e.printStackTrace();
				Parent_Pass.appendChild(testreport);
				extent.endTest(testreport);
				extent.endTest(Parent_Pass);
				extent.endTest(Failed_Report);
				extent.endTest(testreportSkip);
				extent.endTest(FatalErrors_Report);
				
			}
		}else {
			FatalErrors_Report.log(LogStatus.FATAL, keywords.resultFailStatusCode( HOSP_URL, statusCode_baseURL));
		}
		
			}catch(Exception e) {
        	   e.printStackTrace();
        	   continue;
           }
           
			}
		System.out.println("Test Completed");
		
		if(Parent_Pass!=null || Failed_Report!=null||testreportSkip!=null|| FatalErrors_Report!=null) {
			
		extent.endTest(Parent_Pass);
		extent.endTest(Failed_Report);
		extent.endTest(testreportSkip);
		extent.endTest(FatalErrors_Report);
		
		}
		extent.flush();
		extent.close();
	}
	
		
	}