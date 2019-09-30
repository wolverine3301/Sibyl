import dataframe.Column;
import particles.Particle;

public abstract class Regression {
	private Column x;
	private Column y;
	public String y_var;//names
	public String x_var; 
	
	public double slope;
	public double intercept;
	public double SST; //total sum of squares
	public double SSP; //sum of products
	
	public abstract double predictY(Particle x_val);
	
}
