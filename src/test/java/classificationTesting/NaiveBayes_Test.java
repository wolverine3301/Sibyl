package classificationTesting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bayes.NaiveBayes2;
import dataframe.Column;
import dataframe.DataFrame;
import discreteize.EqualFrequencyBinning;

class NaiveBayes_Test {
	
	private static DataFrame df;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@BeforeEach
	void setUp() throws Exception {
		String file = "testfiles/iris.txt";
        df = DataFrame.read_csv(file);
        df.setColumnType(4, 'T');
	}
	
	/*
	 *testing naive bayes setup with only categorical columns 
	 */
	@Test
	void test_setClasses_withOnlyCategorical() {
		
        EqualFrequencyBinning a = new EqualFrequencyBinning(8, df.getColumn(0));
        df.replaceColumn(0, a.binColumn());
        EqualFrequencyBinning b = new EqualFrequencyBinning(8, df.getColumn(1));
        df.replaceColumn(1, b.binColumn());
        EqualFrequencyBinning c = new EqualFrequencyBinning(8, df.getColumn(2));
        df.replaceColumn(2, c.binColumn());
        EqualFrequencyBinning d = new EqualFrequencyBinning(8, df.getColumn(3));
        df.replaceColumn(3, d.binColumn());
		NaiveBayes2 nb = new NaiveBayes2(df);
		System.out.println(nb.classes);
		assertEquals(true,nb.classes.keySet().contains("species"));
		assertEquals(3,nb.classes.get("species").length);
	}
	@Test
	void test_categoricalTable_keysets() {
        EqualFrequencyBinning a = new EqualFrequencyBinning(8, df.getColumn(0));
        df.replaceColumn(0, a.binColumn());
        EqualFrequencyBinning b = new EqualFrequencyBinning(8, df.getColumn(1));
        df.replaceColumn(1, b.binColumn());
        EqualFrequencyBinning c = new EqualFrequencyBinning(8, df.getColumn(2));
        df.replaceColumn(2, c.binColumn());
        EqualFrequencyBinning d = new EqualFrequencyBinning(8, df.getColumn(3));
        df.replaceColumn(3, d.binColumn());
        
		NaiveBayes2 nb = new NaiveBayes2(df);
		
		System.out.println("DF1: "+nb.classes.get("species")[0].getName());
		System.out.println();
		for(Column i : nb.classes.get("species")[0].categorical_columns) {
			System.out.println(i.getName()+" "+i.getTotalUniqueValues());
		}
		System.out.println();
		System.out.println("DF2: "+nb.classes.get("species")[1].getName());
		System.out.println();
		for(Column i : nb.classes.get("species")[1].categorical_columns) {
			System.out.println(i.getName()+" "+i.getTotalUniqueValues());
		}
		System.out.println();
		System.out.println("DF3: "+nb.classes.get("species")[2].getName());
		System.out.println();
		for(Column i : nb.classes.get("species")[2].categorical_columns) {
			System.out.println(i.getName()+" "+i.getTotalUniqueValues());
		}
		System.out.println(nb.cat_Naive_Bayes);
		//assertEquals(nb.cat_Naive_Bayes.get("species")[0].)
	}

}
