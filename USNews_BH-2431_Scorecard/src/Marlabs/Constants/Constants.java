package Marlabs.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.map.MultiValueMap;

//import Marlabs.Core.Xls_Reader;
public abstract class Constants {

	// Online Education Bachelors Jira ID: OE-1969
	public static String TestSuiteName_HospitalDashboardRankings = "Pulmonology.xlsx";
	public static String TestSuitePath_OE = System.getProperty("user.dir")
			+ "\\src\\Marlabs\\Data\\BestHospitals\\" + TestSuiteName_HospitalDashboardRankings;
	public static String Sheetname = "CHF";

	// Properties file path
	public static String PropertiesFilePath = System.getProperty("user.dir")
			+ "\\src\\Marlabs\\Data\\Procedure_data.properties";

	// Multimap Section------------------

	// Online education
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static MultiMap<String, ?> multiMapForOnlineEducation = new MultiValueMap();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static MultiMap<String, ?> multiMapForpcAdultRankings = new MultiValueMap();
	
	
	public static String TestSuiteName_HighSchools = "Footnotes for SC schools - 5-23-2017.xlsx";
	public static String TestSuitePath_HighSchools = System.getProperty("user.dir") + "\\src\\Marlabs\\Data\\Highschools\\"
			+ TestSuiteName_HighSchools;
	
	public static String TestSuiteSheetname_HighSchools = "Cancer";
	public static ArrayList<String>multimapHighSchools = new ArrayList<>();
	public static String NavURL="https://www-uat2.usnews.com/education/best-high-schools/south-carolina/districts/abbeville-60/dixie-high-";


}
