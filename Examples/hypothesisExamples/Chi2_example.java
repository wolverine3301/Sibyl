package hypothesisExamples;

import java.util.HashMap;

import chi2.Chi2Independents;
import dataframe.DataFrame;
import dataframe.DataFrame_Read;

public class Chi2_example {

	public static void main(String[] args) {
		String file = "testfiles/catTest.txt";
		DataFrame  df = DataFrame_Read.loadcsv(file);
        df.setColumnType("Color", 'T');//set target column
        
        
		Chi2Independents chi2 = new Chi2Independents(df);
		//chi2.ranked(0);
		chi2.printObsContengencyTables();
		chi2.printEXPContengencyTables();
		
		chi2.printResults();
		System.out.println();


	}

}
