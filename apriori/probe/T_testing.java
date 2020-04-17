package probe;

import java.util.HashMap;
import dataframe.DataFrame;
import forensics.T_Test;

/**
 * for a particular target variable, broken up into its various classes
 * compare the means of every column to the corresponding column in the other classes
 * @author logan.collier
 *
 */
public class T_testing {
	/**
	 * an array of dataframes broken up by a the target classes
	 */
	private DataFrame[] df;
	private HashMap<String,HashMap<String,Double>> comparisonTable;
	
	public T_testing(DataFrame[] df) {
		this.df = df;

		 initiallizeTable();
	}
	/**
	 * compare the means of every numeric column for each dataframe
	 * the number of calculations required is: let g = number of dataframes 
	 * c = the number of numeric columns. The number of comparicons made is SUM( g-1 * c)
	 * 
	 */
	private void compare() {
		
	}
	/**
	 * initiallize the comparison table
	 */
	private void initiallizeTable() {
		comparisonTable = new HashMap<String,HashMap<String,Double>>();
		for(Integer i : df[0].numericIndexes) {
			
			comparisonTable.put(df[0].getColumn(i).getName(), new HashMap<String,Double>());
			HashMap<String,Double> tmp = new HashMap<String,Double>();
			for(int j = 0;j < df.length-1; j++) {
				for(int c = 0; c < df.length; c++) {
					if(tmp.containsKey(df[c].getName().concat(" vs. "+df[j].getName()))) {
						continue;
					}
					if(c == j)continue;
					T_Test t = T_Test.test(df[j].getColumn(i),df[c].getColumn(i));
					tmp.put(df[j].getName().concat(" vs. " +df[c].getName()),t.pvalue);
				}				
			}
			comparisonTable.put(df[0].getColumn(i).getName(),tmp);
		}
		System.out.println(comparisonTable);
	}
}
