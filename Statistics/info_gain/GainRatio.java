package info_gain;

import java.util.ArrayList;
import java.util.Comparator; 
import java.util.PriorityQueue;

import dataframe.Column;
import dataframe.DataFrame;

/**
 * Gain Ratio algorithm. The main difference of this algorithm is the information gain is divided by the entropy of the calculated column.
 * @author Cade Reynoldson
 * @version 1.0
 */
public class GainRatio extends Gain{
    
    public GainRatio(DataFrame theDataFrame) {
        super(theDataFrame);
    }

    /**
     * Computes the gain ratio of every column in relation to the target column.
     * @param the index of the column in the TARGET data frame to compare entropies with.
     * @return 
     */
    @Override
    public ArrayList<Column> gain(int index) {
        double targetEntropy = targetColumns.getColumn(index).entropy;
        //Holds the calculated info gain in a max heap style.
        PriorityQueue<GainInformation> gainRatios = new PriorityQueue<GainInformation>(categoricalColumns.getNumColumns(), new Comparator<GainInformation>() {
            @Override
            public int compare(GainInformation o1, GainInformation o2) {
                return Double.compare(o2.getInfoGain(), o1.getInfoGain());
            }
        });
        for (int i = 0; i < categoricalColumns.getNumColumns(); i++) { //Calculate info gain of every column compared to the target column
            double tempEntropy = categoricalColumns.getColumn(i).entropy;
            if (tempEntropy != 0) //avoid deviding by 0.
                gainRatios.add(new GainInformation(i, (targetEntropy - tempEntropy) / tempEntropy)); //Only difference: add information gain devided by the temp entropy.
            else
                gainRatios.add(new GainInformation(i, 0)); //Bad choice, no diversity in the column --- Only on rare occasions.
        }
        ArrayList<Column> sortedGains = new ArrayList<Column>();
        while(!gainRatios.isEmpty())
            sortedGains.add(categoricalColumns.getColumn(gainRatios.remove().getIndex()));
        return sortedGains;
    }
    
}
