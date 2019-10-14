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
		this.SSP = Stats.zeroSumMultiple_Columns(x, y); //sum of products
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
		this.slope =  Stats.zeroSumMultiple_Columns(x, y) / Stats.zeroSquaredSum(x);
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
	public double predictY(Particle x_val) {
		return (slope * (double) x_val.getValue()) + intercept;
	}

}
