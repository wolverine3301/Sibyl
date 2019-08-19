package appraiser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import saga.*;

public class Score {
	public HashMap<String, HashMap<Object, Double>> recall;
	public HashMap<String, HashMap<Object, Double>> precision;
	public HashMap<String, HashMap<Object, Double>> F1;
	public HashMap<String, HashMap<Object, Double>> mcc;
	
	private DataFrame df;
	private List<Object[]> predictions;
	private ConfusionMatrix matrix;
	public Score(DataFrame df, List<Object[]> predictions) {
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
				recall.get(i).replace(j,recall(matrix.truePositive.get(i).get(j), matrix.falseNegative.get(i).get(j)));
				precision.get(i).replace(j, precision(matrix.truePositive.get(i).get(j), matrix.falsePositive.get(i).get(j)));
				F1.get(i).replace(j, F1(matrix.truePositive.get(i).get(j), 
						matrix.falsePositive.get(i).get(j),
						matrix.falseNegative.get(i).get(j)));
				mcc.get(i).replace(j, mcc(matrix.truePositive.get(i).get(j), 
						matrix.trueNegative.get(i).get(j),
						matrix.falsePositive.get(i).get(j),
						matrix.falseNegative.get(i).get(j)));
				//sums for average over classes
				recallS = recallS + recall.get(i).get(j);
				precisionS = precisionS + precision.get(i).get(j);
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
	
	public double mcc(int tp, int tn, int fp, int fn) {
		return ( ( (tp * tn) - (fp * fn) ) / (Math.sqrt( (tp + fp)*(tp+fn)*(tn+fp)*(tn+fn) ) ) );	
	}
	public double F1(int tp, int fp, int fn) {
		return(2* (precision(tp,fp) * recall(tp,fn)) / (precision(tp,fp) + recall(tp,fn)));	
	}
	public double precision(int tp, int fp) {
		if(fp == 0) {
			return 0;
		}
		return (tp/(tp+fp));
	}
	public double recall(int tp, int fn) {
		if(fn == 0) {
			return 0;
		}
		return (tp/(tp+fn));	
	}
	/**
	 * initializing scores
	 */
	private void intitallize() {
		int cnt = 0;
		for(int i = 0; i < df.numColumns;i++) {
			//get target columns
			if(df.columnTypes.get(i) == "target") {
				HashMap<Object,Double> arr0 = new HashMap<Object,Double>();
				HashMap<Object,Double> arr1 = new HashMap<Object,Double>();
				HashMap<Object,Double> arr2 = new HashMap<Object,Double>();
				HashMap<Object,Double> arr3 = new HashMap<Object,Double>();
				for(Object j : df.getColumn_byIndex(cnt).uniqueValues()) {
					arr0.put(j, 0.0);
					arr1.put(j, 0.0);
					arr2.put(j, 0.0);
					arr3.put(j, 0.0);
					
				}
				
				recall.put(df.columnNames.get(i),arr0);
				precision.put(df.columnNames.get(i),arr1);
				F1.put(df.columnNames.get(i),arr2);
				mcc.put(df.columnNames.get(i),arr3);
			}
		}
	}
}


