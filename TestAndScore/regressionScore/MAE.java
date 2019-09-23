package regressionScore;

import dataframe.Column;

/**
 * Mean Absolute Error
 *  Mean Absolute Error (MAE) is the average vertical distance between each point and the regression line
 * @author logan.collier
 *
 */
public class MAE {
	public static double mae(Column x,Column y , double slope, double intercept) {
		double pred_y;
		double sum = 0;
		for(int i = 0; i < x.getLength(); i++) {
			pred_y = ((double)x.getParticle_atIndex(i).getValue() * slope) + intercept;
			sum = sum +  Math.abs((double)y.getParticle_atIndex(i).getValue() - pred_y);
		}
		return sum / x.getLength();
	}

}
