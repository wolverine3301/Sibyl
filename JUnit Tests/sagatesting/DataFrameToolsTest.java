package sagatesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataframe.DataFrame;
import dataframe.DataFrameTools;

class DataFrameToolsTest {
	private DataFrame iris = new DataFrame();
	
	
	@BeforeClass
	void setUp() throws Exception {
		iris.loadcsv("testfiles/iris.txt");
	}

	
	/**
	 * Test acuire method
	 */
	@Test
	void testAquire() {
		DataFrame df1 = new DataFrame();
		String[] args = {"species","==","setosa"};
		df1 = DataFrameTools.acquire(iris,args);
		assertEquals(50, df1.getNumRows(), "The new dataframe has incorrect number of rows");
		assertEquals(50,df1.getColumn_byName("species").getLength(),"The column copy has incorrect number of rows");
		assertEquals(1,df1.getColumn_byName("species").numOfUniques());
	}
	@Test
	void testExclude() {
		
	}

}
