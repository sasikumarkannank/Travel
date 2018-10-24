package Marlabs.Reports;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import Marlabs.Constants.Constants;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class ExtentReport {

	public static String currenttime() {
		String currtime;

		String reportpath = System.getProperty("user.dir") + "\\src\\Marlabs\\Reports\\Reports\\";
		String timenow = null;
		String timenow2 = null;
		try {
			Calendar c1 = new GregorianCalendar();
			currtime = c1.getTime().toString();
			String curtime = currtime.replace(" ", "_");
			timenow = "Test_Results_" + curtime.replace(":", ".");
			System.out.println("timenow: " + timenow);
			timenow2 = currtime.replace(":", "");
			String[] testdata = timenow.split(" ");
			// System.out.println(testdata[3]);
			File dir = new File(reportpath + "\\" + timenow);
			dir.mkdir();
		} catch (Exception e) {
			System.out.println(e);
		}
		return timenow;
	}

	static String reportLocation = System.getProperty("user.dir") + "\\src\\Marlabs\\Reports\\Reports\\";
	static String imageLocation = "images/";
	static String time = ExtentReport.currenttime();

	// This method for Extent report creation of browsers
	public static ExtentReports startReport(String result) {

		String name = null;
		if (result.toString().contains(Constants.Sheetname)) {
			name = Constants.Sheetname;
		}

		ExtentReports extent = new ExtentReports(reportLocation + time + "\\" + name + "Report.html", false);

		extent.config().documentTitle("US News Automation test execution report");

		extent.config().reportHeadline("<b>US News Automation test execution report</b>");

		extent.config().reportName("Report");

		return extent;
	}

	// Take scrennshot
	public static String createScreenshot(WebDriver driver) {

		UUID uuid = UUID.randomUUID();

		// generate screenshot as a file object
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// copy file object to designated location
			FileUtils.copyFile(scrFile, new File(reportLocation + imageLocation + uuid + ".png"));

		} catch (Exception e) {
			System.out.println("Error while generating screenshot:\n" + e.toString());
		}
		return reportLocation + imageLocation + uuid + ".png";
	}
}
