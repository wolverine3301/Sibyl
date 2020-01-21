package scout;

import correlation.Correlations;
import correlation.Pearson;
import dataframe.DataFrame;

public class NumericRanker {
	Pearson p;
    Correlations c; 
    public NumericRanker(DataFrame df,int targetIndex) {
    	this.p = new Pearson();
    	this.c = new Correlations(df,p);
    }

}
