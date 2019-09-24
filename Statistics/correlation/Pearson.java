package correlation;

import dataframe.Column;
import dataframe.ColumnTools;
import forensics.Stats;

public class Pearson extends Correlation{

	public Pearson(Column x, Column y) {
		super(x, y);
	}
	/**
	 * Pearson correlation coefficient 
	 */
	@Override
	public double correlation() {
		return Stats.covariance(x,y)/(ColumnTools.mean(x) * ColumnTools.mean(y));
	}

}
