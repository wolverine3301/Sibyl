package forensics;

import dataframe.Column;
import dataframe.ColumnTools;

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
		this.degFree = Math.pow(( (Math.pow(ColumnTools.variance(x), 2) / x.getLength()) +  (Math.pow(ColumnTools.variance(y), 2) / y.getLength())) , 2)  / ( (Math.pow( (Math.pow(ColumnTools.variance(x), 2) / x.getLength()), 2) / (x.getLength() - 1)) + (Math.pow( (Math.pow(ColumnTools.variance(y), 2) / y.getLength()), 2) / (y.getLength() - 1)) );
	}
	/**
	 * LOOK! MATH!
	 * @return
	 */
	public double equal_variance_tValue() {
		return (ColumnTools.mean(x) - ColumnTools.mean(y)) / (Math.sqrt( (((x.getLength()-1) * Math.pow(ColumnTools.variance(x),2)) + ((y.getLength()-1) * Math.pow(ColumnTools.variance(y),2))) / ((2 * x.getLength()-2)) ) * Math.sqrt( (1/x.getLength()) + (1/y.getLength())));
	}
	public double unequal_variance_tValue() {
		return (ColumnTools.mean(x) - ColumnTools.mean(y)) / Math.sqrt( (Math.pow(ColumnTools.variance(x), 2) / x.getLength()) + (Math.pow(ColumnTools.variance(y), 2) / y.getLength()) );
	}
	

}
