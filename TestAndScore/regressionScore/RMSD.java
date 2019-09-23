package regressionScore;

import dataframe.Column;

/**
 * root-mean-square deviation
 * measure of the differences between values (sample or population values) predicted by a model or an estimator and the values observed.
 * RMSD is a measure of accuracy, to compare forecasting errors of different models for a particular dataset and not between datasets
 * @author logan.collier
 *
 */
public class RMSD {
	
	public static double rmsd(Column x, Column y, double slope, double intercept) {
		return Math.sqrt(MSE.mse(x,y,slope,intercept));
	}

}
