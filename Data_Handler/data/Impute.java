package data;

import dataframe.DataFrame;

public class Impute {
	private DataFrame df;
	
	public Impute(DataFrame df) {
		this.df = df;
	}
	public static void MeanMode(DataFrame df) {
		df.convertNANS_mean();
	}

}
