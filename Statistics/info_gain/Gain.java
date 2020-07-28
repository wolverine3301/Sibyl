package info_gain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Copy;

public abstract class Gain {
    
    protected DataFrame dataFrame; 
    
    public static final double LOG_2 = Math.log(2);
    
    public ArrayList<Double> info;
    
    /**
     * Sets up the fields for use in gain methods.
     * @param theDataFrame the data frame to format for gain based calculations.
     */
    public Gain(DataFrame theDataFrame) {
        dataFrame = theDataFrame; 
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
     * Calculates the gain of using the child class' implementation.
     * Possible Calls:
     * - Information Gain
     * - Gain Ratio
     * - Gini Index
     * @param index the index of the target in the target columns data frame.
     * @return an array list of columns, with the best gain at the lowest index, and worst gain at the highest index.
     */
    public abstract ArrayList<GainInformation> gain_nodes(int index);
    
    
    /**
     * Calculates the entropy of a column created hashmap.
     * @param instanceCounts the instances of a column mapped to an integer which represents their quantity.
     * @return The entropy of the row.
     */
    public double entropy(HashMap<Object, Integer> instanceCounts) {
        double entropy = 0;
        double totalInstances = 0;
        for (Object i : instanceCounts.keySet()) {
            totalInstances += instanceCounts.get(i);
        }
        for (Object i : instanceCounts.keySet()) {
            double ratio = ((double) instanceCounts.get(i)) / totalInstances;
            entropy -=  (ratio) * (Math.log(ratio) / LOG_2);
        }
        return entropy;
    }
    
    /**
     * Calculates the conditional entropy of a categorical column, and categorical target. 
     * @param catg column. 
     * @param target
     * @return
     */
    public double conditionalEntropy(Column catgColumn, Column target) {
        //IG(T, A) = H(T) - H(T | a)
        //H(X | Y) = SUM ( P(Y, X) * log (P(Y, X) / P(Y)) )
        HashMap<Object, Integer> catgCounts = catgColumn.getUniqueValueCounts();
        HashMap<Object, Integer> targetCounts = target.getUniqueValueCounts();
        double condEntropy = 0;
        for (int i = 0; i < catgColumn.getLength(); i++) {
            double py = catgCounts.get(catgColumn.getParticle(i).getValue());
            //Use chi2, test for independence. 
            double pyx = catgCounts.get(catgColumn.getParticle(i).getValue()) * targetCounts.get(target.getParticle(i).getValue()); //JOINT PROBABILITY - INDEPENDENT ASSUMPTION, MAY NEED UPDATE. 
            condEntropy -= py * (Math.log(pyx / py) / LOG_2);
        }
        return condEntropy;
    }
    
    protected void addInfo(double e) {
    	info.add(e);
    }
    
    /**
     * Returns a new instance of a gain algorithm to be ran on a seperate data frame.
     * Use when an instance of a gain algorithm is to be used again, but on a different dataframe. 
     * @param df the new df to create a gain algo class of. 
     * @return a new instance of a gain algorithm to be ran on the parameterized data frame. 
     */
    public Gain getGainAlgorithm(DataFrame df) {
        if (this instanceof GainRatio)
            return new GainRatio(df);
        else if (this instanceof InformationGain)
            return new InformationGain(df);
        else if (this instanceof GiniIndex)
            return new GiniIndex(df);
        else
            return null;
    }
}
