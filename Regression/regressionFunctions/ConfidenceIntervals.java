package regressionFunctions;

import particles.Particle;

/**
 * confidence intervals for regressions
 * 
 * for E(y | x*) , the avg expected value of y for a given x* is
 * 
 * ~y = +- t*_n-2 * s_y * sqrt( 1/n + ( (x*-x_mean)^2 / (n-1) * s_x^2) )
 * 
 * s_y = std deviation of residuals = sqrt( sum[ (y_i - predicted_y)^2 ] / n-2)
 * 
 * 
 * https://www2.isye.gatech.edu/~yxie77/isye2028/lecture12.pdf
 * @author logan.collier
 *
 */

public class ConfidenceIntervals {
	
	private Regression function;
	private float alpha;
	private int confidence_level = 90;
	private float critical_prob;

	public ConfidenceIntervals(Regression f) {
		this.function = f;
	}
	/**
	 * set the confidence level, common ones are 90, 95, 99
	 * @param c - desired confidence
	 */
	public void setConfidenceLevel(int c) {
		this.confidence_level = c;
		setAlpha();
	}
	/**
	 * set the alpha level
	 */
	private void setAlpha() {
		this.alpha = 1-(this.confidence_level / 100);
	}
	private void setCritical_prob() {
		this.critical_prob = 1 - (this.alpha/2);
	}

	/*
	private void upper_intervalY(double predicted_y, double x_val) {
		return predicted_y + 
	}
	*/


	

}
