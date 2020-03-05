package pca;
/**
 * can be thought of as a normalized linerar regression kinda
 * @author logan.collier
 *
 */
public class EigenVector {
	public String pc_name;
	public String real_name;
	
	public double y_ratio; //linear combination
	public double x_ratio;
	public double slope;
	
	public EigenVector(double regression_slope) {
		
	}
	private void setRatios(double regression_slope) {
		// single value decomposition, scale to 1
		double hypo = Math.sqrt(Math.pow(1 / regression_slope, 2) + 1);
		this.x_ratio = (1 / regression_slope) / hypo;
		this.y_ratio = 1 / hypo;
	}
	public void printVector() {
		System.out.println("Vector: "+slope+"x");
	}

}
