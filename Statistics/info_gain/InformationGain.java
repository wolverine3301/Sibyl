package info_gain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import saga.*;

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
		targets = df.getColumnByTypes("target");
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
		Set<Object> target_info = targets.get(index).uniqueValues();
		Set<Object> attribute_info;
		//entropies
		Object[] tmp = targets.get(index).uniqueValCnt().values().toArray();
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
			for(Column c : df.columns) {
				if(c.type.contains("target")) {
					continue;
				}
				
				//values in column
				vals = new HashMap<Object,Integer>();
				attribute_info = c.uniqueValues();
				for(Object key2 : attribute_info) {					
					vals.put(key2, 0);
				}
				col.put(c.name,vals);
			}	
			joint_info.put(key1, col);		
		}
		//fill
		HashMap<String,HashMap<Object,Integer>> column_cnt = new HashMap<String,HashMap<Object,Integer>>();
		HashMap<String, HashMap<Object,Double>> columns_p = new HashMap<String, HashMap<Object,Double>>();
		
		
		for(Column c : df.columns) {
			if(c.type.contains("target")) {
				continue;
			}
			c.setFeatureStats();
			column_cnt.put(c.name,c.uniqueValCnt());
			columns_p.put(c.name, c.feature_stats);
			
			for(int j = 0; j < c.getLength();j++) {		
				joint_info.get(targets.get(index).getParticle_atIndex(j).getValue()).get(c.name).replace(c.getParticle_atIndex(j).getValue(), 
				        joint_info.get(targets.get(index).getParticle_atIndex(j).getValue()).get(c.name).get(c.getParticle_atIndex(j).getValue())+1);
				
			}
		}
		HashMap<String, Double> column_e = new HashMap<String,Double>();
		Integer[] classes = new Integer[joint_info.keySet().size()];
		
		int cnt = 0;
		for(Column c : df.columns) {
			if(c.type.contains("target")) {
				continue;
			}
			double GAIN = 0; 
			for( Object i : columns_p.get(c.name).keySet()) {
				cnt = 0;

				for(Object key1 : joint_info.keySet()) {
					classes[cnt] = joint_info.get(key1).get(c.name).get(i);
					cnt++;
				}
				GAIN = GAIN + ((-1) * columns_p.get(c.name).get(i)) * entropy(classes);
				
			}
			
			GAIN = target_e - GAIN;
			column_e.put(c.name, GAIN);
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
