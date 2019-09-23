package linearRegression;

import java.util.HashMap;

import correlation.Pearson;
import dataframe.Column;
import dataframe.DataFrame;
import dataframe.Row;
import forensics.Stats;
import machinations.Model;

/**
 * Linear regression with least squared distance
 * https://www.varsitytutors.com/hotmath/hotmath_help/topics/line-of-best-fit
 * @author logan.collier
 *
 */
public class LinearRegression{
	
	private Column x;
	private Column y;
	public double slope;
	public double intercept;
	public double SST; //total sum of squares
	public double RSS; //residual sum of squares
	
	/**
	 * simple linear regression
	 * @param theDataFrame
	 */
	public LinearRegression(Column y, Column x) {
		this.x = x;
		this.y = y;
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
		this.intercept = y.mean() - slope * x.mean();
	}

}
