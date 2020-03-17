package examples;

import dataframe.Column;
import regressionFunctions.PolyRegression;

public class Polynomial_regression_example {

	public static void main(String[] args) {
		double[] a1 = {-1,0,1,2,3,5,7,9};
		double[] a2 = {-1,3,2.5,5,4,2,5,4};
		Column x = new Column(a1);
		Column y = new Column(a2);
		PolyRegression p = new PolyRegression(x,y,4);
		p.printEquation();
	}

}
