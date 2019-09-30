package scorer;

import java.util.ArrayList;
import java.util.List;
import dataframe.DataFrame;
import dataframe.DataFrameTools;

public class CrossValidation {
	
	private DataFrame[] trials;
	public ArrayList<Score> scores;
	public CrossValidation(DataFrame df, int N, List<Object[]> predictions) {
		this.trials = DataFrameTools.split(df, N);
		scores = new ArrayList<Score>();
		Score score;
		int prev_index = 0;
		int new_index = 0;
		for(int i = 0;i < trials.length; i++) {
			new_index = new_index + trials.length-1;
			for(int j = 0; j < trials[i].getNumRows(); i++) {
				score = new Score(trials[i], predictions.subList(prev_index, new_index));
				scores.add(score);
			}
			prev_index = new_index;
		}
	}
	

}
