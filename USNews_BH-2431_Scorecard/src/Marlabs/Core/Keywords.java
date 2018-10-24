package Marlabs.Core;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.ExtentTestInterruptedException;
import com.relevantcodes.extentreports.LogStatus;

public class Keywords {

	// --------------- Compare the data and report ---------------
	public void dataComparisonContains(String ExpectedData, String ActualData, String nameForReport,
			ExtentTest testreport, ExtentTest testreportFail, String HOSP_URL) throws Exception {
		Keywords keywords = new Keywords();

		try {
			if (ExpectedData.length() > 0 && ActualData!=null) 
			{
				
				if (ActualData.toLowerCase().contains(ExpectedData.toLowerCase())
						|| ExpectedData.toLowerCase().contains(ActualData.toLowerCase())) {
					testreport.log(LogStatus.PASS, keywords.resultPASS(ExpectedData, ActualData, nameForReport));
				} else {
					testreportFail.log(LogStatus.FAIL,
							keywords.resultFAILNEW(HOSP_URL, ExpectedData, ActualData, nameForReport));
					
				}
			}
			
		} catch (Exception e) {
			testreportFail.log(LogStatus.FAIL,
					keywords.resultFAILNEW(HOSP_URL, ExpectedData, ActualData, nameForReport));

		}
	}
	
	public void dataforskip(String nameForReport,
			ExtentTest testreport, ExtentTest testreportSkip, String HOSP_URL) throws Exception {
		Keywords keywords = new Keywords();
		try{
			testreportSkip.log(LogStatus.SKIP,
					keywords.resultSKIP(HOSP_URL,  nameForReport));
		}
		catch(ExtentTestInterruptedException e)
		{
			testreportSkip.log(LogStatus.SKIP,
					keywords.resultSKIP(HOSP_URL,  nameForReport));
		}
		}
	

	
	
	public String resultPASS(String Expected_data, String Actual_data, String data) throws Exception {

		/*if(Actual_data.contains("/")){
			String finaldata=Actual_data.replaceAll("/.*", "");
			String text = "<span style='font-weight:bold;color:black'>" + "Expected " + data + ": " + Expected_data
					+ "</br></span>" + "<span style='font-weight:bold;color:black'>" + "Actual " + data + ": " + finaldata
					+ "</br></span>";
			return text;
		}*/
		
		String text = "<span style='font-weight:bold;color:black'>" + "Expected " + data + ": " + Expected_data
				+ "</br></span>" + "<span style='font-weight:bold;color:black'>" + "Actual " + data + ": " + Actual_data
				+ "</br></span>";
		return text;
		
	}

	public String resultFAIL(String Expected_data, String Actual_data, String data) throws Exception {

		String text = "<span style='font-weight:bold;color:black'>" + "Expected " + data + ": " + Expected_data
				+ "</br></span>" + "<span style='font-weight:bold;color:red'>" + "Actual " + data + ": " + Actual_data
				+ "</br></span>";
		return text;
	}

	public String resultFAILNEW(String Hosp_URL, String Expected_data, String Actual_data, String data)
			throws Exception {

		/*if(Actual_data.contains("/")){
			String finaldata=Actual_data.replaceAll("/.*", "");
			String text = "<span style='font-weight:bold;color:blue'>" + Hosp_URL + "</br></span>"
					+ "<span style='font-weight:bold;color:black'>" + "Expected " + data + ": " + Expected_data
					+ "</br></span>" + "<span style='font-weight:bold;color:red'>" + "Actual " + data + ": " + finaldata
					+ "</br></span>";
			return text;
		}*/
		
		String text = "<span style='font-weight:bold;color:blue'>" + Hosp_URL + "</br></span>"
				+ "<span style='font-weight:bold;color:black'>" + "Expected " + data + ": " + Expected_data
				+ "</br></span>" + "<span style='font-weight:bold;color:red'>" + "Actual " + data + ": " + Actual_data
				+ "</br></span>";
		return text;
		
	}
	
	public String resultSKIP(String Hosp_URL, String data)
			throws Exception {

		String text = "<span style='font-weight:bold;color:blue'>" + Hosp_URL + "</br></span>"
				+ "<span style='font-weight:bold;color:black'>"  + data 
				+ "</br></span>";
		return text;
	}

	public String resultFailStatusCode(String Hosp_URL, int statusCode_baseURL)
			throws Exception {

		String text = "<span style='font-weight:bold;color:blue'>" + Hosp_URL + "</br></span>"
				+ "<span style='font-weight:bold;color:black'>"  + statusCode_baseURL 
				+ "</br></span>";
		return text;
	}
}
