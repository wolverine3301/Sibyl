package info_gain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

import dataframe.Column;
import dataframe.DataFrame;


/**
 * Computes the Gini Impurity of the categorical columns within a data frame.
 * @author Cade Reynoldson
 * @version 1.0
 */
public class GiniIndex extends Gain{

    /**
     * Creates a new instance of GiniIndex.
     * @param theDataFrame the data frame to compute the gini impurity of.
     */
    public GiniIndex(DataFrame theDataFrame) {
        super(theDataFrame);
    }

    /**
     * Returns a "sorted" array list of columns, with the sorting rule being the columns with the highest gini index (impurity).
     * NOTE: PASS 0 TO THE PARAMETER OF THIS FUNCTION. 
     */
    @Override
    public ArrayList<Column> gain(int index) {
    	//if there are no categorical columns
		if(super.categoricalColumns == null) {
			return null;
		}
        PriorityQueue<GainInformation> infoGain = new PriorityQueue<GainInformation>(categoricalColumns.getNumColumns(), new Comparator<GainInformation>() {
            @Override
            public int compare(GainInformation o1, GainInformation o2) {
                return Double.compare(o2.getInfoGain(), o1.getInfoGain());
            }
        });
        for (int i = 0; i < categoricalColumns.getNumColumns(); i++) {
            HashMap<Object, Double> probabilities = categoricalColumns.getColumn(i).getFeatureStats();
            double gain = 0;
            for (Object o : probabilities.keySet()) {
                double prob = probabilities.get(o);
                gain += prob * (1 - prob);
            }
            infoGain.add(new GainInformation(i, gain));
            System.out.println("GINI IMPURITY OF COLUMN " + categoricalColumns.getColumn(i).getName() + ": " + gain);
        }
        ArrayList<Column> sortedColumnGains = new ArrayList<Column>();
        while (!infoGain.isEmpty()) {
            sortedColumnGains.add(categoricalColumns.getColumn(infoGain.remove().getIndex()));
        }
        return sortedColumnGains;
    }
}
