package regressionFunctions;

import dataframe.Column;

public class Multi_PolynomialRegression {
	private Column[] x; //predictors
	private Column y; //variable to predict
	public PolyRegression[] regressions;
	public double[] slopes;
	public double intercept;
	public int degree;
	
	public Multi_PolynomialRegression(Column[] x_cols,Column y,int degree) {
		
		this.x = x_cols;
		this.y = y;
		this.degree = degree;
		this.regressions = new PolyRegression[x.length];
		setRegressions();
	}
	
	private void setRegressions() {
		int c = 0;
		for(Column i : x) {
			regressions[c] = new PolyRegression(i,y,degree);
			c++;
		}
	}
	public double predict(double[] x_vals) {
		double pred = 0;
		for(int i = 0; i < x_vals.length; i++) {
			//-regressions[i].coefficents[0]
			pred = pred + regressions[i].predict(x_vals[i]);
		}
		return pred/x.length;
	}
}
