package Marlabs.TestAPI;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Marlabs.Constants.Constants;
import Marlabs.Core.Keywords;
import Marlabs.Core.Xls_Reader;
import Marlabs.Reports.ExtentReport;

public class TestHospitalRankings3 {
	
	static Keywords keywords = new Keywords();
	public static void main(String[] args) {
		ExtentReports extent = ExtentReport.startReport("Result");
		try {
			
			Xls_Reader reader= new Xls_Reader(Constants.TestSuitePath_OE);
			int rows = reader.getRowCount(Constants.Sheetname);
			int totalurls = rows;
			System.out.println("No of rows: " + totalurls);
			//int Columncount = reader.getColCount(Constants.Sheetname);
			

			
			ExtentTest Parent_Pass = extent.startTest("Passed Data");
			ExtentTest Failed_Report = extent.startTest("Failed Data");
			
			
			//to remove the console warnings
			LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
			java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
			java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
		
			HtmlUnitDriver driver= new HtmlUnitDriver();
			//String hospid=null;
			String hospUrl=null;
			
			for (int k = 3; k <=totalurls; k++) {
			
				hospUrl=reader.getCellData(Constants.Sheetname, "Testing URL", k);
				//System.out.println("Hosp id is "+hospid);
				//hospUrl ="https://health-uat3.usnews.com/best-hospitals/area/ma/boston-childrens-hospital-"+hospid;
			
				
				//driver.setJavascriptEnabled(true);
				URL baseUrl = new URL(hospUrl);
				HttpURLConnection connection_baseURL = (HttpURLConnection) baseUrl.openConnection();
				int statusCode_baseURL = connection_baseURL.getResponseCode();
				//System.out.println(statusCode_baseURL);
				
				if (statusCode_baseURL == 404) {
					ExtentTest FatalErrors_Report = extent.startTest("Fatal Errors");
					FatalErrors_Report.log(LogStatus.FATAL,
							"<span style='font-weight:bold;color:blue'>" + "" + "URL: " + hospUrl + "</br></span>"
									+ "Response code:<span style='font-weight:bold; font-size:large;color:red'>"
									+ statusCode_baseURL + "</span>");
					extent.endTest(FatalErrors_Report);
				}
				
				if(statusCode_baseURL==200)
				{
					driver.get(hospUrl);
					ExtentTest testreportpass = extent.startTest("Hospitals : " + hospUrl);
					
				try {	
						//String attributeName=reader.getCellData(Constants.Sheetname, "Hospital", k);
						//String Expected_Value_TotalRank =reader.getCellData(Constants.Sheetname, "Total specialties ranked", k);
						String Expected_Value_HonorRank =reader.getCellData(Constants.Sheetname, "2018 Rank", k);
						//System.out.println("Exp total Value is "+Expected_Value_TotalRank);
						//System.out.println("Exp honor  Value is "+Expected_Value_HonorRank);
						//String Xpath_total = reader.getCellData(Constants.Sheetname, "Total specialties ranked", 2);
						//System.out.println("Xpath total is "+Xpath_total);
						String Xpath_honor =  reader.getCellData(Constants.Sheetname, "2018 Rank", 2);
						//System.out.println("Xpath honor is "+Xpath_honor);
						
						//String Actual_Value_total = null;
						String Actual_Value_Honor = null;
							
						try {
							//Actual_Value_total = driver.findElement(By.xpath(Xpath_total)).getText();
							//String[] total= Actual_Value_total.split(" ");
							//Actual_Value_total=total[0].trim();
							//System.out.println("Actual Value is "+Actual_Value_total);
							
							if(!Expected_Value_HonorRank.equals("0")) {
								Actual_Value_Honor= driver.findElement(By.xpath(Xpath_honor)).getText();
								//System.out.println("Actual Value is "+Actual_Value_Honor);
								
							}
							//keywords.dataComparisonContains(Expected_Value_TotalRank, Actual_Value_total, "Total Number of Specialities", testreportpass, Failed_Report,hospUrl);
							if(!Expected_Value_HonorRank.equals("0")) {
							keywords.dataComparisonContains(Expected_Value_HonorRank, Actual_Value_Honor, "Honour Roll", testreportpass, Failed_Report,
									hospUrl);
							}
						
						} catch (Exception e) {
							e.printStackTrace();
							//keywords.dataComparisonContains(Expected_Value_TotalRank, Actual_Value_total, "Total Number of Specialities", testreportpass, Failed_Report,hospUrl);
							if(!Expected_Value_HonorRank.equals("0")) {
							keywords.dataComparisonContains(Expected_Value_HonorRank, Actual_Value_Honor, "Honor Roll", testreportpass, Failed_Report,
									hospUrl);
							}

							continue;
						}
					
					
					Parent_Pass.appendChild(testreportpass);
					extent.endTest(testreportpass);
					extent.endTest(Parent_Pass);
					extent.endTest(Failed_Report);
				

				} catch (Exception e) {
					
					e.printStackTrace();
					Parent_Pass.appendChild(testreportpass);
					extent.endTest(testreportpass);
					extent.endTest(Parent_Pass);
					extent.endTest(Failed_Report);
					extent.flush();
					extent.close();
					
				}
			}
		}
	}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		System.out.println("Test Completed");
		extent.flush();
		extent.close();
	}

}
