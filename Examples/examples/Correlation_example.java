package examples;

import correlation.Correlations;
import correlation.Pearson;
import dataframe.DataFrame;

public class Correlation_example {

	public static void main(String[] args) {
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
        Pearson p = new Pearson();
        Correlations c = new Correlations(df,p);
        c.printCorrelations();

	}

}
