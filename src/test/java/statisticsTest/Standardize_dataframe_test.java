package statisticsTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataframe.Column;
import dataframe.DataFrame;
import transform.Standardize;

class Standardize_dataframe_test {
	private DataFrame iris = new DataFrame();
	@BeforeEach
	void setUp() throws Exception {
		iris = DataFrame.read_csv("testfiles/iris.txt");
	}

	@Test
	void testMean() {
		iris = Standardize.standardize_df(iris);
		for(Column i : iris.columns) {
			assertEquals(0,i.mean,"Means are not equal to 0");
			System.out.println(i.getName() + " Mean: "+ i.mean);
		}
	}
	@Test
	void testStandardDeviation() {
		iris = Standardize.standardize_df(iris);
		for(Column i : iris.columns) {
			assertEquals(1,i.mean,"Means are not equal to 0");
		}
	}

}
