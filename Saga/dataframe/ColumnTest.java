package dataframe;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ColumnTest {
	double[] data1 = new double[10];
	Column col;
	@BeforeClass
	void setUp() throws Exception {
		for(int i = 0; i < 10; i++) {
			data1[i] = 6;
		}
	}

	@AfterEach
	void tearDown() throws Exception {
	}


	@Test
	void testColumnColumn() {
		//make an empty column
		Column col = new Column("newColumn");
		assertEquals("newColumn",col.name);
		assertEquals(0,col.columnLength);
		assertNull("The Column is empty", col.column);
		//fail("Not yet implemented"); // TODO
	}
	
	@Test
	void testAdd() {
		Integer a = 0;
		col.add(a);
		assertEquals(1,col.columnLength);
		
		//fail("Not yet implemented"); // TODO
	}
	@Test
	void testGetParticle_atIndex() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testSetType() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testResolveType() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testParticleTypeToColumnType() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testAddToColumn() {
		fail("Not yet implemented"); // TODO
	}



	@Test
	void testChangeValue() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testHasValue() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testRemoveValue() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testRemoveIndex() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetLength() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testMakeColumn_fromArray() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testConcatArray() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testNumOfUniques() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testUniqueValues() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testUniqueValCnt() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testPrintCol() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testSum() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testMode() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testMean() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testMeanOfIndexes() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testSumOfIndexes() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testMedian() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testVariance() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testStandardDeviation() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testEntropy() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testSetFeatureStats() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testToString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testObject() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testGetClass() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testHashCode() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testEquals() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testClone() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testToString1() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testNotify() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testNotifyAll() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testWait() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testWaitLong() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testWaitLongInt() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	void testFinalize() {
		fail("Not yet implemented"); // TODO
	}

}
