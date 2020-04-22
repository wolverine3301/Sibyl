package ranker;

import dataframe.DataFrame;

public interface FeatureRanking {
	
	double[] rank(DataFrame df);
}
