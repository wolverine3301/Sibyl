package appraiser;

import java.util.HashSet;
import java.util.Set;

import saga.*;

public class CrossValidation {
	
	private DataFrame[] trials;
	public CrossValidation(DataFrame df, int N) {
		this.trials = df.split(N);
	}
	

}
