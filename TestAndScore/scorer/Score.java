package scorer;

import java.util.ArrayList;
import java.util.HashMap;

import dataframe.DataFrame;

/**
 * Various model scores
 * @author logan.collier
 *
 */
public class Score {
	/**
	 * recall - also known as true positive rate or sensitivity. how many of a class were correctly identified.
	 * if u say everyone has cancer recall is one because youve successful identified all people who actually have cancer
	 * if you guess 100 people have cancer and 75 actually do then recall is 0.75
	 * 
	 * precision - % of predictions 
	 */
	public HashMap<String, HashMap<Object, Double>> recall; 
	public HashMap<String, HashMap<Object, Double>> precision;
	public HashMap<String, HashMap<Object, Double>> F1;
	public HashMap<String, HashMap<Object, Double>> mcc;
	public HashMap<String, HashMap<Object, Double>> accuracy;
	
	private DataFrame df;
	private HashMap<String, ArrayList<Object>> predictions;
	private ConfusionMatrix matrix;
	/**
	 * Score constructor
	 * @param df
	 * @param predictions
	 */
	public Score(DataFrame df, HashMap<String, ArrayList<Object>> predictions) {
		this.df = df;
		this.predictions = predictions;
		
		recall = new HashMap<String, HashMap<Object, Double>>();
		precision = new HashMap<String, HashMap<Object, Double>>();
		F1 = new HashMap<String, HashMap<Object, Double>>();
		mcc = new HashMap<String, HashMap<Object, Double>>();
		matrix = new ConfusionMatrix(df, predictions);
		
		intitallize();
		score();
		
	}
	/**
	 * Calculate scores
	 */
	private void score() {
		double recallS;
		double precisionS;
		double F1S;
		double mccS;
		
		int cnt;
		for(String i : matrix.truePositive.keySet()) {
			
			recallS = 0;
			precisionS = 0;
			F1S = 0;
			mccS = 0;
			cnt = 0;
			for(Object j : matrix.truePositive.get(i).keySet()) {

				recall.get(i).replace(j,Scores.recall(matrix.truePositive.get(i).get(j).doubleValue(), matrix.falseNegative.get(i).get(j)));
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
		for(int i = 0; i < df.getNumColumns();i++) {
			//get target columns
			if(df.getColumn(i).getType() == 'T') {
				HashMap<Object,Double> arr0 = new HashMap<Object,Double>();
				HashMap<Object,Double> arr1 = new HashMap<Object,Double>();
				HashMap<Object,Double> arr2 = new HashMap<Object,Double>();
				HashMap<Object,Double> arr3 = new HashMap<Object,Double>();
				for(Object j : df.getColumn(i).getUniqueValues()) {
					arr0.put(j, 0.0);
					arr1.put(j, 0.0);
					arr2.put(j, 0.0);
					arr3.put(j, 0.0);
				}
				recall.put(df.getColumn(i).getName(),arr0);
				precision.put(df.getColumn(i).getName(),arr1);
				F1.put(df.getColumn(i).getName(),arr2);
				mcc.put(df.getColumn(i).getName(),arr3);
			}
		}
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
	public void printMatrix() {
		matrix.print_matrix();
	}
	
}


