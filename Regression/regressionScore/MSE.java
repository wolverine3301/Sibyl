package regressionScore;

import dataframe.Column;

/**
 * 
 * @author logan.collier
 * measures the average of the squares of the errors, that is the distance between an actual point and the regression line
 * values closer to zero are better.
 * The MSE is a measure of the quality of an estimator
 */
public class MSE {
	private Column x;
	private Column y;
	private double slope;
	private double intercept;
	public double MSE;
	/**
	 * 
	 * @param x - Column x of a independent variable
	 * @param y - Column y, dependent variable to be predicted
	 * @param slope - slop of regression
	 * @param intercept - regression intercept
	 */
	public MSE(Column x,Column y , double slope, double intercept) {
		this.x = x;
		this.y = y;
		this.slope = slope;
		this.intercept = intercept;
	}
	/**
	 * 
	 * @return the MSE
	 */
	public static double mse(Column x,Column y , double slope, double intercept) {
		double pred_y;
		double sum = 0;
		for(int i = 0; i < x.getLength(); i++) {
			pred_y = ((double)x.getParticle(i).getValue() * slope) + intercept;
			sum = sum +  Math.pow( ((double)y.getParticle(i).getValue() - pred_y),2);
		}
		return sum / x.getLength();
	}

}
