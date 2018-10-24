package Marlabs.TestAPI;

import java.util.ArrayList;
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

public class TestHighschools extends Keywords {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// try{
		Keywords keywords = new Keywords();
		String HOSP_URL = null;

		Xls_Reader getdata_Pages = new Xls_Reader(Constants.TestSuitePath_OE);
		int numOfPages = getdata_Pages.getRowCount(Constants.Sheetname);
		System.out.println("No of pages: " + numOfPages);
		int Columncount = getdata_Pages.getColCount(Constants.Sheetname);

		BaseClass.storeObjectOnlineEducation();

		ExtentReports extent = ExtentReport.startReport("Result");
		ExtentTest Parent_Pass = extent.startTest("Passed Data");
		ExtentTest Failed_Report = extent.startTest("Failed Data");
		ExtentTest testreportSkip = extent.startTest("Skipped Data");
		ExtentTest FatalErrors_Report = extent.startTest("Fatal Errors");

		// Loop for Online Education colleges URL (numOfPages)
		for (int k = 2; k <4; k++) {
			String hossp_id = getdata_Pages.getCellData(Constants.Sheetname, "aha_id", k);
			HOSP_URL="https://health-uat4.usnews.com/best-hospitals/area/pr/hospital-san-francisco-"+hossp_id+"/knee-replacement?renderer=json"; 
			ExtentTest testreport = extent.startTest("Hosptal URL Schools: " + HOSP_URL);

			ArrayList<String> dataValue = BaseClass.getObjectForDataValueOnlineEducation(HOSP_URL);

			String URL = HOSP_URL ;
			System.out.println(k + ": " + "URL : " + URL);

			
			// to work on
			//Response res = get(URL);
			WebDriver driver=new HtmlUnitDriver();
			driver.get(HOSP_URL);
			

			//Object document = Configuration.defaultConfiguration().jsonProvider().parse(res.asString());

			// For getting the column name from excel sheet (Try catch)
			try {

				// Initialise the excel reader for Object repository
				Marlabs.Core.Xls_Reader excel = new Marlabs.Core.Xls_Reader(Constants.TestSuitePath_OE);
				ArrayList al = new ArrayList();

				// Loop to add columns in expected data sheet to array list(Must
				// enter column count)
				for (int l = 0; l <= Columncount - 1; l++) {

					String col_data = excel.getCellData(Constants.Sheetname, l, 1);
					al.add(col_data);
				}

				int column_size = al.size();
				int z = 0;
				// System.out.println("column_size: " + column_size);

				// Loop to fetch column name from array list and Expected value
				// with respect to column name
				for (z = 3; z <= Columncount - 1; z++) {

					String colName = al.get(z).toString();
					String Expected_Value = dataValue.get(z - 1).trim();
					String Xpath="//*[@data-test-id='"+colName+"']";
					String Actual_Value =null;
				
					try {
						if(Expected_Value.isEmpty()){
							keywords.dataforskip(colName,testreport, testreportSkip, HOSP_URL);
						}
							
								/*WebElement ele=driver.findElement(By.xpath(Xpath));
								ele.isDisplayed();*/
						Actual_Value=driver.findElement(By.xpath(Xpath)).getText();

						if (colName.contains("ccd_member")||colName.contains("district_number_of_students_enrolled")){
							if (Actual_Value!=null){
							Actual_Value=Actual_Value.replace(",", "");
							Actual_Value=Actual_Value.replace(" ", "");	
												
						}
							}
						
	
						
						
						keywords.dataComparisonContains(Expected_Value, Actual_Value, colName, testreport,
								Failed_Report, HOSP_URL);
					}
						catch (Exception e) {
						//String Actual_Value1=driver.findElement(By.xpath(Xpath)).getText();

					keywords.dataComparisonContains(Expected_Value, Actual_Value, colName, testreport,
					Failed_Report, HOSP_URL);
						continue;
					}
				}
				Parent_Pass.appendChild(testreport);
				extent.endTest(testreport);
				extent.endTest(Parent_Pass);
				extent.endTest(Failed_Report);
				extent.endTest(testreportSkip);
				extent.endTest(FatalErrors_Report);

			} catch (Exception e) {
				e.printStackTrace();
				Parent_Pass.appendChild(testreport);
				extent.endTest(testreport);
				extent.endTest(Parent_Pass);
				extent.endTest(Failed_Report);
				extent.endTest(testreportSkip);
				extent.endTest(FatalErrors_Report);
				extent.close();
			}

		}

		System.out.println("Test Completed");
		extent.close();
	}
	// }
	// catch(Exception e){

	// }
}
