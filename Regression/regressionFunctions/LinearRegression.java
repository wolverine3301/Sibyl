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
		super(x, y,"Linear");
		setRegression();
		setMeasures();
	}
	@Override
	public void setRegression() {
		super.coefficents = new double[2];
		this.SP = Stats.zeroSumMultiple_Columns(super.x, super.y); //sum of products
		this.SST = Stats.zeroSquaredSum(x);
		setRegression_slopeM();
		setRegression_interceptB();
		super.coefficents[0] = this.intercept;
		super.coefficents[1] = this.slope;
		
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

	public double predictY(Particle x_val) {
		return (slope * x_val.getDoubleValue()) + intercept;
	}
	@Override
	public String getEquation() {
		return "Y = "+ super.coefficents[1] +"X" + " + "+ super.coefficents[0];
	}
	@Override
	protected void set_se_ofCoefficents() {
		super.coefficent_se = new double[2];
		double f = super.RMSD / super.x.getLength();
		super.coefficent_se[0] = f  * ( 1+(Math.pow(x.mean, 2)/x.variance) );
		super.coefficent_se[1] = f * (1/x.std);
		
	}
	@Override
	protected void set_T_ofCoeffiecents() {
		super.coefficent_t_scores = new double[2];
		super.coefficent_t_scores[0] = super.coefficents[0] / super.coefficent_se[0];
		super.coefficent_t_scores[1] = super.coefficents[1] / super.coefficent_se[1];
	}
	@Override
	protected void setDegFree() {
		super.degree_freedom = super.x.getLength()-1;
	}

}
