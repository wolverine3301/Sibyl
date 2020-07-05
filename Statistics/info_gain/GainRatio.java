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
    public ArrayList<GainInformation> gain(int index) {
    	//if there are no categorical columns
		if(dataFrame.categorical_columns.size() == 0) {
			return null;
		}
		double targetEntropy = dataFrame.target_columns.get(index).entropy;
        //Holds the calculated info gain in a max heap style.
        PriorityQueue<GainInformation> gainRatios = new PriorityQueue<GainInformation>(dataFrame.numCategorical, new Comparator<GainInformation>() {
            @Override
            public int compare(GainInformation o1, GainInformation o2) {
                return Double.compare(o2.getInfoGain(), o1.getInfoGain());
            }
        });
        for (int i = 0; i < dataFrame.numCategorical; i++) { //Calculate info gain of every column compared to the target column
            double tempEntropy = dataFrame.categorical_columns.get(0).entropy;
            if (tempEntropy != 0) {//avoid deviding by 0.
                gainRatios.add(new GainInformation(i, (targetEntropy - tempEntropy) / tempEntropy)); //Only difference: add information gain devided by the temp entropy.
                //addInfo((targetEntropy - tempEntropy) / tempEntropy);
            } else
                gainRatios.add(new GainInformation(i, 0)); //Bad choice, no diversity in the column --- Only on rare occasions.
        }
        ArrayList<GainInformation> sortedGains = new ArrayList<GainInformation>();
        while(!gainRatios.isEmpty())
            sortedGains.add(gainRatios.remove());
        return sortedGains;
    }
    
}
