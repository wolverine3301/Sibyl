package correlation;


import dataframe.Column;
import forensics.Stats;
/**
 * Pearson correlation coeffecent
 * @author logan.collier
 *
 */
public class Pearson extends Correlation{
	public Pearson() {
		
	}
	/**
	 * Pearson correlation coefficient 
	 */
	public double getCorrelation(Column x, Column y) {
		return Stats.zeroSumMultiple_Columns(x, y)  / Math.sqrt( Stats.zeroSquaredSum(x) * Stats.zeroSquaredSum(y) );
	}

}
