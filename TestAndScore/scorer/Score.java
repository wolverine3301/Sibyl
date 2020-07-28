package scorer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;

import dataframe.DataFrame;
import log.Loggers;

/**
 * Various model scores
 * @author logan.collier
 *
 */
public class Score {
	/**
	 * recall - also known as true positive rate or sensitivity. how many of a class were correctly identified.
	 * if u say everyone has cancer ,recall = one because youve successful identified all people who actually have cancer.
	 * if you guess 75 people have cancer and 100 actually do then recall is 0.75
	 * 
	 * precision - % of predictions for a class that was correct
	 * 
	 */
	/** targetname -> klass -> score **/
	public HashMap<String, HashMap<Object, Double>> recall; 
	public HashMap<String, HashMap<Object, Double>> precision;
	public HashMap<String, HashMap<Object, Double>> F1;
	public HashMap<String, HashMap<Object, Double>> mcc;
	public HashMap<String, HashMap<Object, Double>> accuracy;
	
	private DataFrame df; //the test data frame
	private HashMap<String,Set<Object>> keyValues; //all values in the original df in case a trial df doesnt contain a particular value
	private HashMap<String, ArrayList<Object>> predictions; //list of predictions
	private ConfusionMatrix matrix; //confusion matrix
	/**
	 * Score constructor
	 * @param df
	 * @param predictions
	 */
	public Score(HashMap<String,Set<Object>> keyValues, DataFrame df, HashMap<String, ArrayList<Object>> predictions) {
		this.df = df;
		this.predictions = predictions;
		this.keyValues = keyValues;
		Loggers.score_Logger.log(Level.CONFIG, "TARGET CLASSES: "+ keyValues.entrySet()+ " # Predictions: "+ predictions.size());
		recall = new HashMap<String, HashMap<Object, Double>>();
		precision = new HashMap<String, HashMap<Object, Double>>();
		F1 = new HashMap<String, HashMap<Object, Double>>();
		mcc = new HashMap<String, HashMap<Object, Double>>();
		matrix = new ConfusionMatrix(keyValues,df, predictions);
		
		intitallize();
		score();
		
	}
	/**
	 * Calculate scores
	 */
	private void score() {
		Loggers.score_Logger.log(Level.INFO,"BEGIN SCORING"); 
		double recallS;
		double precisionS;
		double F1S;
		double mccS;

		int cnt;
		Loggers.score_Logger.log(Level.FINE,"MATRIX KEYSET: "+matrix.truePositive.keySet()); 
		for(String i : matrix.truePositive.keySet()) {
			
			recallS = 0;
			precisionS = 0;
			F1S = 0;
			mccS = 0;
			cnt = 0;
			for(Object j : matrix.truePositive.get(i).keySet()) {
				Loggers.score_Logger.log(Level.FINER,"MATRIX SUB-KEYSET: "+matrix.truePositive.get(i).keySet()); 
				Loggers.score_Logger.log(Level.FINEST,"MATRIX VALUE: "+matrix.truePositive.get(i).get(j)); 
				recall.get(i).replace(j,Scores.recall(matrix.truePositive.get(i).get(j), matrix.falseNegative.get(i).get(j)));
				precision.get(i).replace(j, Scores.precision(matrix.truePositive.get(i).get(j), matrix.falsePositive.get(i).get(j)));
				F1.get(i).replace(j, Scores.F1(matrix.truePositive.get(i).get(j), 
						matrix.falsePositive.get(i).get(j),
						matrix.falseNegative.get(i).get(j)));
				mcc.get(i).replace(j, Scores.mcc(matrix.truePositive.get(i).get(j), 
						matrix.trueNegative.get(i).get(j),
						matrix.falsePositive.get(i).get(j),
						matrix.falseNegative.get(i).get(j)));
				//sums for average over classes
				recallS = recallS + recall.get(i).get(j);
				F1S = F1S + F1.get(i).get(j);
				mccS = mccS + mcc.get(i).get(j);
				precisionS = precisionS + precision.get(i).get(j);
				Loggers.score_Logger.log(Level.FINER, "RECALL: "+recallS+"\n"
						+"F1: "+F1S+"\n"
						+"MCC: "+mccS+"\n"
						+"PRECISION: "+precisionS);
				cnt++;
			}
			recallS = recallS/cnt;
			precisionS = precisionS/cnt;
			F1S = F1S/cnt;
			mccS = mccS/cnt;
			
			recall.get(i).put("OverAll", recallS);
			precision.get(i).put("OverAll", precisionS);
			F1.get(i).put("OverAll", F1S);
			mcc.get(i).put("OverAll", mccS);
		}
		
	}

	/**
	 * initializing scores
	 */
	private void intitallize() {
		for(String i : keyValues.keySet()) {
			HashMap<Object,Double> arr0 = new HashMap<Object,Double>();
			HashMap<Object,Double> arr1 = new HashMap<Object,Double>();
			HashMap<Object,Double> arr2 = new HashMap<Object,Double>();
			HashMap<Object,Double> arr3 = new HashMap<Object,Double>();
			for(Object j : keyValues.get(i)) {
				Loggers.score_Logger.log(Level.FINER,"INIT: "+i+ " -> "+j); 
				arr0.put(j, 0.0);
				arr1.put(j, 0.0);
				arr2.put(j, 0.0);
				arr3.put(j, 0.0);
			}
			recall.put(i,arr0);
			precision.put(i,arr1);
			F1.put(i,arr2);
			mcc.put(i,arr3);
			}
		
	}
	public ConfusionMatrix getConfusionMatrix() {
		return this.matrix;
	}
	
	/**
	 * prints scores
	 */
	public void printScore() {
		System.out.println("Recall: " + recall.toString());
		System.out.println("Precision: " + precision.toString());
		System.out.println("F1: " + F1.toString());
		System.out.println("MCC: " + mcc.toString());
	}
	public String toString() {
		return "Recall: " + recall.toString()+"\n"
				+"Precision: " + precision.toString() +"\n"
				+"F1: " + F1.toString()+"\n"
				+"MCC: " + mcc.toString()+"\n";
	}
	public void printMatrix() {
		matrix.print_matrix();
	}
	public void printComparison() {
		for(String i : this.predictions.keySet()) {
			for(int j = 0; j < predictions.get(i).size(); j++) {
				System.out.println("Actual: "+ df.getColumn_byName(i).getParticle(j).getValue() +" Predicted: "+ predictions.get(i).get(j));
			}
		}
	}
	
}


