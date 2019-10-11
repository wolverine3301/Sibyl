package scorer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import dataframe.DataFrame;
import dataframe.DataFrameTools;
import machinations.Model;

public class CrossValidation {
	
	
	private ArrayList<DataFrame> trials;
	public ArrayList<Score> scores;
	/** The target data for the test data frame. */
	public DataFrame testDF_targets;
	
	/** The training data used to test predictive models. */
	public DataFrame testDF_variables;
	
	public CrossValidation(DataFrame df, int N, Model model) {
		this.trials = DataFrameTools.split(df, N);
		scores = new ArrayList<Score>();
		Score score;
		DataFrame currentTraining;
		//for each trial
		for(int i = 0;i < trials.size(); i++) {
			currentTraining = new DataFrame();
			// make training set
			for(int j = 0; j < trials.size();j++) {
				if(j == i) {
					setTest(trials.get(j));
					continue;
				}
				else {
					for(int c = 0; c < trials.get(j).getNumRows(); c++) {
						currentTraining.addRow(trials.get(j).getRow_byIndex(c));
					}
				}
			}
			model.train(currentTraining);
			HashMap<String, ArrayList<Object>> predicts = model.predictDF(testDF_variables);
			score = new Score(trials.get(i),predicts);
			scores.add(score);
			
		}
	}
	public void setTest(DataFrame testDF) {
		this.testDF_targets = DataFrameTools.shallowCopy_columnTypes(testDF, set_targets());
	    this.testDF_variables = DataFrameTools.shallowCopy_columnTypes(testDF, set_variables());  
	}
	/**
	 * Sets the targets list.
	 * @return a tree set of targets to predict.
	 */
	private TreeSet<Character> set_targets() {
		TreeSet<Character> target = new TreeSet<Character>();
		target.add('T');
		return target;
	}
	
	
	/**
	 * Sets the variables list.
	 * @return a tree set of the variables used to train data.
	 */
	private TreeSet<Character> set_variables() {
		TreeSet<Character> vars = new TreeSet<Character>();
		vars.add('C');
		vars.add('G');
		vars.add('O');
		vars.add('N');
		return vars;
	}
	public void printScores() {
		for(Score i : scores) {
			i.printScore();
		}
	}
	

}
