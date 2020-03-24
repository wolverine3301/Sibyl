package regressionFunctions;
import dataframe.Column;
import particles.Particle;

public abstract class Regression {
	protected Column x;
	protected Column y;

	/**
	 *  Y = mean of observed data ( the target ) = 1/n * E y_i
	 *  y_i = an observed value in target column
	 *  f_i = regression function for a given point like y=2x and x=2 => 2*2 = 4
	 *  residuals = e_i = y_i - f_i
	 */
	public double SST; //total sum of squares	= E(y_i - Y)^2
	public double SSE; //sum of squared errors	= E(y_i - f_i)^2
	public double SSR; //sum of squared regression = E(f_i - Y)^2
	public double SP;  //sum of products
	
	public double R2;  //r squared
	public double MAE; //mean absolute error
	public double MSE; //mean squared error
	public double RMSD; //Root mean squared deviation
	public double[] function;
	
	/**
	 * abstract regression class for all types of regression functions
	 * slope. the y intercept is always the last index of the double array
	 * @param x
	 * @param y
	 */
	public Regression(Column x, Column y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * set the regression equation
	 */
	protected abstract void setRegression();
	/**
	 * returns string representation of the regression function
	 * @return
	 */
	public abstract String getEquation();
	/**
	 * prints the string representation of the regression function
	 */
	public void printEquation() {
		System.out.println(getEquation());
	}
	
	/**
	 * predict a y value based on an x_value
	 * @param x_val
	 * @return
	 */
	public abstract double predictY(Particle x_val);
	
}
