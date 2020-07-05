package ranker;

import java.util.HashMap;

import correlation.Pearson;
import correlation.Spearman;
import dataframe.Column;
import dataframe.DataFrame;

/**
 * Using various correlation methods to rank numeric variables against a numeric target
 * 
 * @author logan.collier
 *
 */
public class Correlation_Ranker {
	private DataFrame df;
	private Pearson p;
	private Spearman s;
	private HashMap<String,HashMap<String,Double>> pearsonRanks;
	private HashMap<String,HashMap<String,Double>> spearmanRanks;
	
	public Correlation_Ranker(DataFrame df) {
		this.df = df;
		this.p = new Pearson();
		this.s = new Spearman();
	}
	/**
	 * set pearson ranks
	 */
	public void rankPearson() {
		this.pearsonRanks = new HashMap<String,HashMap<String,Double>>();
		for(Column i : df.target_columns) {
			HashMap<String,Double> tmp = new HashMap<String,Double>();
			for(Column j : df.numeric_columns) {
				tmp.put(j.getName(), s.getCorrelation(i, j));
			}
			pearsonRanks.put(i.getName(), tmp);
		}
	}
	/**
	 * set spearman ranks
	 */
	public void rankSpearman() {
		this.spearmanRanks = new HashMap<String,HashMap<String,Double>>();
		for(Column i : df.target_columns) {
			HashMap<String,Double> tmp = new HashMap<String,Double>();
			for(Column j : df.numeric_columns) {
				tmp.put(j.getName(), s.getCorrelation(i, j));
			}
			spearmanRanks.put(i.getName(), tmp);
		}
	}

}
