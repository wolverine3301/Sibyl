package regressionFunctions;

import dataframe.Column;
import forensics.Stats;
import particles.Particle;

/**
 * Linear regression with least squared distance
 * https://www.varsitytutors.com/hotmath/hotmath_help/topics/line-of-best-fit
 * @author logan.collier
 *
 */
public class LinearRegression extends Regression{
	/**
	 * simple linear regression
	 * @param theDataFrame
	 */
	public LinearRegression(Column x, Column y) {
		this.x = x;
		this.y = y;
		this.y_var = y.getName();
		this.x_var = x.getName();
		this.SP = Stats.zeroSumMultiple_Columns(x, y); //sum of products
		this.SST = Stats.zeroSquaredSum(x);
		setRegression_slopeM();
		setRegression_interceptB();
	}
	
	/**
	 * least squares method
	 * @param y
	 * @param x
	 * @return
	 */
	private void setRegression_slopeM() {
		this.slope =  this.SP / this.SST;
	}
	/**
	 * set intercept
	 * @param y
	 * @param x
	 * @param slope
	 */
	private void setRegression_interceptB() {
		this.intercept = y.mean - slope * x.mean;
	}
	/**
	 * set residual sum of squares / sum squares estimate error
	 * & regression sum of squares / explained sum of squares
	 */
	private void setSSE_SSR() {
		double sum1 = 0;
		double sum2 = 0;
		for(int i = 0; i < y.getLength();i++) {
			sum1 = sum1 + Math.pow( (y.getDoubleValue(i) - predictY(x.getParticle(i))), 2);
			sum2 = sum2 + Math.pow((predictY(x.getParticle(i)) - y.mean), 2);
		}
		this.SSE = sum1;
		this.SSR = sum2;
		this.R2 = 1 - (this.SSE / this.SST);
	}

	public double predictY(Particle x_val) {
		return (slope * (double) x_val.getValue()) + intercept;
	}

}
