package regressionFunctions;

import forensics.T_Test;
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
	private int confidence_level = 80; //default is 90% confidence
	private float critical_prob;
	private float[] upper;
	private float[] lower;
	public ConfidenceIntervals(Regression f) {
		this.function = f;
		setUpper();
		setLower();
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
	/**
	 * set the upper inteval
	 */
	private void setUpper() {

		this.upper = new float[this.function.coefficents.length];
		for(int i = 0; i < this.function.coefficents.length; i++) {
			System.out.println(this.function.coefficent_t_scores[i]);
			upper[i] = (float) ((float)( this.function.coefficents[i]) + (this.function.coefficent_t_scores[i] * (this.function.coefficent_se[i] / Math.sqrt(this.function.x.getLength() ))) );
		}
	}
	private void setLower() {
		this.lower = new float[this.function.coefficents.length];
		for(int i = 0; i < this.function.coefficents.length; i++) {
			
			T_Test test = T_Test.test(this.function.coefficents[i],this.function.degree_freedom);
			System.out.println("T: "+test.pvalue+ " FT: "+this.function.coefficent_t_scores[i]);
			lower[i] = (float) ((float)( this.function.coefficents[i]) - (this.function.coefficent_t_scores[i] * (this.function.coefficent_se[i] / Math.sqrt(this.function.x.getLength() ))) );
			//lower[i] = (float) (this.function.coefficent_t_scores[i] * this.function.coefficent_se[i]);
		}
	}
	public void printUpper() {
		for(float i : this.upper) {
			System.out.println(i);
		}
	}
	public void printLower() {
		for(float i : this.lower) {
			System.out.println(i);
		}
	}
	/**
	 * return the upper bound confidince for a given x
	 * @param x_val
	 * @return
	 */
	public double upper_intervalY(Particle x_val) {
		double p = 0;
		for(int i = this.function.coefficents.length-1; i >= 0; i--) {
			p = p + upper[i] * Math.pow(x_val.getDoubleValue(), i);
		}
		return p;
	}
	/**
	 * return the lower bound confidince for a given x
	 * @param x_val
	 * @return
	 */
	public double lower_intervalY(Particle x_val) {
		double p = 0;
		for(int i = this.function.coefficents.length-1; i >= 0; i--) {
			p = p + lower[i] * Math.pow(x_val.getDoubleValue(), i);
		}
		return p;
	}


	

}
