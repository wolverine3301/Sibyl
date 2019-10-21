package correlation;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dataframe.Column;
import dataframe.DataFrame;

public class Correlations {
	 	
	private DataFrame num_df;
	private HashMap<String,Double> correlations;
	Correlation function;
	public Correlations(DataFrame df,Correlation function) {
		this.num_df = df.shallowCopy_columnIndexes(df.numericIndexes);
		this.function = function;
		correlations = new HashMap<String,Double>();
		getCorrelations();
	}
	private void getCorrelations() {
		HashMap<String, Double> corr = new HashMap<String, Double>();
		for(Column i : num_df.columns) {
			for(Column j : num_df.columns) {
				if(j == i) {
					continue;
				}else {
					corr.put(i.getName() + " vs. " + j.getName(), function.getCorrelation(i, j));
				}
			}
			
		}
		 // Create a list from elements of HashMap 
        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double> >(corr.entrySet()); 
  
        // Sort the list 
        Collections.sort(list, new Comparator<Map.Entry<String, Double> >() { 
            public int compare(Map.Entry<String, Double> o1,  
                               Map.Entry<String, Double> o2) 
            { 
                return ((Double)Math.abs(o1.getValue())).compareTo((Double)Math.abs(o2.getValue())); 
            } 
        });
        Collections.reverse(list);
        // put data from sorted list to hashmap  
        HashMap<String, Double> temp = new LinkedHashMap<String, Double>(); 
        for (Map.Entry<String, Double> aa : list) { 
            temp.put(aa.getKey(), aa.getValue()); 
        }
        this.correlations = temp;
	}
	public void printCorrelations() {
		System.out.println(correlations.toString());
	}

}
