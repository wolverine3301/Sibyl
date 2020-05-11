package correlation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dataframe.Column;
import dataframe.DataFrame;
/**
 * Performs a specified correlation coeffecient on entire data frame
 * @author logan.collier
 *
 */
public class Correlations {
	 	
	private ArrayList<Column> num_df;
	private HashMap<String,Double> correlations;
	Correlation function;
	
	public Correlations(DataFrame df,Correlation function) {
		this.num_df = df.numeric_columns;
		this.function = function;
		correlations = new HashMap<String,Double>();
		setCorrelations();
	}
	private void setCorrelations() {
		HashMap<String, Double> corr = new HashMap<String, Double>();
		for(Column i : num_df) {
			for(Column j : num_df) {
				if(j == i) {
					continue;
				}else {
					if(corr.containsKey(j.getName() + " vs. " + i.getName())) {
						continue;
					}else {
						corr.put(i.getName() + " vs. " + j.getName(), function.getCorrelation(i, j));
					}
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
	/**
	 * returns map of features and their correlation
	 * @return HashMap<String, Double>
	 */
	public HashMap<String, Double> getCorrelations() {
		return this.correlations;
	}
	public void printCorrelations() {
		System.out.println(correlations.toString());
	}

}
