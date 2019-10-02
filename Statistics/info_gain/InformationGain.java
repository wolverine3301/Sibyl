package info_gain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import dataframe.Column;
import dataframe.ColumnTools;
import dataframe.DataFrame;

import java.util.Set;

/**
 * Computes a modified version of the information gain algorithm. Note: Target columns must be marked before trying to run algorithm.
 * @author Cade Reynoldson 
 * @author Logan Collier
 * @version 1.0
 */
public class InformationGain {
	
    /** The data frame to compute info gain on. */
    private DataFrame df;
	
	/** The target columns. */
	private List<Column> targets;
	
	/** Not used by cade's implentation */
	public HashMap<String,Double> info_gains;
	
	/**
	 * Creates and initializes all data neccesary for the algorithm.
	 * @param df the data frame to compute info gain on.
	 */
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
	 * Implementation of information gain. Computes the gain of all categorical variables to the given index of a 
	 * target in the targets list. 
	 * @param index the index of the target in the target list.
	 * @return a "sorted" array list of columns, with the lowest index (0) representing most gain, and highest index (list.size() - 1) representing least gain.
	 */
	public ArrayList<Column> infoGain(int index) {
	    HashMap<Object, Integer> targetInfo = ColumnTools.uniqueValCnt(targets.get(index));
	    double targetEntropy = entropy(targetInfo);
	    //Holds the calculated info gain in a max heap style.
	    PriorityQueue<EntropyData> infoGain = new PriorityQueue<EntropyData>(df.getNumColumns() - targets.size(), new Comparator<EntropyData>() {
            @Override
            public int compare(EntropyData o1, EntropyData o2) {
                return Double.compare(o2.getInfoGain(), o1.getInfoGain());
            }
	    });
	    for (int i = 0; i < df.getNumColumns(); i++) { //Calculate info gain of every column compared to the target column
	        if (df.getColumn_byIndex(i).getType() == 'C') {
	            HashMap<Object, Integer> uniqueColumn = ColumnTools.uniqueValCnt(df.getColumn_byIndex(i));
	            double tempEntropy = entropy(uniqueColumn);
	            infoGain.add(new EntropyData(i, targetEntropy - tempEntropy));
	        }
	    }
	    ArrayList<Column> sortedGains = new ArrayList<Column>();
	    while(!infoGain.isEmpty())
	        sortedGains.add(df.getColumn_byIndex(infoGain.remove().getIndex()));
	    return sortedGains;
	}
	
	/**
	 * Used for optimizing entropy calculations & fetching columns.
	 * @author Cade Reynoldson
	 * @version 1.0
	 */
	private class EntropyData {
	    
	    /** The index of the column in which the entropy was calculated to. */
	    private int columnIndex;
	    
	    /** The information gained */
	    private double infoGain;
	    
	    /**
	     * Creates a new instance of this (no fucking shit)
	     * @param theColumnIndex COLUMN INDEX
	     * @param theInfoGain THE INFO GAIN??!?!?!?!?
	     */
	    public EntropyData(int theColumnIndex, double theInfoGain) {
	        columnIndex = theColumnIndex;
	        infoGain = theInfoGain;
	    }
	    
	    /**
	     * Returns the index of the column.
	     * @return the index of the column.
	     */
	    public int getIndex() {
	        return columnIndex;
	    }
	    
	    /**
	     * Returns the info gain.
	     * @return the info gain.
	     */
	    public double getInfoGain() {
	        return infoGain;
	    }
	    
	    /**
	     * Creates a string representation of the entropy data.
	     * @return a string representation of the entropy data.
	     */
	    @Override
	    public String toString() {
	        return "Column Index: " + columnIndex + " - Info Gain: " + infoGain;
	    }
	}
	
	public double entropy(HashMap<Object, Integer> instanceCounts) {
	    double entropy = 0;
	    double log_2 = Math.log(2);
        double totalInstances = 0;
        for (Object i : instanceCounts.keySet()) {
            totalInstances += instanceCounts.get(i);
        }
//	    System.out.println("Instance counts: " + instanceCounts + " Total Instances: " + totalInstances);
        for (Object i : instanceCounts.keySet()) {
            double ratio = ((double) instanceCounts.get(i)) / totalInstances;
            entropy -=  (ratio) * (Math.log(ratio) / log_2);
        }
//	    System.out.println("Entropy: " + entropy);
        return entropy;
    }
	
	
	
	
	
	/**
	 * Leaving this here for future reference (if needed)
	 * @param index - for multiple target variables choose the index of the list for one
	 * @return
	 */
	public void loganGain(int index) {
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
			e = e + (-1)* ((double)p[i]/sum) * ((Math.log10((double)p[i]/sum)) / Math.log10(2));
		}
		return e;
	}

}
