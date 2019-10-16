package regressionTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataframe.Column;
import dataframe.DataFrame;
import linearRegression.LinearRegression;

class LinearRegressionTest {
	private DataFrame iris = new DataFrame();
	@BeforeEach
	void setUp() throws Exception {
		iris.loadcsv("testfiles/iris.txt");
	}

	@Test
	void testRegressiom() {
		Column col1 = iris.getColumn(0);
		Column col2 = iris.getColumn(1);
		LinearRegression test = new LinearRegression(col1,col2);
	}

}
