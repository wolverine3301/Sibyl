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
        df.setColumnType(0, 'N');
        df.setColumnType(1, 'N');
        df.setColumnType(2, 'N');
        df.setColumnType(3, 'N');
        df.numericIndexes.add(0);
        df.numericIndexes.add(1);
        df.numericIndexes.add(2);
        df.numericIndexes.add(3);
        
        DataFrame[] hh = Util.splitOnTarget(df, 4);
        for(DataFrame i:hh) {
        	System.out.println(i.getName());
        	i.numericIndexes.add(0);
            i.numericIndexes.add(1);
            i.numericIndexes.add(2);
            i.numericIndexes.add(3);
        }
        T_testing t = new T_testing(hh);
        Pearson p = new Pearson();
        Correlations c = new Correlations(df,p);
        c.printCorrelations();
		OneWay_ANOVA anova = new OneWay_ANOVA(df);
        anova.invokeANOVA();
        anova.printResults();
	}

}
