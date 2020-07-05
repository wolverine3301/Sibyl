package scout;

import java.util.HashMap;

import correlation.Correlations;
import correlation.Pearson;
import correlation.Spearman;
import dataframe.DataFrame;

public class NumericRanker {
	private Pearson p;
	private Spearman s;
    private Correlations rank_pearson;
    private Correlations rank_spearman;
    
    /**
     * rank numeric attributes by correlations, gives and ordered list of features with highest to lowest
     * @param df - the dataframe
     * @param targetIndex - index of target column
     */
    public NumericRanker(DataFrame df,int targetIndex) {
    	this.p = new Pearson();
    	this.s = new Spearman();
    	this.rank_pearson = new Correlations(df,p);
    	this.rank_spearman = new Correlations(df,s);
    }
    /**
     * return pearson correlations in order high to low
     * @return HashMap<String, Double>
     */
    public HashMap<String, Double> getPearson() {
    	return this.rank_pearson.getCorrelations();
    }
    /**
     * return spearman rank correlations in order high to low
     * @return HashMap<String, Double>
     */
    public HashMap<String, Double> getSpearman() {
    	return this.rank_spearman.getCorrelations();
    }

}
