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
			pred_y = ((double)x.getParticle(i).getValue() * slope) + intercept;
			sum = sum +  Math.abs(pred_y - (double)y.getParticle(i).getValue());
		}
		return sum / x.getLength();
	}

}
