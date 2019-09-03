package appraiser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import saga.*;
public class ConfusionMatrix {
	public HashMap<String, HashMap<Object, Integer>> truePositive;
	public HashMap<String, HashMap<Object, Integer>> falsePositive;
	public HashMap<String, HashMap<Object, Integer>> trueNegative;
	public HashMap<String, HashMap<Object, Integer>> falseNegative;
	
	private DataFrame df;
	private List<Object[]> predictions;
	private List<Column> targets; //list of target columns
	
	public ConfusionMatrix(DataFrame df, List<Object[]> predictions) {
		this.df = df;
		this.predictions = predictions;
		truePositive = new HashMap<String, HashMap<Object, Integer>>();
		falsePositive = new HashMap<String, HashMap<Object, Integer>>();
		trueNegative = new HashMap<String, HashMap<Object, Integer>>();
		falseNegative = new HashMap<String, HashMap<Object, Integer>>();
		setTable();
		test_score();
		
	}
	/**
	 * set up confusion matrix arrays
	 * structure:
	 * truePositive[0] = 1st target column --> hashset { class1 : count , class2 : count ...}
	 * truePositive[1] = (optional)2nd targetColumn  --> hashset { class1 : count , class2 : count ...}
	 * truePositive[N] = Nth target column
	 */
	private void setTable() {
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
				truePositive.put(df.columnNames.get(i),arr0);
				falsePositive.put(df.columnNames.get(i),arr1);
				trueNegative.put(df.columnNames.get(i),arr2);
				falseNegative.put(df.columnNames.get(i),arr3);
			}
		}
	}
	/**
	 * finds true positives and negatives
	 */
	private void test_score() {
		int cnt1 = 0;
		int cnt2;
		//for each target column
		for(Object[] i : predictions) {
			cnt2 = 0;
			//for each prediction for a column
			for (Object j : i) {
				//if the predicted value matches the actual
				if(j.equals(targets.get(cnt1).getParticle_atIndex(cnt2).getValue())) {
					truePositive.get(targets.get(cnt1).name).replace(j, truePositive.get(targets.get(cnt1).name).get(j)+1);
					for(Object x : trueNegative.get(targets.get(cnt1).name).keySet()) {
						if(x.equals(j)) {
							continue;
						}else {
						
						trueNegative.get(targets.get(cnt1).name).replace(x, trueNegative.get(targets.get(cnt1).name).get(x)+1);
						
						}
					}
				}
				//if its prediction isnt right
				else if(!j.equals(targets.get(cnt1).getParticle_atIndex(cnt2).getValue())) {
					//update false positive count
					falsePositive.get(targets.get(cnt1).name).replace(j, falsePositive.get(targets.get(cnt1).name).get(j)+1);
					
					for(Object x : falseNegative.get(targets.get(cnt1).name).keySet()) {
						
						if(x.equals(targets.get(cnt1).getParticle_atIndex(cnt2).getValue())) {
							falseNegative.get(targets.get(cnt1).name).replace(x, falseNegative.get(targets.get(cnt1).name).get(x)+1);
							
							continue;
						}
						else if(x.equals(j)){
							
							continue;
						}else {
							
							trueNegative.get(targets.get(cnt1).name).replace(x, trueNegative.get(targets.get(cnt1).name).get(x)+1);
						}
					}
				}
				cnt2++;
			}
			cnt1++;
		}
		
	}
	public void print_matrix() {
		
		System.out.println("True Positives: "+ truePositive.toString());
		System.out.println("False Positives: " + falsePositive.toString());
		System.out.println("True Negatives: " + trueNegative.toString());
		System.out.println("False Negatives: " + falseNegative.toString());
		
	}

	
	

}
