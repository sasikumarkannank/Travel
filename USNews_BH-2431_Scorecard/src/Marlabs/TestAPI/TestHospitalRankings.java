package Marlabs.TestAPI;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Marlabs.Constants.Constants;
import Marlabs.Core.BaseClass;
import Marlabs.Core.Keywords;
import Marlabs.Core.Xls_Reader;
import Marlabs.Reports.ExtentReport;

public class TestHospitalRankings {
	
	static Keywords keywords = new Keywords();
	public static void main(String[] args) {

		String Actual_Value = null;
		String finaldata = null;
		WebElement element=null;
		String URL =null;
		String Xpath=null;
		ExtentReports extent = ExtentReport.startReport("Result");
		try {
			
			Xls_Reader reader= new Xls_Reader(Constants.TestSuitePath_OE);
			int rows = reader.getRowCount(Constants.Sheetname);
			int totalurls = rows;
			System.out.println("No of rows: " + totalurls);
			int Columncount = reader.getColCount(Constants.Sheetname);
			ArrayList<String> ColAttributeName= new ArrayList<>();
			
			BaseClass.storePcRankingsData(reader);

			
			ExtentTest Parent_Pass = extent.startTest("Passed Data");
			ExtentTest Failed_Report = extent.startTest("Failed Data");
			
			
			//to remove the console warnings
			LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
			java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
			java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
		
			
		    
			//String hospid=null;
			String hospUrl=null;
			ArrayList<String> al = new ArrayList<String>();
			for (int l = 0; l < Columncount; l++) {

				String col_data = reader.getCellData(Constants.Sheetname, l, 2);
				
				al.add(col_data);
				
			}
			//System.out.println("Add col_data "+al.size());
			for (int l = 0; l < Columncount ; l++) {

				String col_data = reader.getCellData(Constants.Sheetname, l, 1);
				ColAttributeName.add(col_data);
			}
			ArrayList<String> dataValue=null;
			for (int k =3; k <2200; k++) {
			
			
					hospUrl=reader.getCellData(Constants.Sheetname, "Testing URL", k);
					dataValue =BaseClass.getObjectForDataValueOnlineEducation(hospUrl);
					URL= hospUrl;
					System.out.println(k + ": " + "URL : " + URL);
					
					
					
					URL baseUrl = new URL(hospUrl);
					HttpURLConnection connection_baseURL = (HttpURLConnection) baseUrl.openConnection();
					int statusCode_baseURL = connection_baseURL.getResponseCode();
									
					if (statusCode_baseURL == 404) {
						ExtentTest FatalErrors_Report = extent.startTest("Fatal Errors");
						FatalErrors_Report.log(LogStatus.FATAL,
								"<span style='font-weight:bold;color:blue'>" + "" + "URL: " + URL + "</br></span>"
										+ "Response code:<span style='font-weight:bold; font-size:large;color:red'>"
										+ statusCode_baseURL + "</span>");
						extent.endTest(FatalErrors_Report);
					}
					
					if(statusCode_baseURL==200)
					{
						HtmlUnitDriver driver=new HtmlUnitDriver();
						driver.get(URL);
						driver.setJavascriptEnabled(true);
						Thread.sleep(1000);
						ExtentTest testreportpass = extent.startTest("Hospitals : " + hospUrl);
						
					try {

											
					//	int column_size = al.size();
						int z = 0;
						String Expected_Value=null;
						
						// Loop to fetch column name from array list and Expected value
						// with respect to column name
						// change the column number as to compare data with we
						
						for (z = 2; z < Columncount; z++) {
							
							String attributeName=ColAttributeName.get(z).toString();
							
							String colName = al.get(z).toString();
							
							//System.out.println("Col Value is "+colName );
							//System.out.println(dataValue);
							Expected_Value = dataValue.get(z).trim();
							
							
							
								Xpath= "//*[@data-test-id='"+colName+"']";		
							
						

							try {
								if(colName.contains("measure-intens")||colName.contains("measure-niv_flag10pct")) //specify the column for which we have tick
								{
									//sSystem.out.println("col Na,me is "+colName);
									String xpathtick="//*[@class='icon pro' and @data-test-id='"+colName+"']";
									if(driver.findElements(By.xpath(xpathtick)).size()>0){
										Actual_Value="Yes";
										keywords.dataComparisonContains(Expected_Value, Actual_Value, colName, testreportpass, Failed_Report,
												hospUrl);
										}
									else 
									{
										Actual_Value="No";
										keywords.dataComparisonContains(Expected_Value, Actual_Value, attributeName, testreportpass, Failed_Report,
												hospUrl);
									}
								} 
								else{
									
									if(driver.findElements(By.xpath(Xpath)).size()>0) {
										
									element = driver.findElement(By.xpath(Xpath));
									Actual_Value=(String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML", element);
									//Actual_Value=element.getText();
									finaldata = Actual_Value.replaceAll("/.*", "");
									Expected_Value=(Expected_Value.length() > finaldata.length())?convertToDouble(Expected_Value):Expected_Value;

									if (finaldata.contains("%")) {
										
										finaldata = Actual_Value.replaceAll("%", "");
										keywords.dataComparisonContains(Expected_Value, finaldata, attributeName, testreportpass, Failed_Report,
												hospUrl);
										
									} else {
										keywords.dataComparisonContains(Expected_Value, finaldata, attributeName, testreportpass,
												Failed_Report, hospUrl);
										
									}
									
									}
									
							}
							} catch (Exception e) {
							e.printStackTrace();
								keywords.dataComparisonContains(Expected_Value, Actual_Value, attributeName, testreportpass, Failed_Report,
										hospUrl);
								
								continue;
							}
						
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
	
	public static String  convertToDouble(String value2) {
		double value = Double.parseDouble(value2);
		DecimalFormat f = new DecimalFormat("##.#");
		return f.format(value);

	}

}