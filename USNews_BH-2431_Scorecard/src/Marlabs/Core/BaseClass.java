package Marlabs.Core;

import java.util.ArrayList;



//import Marlabs.Bugzilla.Bugzilla;


import Marlabs.Constants.Constants;

public class BaseClass {

	// --------------- Load data from excel sheet to multimap ---------------

	public static void storeObjectOnlineEducation() {

		try {

			// Initialise the excel reader for Object repository
			Marlabs.Core.Xls_Reader excel = new Marlabs.Core.Xls_Reader(Constants.TestSuitePath_OE);
			int OR_Rowcount = excel.getRowCount(Constants.Sheetname);
			int Columncount = excel.getColCount(Constants.Sheetname);
			ArrayList<String> al = new ArrayList<String>();
			for (int k = 0; k <= Columncount; k++) {

				String col_data = excel.getCellData(Constants.Sheetname, k, 1);
				al.add(col_data);
				
			}
			
			int column_size = al.size();
			int j = 0;
			System.out.println("column_size: " + column_size);
			System.out.println("Row Count: " + OR_Rowcount);
			String Columname = null;

			for (int i = 2900; i <= 4600; i++) {

				String URL = excel.getCellData(Constants.Sheetname, "Testing URL", i);
				Constants.multiMapForOnlineEducation.put(URL, URL);
				for (j = 1; j <= column_size - 1; j++) {

					String colName = al.get(j).toString();
					Columname = excel.getCellData(Constants.Sheetname, colName, i);
					Constants.multiMapForOnlineEducation.put(URL, Columname);
				}
			}
			System.out.println("Adding Expected data for Hospitals Dashboard page completed");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void storePcRankingsData(Xls_Reader excel) {

		try {
			
			// Initialise the excel reader for Object repository
			//Marlabs.Core.Xls_Reader excel = new Marlabs.Core.Xls_Reader(Constants.TestSuitePath_OE);
			int OR_Rowcount = excel.getRowCount(Constants.Sheetname);
			int Columncount = excel.getColCount(Constants.Sheetname);
			ArrayList<String> al = new ArrayList<String>();
			
			for (int k = 2; k < Columncount; k++) {

				String col_data = excel.getCellData(Constants.Sheetname, k, 1);
				al.add(col_data);
				
			}
			
			int j = 0;
			
			String Columname = null;

			for (int i = 2; i < OR_Rowcount; i++) {

				String URL = excel.getCellData(Constants.Sheetname, "Testing URL", i);
				//Constants.multiMapForpcAdultRankings.put(URL, URL);
				for (j = 0; j < Columncount; j++) {

					//String colName = al.get(j).toString();
					
					Columname = excel.getCellData(Constants.Sheetname, j, i);
					
					Constants.multiMapForpcAdultRankings.put(URL, Columname);
					
				}
			}
			System.out.println("Adding Expected data for Hospitals Dashboard page completed : "+Constants.multiMapForpcAdultRankings.size());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	
		
	}

	
	public static void storePcRankingsData(Xls_Reader excel, String Sheetname) {

		Constants.Sheetname=Sheetname;
		try {
			
			// Initialise the excel reader for Object repository
			//Marlabs.Core.Xls_Reader excel = new Marlabs.Core.Xls_Reader(Constants.TestSuitePath_OE);
			int OR_Rowcount = excel.getRowCount(Constants.Sheetname);
			int Columncount = excel.getColCount(Constants.Sheetname);
			ArrayList<String> al = new ArrayList<String>();
			
			for (int k = 2; k < Columncount; k++) {

				String col_data = excel.getCellData(Constants.Sheetname, k, 1);
				al.add(col_data);
				
			}
			
			int j = 0;
			
			String Columname = null;

			for (int i = 2; i < OR_Rowcount; i++) {

				String URL = excel.getCellData(Constants.Sheetname, "Testing URL", i);
				//Constants.multiMapForpcAdultRankings.put(URL, URL);
				for (j = 2; j < Columncount; j++) {

					//String colName = al.get(j).toString();
					
					Columname = excel.getCellData(Constants.Sheetname, j, i);
					
					Constants.multiMapForpcAdultRankings.put(URL, Columname);
					
				}
			}
			System.out.println("Adding Expected data for Hospitals Dashboard page completed : "+Constants.multiMapForpcAdultRankings.size());
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

	
		
	}

	@SuppressWarnings("unchecked")
	public static ArrayList<String> getObjectForDataValueOnlineEducation(String ORefer) {
		ArrayList<String> object = null;
		try {
			object = (ArrayList<String>) Constants.multiMapForpcAdultRankings.get(ORefer);
		} catch (Exception e) {
			System.out.println("There is no object by the name: " + ORefer);
		}
		return object;
	}

}
