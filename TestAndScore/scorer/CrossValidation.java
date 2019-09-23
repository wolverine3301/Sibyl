package appraiser;

import java.util.HashSet;
import java.util.Set;

import dataframe.DataFrame;
import saga.*;

public class CrossValidation {
	
	private DataFrame[] trials;
	public CrossValidation(DataFrame df, int N) {
		this.trials = df.split(N);
		Score score = new Score(df, null);
		for(int i = 0;i < trials.length; i++) {
			
		}
	}
	

}
