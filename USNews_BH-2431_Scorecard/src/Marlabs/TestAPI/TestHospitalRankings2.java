package Marlabs.TestAPI;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Marlabs.Constants.Constants;
import Marlabs.Core.BaseClass;
import Marlabs.Core.Keywords;
import Marlabs.Core.Xls_Reader;
import Marlabs.Reports.ExtentReport;

public class TestHospitalRankings2 {

	static Keywords keywords = new Keywords();

	public static void main(String[] args) {

		try {
			Xls_Reader reader= new Xls_Reader(Constants.TestSuitePath_OE);
			System.out.println(reader.getSheetCount());
			System.setProperty("webdriver.chrome.driver", ".//chromedriver.exe");
			WebDriver driver= new ChromeDriver();
			driver.manage().window().maximize();
			/*for(int sheet=0;sheet<reader.getSheetCount();sheet++) {*/
				
				//Constants.Sheetname=reader.GetSheetName(Constants.Sheetname);
				String Actual_Value_score=null;
				String Expected_Value_score=null;
				String Actual_Value_rank=null;
				String Expected_Value_rank=null;
				String finaldata_score=null;
				ExtentReports extent = ExtentReport.startReport(Constants.Sheetname);
				int rows = reader.getRowCount(Constants.Sheetname);
				int totalurls = rows;
				System.out.println("No of rows: " + totalurls);

				BaseClass.storePcRankingsData(reader);
				//reader.readOverAllScore();

				ExtentTest Parent_Pass = extent.startTest("Passed Data");
				ExtentTest Failed_Report = extent.startTest("Failed Data");
				ExtentTest testreportpass=null;

				//to remove the console warnings
				LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log","org.apache.commons.logging.impl.NoOpLog");
				java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
				java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

				/*HtmlUnitDriver driver= new HtmlUnitDriver();
				  driver.setJavascriptEnabled(true);*/
				String hospUrl=getUrl(Constants.Sheetname);		

				URL baseUrl = new URL(hospUrl);
				HttpURLConnection connection_baseURL = (HttpURLConnection) baseUrl.openConnection();
				int statusCode_baseURL = connection_baseURL.getResponseCode();
				System.out.println(statusCode_baseURL);

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


					try {
						
						driver.get(hospUrl);
						Thread.sleep(2000);
						List<WebElement> list_rank = null;
						List<WebElement> list_score = null;
						int size=Constants.multiMapForpcAdultRankings.size();
						int scrollTimes=(size%10==0) ? (size/10): (size/10)+1;	
						System.out.println("no of scrolling time is "+scrollTimes);
						for(int k=1;k<scrollTimes;k++){

							if(driver.findElements(By.xpath("//a[@data-js-id='load-more-button']")).size()>0)
							{

								//Wait for the next button
								WebDriverWait wait = new WebDriverWait(driver, 10);

								WebElement click_button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@data-js-id='load-more-button']")));
								Thread.sleep(2000);
								//Click next button
								click_button.click();

								//System.out.println("Clicked");
								Thread.sleep(20000);
							}

						}
						list_rank = driver.findElements(By.xpath("//span[@data-test-id='ranking']"));

						list_score = driver.findElements(By.xpath("//dt[@data-test-id='ranking_score']"));



						//System.out.println("Ranking list size "+list_rank.size());
						//System.out.println("Score list size "+list_score.size());
						int z=0;
						int y=1;
						int x=1;
						int s=2;
						for(int i=0;i<list_rank.size();i++) {
							try {
								ArrayList<String> dataList = BaseClass.getObjectForDataValueOnlineEducation(reader.getCellData(Constants.Sheetname, "Testing URL", s++));
								testreportpass = extent.startTest("Hospitals : " + driver.findElement(By.xpath("(//h3[@class='block-flush heading-large'])["+(x++)+"]")).getText());
								Actual_Value_rank=list_rank.get(i).getText();
								//System.out.println("Actual Value rank"+Actual_Value_rank);

								Expected_Value_rank = dataList.get(z);
								//System.out.println("Expected Value rank "+Expected_Value_rank);

								keywords.dataComparisonContains(Expected_Value_rank, Actual_Value_rank, Constants.Sheetname+" Rank", testreportpass,Failed_Report, hospUrl);
								if(i<list_score.size()) {

									Actual_Value_score=list_score.get(i).getText();
									//System.out.println("Actual Value Score "+Actual_Value_score);

									Expected_Value_score = dataList.get(y);
									//System.out.println("Expected Value score "+Expected_Value_score);


									if (Actual_Value_score.contains("/") ) {

										finaldata_score = Actual_Value_score.replaceAll("/.*", "");
										if( finaldata_score.equals("100.0")) {
											finaldata_score = "100";
										}
										if (Expected_Value_score.length()>finaldata_score.length()) {
											Expected_Value_score=convertToDouble(Expected_Value_score);
										}
										keywords.dataComparisonContains(Expected_Value_score, finaldata_score, Constants.Sheetname+" Score", testreportpass, Failed_Report,
												hospUrl);

									} else {
										keywords.dataComparisonContains(Expected_Value_score, Actual_Value_score,Constants.Sheetname+" Score", testreportpass,Failed_Report, hospUrl);
									}
								}

								
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							Parent_Pass.appendChild(testreportpass);
							extent.endTest(testreportpass);
						}

					}
					catch (Exception e) 
					{
						e.printStackTrace();
						keywords.dataComparisonContains(Expected_Value_score, Actual_Value_score, Constants.Sheetname+" Score", testreportpass, Failed_Report,
								hospUrl);
					}

					extent.endTest(Parent_Pass);
					extent.endTest(Failed_Report);

				}
				System.out.println("Test Completed");
				extent.flush();
				extent.close();
				Constants.multiMapForpcAdultRankings.clear();
			//}
		}
		catch (Exception e) {
			Constants.multiMapForpcAdultRankings.clear();
		}

	}
	public static String  convertToDouble(String value2) {
		double value = Double.parseDouble(value2);
		DecimalFormat f = new DecimalFormat("##.#");
		return f.format(value);

	}

	public static String  getUrl(String sheetName) {

		String URL=null;
		switch(sheetName) {		
		
		case "Nephrology":URL="https://health-uat3.usnews.com/best-hospitals/rankings/nephrology";
		break;
		case "Neurology":URL="https://health-uat3.usnews.com/best-hospitals/rankings/neurology-and-neurosurgery";
		break;
		case "Orthopedics":URL="https://health-uat3.usnews.com/best-hospitals/rankings/orthopedics";
		break;
		case "Pulmonology":	URL="https://health-uat3.usnews.com/best-hospitals/rankings/pulmonology";
		break;
		case "Urology":URL="https://health-uat3.usnews.com/best-hospitals/rankings/urology";
		break;
		case "Ophthalmology":URL="https://health-uat3.usnews.com/best-hospitals/rankings/ophthalmology";
		break;
		case "Psychiatry":URL="https://health-uat3.usnews.com/best-hospitals/rankings/psychiatry";
		break;
		case "Rehabilitation":URL="https://health-uat3.usnews.com/best-hospitals/rankings/rehabilitation";
		break;
		case "Rheumatology":URL="https://health-uat3.usnews.com/best-hospitals/rankings/rheumatology";
		break;

		}
		return URL;
	}
}

