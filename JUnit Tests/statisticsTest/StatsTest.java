package statisticsTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataframe.Column;
import dataframe.DataFrame;
import forensics.Stats;

class StatsTest {
	private DataFrame iris = new DataFrame();
	@BeforeEach
	void setUp() throws Exception {
		iris.loadcsv("testfiles/iris.txt");
	}

	@Test
	void testZeroSquaredSum() {
		Column col1 = iris.getColumn_byIndex(0);
		assertEquals(102.16833333333332,Stats.zeroSquaredSum(col1));
	}

	@Test
	void testCovariance() {
		Column col1 = iris.getColumn_byIndex(0);
		Column col2 = iris.getColumn_byIndex(1);
		assertEquals(-0.03900666666666667,Stats.covariance(col1,col2),"covariance is incorrect");
		
	}
	@Test
	void testZeroSumMultiple_Columns() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testSumMultiple_Columns() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testComean() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testSquareMean() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testCovariance_matrix() {
		fail("Not yet implemented"); // TODO
	}

}
