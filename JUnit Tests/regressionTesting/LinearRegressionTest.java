package regressionTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataframe.Column;
import dataframe.DataFrame;
import regressionFunctions.*
;class LinearRegressionTest {
	private DataFrame df;
	@BeforeEach
	void setUp() throws Exception {
		df = DataFrame.read_csv("testfiles/regress");
	}

	@Test
	void testRegressiom() {
		Column col1 = df.getColumn(0);
		Column col2 = df.getColumn(1);
		Column col3 = df.getColumn(2);
		LinearRegression test = new LinearRegression(col1,col2);
		System.out.println("Y intercept: "+test.intercept);
		System.out.println("Slope: "+test.slope);
		System.out.println("SS :"+ test.SST);
		System.out.println("SSP: "+test.SP);
		System.out.println();
		assertEquals(11,test.intercept);
		assertEquals(-1,test.slope);
		assertEquals(42,test.SST);
		assertEquals(-42,test.SP);
		//test 2
		LinearRegression test2 = new LinearRegression(col1,col3);
		System.out.println("Y intercept: "+test2.intercept);
		System.out.println("Slope: "+test2.slope);
		System.out.println("SS :"+ test2.SST);
		System.out.println("SSP: "+test2.SSE);
		System.out.println();
		assertEquals(2.25,test2.intercept);
		assertEquals(0.9166666666666666,test2.slope);
		assertEquals(42,test2.SST);
		assertEquals(38.5,test2.SP);
		
	}

}
