package forensics;

import dataframe.Column;
import saga.*;

public class Stats {
	
	/**
	 * @return the covariance of 2 columns
	 */
	public static double covariance(Column x, Column y) {
		double covar = 0;
		for(int i = 0;i < x.getLength(); i++) {
			covar = covar + (((double)x.getParticle_atIndex(i).getValue() - x.mean()) * ((double)y.getParticle_atIndex(i).getValue() - y.mean()));
		}
		return covar/x.getLength()-1;	
	}
	public double sumMultiple_Columns(Column x, Column y) {
		double sum = 0;
		for(int i = 0; i < x.getLength();i++) {
			sum = sum + ((double)x.getParticle_atIndex(i).getValue() * (double)y.getParticle_atIndex(i).getValue());
		}
		return sum;
	}
	/**
	 * return coaverage of 2 columns
	 * @param x
	 * @param y
	 * @return
	 */
	public double comean(Column x, Column y) {
		return sumMultiple_Columns(x, y) / x.getLength();
	}
	/**
	 * return square mean of a column
	 * @param x
	 * @return
	 */
	public double squareMean(Column x) {
		double s = 0;
		for(int i = 0; i < x.getLength(); i++) {
			s = s + Math.pow((double)x.getParticle_atIndex(i).getValue(),2);
		}
		return s / x.getLength();
	}

	

}
