package test;

/*
 * These are TestNG tests for methods of the BrainInterface (below). 
 * 1. isStringValid included in stringValidTest()
 * 
 * public interface BrainInterface {
 *	
 *	public int isStringValid(String str); 		// -1 if valid, otherwise index of problematic character
 *	public int showOpenParen(String str); 		// -1 if all closed, otherwise index of open parenthesis
 *	public String stepStringIn(String str);		// string inside leftmost set of parentheses
 *	public String solveAll(String str); 		// returns solution in string form
 *	public String solveStep(String str); 		// performs next step (order of ops), re-parses and returns string
 *
 * }
 * 
 * 
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import brain.*;
import data.*;

public class TestBrain {
	
	protected String filePath = "./Data/CalculatorTestPlan.xls";
	protected String sheetName = "TestData";
	protected List<ArrayList<?>> sheetData;
	
	// trims enclosing quotes from object assumed to be cast-able to String
	protected String trimQuotes(Object o) {
		return ((String) o).substring(1, ((String) o).length()-1 ); // trim enclosing quotes
	}
	
	@BeforeClass
	public void setUp() throws IOException, InterruptedException {
		MyExcelReader reader=new MyExcelReader(filePath);
		sheetData = reader.readDataFromExcelSheet(sheetName);
	}
	
	@Test(dataProvider="stringValidData")
	public void stringValidTest(Object testNum, Object scenario, Object input, 
			Object expected, Object comments) throws InterruptedException {
		
		String myInput = this.trimQuotes(input);
		
		Calculator C = new Calculator();
		int stringResult = C.isStringValid(myInput);
		
		Assert.assertEquals( Double.valueOf((double) stringResult), expected);
	}
	
	
	@Test(dataProvider="stepInData")
	public void stepInTest(Object testNum, Object scenario, Object input, 
			Object expected, Object comments) throws InterruptedException {
		
		String myInput = this.trimQuotes(input);
		String myExpected = this.trimQuotes(expected);
		
		Calculator C = new Calculator();
		String myResult = C.stepStringIn(myInput);
		
		Assert.assertEquals( myResult, myExpected);
		
	}
	
	@Test(dataProvider="solveStepData")
	public void solveStepTest(Object testNum, Object scenario, Object input, 
			Object expected, Object comments) throws InterruptedException {
		
		String myInput = this.trimQuotes(input);
		String myExpected = this.trimQuotes(expected);
		
		Calculator C = new Calculator();
		String myResult = C.solveStep(myInput);
		
		Assert.assertEquals( myResult, myExpected);
		
	}
	
	@Test(dataProvider="solveAllData")
	public void solveAllTest(Object testNum, Object scenario, Object input, 
			Object expected, Object comments) throws InterruptedException {
		
		String myInput = this.trimQuotes(input);
		String myExpected = this.trimQuotes(expected);
		
		Calculator C = new Calculator();
		String myResult = C.solveAll(myInput);
		
		Assert.assertEquals( myResult, myExpected);
		
	}
	
	@Test(dataProvider="mathErrorData")
	public void mathErrorTest(Object testNum, Object scenario, Object input, 
			Object expected, Object comments) throws InterruptedException {
		
		String myInput = this.trimQuotes(input);
		String myExpected = this.trimQuotes(expected);
		
		Calculator C = new Calculator();
		String myResult = C.solveAll(myInput);
		
		Assert.assertEquals( myResult, myExpected);
		
	}
	
	
	
	@DataProvider(name="stringValidData")
	public Object[][] stringValidData() throws IOException {
		String testName = "StringValidTest";
		Object[][] out = DataParser.filterParseData(sheetData, testName);
		return out;

	}
	
	@DataProvider(name="stepInData")
	public Object[][] stepInData() throws IOException {
		String testName = "StepInTest";
		Object[][] out = DataParser.filterParseData(sheetData, testName);
		return out;

	}
	
	@DataProvider(name="solveStepData")
	public Object[][] solveStepData() throws IOException {
		String testName = "SolveStepTest";
		Object[][] out = DataParser.filterParseData(sheetData, testName);
		return out;

	}
	
	@DataProvider(name="solveAllData")
	public Object[][] solveAllData() throws IOException {
		String testName = "SolveAllTest";
		Object[][] out = DataParser.filterParseData(sheetData, testName);
		return out;

	}
	
	@DataProvider(name="mathErrorData")
	public Object[][] mathErrorData() throws IOException {
		String testName = "MathErrorTest";
		Object[][] out = DataParser.filterParseData(sheetData, testName);
		return out;
	}

}
