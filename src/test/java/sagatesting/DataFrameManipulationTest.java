package sagatesting;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Read;
import dataframe.Util;
import discreteize.EqualFrequencyBinning;

class DataFrameManipulationTest {
	private DataFrame df;
	@BeforeEach
	void setUp() throws Exception {
		df = DataFrame_Read.loadcsv("testfiles/iris.txt");
	}

	@Test
	void testColumnArrays() {
		System.out.println("There should be 4 numeric columns and 1 categorical");
		assertEquals(4,df.numNumeric,"INCORRECT NUMBER OF NUMERIC COLUMN COUNT");
		assertEquals(1,df.numCategorical,"INCORRECT NUMBER OF CATEGORICAL COLUMN COUNT");
		assertEquals(4,df.numeric_columns.size(),"numeric column arraylist size incorrect");
		assertEquals(1,df.categorical_columns.size(),"categorical arrayList size incorrect");
		System.out.println("Testing if correct columns are in the correct arrays");
		System.out.println(df.columnNamesToString());
		String[] expected_numeric_names = {"﻿sepal_length", "sepal_width", "petal_length", "petal_width"};
		String expected_cat_name = "species";
		int cnt = 0;
		assertEquals("species",df.categorical_columns.get(0).getName(),"incorrect catergorical name");
		for(Column i : df.numeric_columns) {
			System.out.println("EXPECTED: "+expected_numeric_names[cnt]+ " ACTUAL: "+i.getName());
			assertEquals(expected_numeric_names[cnt],i.getName());
			cnt++;
		}
		
		System.out.println("setting species to a target, categorical column count should be 0, and targets should be 1");
        df.setColumnType(4, 'T');
        assertEquals(0,df.numCategorical,"categorical column count incorrect");
        assertEquals(0,df.categorical_columns.size(),"Categorical column array size incorrect");
        assertEquals(1,df.numTargets,"Target column count incorrect");
        assertEquals(1,df.target_columns.size(),"Target array size incorrect");
        System.out.println("TRUE");
        System.out.println("Changing column 3 to meta");
        df.setColumnType(3, 'M');
        assertEquals(1,df.numMeta,"Meta column count incorrect");
        assertEquals(1,df.meta_columns.size(),"Meta column array size incorrect");
        assertEquals(3,df.numNumeric,"numeric coulmn count incorrect");
        assertEquals(3,df.numeric_columns.size(),"numeric column array size incorrect");
        System.out.println("TRUE");
        
        System.out.println("Binning column 0 and changing to cetegorical ");
        EqualFrequencyBinning a = new EqualFrequencyBinning(8, df.getColumn(0));
        df.replaceColumn(0, a.binColumn());
        assertEquals(2,df.numNumeric,"Numeric column count incorrect");
        assertEquals(2,df.numeric_columns.size(),"Numeric column array size incorrect");
        assertEquals(1,df.numCategorical,"Categorical column count incorrect");
        assertEquals(1,df.categorical_columns.size(),"Categorical column array size incorrect");
	}
	@Test
	void testColumnManipulation_and_dataframeSplit() {
		System.out.println("setting speices as target");
		df.setColumnType(4, 'T');
		ArrayList<DataFrame[]> classes = setClasses(df);
		System.out.println("there sould be an arraylist of 1, because there is one target column");
		System.out.println("there should be 3 dataframe arrays because there are 3 values in that column");
		assertEquals(1,classes.size(),"incorrect target array size");
		assertEquals(3,classes.get(0).length,"incorrect length of dataframe array");
		
		String[] exp_df_names = {"versicolor","setosa","virginica"};
		
        for(int i = 0; i< classes.size();i++) {
        	System.out.println("TARGET ITERATION: "+i);
        	System.out.println("SUB-DATAFRAMES: "+classes.get(i).length);
        	for(int j = 0; j < classes.get(i).length; j++) {
        		System.out.println("expected: "+exp_df_names[j]+" actual: "+classes.get(i)[j].getName());
        		assertEquals(exp_df_names[j],classes.get(i)[j].getName(),"Names are incorrect");
        		
        		assertEquals(50,classes.get(i)[j].getNumRows(),"incorrect number of rows");
        		assertEquals(1,classes.get(i)[j].numTargets,"incorect number of targets");
        		assertEquals(4,classes.get(i)[j].numNumeric,"incorrect number of numerics");
        	}
        }
        System.out.println();
        System.out.println();
        
	}

	private static ArrayList<DataFrame[]> setClasses(DataFrame df) {
		ArrayList<DataFrame[]> classes = new ArrayList<DataFrame[]>();
		for(Column i : df.target_columns) {
			classes.add(Util.splitOnTarget(df, i));
		}
		return classes;
	}
}
