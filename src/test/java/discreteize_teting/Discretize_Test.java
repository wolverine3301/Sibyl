package discreteize_teting;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataframe.Column;
import dataframe.DataFrame;
import discreteize.EqualFrequencyBinning;
import discreteize.EqualWidthBinning;
import transform.Standardize;

class Discretize_Test {
	private DataFrame iris = new DataFrame();
	@BeforeEach
	void setUp() throws Exception {
		iris = DataFrame.read_csv("testfiles/iris.txt");
	}

	@Test
	void testEqualFrequencyBinning() {
		EqualFrequencyBinning e = new EqualFrequencyBinning(10, iris.getColumn(0));
		System.out.println("DISCRETIZE WITH EQUAL FREQUENCY");
		assertEquals(10,e.getNumBins(),"Number of bins invalid");
		Column c = e.binColumn();
		assertEquals(10,c.getTotalUniqueValues(),"Number of unique objects not equal to bins");
		System.out.println("MODE: "+c.mode);
		assertEquals("(5.29999,5.6]",c.mode);
		assertEquals("Equal Frequency",e.getMethod());
		System.out.println(c.getUniqueValueCounts());
		for(Object i : c.getUniqueValueCounts().keySet()) {
			assertTrue(13 <= c.getUniqueValueCounts().get(i) && c.getUniqueValueCounts().get(i) <= 19);
		}
		System.out.println("Column type: "+c.getType());
		assertEquals('C',c.getType());
	}
	@Test
	void testEqualWidthBinning() {
		EqualWidthBinning e = new EqualWidthBinning(10, iris.getColumn(0));
		System.out.println("DISCRETIZE WITH EQUAL WIDTH");
		assertEquals(10,e.getNumBins(),"Number of bins invalid");
		Column c = e.binColumn();
		assertEquals(10,c.getTotalUniqueValues(),"Number of unique objects not equal to bins");
		System.out.println("MODE: "+c.mode);
		assertEquals("(5.380000000000001,5.740000000000001]",c.mode);
		assertEquals("Equal Width",e.getMethod());
		System.out.println(c.getUniqueValueCounts());
		for(Object i : c.getUniqueValueCounts().keySet()) {
			assertTrue(1 <= c.getUniqueValueCounts().get(i) && c.getUniqueValueCounts().get(i) <= 100);
		}
		System.out.println("Column type: "+c.getType());
		assertEquals('C',c.getType());
	}

}
