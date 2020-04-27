package probe;

import anova.OneWay_ANOVA;
import correlation.Correlations;
import correlation.Pearson;
import dataframe.DataFrame;
import dataframe.Util;
import hypothesis.T_testing;
import transform.Standardize;

public class bench {

	public static void main(String[] args) {
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
        df.setColumnType("species", 'T');
        df = Standardize.standardize_df(df);
        DataFrame[] hh = Util.splitOnTarget(df, 4);

        T_testing t = new T_testing(hh);

        

	}

}
