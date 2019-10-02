package info_gain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import dataframe.Column;
import dataframe.ColumnTools;
import dataframe.DataFrame;


/**
 * Computes a modified version of the information gain algorithm. Note: Target columns must be marked before trying to run algorithm.
 * @author Cade Reynoldson 
 * @author Logan Collier
 * @version 1.0
 */
public class InformationGain extends Gain{
    
	/**
	 * Creates and initializes all data neccesary for the algorithm.
	 * @param df the data frame to compute info gain on.
	 */
	public InformationGain(DataFrame df) {
	    super(df);
	}
	
	/**
	 * Implementation of information gain. Computes the gain of all categorical variables to the given index of a 
	 * target in the targets list. 
	 * @param index the index of the target in the target data frame.
	 * @return a "sorted" array list of columns, with the lowest index (0) representing most gain, and highest index (list.size() - 1) representing least gain.
	 */
	public ArrayList<Column> gain(int index) {
	    double targetEntropy = entropy(ColumnTools.uniqueValCnt(targetColumns.getColumn_byIndex(index)));
	    //Holds the calculated info gain in a max heap style.
	    PriorityQueue<GainInformation> infoGain = new PriorityQueue<GainInformation>(categoricalColumns.getNumColumns(), new Comparator<GainInformation>() {
            @Override
            public int compare(GainInformation o1, GainInformation o2) {
                return Double.compare(o2.getInfoGain(), o1.getInfoGain());
            }
	    });
	    for (int i = 0; i < categoricalColumns.getNumColumns(); i++) { //Calculate info gain of every column compared to the target column
            HashMap<Object, Integer> uniqueColumn = ColumnTools.uniqueValCnt(categoricalColumns.getColumn_byIndex(i));
            double tempEntropy = entropy(uniqueColumn);
            infoGain.add(new GainInformation(i, targetEntropy - tempEntropy));
	    }
	    ArrayList<Column> sortedGains = new ArrayList<Column>();
	    while(!infoGain.isEmpty())
	        sortedGains.add(categoricalColumns.getColumn_byIndex(infoGain.remove().getIndex()));
	    return sortedGains;
	}
}
