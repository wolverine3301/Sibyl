package correlation;

import forensics.Stats;
import saga.Column;

public class Pearson extends Correlation{

	public Pearson(Column x, Column y) {
		super(x, y);
	}
	/**
	 * Pearson correlation
	 */
	@Override
	public double correlation() {
		return Stats.covariance(x,y)/(x.mean() * y.mean());
	}

}
