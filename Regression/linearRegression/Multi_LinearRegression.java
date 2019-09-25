package linearRegression;

import dataframe.Column;

/**
 * 
 * @author logan.collier
 *
 */
public class Multi_LinearRegression {
	
	private Column[] x;
	private Column y;
	private LinearRegression[] regressions;
	private double intercept;
	
	public Multi_LinearRegression(Column[] x, Column y) {
		this.x = x;
		this.y = y;
		setRegressions();
	}
	/**
	 * regression lines for columns
	 * @param target
	 * @return
	 */
	private void setRegressions() {
		regressions = new LinearRegression[x.length];
		int cnt = 0;
		for(Column i : x) {
			regressions[cnt] = new LinearRegression(y,i);
			intercept = intercept + regressions[cnt].intercept;
			cnt++;
		}
	}
}
