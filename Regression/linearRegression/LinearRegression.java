package linearRegression;

import java.util.HashMap;

import correlation.Pearson;
import dataframe.Column;
import dataframe.DataFrame;
import dataframe.Row;
import forensics.Stats;
import machinations.Model;

/**
 * Linear regression with single value decompesition
 * @author logan.collier
 *
 */
public class LinearRegression extends Model{
	private Stats stat = new Stats();
	/**
	 * simple linear regression
	 * @param theDataFrame
	 */
	public LinearRegression(DataFrame theDataFrame) {
		super(theDataFrame);
		
	}
	/**
	 * optimizing start scalar for linear decomposition
	 * @return
	 */
	private double[] optimization_scalar_y() {
		Pearson pearsonCorr = new Pearson(null, null);
		double[] corr = pearsonCorr.correlationMatrix();
	}
	
	private double optimization_scalar_x(double[] means) {
		double sum = 0;
		for(int i = 0; i < means.length; i++) {
			sum += means[i];
		}
		return Math.sqrt(sum);
	}
	
	/**
	 * regression lines for columns
	 * @param target
	 * @return
	 */
	private double[] multi_regression_slope(Column target) {
		
		double[] slope = new double[super.trainDF_variables.numColumns-super.trainDF_targets.numColumns];
		int cnt = 0;
		for(Column i : super.trainDF_variables.columns) {
			slope[cnt] = regression_slopeM(target, i);
			cnt++;
		}
		return slope;
	}
	
	/**
	 * average slope intercept
	 * @param target
	 * @param slopes
	 * @return
	 */
	private double multi_regreesion_intercept(Column target, double[] slopes) {
		double b = 0;
		int cnt = 0;
		for(Column i : super.trainDF_variables.columns) {
			b = b + regression_interceptB(target, i , slopes[cnt]);
			cnt++;
		}
		return b                                   ;
	}
	/**
	 * Calculate slope intercept
	 * @param target
	 * @param x
	 * @return
	 */
	private double regression_interceptB(Column target, Column x,double slope) {
		return ((target.sum() * stat.squareMean(x)) - (x.sum() * stat.sumMultiple_Columns(target, x) )) / 
				((x.getLength() * stat.squareMean(x)) - Math.pow(x.sum(),2));
	}
	/**
	 * Calculate regression slope with Singular value decomposition
	 * @param target
	 * @param x
	 * @return
	 */
	private double regression_slopeM(Column target, Column x) {
		return ((x.getLength() * stat.sumMultiple_Columns(target, x)) - (target.sum() * x.sum())) /
				( (x.getLength() * stat.squareMean(x)) - Math.pow(x.sum(), 2));
	}
	@Override
	public HashMap<Object, Double> probability(Row row) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object predict(Row row) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
