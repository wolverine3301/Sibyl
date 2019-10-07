package regressionFunctions;


import dataframe.Column;
import dataframe.ColumnTools;
import particles.Particle;

/**
 * 
 * @author logan.collier
 * Logrithmic regression
 * http://mathworld.wolfram.com/LeastSquaresFittingLogarithmic.html
 *
 */
public class LogRegression extends Regression{
	
	private double sum_ylnx = 0;
	private double sum_lnx = 0;
	private double sum_lnx_squared = 0;
	
	public LogRegression(Column x, Column y) {
		this.x = x;
		this.y = y;
		this.y_var = y.getName();
		this.x_var = x.getName();
		setVars();
		this.slope = log_slopeM();
		this.intercept = intercept_b();
	}
	/**
	 * return the slop of the best fitted logrithm using least squares fitting , E y_i  - b * E(ln(x_i)) / n
	 * @return
	 */
	public double log_slopeM() {
		return ((x.getLength() * sum_ylnx) - (y.sum * sum_lnx)) / ((x.getLength() * sum_lnx_squared) - Math.pow(sum_lnx, 2));
	}
	public double intercept_b() {
		return (y.sum - (log_slopeM() * sum_lnx)) / x.getLength();
	}
	private void setVars() {
		double x_1 = 0;
		double y_1 = 0;
		for(int i = 0; i < x.getLength(); i++) {
			if(x.getParticle(i).getValue() instanceof Double) {
				x_1 = (double)x.getParticle(i).getValue();
			}
			else if(x.getParticle(i).getValue() instanceof Integer) {
				x_1 = (int)x.getParticle(i).getValue();
			}
			if(y.getParticle(i).getValue() instanceof Double) {
				y_1 = (double)y.getParticle(i).getValue();
			}
			else if(y.getParticle(i).getValue() instanceof Integer) {
				y_1 = (int)y.getParticle(i).getValue();
			}
			sum_ylnx = sum_ylnx + y_1 * Math.log(x_1);
			sum_lnx = sum_lnx + Math.log(x_1);
			sum_lnx_squared = sum_lnx_squared + Math.pow(Math.log(x_1), 2);
		}
	}
	
	public double predictY(Particle x_val) {
		return (slope * Math.log((double) x_val.getValue())) + intercept;
	}

}
