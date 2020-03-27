package forensics;

import dataframe.Column;

/**
 * 
 * @author logan
 * A t-test is a type of inferential statistic used to determine if there is a significant difference 
 * between the means of two groups
 * 
 * Mathematically, the t-test takes a sample from each of the two sets and establishes the problem 
 * statement by assuming a null hypothesis that the two means are equal. 
 * Based on the applicable formulas, certain values are calculated and compared against the standard values,
 *  and the assumed null hypothesis is accepted or rejected accordingly.
 *
 */
public class T_Test {
	private double degFree;
	private Column x;
	private Column y;
	
	public T_Test(Column x, Column y) {
		this.x = x;
		this.y = y;
	}
	private void equal_variance_DegreesFreedom() {
		this.degFree = (2* x.getLength()) - 2;
	}
	private void unequal_variance_DegreesFreedom() {
		this.degFree = Math.pow(( (Math.pow(x.variance, 2) / x.getLength()) +  (Math.pow(y.variance, 2) / y.getLength())) , 2)  / ( (Math.pow( (Math.pow(x.variance, 2) / x.getLength()), 2) / (x.getLength() - 1)) + (Math.pow( (Math.pow(y.variance, 2) / y.getLength()), 2) / (y.getLength() - 1)) );
	}
	/**
	 * Use to compare the mean of a group to that of the overall population
	 * @param population_mean
	 * @return
	 */
	public double one_samp_ttest(double population_mean) {
		this.degFree = x.getLength()-1;
		return (x.mean - population_mean) / (x.std / Math.sqrt(x.getLength()));
	}
	/**
	 * LOOK! MATH!
	 * @return the p-value
	 */
	public double equal_variance_tValue() {
		return (x.mean- y.mean) / (Math.sqrt( (((x.getLength()-1) * Math.pow(x.variance,2)) + ((y.getLength()-1) * Math.pow(y.variance,2))) / ((2 * x.getLength()-2)) ) * Math.sqrt( (1/x.getLength()) + (1/y.getLength())));
	}
	public double unequal_variance_tValue() {
		return (x.mean - y.mean) / Math.sqrt( (Math.pow(x.variance, 2) / x.getLength()) + (Math.pow(y.variance, 2) / y.getLength()) );
	}
	

}
