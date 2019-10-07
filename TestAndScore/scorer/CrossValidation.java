package scorer;

import java.util.ArrayList;
import java.util.List;
import dataframe.DataFrame;
import dataframe.DataFrameTools;
import machinations.Model;

public class CrossValidation {
	
	
	private ArrayList<DataFrame> trials;
	public ArrayList<Score> scores;
	public CrossValidation(DataFrame df, int N, Model model) {
		this.trials = DataFrameTools.split(df, N);
		scores = new ArrayList<Score>();
		Score score;
		int prev_index = 0;
		int new_index = 0;
		DataFrame currentTraining;
		for(int i = 0;i < trials.size(); i++) {
			currentTraining = new DataFrame();
			for(int j = 0; j < trials.size();j++) {
				if(j == i)
					continue;
				else {
					for(int c = 0; c < trials.get(j).getNumRows(); c++) {
						currentTraining.addRow(trials.get(j).getRow_byIndex(c));
					}
				}
			}
			for(int j = 0; j < trials.get(i).getNumRows(); i++) {
				score = new Score(trials.get(i), );
				scores.add(score);
			}
		}
	}
	public void printScores() {
		for(Score i : scores) {
			i.printScore();
		}
	}
	

}
