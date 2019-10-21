package correlation;

import dataframe.Column;
import forensics.Stats;
/**
 * Abstract correlation
 * @author logan.collier
 *
 */
public abstract class Correlation {
	Stats stat = new Stats();
	
	public Correlation() {
	}

	/**
	 * @return the correlation coefficient
	 */
	public abstract double getCorrelation(Column x, Column y);

}
