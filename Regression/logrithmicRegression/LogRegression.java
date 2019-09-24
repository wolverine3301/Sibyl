package logrithmicRegression;

import dataframe.Column;
import dataframe.ColumnTools;

/**
 * 
 * @author logan.collier
 * Logrithmic regression
 * http://mathworld.wolfram.com/LeastSquaresFittingLogarithmic.html
 *
 */
public class LogRegression {
	
	private Column x;
	private Column y;
	private double sum_ylnx = 0;
	private double sum_lnx = 0;
	private double sum_lnx_squared = 0;
	
	public LogRegression(Column x, Column y) {
		this.x = x;
		this.y = y;
		setVars();
	}
	/**
	 * return the slop of the best fitted logrithm using least squares fitting , E y_i  - b * E(ln(x_i)) / n
	 * @return
	 */
	public double log_slopeM() {
		return ((x.getLength() * sum_ylnx) - (ColumnTools.sum(y) * sum_lnx)) / ((x.getLength() * sum_lnx_squared) - Math.pow(sum_lnx, 2));
	}
	public double intercept_b() {
		return (ColumnTools.sum(y) - (log_slopeM() * sum_lnx)) / x.getLength();
	}
	private void setVars() {
		for(int i = 0; i < x.getLength(); i++) {	
			sum_ylnx = sum_ylnx + (double)y.getParticle(i).getValue() * Math.log((double) x.getParticle(i).getValue());
			sum_lnx = sum_lnx + Math.log((double) x.getParticle(i).getValue());
			sum_lnx_squared = sum_lnx_squared + Math.pow(Math.log((double) x.getParticle(i).getValue()), 2);
		}
	}

}
