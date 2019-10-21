package correlation;

import dataframe.Column;
import dataframe.DataFrame;

/**
 * correlation example
 * @author logan.collier
 *
 */
public class corrBench {

	public static void main(String[] args) {
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
        //df.printDataFrame();
        for(Column i : df.columns) {
        	i.printCol();
        }
        Pearson p = new Pearson();
        Correlations c = new Correlations(df,p);
        c.printCorrelations();

	}

}
