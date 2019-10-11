package scorer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import dataframe.DataFrame;
import machinations.Model;

public class CrossValidation {
	
	
	public int N;
	public ArrayList<Score> scores;
	public DataFrame df;
	/** The target data for the test data frame. */
	public DataFrame testDF_targets;
	/** The training data used to test predictive models. */
	public DataFrame testDF_variables;
	ArrayList<TestTrainFit> trials;
	
	public CrossValidation(DataFrame df, int N, Model model) {
		this.N = N;
		this.df = df.shuffle(df);
		scores = new ArrayList<Score>();
		Score score;
		setTrials();
		//for each trial
		for(int i = 0;i < this.trials.size(); i++) {
			model.train(trials.get(i).raw_train);
			model.initiallize();
			HashMap<String, ArrayList<Object>> predicts = model.predictDF(trials.get(i).trial_test_variables);
			score = new Score(trials.get(i).trial_test_targets,predicts);
			scores.add(score);
		}
	}
	public void setTrials() {
		int interval = Math.floorDiv(this.df.getNumRows(), N);
	    //shuffle(df);
		ArrayList<TestTrainFit> trials = new ArrayList<TestTrainFit>();
		TestTrainFit trial;
	    Set<Integer> set1 = new HashSet<Integer>();
	    Set<Integer> set2 = new HashSet<Integer>();
	    for (int i = 0; i < this.df.getNumRows() -1; i += interval) {
	        set1.clear();
	        for(int j = i; j < i + interval; j++) {
	        	if(j >= this.df.getNumRows()) break;
	            set1.add(j); 
	        }
	        set2.clear();
	        for(int c = 0; c < this.df.getNumRows(); c++) {
	        	if(c >= this.df.getNumRows()) break;
	        	if(set1.contains(c)) {
	        		continue;
	        	}else {
	        		set2.add(c);
	        	}
	        	
	        }
	        trial = new TestTrainFit(this.df.shallowCopy_rowIndexes(this.df,set1),this.df.shallowCopy_rowIndexes(this.df, set2));
	        trials.add(trial);
	    }
	    this.trials = trials;
	}
	public void avgScores() {
		HashMap<String, HashMap<Object, Double>> recall = new HashMap<String, HashMap<Object, Double>>();
		HashMap<String, HashMap<Object, Double>> precision = new HashMap<String, HashMap<Object, Double>>();
		HashMap<String, HashMap<Object, Double>> F1 = new HashMap<String, HashMap<Object, Double>>();
		HashMap<String, HashMap<Object, Double>> mcc = new HashMap<String, HashMap<Object, Double>>();
		int cnt = 0;
		
		String overa = "OverAll";
		ArrayList<Double> totalRecall = new ArrayList<Double>();
		double[] acc = null;
		for(Score i : scores) {
			cnt = 0;
			for(String j : i.recall.keySet()) {
				
				totalRecall.set(index, element) acc[cnt] + i.recall.get(j).get(overa);
				System.out.println(i.recall.get(j).get("OverAll") + " " +acc[cnt]);
				cnt++;
			}
			for(int z = 0; z < cnt; z++) {
				System.out.println(z + " "+ acc[z]);
			}

		}
		
	}
	public void printScores() {
		int cnt = 0;
		for(Score i : scores) {
			System.out.print("TRIAL: " + cnt + "  ");
			i.printScore();
			cnt++;
		}
	}
	public void printMatrixs() {
		int cnt = 0;
		for(Score i : scores) {
			System.out.print("TRIAL: " + cnt + "  ");
			i.printMatrix();
			cnt++;
		}
	}
	

}
