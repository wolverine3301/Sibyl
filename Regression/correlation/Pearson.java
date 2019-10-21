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
		return Stats.covariance(x, y) / Math.sqrt( (x.variance * y.variance ) );
	}

}
