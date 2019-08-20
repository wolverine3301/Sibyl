package forensics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import saga.*;

public class InformationGain {
	private DataFrame df;
	private List<Column> targets;
	
	public HashMap<String,Double> info_gains;
	
	public InformationGain(DataFrame df) {
		this.df = df;
		info_gains = new HashMap<String,Double>();
		

	}
	/**
	 * set target variables
	 */
	private void setTargets() {
		targets = df.getColumnByTypes("target");
	}
	public double gain(int index) {
		HashMap<Object, List<HashMap<Object,Integer>>> joint_info = new HashMap<Object, List<HashMap<Object,Integer>>>();
		Set<Object> target_info = targets.get(index).uniqueValues();
		Set<Object> attribute_info;
		//Initialize
		List<HashMap<Object, Integer>> col;
		HashMap<Object, Integer> vals;
		for(Object key1 : target_info) {
			//columns
			for(Column c : df.columns) {
				col = new ArrayList<HashMap<Object,Integer>>();
				attribute_info = c.uniqueValues();
				//unique vals in column
				for(Object key2 : attribute_info) {
					vals = new HashMap<Object,Integer>();
					vals.put(key2, 0);
					col.add(vals);
				}
				joint_info.put(key1, col);	
			}
			
		}
		
		
	}
	public double entropy(Integer[] p) {
		double e = 0;
		for(int i = 0; i < p.length;i++) {
			e = e + (-1)* ((double)p[i]/df.getLength()) * (Math.log10((double)p[i]/df.getLength()) / Math.log10(2));
		}
		return e;
		
	}

}
