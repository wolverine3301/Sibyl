package pca;

public class EigenVector {
	public String pc_name;
	public String real_name;
	public double scree_val;
	public double slope;
	
	public EigenVector(double regression_slope) {
		
	}
	private void setSlope(double regression_slope) {
		this.slope = 1 / regression_slope;
	}

}
