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
	
	public double slope;
	public double intercept;
	
	public LinearRegression(Column x, Column y) {
		super(x, y);
		setRegression();
	}
	@Override
	public void setRegression() {
		super.function = new double[2];
		this.SP = Stats.zeroSumMultiple_Columns(super.x, super.y); //sum of products
		this.SST = Stats.zeroSquaredSum(x);
		setRegression_slopeM();
		setRegression_interceptB();
		super.function[0] = this.intercept;
		super.function[1] = this.slope;
	}

	/**
	 * least squares method
	 * @param y
	 * @param x
	 * @return
	 */
	private void setRegression_slopeM() {
		this.slope = this.SP / this.SST;
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
	private void standard_error() {
		for(int i = 0; i < y.getLength();i++) {
			
		}
	}

	public double predictY(Particle x_val) {
		return (slope * (double) x_val.getValue()) + intercept;
	}
	@Override
	public String getEquation() {
		return "Y = "+ super.function[1] +"X" + " + "+ super.function[0];
	}





}
