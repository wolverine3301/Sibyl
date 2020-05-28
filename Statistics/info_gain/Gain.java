package info_gain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Copy;

public abstract class Gain {
    
    /** The columns to rank in regards to the target columns. */
    DataFrame categoricalColumns;
    
    /** The target columns for the algorithms to run off of. */
    DataFrame targetColumns;
    
    public ArrayList<Double> info;
    /**
     * Sets up the fields for use in gain methods.
     * @param theDataFrame the data frame to format for gain based calculations.
     */
    public Gain(DataFrame theDataFrame) {
    	if(theDataFrame.numCategorical < 1) {
    		
    	}else {
    		categoricalColumns = DataFrame_Copy.shallowCopy_columnTypes(theDataFrame, setVariables());
    		targetColumns = DataFrame_Copy.shallowCopy_columnTypes(theDataFrame, setTargets());
    		info = new ArrayList<Double>();
    	}
    }
    
    /**
     * Calculates the gain of using the child class' implementation.
     * Possible Calls:
     * - Information Gain
     * - Gain Ratio
     * - Gini Index
     * @param index the index of the target in the target columns data frame.
     * @return an array list of columns, with the best gain at the lowest index, and worst gain at the highest index.
     */
    public abstract ArrayList<Column> gain(int index);
    
    /**
     * Sets the targets list.
     * @return a tree set of targets to predict.
     */
    private TreeSet<Character> setTargets() {
        TreeSet<Character> target = new TreeSet<Character>();
        target.add('T');
        return target;
    }
    
    /**
     * Sets the variables list.
     * @return a tree set of the variables used to train data.
     */
    private TreeSet<Character> setVariables() {
        TreeSet<Character> vars = new TreeSet<Character>();
        vars.add('C');
        return vars;
    }
    
    
    /**
     * Calculates the entropy of a column created hashmap.
     * @param instanceCounts the instances of a column mapped to an integer which represents their quantity.
     * @return The entropy of the row.
     */
    public double entropy(HashMap<Object, Integer> instanceCounts) {
        double entropy = 0;
        double log_2 = Math.log(2);
        double totalInstances = 0;
        for (Object i : instanceCounts.keySet()) {
            totalInstances += instanceCounts.get(i);
        }
      System.out.println("Instance counts: " + instanceCounts + " Total Instances: " + totalInstances);
        for (Object i : instanceCounts.keySet()) {
            double ratio = ((double) instanceCounts.get(i)) / totalInstances;
            entropy -=  (ratio) * (Math.log(ratio) / log_2);
        }
      System.out.println("Entropy: " + entropy);
        return entropy;
    }
    protected void addInfo(double e) {
    	info.add(e);
    }
    /**
     * Used for optimizing entropy calculations & fetching columns.
     * @author Cade Reynoldson
     * @version 1.0
     */
    public class GainInformation {
        
        /** The index of the column in which the entropy was calculated to. */
        private int columnIndex;
        
        /** The information gained */
        private double infoGain;
        
        /**
         * Creates a new instance of this (no fucking shit)
         * @param theColumnIndex COLUMN INDEX
         * @param theInfoGain THE INFO GAIN??!?!?!?!?
         */
        public GainInformation(int theColumnIndex, double theInfoGain) {
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
}
