package regressionFunctions;

import dataframe.Column;
import dataframe.DataFrame;

public class statsBench {

	public static void main(String[] args) {
		DataFrame iris = DataFrame.read_csv("testfiles/regress");
		Column col1 = iris.getColumn(0);
		Column col2 = iris.getColumn(1);
		Column col3 = iris.getColumn(2);
		
		//col1.printCol();
		//System.out.println(Stats.zeroSquaredSum(col1));
		//System.out.println(Stats.covariance(col1, col2));
		Column x = new Column("X");
		Column y = new Column("Y");
		
		y.setType('N');

		x.add(Integer.valueOf(1));
		x.add(3);
		x.add(4);
		y.add(2);
		y.add(3);
		y.add(2);
		
		
		LinearRegression test = new LinearRegression(col1,col3);
		System.out.println("Total sum of Squares: " + test.SST);
		System.out.println("Total squared sum products:"+  test.SSE);
		System.out.println("Slope:" + test.slope +"x");
		System.out.println("Y Intercept:" + test.intercept);
		System.out.println();
		
		System.out.println("LOG");
		LogRegression test0 = new LogRegression (col1,col2);
		System.out.println("Slope:" + test0.slope +"x");
		System.out.println("Y Intercept:" + test0.intercept);
		System.out.println();
		
		
		LinearRegression test2 = new LinearRegression(col2,col3);
		System.out.println("Total sum of Squares: " + test2.SST);
		System.out.println("Total squared sum products:"+  test2.SSE);
		System.out.println("Slope:" + test2.slope +"x");
		System.out.println("Y Intercept:" + test2.intercept);
		System.out.println();
		
		System.out.println("MULTI");
		Column[] x_cols = {col1,col2};
		Multi_LinearRegression test3 = new Multi_LinearRegression(x_cols,col3);
		System.out.println(test3.intercept);
		System.out.println(test3.regressions[0].slope);
		System.out.println(test3.regressions[1].slope);
		System.out.println(test3.slopes.toString());
		
		System.out.println("POLYNOMIAL");
		PolyRegression p = new PolyRegression(col1, col3, 3);
		System.out.println(p.getEquation());
	}

}
