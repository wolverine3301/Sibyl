package correlation;

import dataframe.Column;

/**
 * @author logan.collier
 * is a measure of how well the relationship between two variables can be described by a monotonic function.
 *
 */
public class Spearman extends Correlation{

	public Spearman(Column x, Column y) {
		super(x, y);
	}

	@Override
	public double correlation() {
		return 1 - (rankDifference() / (x.getLength() * ( Math.pow(x.getLength(), 2) - 1) ));
	}
	
	private double d_Squared(double d) {
		return Math.pow(d, 2);
	}
	//calculates the sum of difference of ranked variables
	public double rankDifference() {
		double d_squared = 0;
		for(int i = 0; i < x.getLength(); i++) {
			d_squared = d_squared +  d_Squared(rankX[i] - rankY[i]);
		}
		return 6 * d_squared;
	}
	public Column rank_x() {
		Column X_i = x.makeColumn_fromArray(arr);
		for(int i = 0; i < x.getLength(); i++) {
			
		}
		
		
	}

}
