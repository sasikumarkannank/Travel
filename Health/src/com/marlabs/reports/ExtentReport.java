package com.marlabs.reports;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;

public class ExtentReport {
	
	public static ExtentReports report=null;

	 protected void startReport(String Testcase) {
	
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		String Extent_Report_Path= "//src//com//marlabs//Extent//Reports//"+Testcase+" "+timeStamp+".html";
		 report = new ExtentReports(System.getProperty("user.dir")+Extent_Report_Path);
	}
	 
	
	 protected void endReport() {
		 report.flush();
		 report.close();

		 System.out.println("Report Ended");
	}
	
	
}
