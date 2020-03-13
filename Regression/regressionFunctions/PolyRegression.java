package regressionFunctions;


import dataframe.Column;
import forensics.Stats;
/**
 * polynomial regression using lowess fitting and smoothing
 * STEP 1:
 * use a sliding window to divide data into smaller sections
 * STEP 2:
 * make a linar regression for each section
 * @author logan.collier
 *
 */
public class PolyRegression {
	private Column x;
	private Column y;
	int degree; // degree of the polynomial
	/**
	 * 
	 * @param x
	 * @param y
	 * @param degree - max degree of polynomial
	 */
	public PolyRegression(Column x, Column y,int degree) {
		this.x = x;
		this.y = y;
		this.degree = degree;
	}
	private void residual_squared_sum() {
		double r_sum = 0;
		for(int i = 0; i < x.getLength(); i++) {
			r_sum = r_sum + Math.pow(a, b)
		}
	}
	/**
	 * 		2 * | n   E x   | | b | = 2 * | E y |
	 * 		    | E x E x^2 | | m |		  | E xy|
	 */
	private void matrix_inversion() {
		double c  = 1 / 
	}
	private void degree_sums() {
		double[] poly_x = new double[this.degree];
		poly_x[0] = x.sum;
		
		double[] poly_xy = new double[this.degree];
		poly_xy[0] = y.sum;
		poly_xy[1] = Stats.sumMultiple_Columns(x, y);
		int cnt_xy = 2;
		int cnt_x = 1;
		for(int i = 2; i <= this.degree; i++) {
			double sum_x = 0;
			double sum_xy = 0;
			for(int j = 0; j < x.getLength(); j ++) {
				sum_x = sum_x + Math.pow(x.getDoubleValue(j), i);
				sum_xy = sum_xy + (Math.pow(x.getDoubleValue(j), i) * y.getDoubleValue(j));
			}
			poly_x[cnt_x] = sum_x;
			poly_xy[cnt_xy] = sum_xy;
			cnt_x++;
			cnt_xy++;
		}
	}
	
}
