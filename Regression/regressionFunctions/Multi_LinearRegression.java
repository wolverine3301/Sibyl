package regressionFunctions;


import dataframe.Column;

/**
 * 
 * @author logan.collier
 *
 */
public class Multi_LinearRegression {
	
	private Column[] x; //predictors
	private Column y; //variable to predict
	public LinearRegression[] regressions;
	public double[] slopes;
	public double intercept;
	
	public Multi_LinearRegression(Column[] x, Column y) {
		this.x = x;
		this.y = y;
		setRegressions();
		setSlopes();
	}
	/**
	 * regression lines for columns
	 * @param target
	 * @return
	 */
	private void setRegressions() {
		regressions = new LinearRegression[x.length];
		slopes = new double[x.length];
		int cnt = 0;
		for(Column i : x) {
			regressions[cnt] = new LinearRegression(y,i);
			intercept = intercept + regressions[cnt].intercept;
			cnt++;
		}
	}
	private void setSlopes() {
		int cnt = 0;
		for(LinearRegression i : regressions) {
			slopes[cnt] = i.slope;
			cnt++;
		}
	}
	public int numPredictors() {
		return x.length;
	}
	/**
	 * predicts a y from multiple predictor variables
	 * @param x - double array ,length equal to number of predictor columns
	 * @return 
	 */
	public double predict(double x[]) {
		double pred = 0;
		for(int i = 0; i < slopes.length;i++) {
			pred = pred + (x[i]*slopes[i]);
		}
		return pred;
	}
}
