package Marlabs.TestAPI;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Marlabs.Constants.Constants;
import Marlabs.Core.BaseClass;
import Marlabs.Core.Keywords;
import Marlabs.Core.Xls_Reader;
import Marlabs.Reports.ExtentReport;

public class TestBestHospitals extends Keywords {

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		// try{
		Keywords keywords = new Keywords();
		String HOSP_URL = null;

		Xls_Reader getdata_Pages = new Xls_Reader(Constants.TestSuitePath_OE);
		int numOfPages = getdata_Pages.getRowCount(Constants.Sheetname);
		int totalurls = numOfPages;
		System.out.println("No of pages: " + totalurls);
		int Columncount = getdata_Pages.getColCount(Constants.Sheetname);

		BaseClass.storeObjectOnlineEducation();

		ExtentReports extent = ExtentReport.startReport("Result");
		ExtentTest Parent_Pass = extent.startTest("Passed Data");
		ExtentTest Failed_Report = extent.startTest("Failed Data");
		/* ExtentTest testreportSkip = extent.startTest("Skipped Data"); */
		

		// Loop for Online Education colleges URL (numOfPages)
		for (int k = 3; k <=90; k++) {
			HOSP_URL = getdata_Pages.getCellData(Constants.Sheetname, "Testing URL", k);
			/*ExtentTest testreport = extent.startTest("Hospitals : " + HOSP_URL);
			ExtentTest testreport2 = extent.startTest("Hospitals : " + HOSP_URL);*/

			ArrayList<String> dataValue =
			BaseClass.getObjectForDataValueOnlineEducation(HOSP_URL);

			String URL = HOSP_URL;
			System.out.println(k + ": " + "URL : " + URL);

			// to work on
			// Response res = get(URL);
			// to remove console warnings of htmlunit
			LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
			java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
			java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

			HtmlUnitDriver driver = new HtmlUnitDriver();

			driver.get(HOSP_URL);
			driver.setJavascriptEnabled(true);
			

			URL baseUrl = new URL(HOSP_URL);
			HttpURLConnection connection_baseURL = (HttpURLConnection) baseUrl.openConnection();
			int statusCode_baseURL = connection_baseURL.getResponseCode();
			System.out.println(statusCode_baseURL);

			if (statusCode_baseURL == 404) {
				ExtentTest FatalErrors_Report = extent.startTest("Fatal Errors");
				FatalErrors_Report.log(LogStatus.FATAL,
						"<span style='font-weight:bold;color:blue'>" + "" + "URL: " + URL + "</br></span>"
								+ "Response code:<span style='font-weight:bold; font-size:large;color:red'>"
								+ statusCode_baseURL + "</span>");
				extent.endTest(FatalErrors_Report);
			}

			
			// For getting the column name from excel sheet (Try catch)
			if(statusCode_baseURL==200)
			{
				ExtentTest testreportpass = extent.startTest("Hospitals : " + HOSP_URL);
				//ExtentTest testreport2 = extent.startTest("Hospitals : " + HOSP_URL);
			try {

				// directly looking at pediatric tab
				// Initialise the excel reader for Object repository
				Marlabs.Core.Xls_Reader excel = new
				Marlabs.Core.Xls_Reader(Constants.TestSuitePath_OE);
				ArrayList<String> al = new ArrayList<String>();

				// Loop to add columns in expected data sheet to array list(Must
				// enter column count)
				for (int l = 0; l <= Columncount - 1; l++) {

					String col_data = excel.getCellData(Constants.Sheetname, l, 2);
					al.add(col_data);
				}

				int column_size = al.size();
				int z = 0;
				// System.out.println("column_size: " + column_size);

				// Loop to fetch column name from array list and Expected value
				// with respect to column name
				// change the coloumn number as to comapare data with we
				for (z = 4; z <= Columncount-1; z++) {

					String colName = al.get(z).toString();
					String Expected_Value = dataValue.get(z - 1).trim();
					String Xpath = "//*[@data-test-id='" + colName + "']";
					String Actual_Value = "null";
					String finaldata = null;
					WebElement element=null;
						
					try {
						if(colName.contains("nurse_magnet") || colName.contains("icon pro") || colName.contains("measure-fact_accreditation")) //specify the column for which we have tick
						{
							String xpathtick="//*[@class='icon pro' and @data-test-id='"+colName+"']";
							if(driver.findElements(By.xpath(xpathtick)).size()>0){
								Actual_Value="1";
								keywords.dataComparisonContains(Expected_Value, Actual_Value, colName, testreportpass, Failed_Report,
										HOSP_URL);
							}
							else 
							{
								Actual_Value="0";
								keywords.dataComparisonContains(Expected_Value, Actual_Value, colName, testreportpass, Failed_Report,
										HOSP_URL);
							}
						} 
						else{
							
						element = driver.findElement(By.xpath(Xpath));
						Actual_Value=(String) ((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML", element);
						// remove after / for ex: 10/25 (here eliminating /25 at
						// to take only 10)
						if (Actual_Value.contains("/")) {
							finaldata = Actual_Value.replaceAll("/.*", "");
							keywords.dataComparisonContains(Expected_Value, finaldata, colName, testreportpass, Failed_Report,
									HOSP_URL);
						} else {
							/*
							 * keywords.dataComparisonContains(Expected_Value,
							 * Actual_Value, colName, test-report, Failed_Report,
							 * HOSP_URL);
							 */
							keywords.dataComparisonContains(Expected_Value, Actual_Value, colName, testreportpass,
									Failed_Report, HOSP_URL);
						}
					}
					} catch (Exception e) {
						//e.printStackTrace();
						keywords.dataComparisonContains(Expected_Value, Actual_Value, colName, testreportpass, Failed_Report,
								HOSP_URL);

						continue;
					}
				
				}
				Parent_Pass.appendChild(testreportpass);
				//Failed_Report.appendChild(testreport2);
				extent.endTest(testreportpass);
				//extent.endTest(testreport2);
				extent.endTest(Parent_Pass);
				extent.endTest(Failed_Report);
				/* extent.endTest(testreportSkip); */
				//extent.endTest(FatalErrors_Report);

			} catch (Exception e) {
				e.printStackTrace();
				Parent_Pass.appendChild(testreportpass);
				//Failed_Report.appendChild(testreport2);
				extent.endTest(testreportpass);
				//extent.endTest(testreport2);
				extent.endTest(Parent_Pass);
				extent.endTest(Failed_Report);
				/* extent.endTest(testreportSkip); */
				//extent.endTest(FatalErrors_Report);
				extent.flush();
				extent.close();
			}
		}
			
		}

		System.out.println("Test Completed");
		extent.flush();
		extent.close();
	}
	// }
	// catch(Exception e){

	// }
}
