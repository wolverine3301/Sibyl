package forensics;
import saga.*;

public abstract class Correlation {
	Column x;
	Column y;
	public Correlation(Column x, Column y) {
		this.x = x;
		this.y = y;
		
	}
	/**
	 * @return the covariance of 2 columns
	 */
	public double covariance() {
		double covar = 0;
		for(int i = 0;i < x.getLength(); i++) {
			covar = covar + (((double)x.getParticle_atIndex(i).getValue() - x.mean()) * ((double)y.getParticle_atIndex(i).getValue() - y.mean()));
		}
		return covar/x.getLength();	
	}
	/**
	 * @return the correlation coefficient
	 */
	public abstract double correlation();

}
