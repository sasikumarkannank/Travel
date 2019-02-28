package com.marlabs.core;

import java.io.IOException;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class Controller extends DriverOperation{

	@BeforeSuite
	protected void init( ) throws IOException {
		initDriver();	
	}
	
	@AfterSuite
	protected void end() throws IOException {
			endDriver();
			
	}
}
