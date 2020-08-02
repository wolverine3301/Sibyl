package scorer;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import dataframe.Column;
import dataframe.DataFrame;
import log.Loggers;
public class ConfusionMatrix {
	public HashMap<String, HashMap<Object, Integer>> truePositive;
	public HashMap<String, HashMap<Object, Integer>> falsePositive;
	public HashMap<String, HashMap<Object, Integer>> trueNegative;
	public HashMap<String, HashMap<Object, Integer>> falseNegative;
	
	private DataFrame df; //dataframe of correct answers
	private HashMap<String, ArrayList<Object>> predictions;
	public HashMap<String,HashMap<Object,HashMap<Object,Integer>>> matrix;
	HashMap<String,Set<Object>> keyValues;
	
	public ConfusionMatrix() {
		
	}
	
	/**
	 * construct a confusion matrix from a dataframe and predictions
	 * the matrix will compare the predictions to the actual values in the dataframe and tally right/wrong 
	 * @param df
	 * @param predictions
	 */
	public ConfusionMatrix(HashMap<String,Set<Object>> keyValues, DataFrame df, HashMap<String, ArrayList<Object>> predictions) {
		//System.out.println("CM: "+df.target_columns);
		Loggers.cm_Logger.log(Level.CONFIG, "Targets: " + df.numTargets + " Predictions: " + predictions.get(df.target_columns.get(0).getName()).size());
		Loggers.cm_Logger.log(Level.CONFIG,"SET "+keyValues.entrySet());
		this.keyValues = keyValues;
		this.df = df;
		this.predictions = predictions;
		truePositive = new HashMap<String, HashMap<Object, Integer>>();
		falsePositive = new HashMap<String, HashMap<Object, Integer>>();
		trueNegative = new HashMap<String, HashMap<Object, Integer>>();
		falseNegative = new HashMap<String, HashMap<Object, Integer>>();
		setTable();
		test_score();
		setMatrix();
	}
	
	/**
	 * initiallize matrix
	 */
	public void setMatrix() {
		Loggers.cm_Logger.entering("ConfusionMatrix", "setMatrix");
		
		matrix = new HashMap<String,HashMap<Object,HashMap<Object,Integer>>>();
		//initiallize
		for(String k : this.keyValues.keySet()) {
			matrix.put(k, new HashMap<Object,HashMap<Object,Integer>>());
			for(Object i : keyValues.get(k)) {
				matrix.get(k).put(i, new HashMap<Object,Integer>());
				for(Object j : keyValues.get(k)) {
					matrix.get(k).get(i).put(j, 0);
				}
			}
		}
		Loggers.cm_Logger.log(Level.FINE, "INITIALIZE MATRIX KEYSET: "+matrix.keySet());
		int cnt2 = 0;

		for(Column i : df.target_columns) {
			cnt2 = 0;
			for(Object j : predictions.get(i.getName())) {
				//System.out.println("CM: "+i.getName()+" "+predictions.keySet()+" "+predictions.get("Winner"));
				//true positives the diagonal of the matrix
				matrix.get(i.getName()).get(j).put(i.getParticle(cnt2).getValue(), matrix.get(i.getName()).get(j).get(i.getParticle(cnt2).getValue())+1);
				cnt2++;
			}
			Loggers.cm_Logger.log(Level.FINEST, matrix.toString());
		}
		Loggers.cm_Logger.exiting("ConfusionMatrix", "setMatrix");
	}
	/**
	 * set up confusion matrix arrays
	 * structure:
	 * truePositive[0] = 1st target column --> hashset { class1 : count , class2 : count ...}
	 * truePositive[1] = (optional)2nd targetColumn  --> hashset { class1 : count , class2 : count ...}
	 * truePositive[N] = Nth target column
	 */
	private void setTable() {
		Loggers.cm_Logger.entering("ConfusionMatrix", "setTable");
		int cnt = 0;
		for(String i : keyValues.keySet()) {
			HashMap<Object,Integer> arr0 = new HashMap<Object,Integer>();
			HashMap<Object,Integer> arr1 = new HashMap<Object,Integer>();
			HashMap<Object,Integer> arr2 = new HashMap<Object,Integer>();
			HashMap<Object,Integer> arr3 = new HashMap<Object,Integer>();
			for(Object j : keyValues.get(i)) {
				Loggers.cm_Logger.log(Level.FINEST, "ADDING TO TABLE: "+j);
				arr0.put(j, 0);
				arr1.put(j, 0);
				arr2.put(j, 0);
				arr3.put(j, 0);
				
			}
			truePositive.put(i,arr0);
			falsePositive.put(i,arr1);
			trueNegative.put(i,arr2);
			falseNegative.put(i,arr3);
		}
		Loggers.cm_Logger.log(Level.FINE, "ENTRIES: "+truePositive.entrySet());
		Loggers.cm_Logger.exiting("ConfusionMatrix", "setTable");
	}
	/**
	 * finds true positives and negatives
	 */
	private void test_score() {
		Loggers.cm_Logger.entering("ConfusionMatrix", "test_score");
		int cnt1 = 0;
		int cnt2;
		//for each target column
		for(String i : predictions.keySet()) {
			cnt2 = 0;
			//for each prediction for a target
			for (Object j : predictions.get(i)) {
				//if the predicted value matches the actual
				Loggers.cm_Logger.log(Level.FINEST,"Predicted: "+ j+" Actual: "+df.getColumn_byName(i).getParticle(cnt2).getValue());		
				if(j.equals(df.getColumn_byName(i).getParticle(cnt2).getValue())) {
					truePositive.get(i).replace(j, truePositive.get(i).get(j)+1);
					//update true negatives
					for(Object x : trueNegative.get(i).keySet()) {
						if(x.equals(j)) {
							continue;
						}else {					
							trueNegative.get(i).replace(x, trueNegative.get(i).get(x)+1);
						}
					}
				}
				//if its prediction isnt right
				else if(!j.equals(df.getColumn_byName(i).getParticle(cnt2).getValue())) {
					//update false positive count for the object predicted
					if(falsePositive.get(i).get(j)== null){
						falsePositive.get(i).put(j, 1);
					}else {
						falsePositive.get(i).replace(j, falsePositive.get(i).get(j)+1);
					}
					//update the actual values false negative count
					for(Object x : falseNegative.get(i).keySet()) {
						if(x.equals(j)){
							continue;
						}
						//finding the matching actual value in the map
						else if(x.equals(df.getColumn_byName(i).getParticle(cnt2).getValue())) {
							falseNegative.get(i).replace(x, falseNegative.get(i).get(x)+1);
							continue;
						}else {
							trueNegative.get(i).replace(x, trueNegative.get(i).get(x)+1);
						}
					}
				}
				cnt2++;
			}
			cnt1++;
		}
		Loggers.cm_Logger.log(Level.FINER, "TRUE POSITIVES: " + truePositive.entrySet());
		Loggers.cm_Logger.log(Level.FINER, "TRUE NEGATIVES: " + trueNegative.entrySet());
		Loggers.cm_Logger.log(Level.FINER, "FALSE POSITIVES: " + falsePositive.entrySet());
		Loggers.cm_Logger.log(Level.FINER, "FALSE NEGATIVES: " + falseNegative.entrySet());
		Loggers.cm_Logger.exiting("ConfusionMatrix", "test_score");
		
	}
	public void print_matrix() {
		System.out.println("True Positives: "+ truePositive.toString());
		System.out.println("False Positives: " + falsePositive.toString());
		System.out.println("True Negatives: " + trueNegative.toString());
		System.out.println("False Negatives: " + falseNegative.toString());	
	}
	public HashMap<String, HashMap<Object, Integer>> getTruePositive() {
		return truePositive;
	}
	public void setTruePositive(HashMap<String, HashMap<Object, Integer>> truePositive) {
		this.truePositive = truePositive;
	}
	public HashMap<String, HashMap<Object, Integer>> getFalsePositive() {
		return falsePositive;
	}
	public void setFalsePositive(HashMap<String, HashMap<Object, Integer>> falsePositive) {
		this.falsePositive = falsePositive;
	}
	public HashMap<String, HashMap<Object, Integer>> getTrueNegative() {
		return trueNegative;
	}
	public void setTrueNegative(HashMap<String, HashMap<Object, Integer>> trueNegative) {
		this.trueNegative = trueNegative;
	}
	public HashMap<String, HashMap<Object, Integer>> getFalseNegative() {
		return falseNegative;
	}
	public void setFalseNegative(HashMap<String, HashMap<Object, Integer>> falseNegative) {
		this.falseNegative = falseNegative;
	}
	
}
