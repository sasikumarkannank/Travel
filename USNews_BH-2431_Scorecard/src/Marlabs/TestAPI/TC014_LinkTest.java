package Marlabs.UsNews.LinkTest;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Marlabs.Constants.Constants;
import Marlabs.Core.Xls_Reader;
import Marlabs.Reports.ExtentReport;

public class TC014_LinkTest {
	public static Xls_Reader excel = null;
	public static Xls_Reader getData = null;
	public static int k;
	public static String sub_URL = null;
	public static int numOfScenarios = 0;
	public static String execStatus = null;
	public static String page_Name;
	public static String URL;

	public static void test(WebDriver driver) throws Exception {
		ExtentReports extent = ExtentReport.startReport(driver);
		ExtentTest testreport_passed = extent.startTest("Passed Data");
		ExtentTest testreport_Failed = extent.startTest("Failed Data");
		// To get the subURL
		Xls_Reader getdata_subURL = new Xls_Reader(Constants.TestSuitePath_LinkTest);
		numOfScenarios = getdata_subURL.getRowCount(Constants.Mainsheet);
		System.out.println("No of Scenarios: " + numOfScenarios);
		try {
			for (k = 1; k <= numOfScenarios; k++) {
				execStatus = getdata_subURL.getCellData(Constants.Mainsheet, 2, k);
				System.out.println("Execution Status: " + execStatus);
				if (execStatus.equalsIgnoreCase("Yes")) {
					sub_URL = getdata_subURL.getCellData(Constants.Mainsheet, 1, k);
					page_Name = getdata_subURL.getCellData(Constants.Mainsheet, 0, k);
					System.out.println("Page Name: " + page_Name);

					//URL = Constants.currentURL.concat(sub_URL);
					URL = sub_URL;
					System.out.println("URL: " + URL);
					// Initialize by getting the main URL
					driver.get(URL);
					String actual_linktext=driver.getTitle();
					System.out.println("Page Title : " +actual_linktext);
				try {
						URL baseUrl = new URL(URL);
						HttpURLConnection connection_baseURL = (HttpURLConnection) baseUrl.openConnection();
						int statusCode_baseURL = connection_baseURL.getResponseCode();
						System.out.println(statusCode_baseURL);
						String msg_baseURl = connection_baseURL.getResponseMessage();
						System.out.println(msg_baseURl);
						if (statusCode_baseURL == 200) {
							
							/*if(baseUrl.getProtocol().equals("https"))
								{*/
								if(driver.findElements(By.tagName("a")).size()>0){
				
								List<WebElement> link_counter = driver.findElements(By.tagName("a"));
								int count = link_counter.size();
								System.out.println("No of links  " + count);
								String links_href=null;
								for (WebElement element: link_counter) {
									String actual_linktext1 = element.getText();
									System.out.println("Actual Link: " + actual_linktext1);
							
								try {
									 links_href =element.getAttribute("href");
									System.out.println(": Actual URL: " + links_href);
									
									URL url = new URL(links_href);
									HttpURLConnection connection = (HttpURLConnection) url.openConnection();
									int statusCode = connection.getResponseCode();
									System.out.println(statusCode);
									String msg = connection.getResponseMessage();
									System.out.println(msg);
									
									
									System.out.println(connection.getContentType());
									System.out.println(url.getProtocol());
									
									if (statusCode == 200 ) {
										if(url.getProtocol().equals("https"))
										{
										System.out.println("Pass");
										testreport_passed.log(LogStatus.PASS,
												"<span style='font-weight:bold;color:black'>" + actual_linktext + "</br></span>"
														+ "<span style='font-weight:bold;color:blue'>" + "" + " https: "
														+ links_href +"</br></span>"
														+ "Response code:<span style='font-weight:bold; font-size:large;color:green'>"
														+ statusCode + "</span>");
										
										}
										else 
										{
											testreport_Failed.log(LogStatus.FAIL,
													"<span style='font-weight:bold;color:black'>" + actual_linktext + "</br></span>"
															+ "<span style='font-weight:bold;color:blue'> http only: " + links_href
															+ " </br></span>"
															+ "<span style='font-weight:bold; font-size:large;color:red'>Response:"
															+ statusCode + "</span>");
										}
									
									} else if (statusCode == 301) {
										System.out.println("External links with statusCode 301");
									} else {
										System.out.println("Fail");
										testreport_Failed.log(LogStatus.FAIL,
												"<span style='font-weight:bold;color:black'>" + actual_linktext + "</br></span>"
														+ "<span style='font-weight:bold;color:blue'>Actual_URL: " + links_href
														+ " </br></span>"
														+ "<span style='font-weight:bold; font-size:large;color:red'>Response:"
														+ statusCode + "</span>");
									}
								}					
									
								 catch (Exception e) {
									System.out.println("Status code out of range");
									testreport_Failed.log(LogStatus.FAIL,
											"<span style='font-weight:bold;color:Red'>" + actual_linktext1 + "</br></span>"
													+ "<span style='font-weight:bold;color:Red'>Actual_URL: " + links_href
													+ " </br></span>" + "</span>");
									continue;
								}
								}
																		
													
							}
								System.out.println("Pass");
								testreport_passed.log(LogStatus.PASS,
										"<span style='font-weight:bold;color:black'>" + page_Name + "</br></span>"
												+ "<span style='font-weight:bold;color:blue'>" + "" + " https: "
												+ URL +"</br></span>"
												+ "Response code:<span style='font-weight:bold; font-size:large;color:green'>"
												+ statusCode_baseURL + "</span>");
							//}
							
							/*else 
							{
								testreport_Failed.log(LogStatus.FAIL,
										"<span style='font-weight:bold;color:black'>" + page_Name + "</br></span>"
												+ "<span style='font-weight:bold;color:blue'> http only: " + URL
												+ " </br></span>"
												+ "<span style='font-weight:bold; font-size:large;color:red'>Response:"
												+ statusCode_baseURL + "</span>");
							}*/
						
						continue;

						}else{
							testreport_Failed.log(LogStatus.FAIL,
									"<span style='font-weight:bold;color:black'>" + page_Name + "</br></span>"
											+ "<span style='font-weight:bold;color:blue'> http only: " + URL
											+ " </br></span>"
											+ "<span style='font-weight:bold; font-size:large;color:red'>Response:"
											+ statusCode_baseURL + "</span>");
						}
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("status code of out range");
						
					}
									}

			}
			extent.endTest(testreport_passed);
			extent.endTest(testreport_Failed);
			extent.close();
			System.out.println("Completed");
		}catch(	Exception e)
	{
		System.out.println(e);
		extent.endTest(testreport_passed);
		extent.endTest(testreport_Failed);
		extent.close();
		driver.quit();
		System.out.println("Execution aborted");
		System.out.println("Execution Completed");
	}
}
}
