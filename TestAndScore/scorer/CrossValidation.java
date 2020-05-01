package scorer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import dataframe.DataFrame;
import machinations.Model;
/**
 * Cross validation for training and testing models 
 * @author logan.collier
 *
 */
public class CrossValidation {
	
	public int N;
	public ArrayList<Score> scores;
	public DataFrame df;
	/** The target data for the test data frame. */
	public DataFrame testDF_targets;
	/** The training data used to test predictive models. */
	public DataFrame testDF_variables;
	ArrayList<TestTrainFit> trials;
	
	HashMap<String, HashMap<Object, Double>> total_recall;
	HashMap<String, HashMap<Object, Double>> total_precision;
	HashMap<String, HashMap<Object, Double>> total_f1;
	HashMap<String, HashMap<Object, Double>> total_mcc;
	/**
	 * Cross Validation
	 * @param df - the dataframe 
	 * @param N - number of folds
	 * @param model - a model object to be tested
	 */
	public CrossValidation(DataFrame df, int N, Model model) {
		System.out.println("CV: "+df.numTargets);
		System.out.println(df.target_columns.get(0).getName());
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
	/**
	 * setup an array of dataframes to test and train with
	 */
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
	        trial = new TestTrainFit(this.df.shallowCopy_rowIndexes(set2), this.df.shallowCopy_rowIndexes(set1));
	        trials.add(trial);
	    }
	    this.trials = trials;
	}
	/**
	 * Avg scores from all trials
	 */
	public void avgScores() {
		HashMap<String, HashMap<Object, Double>> recall = new HashMap<String, HashMap<Object, Double>>();
		HashMap<String, HashMap<Object, Double>> precision = new HashMap<String, HashMap<Object, Double>>();
		HashMap<String, HashMap<Object, Double>> F1 = new HashMap<String, HashMap<Object, Double>>();
		HashMap<String, HashMap<Object, Double>> mcc = new HashMap<String, HashMap<Object, Double>>();
		
		//initiallize
		HashMap<Object, Double> trial;
		HashMap<Object, Double> trial2;
		HashMap<Object, Double> trial3;
		HashMap<Object, Double> trial4;
		for(String j : scores.get(0).recall.keySet()) {
			trial = new HashMap<Object,Double>();
			trial2 = new HashMap<Object,Double>();
			trial3 = new HashMap<Object,Double>();
			trial4 = new HashMap<Object,Double>();
			for(Object z : scores.get(0).recall.get(j).keySet()) {
				trial.put(z, (double) 0);
				trial2.put(z, (double) 0);
				trial3.put(z, (double) 0);
				trial4.put(z, (double) 0);
			}
			recall.put(j, trial);	
			precision.put(j, trial2);
			F1.put(j, trial3);
			mcc.put(j, trial4);
		}
		//sum scores
		for(Score i : scores) {
			for(String j : i.recall.keySet()) {
				for(Object z : i.recall.get(j).keySet()) {
					recall.get(j).replace(z, recall.get(j).get(z) + i.recall.get(j).get(z));
					precision.get(j).replace(z, precision.get(j).get(z) + i.precision.get(j).get(z));
					F1.get(j).replace(z, F1.get(j).get(z) + i.F1.get(j).get(z));
					mcc.get(j).replace(z, mcc.get(j).get(z) + i.mcc.get(j).get(z));
				}
			}
		}
		
		//average scores
		for(String j : recall.keySet()) {
			for(Object z : recall.get(j).keySet()) {
				recall.get(j).replace(z, recall.get(j).get(z) / scores.size());
				precision.get(j).replace(z, precision.get(j).get(z) / scores.size());
				F1.get(j).replace(z, F1.get(j).get(z) / scores.size());
				mcc.get(j).replace(z, mcc.get(j).get(z) / scores.size());
			}
		}
		this.total_recall = recall;
		this.total_precision = precision;
		this.total_f1 = F1;
		this.total_mcc = mcc;
		//System.out.println("Recall: "+recall.toString());
		//System.out.println("Precicion: "+precision.toString());
		//System.out.println("F1: "+F1.toString());
	}
	public void printScores() {
		int cnt = 0;
		for(Score i : scores) {
			System.out.println("TRIAL: " + cnt + "  ");
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
	public void printOverAllScore() {
		System.out.println("RECALL:");
		System.out.println(this.total_recall);
		System.out.println("PRECISION:");
		System.out.println(this.total_precision);
		System.out.println("F!: ");
		System.out.println(this.total_f1);
		System.out.println("MCC:");
		System.out.println(this.total_mcc);
	}
	

}
