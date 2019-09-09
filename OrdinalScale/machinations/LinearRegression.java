package machinations;

import java.util.HashMap;

import forensics.Pearson;
import forensics.Stats;
import saga.Column;
import saga.DataFrame;
import saga.Row;

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
		double[] slope = new double[super.trainDF.numColumns-super.targets.size()];
		int cnt = 0;
		for(Column i : super.trainDF.columns) {
			if(i.type.contentEquals("target")) {
				continue;
			}
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
		for(Column i : super.trainDF.columns) {
			if(i.type.contentEquals("target")) {
				continue;
			}
			b = b + regression_interceptB(target, i , slopes[cnt]);
		}
		return b / slopes.length;
	}
	/**
	 * Calculate slope intercept
	 * @param target
	 * @param x
	 * @return
	 */
	private double regression_interceptB(Column target, Column x,double slope) {
		return target.mean() - (slope * x.mean());
	}
	/**
	 * Calculate regression slope
	 * @param target
	 * @param x
	 * @return
	 */
	private double regression_slopeM(Column target, Column x) {
		return (stat.comean(target, x)  - (target.mean() * x.mean())) / (stat.squareMean(x) - Math.pow(x.mean(),2));
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
