package hypothesis;

import correlation.Correlations;
import correlation.Pearson;
import correlation.Spearman;
import dataframe.Column;
import dataframe.DataFrame;
import dataframe.Util;

public class Hypothesis {
	
	/**
	 * This is a high level program file to calculate in depth statistical analysis
	 * It will perform several test appropriate to the data and can output the entire analysis
	 * or just the most important findings
	 * 
	 * 
	 * @param df
	 */
	private Correlations pearson;
	private Correlations spearman;
	private T_testing t;
	
	private DataFrame df;
	public Hypothesis(DataFrame df) {
		this.df = df;
		System.out.println("TARGET VARIABLES:");
		for(Column j : df.target_columns) {
			System.out.print(j.getName()+", ");
		}
		System.out.println();
		for(Column i : df.target_columns) {
			System.out.println("BEGINNING ANALYSIS FOR TARGET: "+ i.getName());
			System.out.println("Classes: "+i.getUniqueValues());
			Thread t0 = new Thread(() -> pearson = pearsonCorr());
			Thread t1 = new Thread(() -> spearman = spearmanCorr());
			Thread t2 = new Thread(() -> t = ttest());
			t0.start();
			t1.start();
			t2.start();
			
			try {
			    t0.join();
			    t1.join();
			    t2.join();
			} catch (InterruptedException e) { /* NOP */ }
			pearson.printCorrelations();
			spearman.printCorrelations();
		
	        
		}
		
		
	}
	/**
	 * perform a chi2 independents test
	 */
	private void chi2() {
		
	}
	/**
	 * perform a t-test
	 * @return 
	 */
	private T_testing ttest() {
		DataFrame[] hh = Util.splitOnTarget(df, 4);
		return new T_testing(hh);
	}
	/**
	 * perform perason correlation
	 * @return 
	 */
	private Correlations pearsonCorr() {
		System.out.println("PEARSON");
		Pearson p = new Pearson();
		return new Correlations(df,p);
		
	}
	/**
	 * perfrom spearman correlation
	 * @return 
	 */
	private Correlations spearmanCorr() {
		System.out.println("SPEARMAN");
		Spearman s = new Spearman();
        return new Correlations(df,s);
	}
	

}
