package info_gain;

import java.util.HashMap;
import java.util.List;

import dataframe.Column;
import dataframe.ColumnTools;
import dataframe.DataFrame;

import java.util.Set;


public class InformationGain {
	private DataFrame df;
	private List<Column> targets;
	
	public HashMap<String,Double> info_gains;
	
	public InformationGain(DataFrame df) {
		this.df = df;
		info_gains = new HashMap<String,Double>();
		setTargets();
		

	}
	/**
	 * set target variables
	 */
	private void setTargets() {
		targets = df.getColumnByTypes('T');
	}
	/**
	 * 
	 * @param index - for multiple target variables choose the index of the list for one
	 * @return
	 */
	public void gain(int index) {
		
		// first keys are the values that are in the target column,
		// second keys are column names
		// third keys are unique vals in the column that point to count of occuances with a specific target variable
		
		HashMap<Object, HashMap<String, HashMap<Object,Integer>>> joint_info = new HashMap<Object, HashMap<String, HashMap<Object,Integer>>>();
		Set<Object> target_info = ColumnTools.uniqueValues(targets.get(index));
		Set<Object> attribute_info;
		//entropies
		Object[] tmp = ColumnTools.uniqueValCnt(targets.get(index)).values().toArray();
		Integer[] te = new Integer[tmp.length];
		for(int i = 0; i < tmp.length; i++) {
			te[i] = (Integer) tmp[i];
		}
		
		double target_e = entropy(te);
		
		//Initialize
		HashMap<String , HashMap<Object, Integer>> col;
		HashMap<Object, Integer> vals;
		for(Object key1 : target_info) {
			//columns
			col = new HashMap<String, HashMap<Object,Integer>>();
			for(int i = 0; i < df.getNumColumns(); i++) {
			    Column c = df.getColumn_byIndex(i);
				if(c.getType() == 'T') {
					continue;
				}
				
				//values in column
				vals = new HashMap<Object,Integer>();
				attribute_info = ColumnTools.uniqueValues(c);
				for(Object key2 : attribute_info) {					
					vals.put(key2, 0);
				}
				col.put(c.getName(),vals);
			}	
			joint_info.put(key1, col);		
		}
		//fill
		HashMap<String,HashMap<Object,Integer>> column_cnt = new HashMap<String,HashMap<Object,Integer>>();
		HashMap<String, HashMap<Object,Double>> columns_p = new HashMap<String, HashMap<Object,Double>>();
		
		
		for(int i = 0; i < df.getNumColumns(); i++) {
		    Column c = df.getColumn_byIndex(i);
			if(c.getType() == 'T') {
				continue;
			}
			c.setFeatureStats();
			column_cnt.put(c.getName(), ColumnTools.uniqueValCnt(c));
			columns_p.put(c.getName(), c.getFeatureStats());
			
			for(int j = 0; j < c.getLength();j++) {		
				joint_info.get(targets.get(index).getParticle(j).getValue()).get(c.getName()).replace(c.getParticle(j).getValue(), 
				        joint_info.get(targets.get(index).getParticle(j).getValue()).get(c.getName()).get(c.getParticle(j).getValue())+1);
				
			}
		}
		HashMap<String, Double> column_e = new HashMap<String,Double>();
		Integer[] classes = new Integer[joint_info.keySet().size()];
		
		int cnt = 0;
		for(int i = 0; i < df.getNumColumns(); i++) {
		    Column c = df.getColumn_byIndex(i);
			if(c.getType() == 'T') {
				continue;
			}
			double GAIN = 0; 
			for(Object j : columns_p.get(c.getName()).keySet()) {
				cnt = 0;

				for(Object key1 : joint_info.keySet()) {
					classes[cnt] = joint_info.get(key1).get(c.getName()).get(i);
					cnt++;
				}
				GAIN = GAIN + ((-1) * columns_p.get(c.getName()).get(i)) * entropy(classes);
				
			}
			
			GAIN = target_e - GAIN;
			column_e.put(c.getName(), GAIN);
		}
		System.out.println(column_e);
	}
	
	public double entropy(Integer[] p) {
		double e = 0;
		int sum =0;
		for(int i = 0; i < p.length;i++) {
			sum = sum + p[i];
		}
		for(int i = 0; i < p.length;i++) {
			e = e + (-1)* ((double)p[i]/sum) * (Math.log10((double)p[i]/sum) / Math.log10(2));
		}
		return e;
		
	}

}
