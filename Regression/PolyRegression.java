

import dataframe.Column;
/**
 * 
 * @author logan.collier
 *
 */
public class PolyRegression {
	private Column x;
	private Column y;
	int degree;
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
	

}
