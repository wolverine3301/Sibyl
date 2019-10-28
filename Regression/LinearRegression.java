

import dataframe.Column;
import examples.ColumnTools;
import forensics.Stats;
import particles.Particle;

/**
 * Linear regression with least squared distance
 * https://www.varsitytutors.com/hotmath/hotmath_help/topics/line-of-best-fit
 * @author logan.collier
 *
 */
public class LinearRegression extends Regression{
	
	private Column x;
	private Column y;
	public double slope;
	public double intercept;
	
	public String y_var;//names
	public String x_var; 
	public double RSS; //residual sum of squars = E(y_i - regressionPrediction(x_i))^2
	public double SST; //total sum of squares
	public double SSP; //sum of products
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
		this.intercept = ColumnTools.mean(y) - slope * ColumnTools.mean(x);
	}
	public double predictY(Particle x_val) {
		return (slope * (double) x_val.getValue()) + intercept;
	}

}
