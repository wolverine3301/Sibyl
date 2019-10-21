package correlation;

import java.util.HashMap;

import dataframe.Column;
import dataframe.DataFrame;

public class Correlations {
	 	
	private DataFrame num_df;
	private HashMap<String,Double> correlations;
	Correlation function;
	public Correlations(DataFrame df,Correlation function) {
		this.num_df = df.shallowCopy_columnIndexes(df.numericIndexes);
		this.function = function;
		correlations = new HashMap<String,Double>();
		getCorrelations();
	}
	private void getCorrelations() {
		for(Column i : num_df.columns) {
			for(Column j : num_df.columns) {
				if(j == i) {
					continue;
				}else {
					correlations.put(i.getName() + " vs. " + j.getName(), function.getCorrelation(i, j));
				}
			}
			
		}
	}
	public void printCorrelations() {
		System.out.println(correlations.toString());
	}

}
