package scorer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import dataframe.Column;
import dataframe.DataFrame;
import log.Loggers;
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
	
	/**
	 * overall metrics for each target variable
	 */
	protected HashMap<String,Double> overall_accuaccy;
	protected HashMap<String,Double> overall_recall;
	protected HashMap<String,Double> overall_precision;
	protected HashMap<String,Double> overall_f1;
	protected HashMap<String,Double> overall_mcc;
	/**
	 * detailed metrics for individual target variables classes
	 */
	protected HashMap<String, HashMap<Object, Double>> total_accuracy;
	protected HashMap<String, HashMap<Object, Double>> total_recall;
	protected HashMap<String, HashMap<Object, Double>> total_precision;
	protected HashMap<String, HashMap<Object, Double>> total_f1;
	protected HashMap<String, HashMap<Object, Double>> total_mcc;
	
	protected HashMap<String, HashMap<Object, Integer>> total_truePositive;
	protected HashMap<String, HashMap<Object, Integer>> total_falsePositive;
	protected HashMap<String, HashMap<Object, Integer>> total_trueNegative;
	protected HashMap<String, HashMap<Object, Integer>> total_falseNegative;
	
	protected HashMap<String,HashMap<Object,HashMap<Object,Integer>>> matrix;
	public ConfusionMatrix confusion_matrix;
	
	private HashMap<String,Set<Object>> keyValues;
	/**
	 * Cross Validation
	 * @param df - the dataframe 
	 * @param N - number of folds
	 * @param model - a model object to be tested
	 */
	public CrossValidation(DataFrame df, int N, Model model) {
		
		this.N = N;
		this.df = df.shuffle(df);
		this.scores = new ArrayList<Score>();
		Score score;
		
		setTrials();
		HashMap<String,Set<Object>> keyVals = new HashMap<String,Set<Object>>();
		for(Column i : this.df.target_columns) {
			keyVals.put(i.getName(), i.getUniqueValues());
		}
		this.keyValues = keyVals;
		Loggers.cv_Logger.log(Level.CONFIG, "Targets: " + df.numTargets + " Splits = " + N+"\n"+keyVals);
		//for each trial
		for(int i = 0;i < this.trials.size(); i++) {
			model.train(trials.get(i).raw_train);
			model.initiallize();
			
			HashMap<String, ArrayList<Object>> predicts = model.predictDF(trials.get(i).trial_test_variables);
			//for (String s : predicts.keySet())
			    //System.out.println(predicts.get(s).toString());
			score = new Score(keyVals,trials.get(i).trial_test_targets,predicts);
			scores.add(score);
		}
		this.confusion_matrix = new ConfusionMatrix();
		
		overallScores();

	}
	/**
	 * setup an array of dataframes to test and train with
	 */
	public void setTrials() {
		Loggers.cv_Logger.entering("CrossValidation", "Set Trials");
		
		int interval = Math.floorDiv(this.df.getNumRows(), N);
	    //shuffle(df);
		ArrayList<TestTrainFit> trials = new ArrayList<TestTrainFit>();
		TestTrainFit trial;
	    Set<Integer> set1 = new HashSet<Integer>();
	    Set<Integer> set2 = new HashSet<Integer>();
	    //Loggers.cv_Logger.log(Level.FINER,"ROWS: "+df.getNumRows()+" INTERVAL: "+interval*N);
	    for (int i = 0; i < this.df.getNumRows() -2; i += interval) {
	        set1.clear();
	        //make test set
	        for(int j = i; j < i + interval; j++) {
	        	if(j >= this.df.getNumRows()) break;
	            set1.add(j); 
	        }
	        set2.clear();
	        //make train set
	        for(int c = 0; c < this.df.getNumRows(); c++) {
	        	if(c >= this.df.getNumRows()) break;
	        	if(set1.contains(c)) {
	        		continue;
	        	}else {
	        		set2.add(c);
	        	}
	        	
	        }
	        Loggers.cv_Logger.log(Level.FINER,"TRAIN SET: "+ set2.size() + " TEST SET: "+set1.size());

	        trial = new TestTrainFit(this.df.shallowCopy_rowIndexes(set2), this.df.shallowCopy_rowIndexes(set1));
	        trials.add(trial);
	    }
	    this.trials = trials;
	    
	}
	//sum up all trial confusion matrixes
	private void sumConfusionMatrix() {
		Loggers.cv_Logger.log(Level.FINE,"Summing ConfusionMatrix");
		
		this.overall_accuaccy = new HashMap<String,Double>();
		this.overall_recall = new HashMap<String,Double>();
		this.overall_precision = new HashMap<String,Double>();
		this.overall_f1 = new HashMap<String,Double>();
		this.overall_mcc = new HashMap<String,Double>();
		
		HashMap<String, HashMap<Object, Integer>> tp = new HashMap<String, HashMap<Object, Integer>>();
		HashMap<String, HashMap<Object, Integer>> fp = new HashMap<String, HashMap<Object, Integer>>();
		HashMap<String, HashMap<Object, Integer>> tn = new HashMap<String, HashMap<Object, Integer>>();
		HashMap<String, HashMap<Object, Integer>> fn = new HashMap<String, HashMap<Object, Integer>>();
		
		matrix = new HashMap<String,HashMap<Object,HashMap<Object,Integer>>>();
		//Initialize
		for(String i : scores.get(0).getConfusionMatrix().truePositive.keySet()) {
			HashMap<Object, Integer> tmp0 = new HashMap<Object, Integer>();
			HashMap<Object, Integer> tmp1 = new HashMap<Object, Integer>();
			HashMap<Object, Integer> tmp2 = new HashMap<Object, Integer>();
			HashMap<Object, Integer> tmp3 = new HashMap<Object, Integer>();
			
			matrix.put(i, new HashMap<Object,HashMap<Object,Integer>>());
			for(Object j : scores.get(0).getConfusionMatrix().truePositive.get(i).keySet()) {
				tmp0.put(j, 0);
				tmp1.put(j, 0);
				tmp2.put(j, 0);
				tmp3.put(j, 0);
				matrix.get(i).put(j, new HashMap<Object,Integer>());
				
			}
			overall_recall.put(i, 0.0);
			overall_precision.put(i, 0.0);
			overall_f1.put(i, 0.0);
			overall_mcc.put(i, 0.0);
			tp.put(i, tmp0);
			fp.put(i, tmp1);
			tn.put(i, tmp2);
			fn.put(i, tmp3);
		}
		Loggers.cv_Logger.log(Level.FINE,"INITIALIZATION OF OVER MATIX COMPLETE");

		Loggers.cv_Logger.log(Level.FINE,"SUMMING SCORES");
		
		//sum
		for(Score i : scores) {
			ConfusionMatrix cm = i.getConfusionMatrix();
			for(String j : cm.falseNegative.keySet()) {
				for(Object z : cm.falseNegative.get(j).keySet()) {
					tp.get(j).replace(z, tp.get(j).get(z) + cm.truePositive.get(j).get(z));
					fp.get(j).replace(z, fp.get(j).get(z) + cm.falsePositive.get(j).get(z));
					tn.get(j).replace(z, tn.get(j).get(z) + cm.trueNegative.get(j).get(z));
					fn.get(j).replace(z, fn.get(j).get(z) + cm.falseNegative.get(j).get(z));
				}
			}
		}

		
		Loggers.cv_Logger.log(Level.FINE, "CONFUSION MATRIX SYNC COMPLETE");
		Loggers.cv_Logger.log(Level.CONFIG,"TRUE POSITIVES:"+"\n"+tp
				+"\n FALSE POSITIVES:"+"\n"+fp
				+"\n TRUE NEGATIVES:"+"\n"+tn
				+"\n FALSE NEGATIVES:"+"\n"+fn);

		ArrayList<HashMap<String,HashMap<Object,HashMap<Object,Integer>>>> matrix_c = new ArrayList<HashMap<String,HashMap<Object,HashMap<Object,Integer>>>>();
		//HashMap<String, HashMap<Object, HashMap<Object, Integer>>> matrix = new HashMap<String, HashMap<Object, HashMap<Object, Integer>>>();
		
		//initiallize
		for(Column i : df.target_columns) {
			matrix.put(i.getName(), new HashMap<Object, HashMap<Object, Integer>>());
			for(Object j : i.getUniqueValues()) {
				matrix.get(i.getName()).put(j, new HashMap<Object, Integer>());
				for(Object z : i.getUniqueValues()) {
					matrix.get(i.getName()).get(j).put(z, 0);
				}
			}
		}
		for(Score score : scores) {
			matrix_c.add(score.getConfusionMatrix().matrix);
		}
		//sum matricies
		//System.out.println("SUM MATRIX");
		for(HashMap<String, HashMap<Object, HashMap<Object, Integer>>> i : matrix_c) {
			for(String j : i.keySet()) {
				for(Object k : i.get(j).keySet()) {
					for(Object kk : i.get(j).get(k).keySet()) {
						matrix.get(j).get(k).put(kk, matrix.get(j).get(k).get(kk)+i.get(j).get(k).get(kk));
					}
				}
			}
		}
		//System.out.println("CROSS VAL");
		//System.out.println(matrix);
		
		//set
		confusion_matrix.setTruePositive(tp);
		confusion_matrix.setFalsePositive(fp);
		confusion_matrix.setTrueNegative(tn);
		confusion_matrix.setFalseNegative(fn);
		
		this.total_truePositive = tp;
		this.total_falsePositive = fp;
		this.total_trueNegative = tn;
		this.total_falseNegative = fn;
	}
	private void overallScores() {
		sumConfusionMatrix();
		//overall score storage
		HashMap<String, HashMap<Object, Double>> accuracy = new HashMap<String, HashMap<Object, Double>>();
		HashMap<String, HashMap<Object, Double>> recall = new HashMap<String, HashMap<Object, Double>>();
		HashMap<String, HashMap<Object, Double>> precision = new HashMap<String, HashMap<Object, Double>>();
		HashMap<String, HashMap<Object, Double>> F1 = new HashMap<String, HashMap<Object, Double>>();
		HashMap<String, HashMap<Object, Double>> mcc = new HashMap<String, HashMap<Object, Double>>();
		
		//initiallize
		HashMap<Object, Double> re;
		HashMap<Object, Double> pr;
		HashMap<Object, Double> f1;
		HashMap<Object, Double> mc;
		
		Loggers.cv_Logger.log(Level.FINER,"SCORES SET: "+ scores.get(0).recall.entrySet());
		for(String j : keyValues.keySet()) {
		//for(String j : df.target_columns) {
			re = new HashMap<Object,Double>();
			pr = new HashMap<Object,Double>();
			f1 = new HashMap<Object,Double>();
			mc = new HashMap<Object,Double>();
			
			for(Object z : keyValues.get(j)) {
				re.put(z, Scores.recall(total_truePositive.get(j).get(z), total_falseNegative.get(j).get(z)));
				pr.put(z, Scores.precision(total_truePositive.get(j).get(z), total_falsePositive.get(j).get(z)));
				f1.put(z, Scores.F1(total_truePositive.get(j).get(z), total_falsePositive.get(j).get(z), total_falseNegative.get(j).get(z)));
				mc.put(z, Scores.mcc(total_truePositive.get(j).get(z), total_trueNegative.get(j).get(z), total_falsePositive.get(j).get(z),total_falseNegative.get(j).get(z)));
				
				//System.out.println(scores.get(0).getConfusionMatrix().matrix);
			}
			Loggers.cv_Logger.log(Level.CONFIG,"CROSS"+f1);
			recall.put(j, re);	
			precision.put(j, pr);
			F1.put(j, f1);
			mcc.put(j, mc);
			
			
		}
		Loggers.cv_Logger.log(Level.CONFIG,"RECALL: \n"+ recall
				+ "\n PRECISION: \n"+precision
				+ "\n F1: \n"+F1
				+ "\n MCC: \n"+mcc);
		this.total_recall = recall;
		this.total_precision = precision;
		this.total_f1 = F1;
		this.total_mcc = mcc;
		
		double ree = 0;
		double pe = 0;
		double f = 0;
		double m = 0;
		for(String j : this.total_recall.keySet()) {
			ree = 0;
			pe = 0;
			f = 0;
			m = 0;
			for(Object i : this.total_recall.get(j).keySet()) {
				ree = ree + this.total_recall.get(j).get(i);
				pe = pe + this.total_precision.get(j).get(i);
				f = f + this.total_f1.get(j).get(i);
				m = m + this.total_mcc.get(j).get(i);
			}
			overall_recall.replace(j, ree/keyValues.get(j).size());
			overall_precision.replace(j, pe/keyValues.get(j).size());
			overall_f1.replace(j,f/keyValues.get(j).size());
			overall_mcc.replace(j, m/keyValues.get(j).size());

		}
	}
	/**
	 * Avg scores from all trials
	 */
	public void avgScores() {
		Loggers.cv_Logger.log(Level.FINE,"Averaging scores");
		
		//overall score storage
		HashMap<String, HashMap<Object, Double>> accuracy = new HashMap<String, HashMap<Object, Double>>();
		HashMap<String, HashMap<Object, Double>> recall = new HashMap<String, HashMap<Object, Double>>();
		HashMap<String, HashMap<Object, Double>> precision = new HashMap<String, HashMap<Object, Double>>();
		HashMap<String, HashMap<Object, Double>> F1 = new HashMap<String, HashMap<Object, Double>>();
		HashMap<String, HashMap<Object, Double>> mcc = new HashMap<String, HashMap<Object, Double>>();
	
		//initiallize
		HashMap<Object, Double> trial;
		HashMap<Object, Double> trial2;
		HashMap<Object, Double> trial3;
		HashMap<Object, Double> trial4;
		
		Loggers.cv_Logger.log(Level.FINER,"SCORES SET: "+ scores.get(0).recall.entrySet());
		for(String j : scores.get(0).recall.keySet()) {
		//for(String j : df.target_columns) {
			trial = new HashMap<Object,Double>();
			trial2 = new HashMap<Object,Double>();
			trial3 = new HashMap<Object,Double>();
			trial4 = new HashMap<Object,Double>();
			
			for(Object z : scores.get(0).recall.get(j).keySet()) {
				trial.put(z, (double) 0);
				trial2.put(z, (double) 0);
				trial3.put(z, (double) 0);
				trial4.put(z, (double) 0);

				//System.out.println(scores.get(0).getConfusionMatrix().matrix);
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
					//TODO

					//else {
						recall.get(j).replace(z, recall.get(j).get(z) + i.recall.get(j).get(z));
						precision.get(j).replace(z, precision.get(j).get(z) + i.precision.get(j).get(z));
						F1.get(j).replace(z, F1.get(j).get(z) + i.F1.get(j).get(z));
						mcc.get(j).replace(z, mcc.get(j).get(z) + i.mcc.get(j).get(z));
					//}
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
	}
	public void printScores() {
		int cnt = 0;
		for(Score i : scores) {
			System.out.println("TRIAL: " + cnt + "  ");
			i.printScore();
			cnt++;
		}
	}
	public ArrayList<TestTrainFit> getTrials() {
		return trials;
	}
	public void setTrials(ArrayList<TestTrainFit> trials) {
		this.trials = trials;
	}
	public HashMap<String, HashMap<Object, Double>> getTotal_recall() {
		return total_recall;
	}
	public void setTotal_recall(HashMap<String, HashMap<Object, Double>> total_recall) {
		this.total_recall = total_recall;
	}
	public HashMap<String, HashMap<Object, Double>> getTotal_precision() {
		return total_precision;
	}
	public void setTotal_precision(HashMap<String, HashMap<Object, Double>> total_precision) {
		this.total_precision = total_precision;
	}
	public HashMap<String, HashMap<Object, Double>> getTotal_f1() {
		return total_f1;
	}
	public void setTotal_f1(HashMap<String, HashMap<Object, Double>> total_f1) {
		this.total_f1 = total_f1;
	}
	public HashMap<String, HashMap<Object, Double>> getTotal_mcc() {
		return total_mcc;
	}
	public void setTotal_mcc(HashMap<String, HashMap<Object, Double>> total_mcc) {
		this.total_mcc = total_mcc;
	}
	public HashMap<String, HashMap<Object, Integer>> getTotal_truePositive() {
		return total_truePositive;
	}
	public void setTotal_truePositive(HashMap<String, HashMap<Object, Integer>> total_truePositive) {
		this.total_truePositive = total_truePositive;
	}
	public HashMap<String, HashMap<Object, Integer>> getTotal_falsePositive() {
		return total_falsePositive;
	}
	public void setTotal_falsePositive(HashMap<String, HashMap<Object, Integer>> total_falsePositive) {
		this.total_falsePositive = total_falsePositive;
	}
	public HashMap<String, HashMap<Object, Integer>> getTotal_trueNegative() {
		return total_trueNegative;
	}
	public void setTotal_trueNegative(HashMap<String, HashMap<Object, Integer>> total_trueNegative) {
		this.total_trueNegative = total_trueNegative;
	}
	public HashMap<String, HashMap<Object, Integer>> getTotal_falseNegative() {
		return total_falseNegative;
	}
	public void setTotal_falseNegative(HashMap<String, HashMap<Object, Integer>> total_falseNegative) {
		this.total_falseNegative = total_falseNegative;
	}
	public HashMap<String, Double> getOverall_recall() {
		return overall_recall;
	}
	public void setOverall_recall(HashMap<String, Double> overall_recall) {
		this.overall_recall = overall_recall;
	}
	public HashMap<String, Double> getOverall_precision() {
		return overall_precision;
	}
	public void setOverall_precision(HashMap<String, Double> overall_precision) {
		this.overall_precision = overall_precision;
	}
	public HashMap<String, Double> getOverall_f1() {
		return overall_f1;
	}
	public void setOverall_f1(HashMap<String, Double> overall_f1) {
		this.overall_f1 = overall_f1;
	}
	public HashMap<String, Double> getOverall_mcc() {
		return overall_mcc;
	}
	public void setOverall_mcc(HashMap<String, Double> overall_mcc) {
		this.overall_mcc = overall_mcc;
	}
	public int[][] getOverallMatrix(String target){
		int[][] m = new int[matrix.get(target).keySet().size()][matrix.get(target).keySet().size()];
		int cnt1 = 0;
		int cnt2;
		for(Object i : matrix.get(target).keySet()) {
			cnt2 = 0;
			for(Object j : matrix.get(target).get(i).keySet()) {
				m[cnt1][cnt2] = matrix.get(target).get(i).get(j);
				cnt2++;
			}
			cnt1++;
		}
		return m;
	}
	public void printMatrixs() {
		int cnt = 0;
		for(Score i : scores) {
			System.out.print("TRIAL: " + cnt + "  ");
			i.printMatrix();
			cnt++;
		}
	}
	public void printOverAllMatrix() {
		System.out.println("TRUE POSITIVES: "+this.total_truePositive);
		System.out.println("FALSE POSITIVES: "+this.total_falsePositive);
		System.out.println("TRUE NEGATIVES: "+this.total_trueNegative);
		System.out.println("FALSE NEGATIVES: "+this.total_falseNegative);
	}
	public void printOverAllScore() {
		System.out.println("RECALL:");
		System.out.println(this.overall_recall);
		System.out.println("PRECISION:");
		System.out.println(this.overall_precision);
		System.out.println("F1: ");
		System.out.println(this.overall_f1);
		System.out.println("MCC:");
		System.out.println(this.overall_mcc);
	}
	public void printDetailedScore() {
		System.out.println("RECALL:");
		System.out.println(this.total_recall);
		System.out.println("PRECISION:");
		System.out.println(this.total_precision);
		System.out.println("F1: ");
		System.out.println(this.total_f1);
		System.out.println("MCC:");
		System.out.println(this.total_mcc);
	}
	

}
