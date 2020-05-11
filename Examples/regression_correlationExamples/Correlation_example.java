package regression_correlationExamples;

import correlation.Correlations;
import correlation.Pearson;
import dataframe.Column;
import dataframe.DataFrame;
import regressionFunctions.LinearRegression;

public class Correlation_example {

	public static void main(String[] args) {
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
		Column col2 = df.getColumn(1);
		Column col3 = df.getColumn(2);
		LinearRegression test = new LinearRegression(col2,col3);
	
		
		
        Pearson p = new Pearson();
        System.out.println(p.getCorrelation(col2, col3));
        Correlations c = new Correlations(df,p);
        c.printCorrelations();

	}

}
