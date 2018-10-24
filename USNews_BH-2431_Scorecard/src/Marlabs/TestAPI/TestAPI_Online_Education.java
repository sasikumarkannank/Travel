package Marlabs.TestAPI;

import static com.jayway.restassured.RestAssured.get;

import java.util.ArrayList;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.response.Response;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import Marlabs.Constants.Constants;
import Marlabs.Core.BaseClass;
import Marlabs.Core.Keywords;
import Marlabs.Core.Xls_Reader;
import Marlabs.Reports.ExtentReport;

public class TestAPI_Online_Education extends Keywords {

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
		ExtentTest FatalErrors_Report = extent.startTest("Fatal Errors");

		// Loop for Online Education colleges URL (numOfPages)
		for (int k = 2; k <= 5; k++) {
			HOSP_URL = getdata_Pages.getCellData(Constants.Sheetname, 1, k);
			ExtentTest testreport = extent.startTest("Online Education: " + HOSP_URL);

			ArrayList<String> dataValue = BaseClass.getObjectForDataValueOnlineEducation(HOSP_URL);

			String URL = HOSP_URL;
			System.out.println(k + ": " + "URL in JSON format: " + URL);

			Response res = get(URL);

			Object document = Configuration.defaultConfiguration().jsonProvider().parse(res.asString());

			// For getting the column name from excel sheet (Try catch)
			try {

				// Initialise the excel reader for Object repository
				Marlabs.Core.Xls_Reader excel = new Marlabs.Core.Xls_Reader(Constants.TestSuitePath_OE);
				ArrayList<String> al = new ArrayList<String>();

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
				for (z = 2; z <= Columncount - 1; z++) {

					String colName = al.get(z).toString();
					String Expected_Value = dataValue.get(z - 1).trim();

					String Actual_Value = null;
					try {
						//Actual_Value = JsonPath.read(document, "$.data.schoolData." + colName + ".rawValue");
						Actual_Value= JsonPath.read(document, "$.*.*[?(@.test-id="+colName+")].val[0]");
						//if containes Phone format
						if (colName.contains("admissions_phone")||colName.contains("finaid_phone")){
							if (Actual_Value!=null){
							Actual_Value=Actual_Value.replace("(", "");
							Actual_Value = Actual_Value.replace(")", "");
							Actual_Value=Actual_Value.replace("-", "");
							Actual_Value=Actual_Value.replace(" ", "");	
												
						}
							}
						
						keywords.dataComparisonContains(Expected_Value, Actual_Value, colName, testreport,
								Failed_Report, HOSP_URL);
					} catch (java.lang.ClassCastException e) {
						String Actual_Value1 = JsonPath.read(document, "$.data.schoolData." + colName + ".rawValue")
								.toString();
						// String Actual = Double.toString(Actual_Value1);
						keywords.dataComparisonContains(Expected_Value, Actual_Value1, colName, testreport,
								Failed_Report, HOSP_URL);
					} catch (com.jayway.jsonpath.PathNotFoundException e) {
						continue;
					}

				}
				Parent_Pass.appendChild(testreport);
				extent.endTest(testreport);
				extent.endTest(Parent_Pass);
				extent.endTest(Failed_Report);
				extent.endTest(FatalErrors_Report);
				

			} catch (Exception e) {
				e.printStackTrace();
				Parent_Pass.appendChild(testreport);
				extent.endTest(testreport);
				extent.endTest(Parent_Pass);
				extent.endTest(Failed_Report);
				extent.endTest(FatalErrors_Report);
				
				extent.close();
			}

		}

		System.out.println("Test Completed");
		extent.close();
	}
	
}
