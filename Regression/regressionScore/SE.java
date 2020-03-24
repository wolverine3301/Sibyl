package regressionScore;

import dataframe.Column;
import forensics.Stats;
import regressionFunctions.Regression;

public class SE {
	
	private double SE; //standard error
	
	/**
	 * SE = SQRT( [MSE / n*2] ) / SQRT((x_i - x_mean)^2)
	 * @param x
	 * @param y
	 * @param function
	 */
	private void setSE(Column x, Column y,Regression function) {
		double sum1 = 0;
		
		for(int i = 0; i < x.getLength(); i++) {
			sum1 = sum1 + Math.pow((y.getDoubleValue(i) - function.predictY(x.getParticle(i))),2);
		}
		this.SE = Math.sqrt(sum1/(x.getLength()-2)) / Math.sqrt(Stats.zeroSquaredSum(x));
		
	}
	

}
