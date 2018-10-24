package Marlabs.TestAPI;

import java.util.ArrayList;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import Marlabs.Constants.Constants;
import Marlabs.Core.BaseClass;
import Marlabs.Core.Keywords;
import Marlabs.Core.Xls_Reader;
import Marlabs.Reports.ExtentReport;

public class TestHospitalsDashboard extends Keywords {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// try{
		Keywords keywords = new Keywords();
		String HOSP_URL = null;

		Xls_Reader getdata_Pages = new Xls_Reader(Constants.TestSuitePath_OE);
		int numOfPages = getdata_Pages.getRowCount(Constants.Sheetname);
		int totalurls=numOfPages;
		System.out.println("No of pages: " +totalurls );
		int Columncount = getdata_Pages.getColCount(Constants.Sheetname);

		BaseClass.storeObjectOnlineEducation();

		ExtentReports extent = ExtentReport.startReport("Result");
		ExtentTest Parent_Pass = extent.startTest("Passed Data");
		ExtentTest Failed_Report = extent.startTest("Failed Data");
		ExtentTest testreportSkip = extent.startTest("Skipped Data");
		//ExtentTest FatalErrors_Report = extent.startTest("Fatal Errors");*/
		ExtentTest testreport = null;
		String Actual_Value =null;
		String colName=null;
		String col_data=null;
		String Expected_Value =null;
		String Xpath=null;
		ArrayList<String> ColAttributeName= new ArrayList<>();
		String AttributeName=null;
		//System.out.println("Add col_data "+al.size());
		for (int l = 0; l <= Columncount - 1; l++) {

			String coldataName = getdata_Pages.getCellData(Constants.Sheetname, l, 1);
			ColAttributeName.add(coldataName);
			
		}
		// Loop for Online Education colleges URL (numOfPages)
		for (int k = 3000; k <4500; k++) {
			HOSP_URL = getdata_Pages.getCellData(Constants.Sheetname, "Testing URL", k);

			ArrayList<String> dataValue = BaseClass.getObjectForDataValueOnlineEducation(HOSP_URL);

			String URL = HOSP_URL ;
			
			System.out.println(k + ": " + "URL : " + URL);

			// to work on
			//Response res = get(URL);

			//to remove console warnings of htmlunit
			LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
			java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
			java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

			WebDriver driver=new HtmlUnitDriver();

			driver.get(HOSP_URL);

			/*driver.findElement(By.name("login")).sendKeys("ITS-MIS@usnews.com");
			driver.findElement(By.name("password")).sendKeys("?93is?*nJhMeyGO");
			driver.findElement(By.name("form.submitted")).click();*/

			//to choose year 2017-2018
			/*WebElement element=driver.findElement(By.xpath("(//div[@class='hospitalDrop'])[2]"));
			Actions action = new Actions(driver);
			action.moveToElement(element).moveToElement(driver.findElement(By.xpath("(//a[@class='hospitalDrop_a t'])[3]")));
			action.click().build().perform();

			Thread.sleep(2000);*/

			// For getting the column name from excel sheet (Try catch)
			try {
				//comment by koushik 21/05/18
				//if Adult tab is visible in page
				/*if(driver.findElements(By.xpath("//a[@href='#adult-tab']")).size()!=0)
				{
					driver.findElement(By.xpath("//a[@href='#child-tab']")).click();
				}*/

				//directly looking at pediatric tab
				// Initialise the excel reader for Object repository
				Marlabs.Core.Xls_Reader excel = new Marlabs.Core.Xls_Reader(Constants.TestSuitePath_OE);
				ArrayList al = new ArrayList();

				// Loop to add columns in expected data sheet to array list(Must
				// enter column count)
				for (int l = 0; l <= Columncount - 1; l++) {

					col_data = excel.getCellData(Constants.Sheetname, l, 2);
					al.add(col_data);

				}

				//int column_size = al.size();
				int z = 0;
				// System.out.println("column_size: " + column_size);

				// Loop to fetch column name from array list and Expected value
				// with respect to column name
				/*if(driver.findElements(By.xpath("//a[@href='#adult-tab']")).size()>0) {*/
				testreport=extent.startTest("Hospitals URL: " + HOSP_URL);
				for (z = 2; z <= Columncount - 1; z++) {

					colName = al.get(z).toString();
					AttributeName= ColAttributeName.get(z).trim();
					Expected_Value = dataValue.get(z).trim();
					//System.out.println(AttributeName+"-->"+colName+"-->"+Expected_Value);
									
					try {
						if(AttributeName.contains("Number Of Ranked Adult Specialties")){
							if(Expected_Value.equals("0")) {
							//System.out.println("Inside Number of specilities for skip");
							keywords.dataforskip(AttributeName,testreport, testreportSkip, HOSP_URL);
							
							}
							else 
							{
								//System.out.println("Inside Number of specilities for data");
								Xpath=colName;
							}
							}else {
								Xpath="//*[@data-test-id='"+colName+"']";
							}

						if(Expected_Value.equals("Ineligible")|| Expected_Value.equals("No Scorecard")){
							
							keywords.dataforskip(AttributeName,testreport, testreportSkip, HOSP_URL);
							
						}
						if(Expected_Value.equals("Unranked")){
							
							Expected_Value="Not Ranked";
						
						}
						if(Expected_Value.equals("Insufficient Volume")){
							
							Expected_Value="Not rated";
						
						}
						if(AttributeName.equals("State Rank") && Expected_Value.equals(".")){
							
							keywords.dataforskip(AttributeName,testreport, testreportSkip, HOSP_URL);
							
						}
						if(AttributeName.equals("Metro Rank") && Expected_Value.equals(".")){
							
							keywords.dataforskip(AttributeName,testreport, testreportSkip, HOSP_URL);
							
						}
						//System.out.println(AttributeName+"-->"+Xpath);
						if(Xpath!=null  && driver.findElements(By.xpath(Xpath)).size()>0) {
							
						Actual_Value=driver.findElement(By.xpath(Xpath)).getText();
						
						if(Actual_Value.contains("#")) {
							Actual_Value = Actual_Value.replaceAll("#", "");
						}
						
						keywords.dataComparisonContains(Expected_Value, Actual_Value, AttributeName, testreport,
								Failed_Report, HOSP_URL);

					}
					}
					catch (Exception e) {
						
						//String Actual_Value1=driver.findElement(By.xpath(Xpath)).getText();
						keywords.dataComparisonContains(Expected_Value, Actual_Value, AttributeName, testreport,
								Failed_Report, HOSP_URL);
						continue;
					}finally {
						Xpath=null;
					}
				}
				Parent_Pass.appendChild(testreport);
				extent.endTest(testreport);
				extent.endTest(Parent_Pass);
				extent.endTest(Failed_Report);
				extent.endTest(testreportSkip);
				/*extent.endTest(FatalErrors_Report);*/
				//}


			} catch (Exception e) {
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
	// }
	// catch(Exception e){

	// }
}
