package examples;

import java.util.HashMap;

import chi2.Chi2Independents;
import dataframe.DataFrame;
import dataframe.DataFrame_Read;

public class Chi2_example {

	public static void main(String[] args) {
		String file = "testfiles/heart_disease.csv";
		DataFrame  df = DataFrame_Read.loadcsv(file);
        df.setColumnType("gender", 'T');//set target column
		Chi2Independents chi2 = new Chi2Independents(df);
		
		chi2.printResults();

	}

}
