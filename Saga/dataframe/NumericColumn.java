package dataframe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

import particles.DoubleParticle;
import particles.IntegerParticle;
import particles.Particle;

public class NumericColumn extends Column{
	public double sum;
	public double mean;
	public double median;
	public double mode;
	public double variance;
	public double std;
	public double entropy;
    
	public NumericColumn(Column theColumn) {
		super(theColumn);
		setSum();
		// TODO Auto-generated constructor stub
	}
    /**
     * Calculates the sum of a numeric column. Returns 0 if column is non numeric.
     * @param column the column to preform calculations on.
     * @return the sum of a numeric column.
     */
    public void setSum() {
    	//long sum = IntStream.of(array).parallel().sum();
        double sum = 0;
           for(int i = 0;i < column.size();i++) {
               if (column.get(i) instanceof DoubleParticle)
                   sum += (Double) column.get(i).getValue();
               else
                   sum += (Integer) column.get(i).getValue();
            }
        this.sum = sum;
    }
    /**
     * Calculates the mean of a column.
     * @param column the column to preform calculations on.
     * @return the mean of a column.
     */
    public void setMean() {
        this.mean =  sum / column.size();  
    }
    /**
     * Calculates the entropy of a column.
     * @param theColumn the column to preform calculations on.
     * @return the entropy of a column.
     */
    public void setEntropy() {
        HashMap<Object,Integer> values = uniqueValCnt;
        double ent = 0;
        for (Integer value : values.values()) {
            ent = ent + (-1)* ((double)value / column.size()) * ( Math.log10(((double)value / column.size())) / Math.log10(2));
        }
        this.entropy  = ent;
    }
    /**
     * Calculates the variance of a column.
     * @param theColumn the column to preform calculations on.
     * @return The variance of a column.
     */
    public void setVariance() {
        double var = 0.0;
        for(int i = 0;i < column.size();i++) {
            var += Math.pow(((Double)column.get(i).getValue() - mean), 2);
        }
        this.variance = var / column.size();
    }
    /**
     * Calculates the median value in a column. Returns the mode if column is non numeric.
     * @param theColumn the column to preform calculations on.
     * @return the median value of a column if column is numeric, mode if column is non numeric.
     */
    public void setMedian() {
        List<Particle> sorted = new ArrayList<Particle>();
        for (int i = 0; i < column.size(); i++) {
            sorted.add(column.get(i).deepCopy());
        }
        Collections.sort(sorted);
        this.median  = (double) sorted.get(sorted.size() / 2).getValue();
    }
    /**
     * Calculates the standard deviation of a column.
     * @param theColumn the column to preform calculations on.
     * @return the standard deviation of a column.
     */
    public void setStandardDeviation() {
        this.std = Math.sqrt(variance);       
    }
    /**
     * Returns the sum of a given set of indexes.
     * @param theColumn the column to preform calculations on.
     * @param indexes the set of indexes.
     * @return the sum of a given set of indexes.
     */
    public static double sumOfIndexes(Column theColumn, Set<Integer> indexes) {
        double sum = 0; 
        if (theColumn.getType() == 'N') {
            for (Integer i : indexes) {
                Particle p = theColumn.getParticle(i);
                if (p instanceof DoubleParticle)
                    sum += (Double) p.getValue();
                else if (p instanceof IntegerParticle)
                    sum += (Integer) p.getValue();
            }
        }
        return sum;
    }
    /**
     * Returns the mean of a given set of indexes.
     * @param theColumn the column to preform calculations on.
     * @param indexes the set of indexes.
     * @return the sum of a given set of indexes.
     */
    public static double meanOfIndexes(Column theColumn, Set<Integer> indexes) {
        double sum = sumOfIndexes(theColumn, indexes);
        return sum / indexes.size();
    }

}
