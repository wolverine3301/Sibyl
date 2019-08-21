package forensics;

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
		HashMap<Object, HashMap<String, HashMap<Object,Integer>>> joint_info = new HashMap<Object, HashMap<String, HashMap<Object,Integer>>>();
		Set<Object> target_info = targets.get(index).uniqueValues();
		Set<Object> attribute_info;
		//entropies
		double target_e = entropy((Integer[])targets.get(index).uniqueValCnt().values().toArray());
		HashMap<String, HashMap<Object,Double>> columns_p = new HashMap<String, HashMap<Object,Double>>();
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
		HashMap<Object,Double> val_prob;
		
		for(Column c : df.columns) {
			if(c.type.contains("target")) {
				continue;
			}
			val_prob = new HashMap<Object,Double>();
			HashMap<Object, Integer> val_cnt = c.uniqueValCnt();
			//calculate value probs
			for(Entry<Object, Integer> i : val_cnt.entrySet()) {
				val_prob.put(i.getKey(), (double)i.getValue()/c.getLength());
			}
			columns_p.put(c.name, val_prob);
			for(int j = 0; j < c.getLength();j++) {		
				joint_info.get(targets.get(index).getParticle_atIndex(j).getValue()).get(c.name).replace(c.getParticle_atIndex(j).getValue(), joint_info.get(targets.get(index).getParticle_atIndex(j).getValue()).get(c.name).get(c.getParticle_atIndex(j).getValue())+1);
				
			}
	
		}
		HashMap<String, Double> column_e = new HashMap<String,Double>();
		double GAIN;
		for(Column c : df.columns) {
			if(c.type.contains("target")) {
				continue;
			}
			GAIN = 0; 
			for(Object key1 : joint_info.keySet()) {
				GAIN = GAIN + (-1) * 
				
				
				
				
			
			}
		}
	}
	public double gaincol(Column c, int index) {
		double target_e = entropy((Integer[])targets.get(index).uniqueValCnt().values().toArray());
		
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
