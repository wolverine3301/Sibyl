package scout;

import java.util.ArrayList;
import java.util.List;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Copy;
import machinations.NaiveBayes;
import scorer.CrossValidation;

public class Invoke {
	private static CategoryRanker CR; 
	private static NumericRanker NR; 
	private static DataFrame  df;
	public static void main(String[] args) {
		String file = "testfiles/heart_disease.csv";
        df = DataFrame.read_csv(file);
        df.setColumnType("thal", 'T');//set target column
        System.out.println(df.columnNamesToString());
        CR = new CategoryRanker(df, 0);
		//NR = new NumericRanker(df);
		CR.printRankings();
		generateRecollection(1, 5);
		NaiveBayes nb = new NaiveBayes();
        CrossValidation cv = new CrossValidation(df, 2, nb);
        cv.avgScores();
	}
	/**
	 * evocation
	 * creates an arraylist of dataframes with varying number of columns ranked by various measures in Ranker classes
	 * the first column will always be the best ranked column for a particular ranking method.
	 * @param initialNumColumns - the number of columns to start with
	 * @param terminate - number of columns to terminate on
	 * @return
	 */
	private static ArrayList<DataFrame> generateRecollection(int initialNumColumns, int terminate) {
		ArrayList<DataFrame> recollection = new ArrayList<DataFrame>();
		List<String> variableColumns = new ArrayList<String>();
		for(int cnt = initialNumColumns; cnt < terminate; cnt++) {
			// INFO GAIN
			for(int i = 0; i < cnt; i++) {
				if(i == CR.GAIN.size()) break;
				variableColumns.add(CR.GAIN.get(i).getName());
			}
			recollection.add(DataFrame_Copy.shallowCopy_columnNames(df, variableColumns));
			variableColumns.clear();
			// GAIN RATIO
			for(int i = 0; i < cnt; i++) {
				if(i == CR.GAIN_RATIO.size()) break;
				variableColumns.add(CR.GAIN_RATIO.get(i).getName());
			}
			recollection.add(DataFrame_Copy.shallowCopy_columnNames(df, variableColumns));
			variableColumns.clear();
			// GINI
			for(int i = 0; i < cnt; i++) {
				if(i == CR.GINI.size()) break;
				variableColumns.add(CR.GINI.get(i).getName());
			}
			recollection.add(DataFrame_Copy.shallowCopy_columnNames(df, variableColumns));
			variableColumns.clear();
			// CHI SQUARED
			for(int i = 0; i < cnt; i++) {
				if(i == CR.CHI2.size()) break;
				variableColumns.add(CR.CHI2.get(i).getName());
			}
			recollection.add(DataFrame_Copy.shallowCopy_columnNames(df, variableColumns));
		}
		System.out.println(recollection.size());
		return recollection;
	}
	

}
