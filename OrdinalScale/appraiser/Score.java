package appraiser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import saga.*;

public class Score {
	public HashMap<String, HashMap<Object, Integer>> recall;
	public HashMap<String, HashMap<Object, Integer>> precision;
	public HashMap<String, HashMap<Object, Integer>> F1;
	public HashMap<String, HashMap<Object, Integer>> mcc;

	private List<Column> targets;
	private DataFrame df;
	private List<Object[]> predictions;
	private HashMap<String, HashMap<Object, Integer>> actuals; //number of actual counts
	private ConfusionMatrix matrix;
	public Score(DataFrame df, List<Object[]> predictions) {
		this.df = df;
		this.predictions = predictions;
		
		recall = new HashMap<String, HashMap<Object, Integer>>();
		precision = new HashMap<String, HashMap<Object, Integer>>();
		F1 = new HashMap<String, HashMap<Object, Integer>>();
		mcc = new HashMap<String, HashMap<Object, Integer>>();
		matrix = new ConfusionMatrix(df, predictions);
		
	}
	
	public double mcc(int tp, int tn, int fp, int fn) {
		return ( ( (tp * tn) - (fp * fn) ) / (Math.sqrt( (tp + fp)*(tp+fn)*(tn+fp)*(tn+fn) ) ) );
		
	}
	public double F1(int tp, int fp, int fn) {
		return(2* (precision(tp,fp) * recall(tp,fn)) / (precision(tp,fp) + recall(tp,fn)));	
	}
	public double precision(int tp, int fp) {
		return (tp/(tp+fp));
	}
	public double recall(int tp, int fn) {
		return (tp/(tp+fn));	
	}
	private void intitallize() {
		targets = new ArrayList<Column>();
		int cnt = 0;
		for(int i = 0; i < df.numColumns;i++) {
			//get target columns
			if(df.columnTypes.get(i) == "target") {
				targets.add(df.getColumn_byIndex(i)); //target columns
				HashMap<Object,Integer> arr0 = new HashMap<Object,Integer>();
				HashMap<Object,Integer> arr1 = new HashMap<Object,Integer>();
				HashMap<Object,Integer> arr2 = new HashMap<Object,Integer>();
				HashMap<Object,Integer> arr3 = new HashMap<Object,Integer>();
				for(Object j : targets.get(cnt).uniqueValues()) {
					arr0.put(j, 0);
					arr1.put(j, 0);
					arr2.put(j, 0);
					arr3.put(j, 0);
					
				}
				actuals.put(df.columnNames.get(i),targets.get(cnt).uniqueValCnt());
				
				recall.put(df.columnNames.get(i),arr0);
				precision.put(df.columnNames.get(i),arr1);
				F1.put(df.columnNames.get(i),arr2);
				mcc.put(df.columnNames.get(i),arr3);
			}
		}
	}
}


