package forensics;

import saga.*;

public class Stats {
	
	/**
	 * @return the covariance of 2 columns
	 */
	public double covariance(Column x, Column y) {
		double covar = 0;
		for(int i = 0;i < x.getLength(); i++) {
			covar = covar + (((double)x.getParticle_atIndex(i).getValue() - x.mean()) * ((double)y.getParticle_atIndex(i).getValue() - y.mean()));
		}
		return covar/x.getLength();	
	}

	

}
