package regressionScore;

import dataframe.Column;
import regressionFunctions.Regression;

public class SE {
	
	private double se; //standard error
	
	/**
	 * SE = SQRT( [MSE / n*2] ) / SQRT((x_i - x_mean)^2)
	 * @param x
	 * @param y
	 * @param function
	 */
	private void setSE(Column x, Column y,Regression function) {
		double sum = 0;
		for(int i = 0; i < x.getLength(); i++) {
			sum = sum + (y.getDoubleValue(i) - function.predictY(x.getParticle(i))
		}
		
	}
	

}
