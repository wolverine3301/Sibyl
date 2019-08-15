package forensics;

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
		return covariance()/(x.mean() * y.mean());
	}

}
