package regressionFunctions;
import dataframe.Column;
import particles.Particle;

public abstract class Regression {
	public Column x;
	public Column y;
	public String y_var;//names
	public String x_var; 
	
	public double slope;
	public double intercept;
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
	
	
	public abstract double predictY(Particle x_val);
	
}
