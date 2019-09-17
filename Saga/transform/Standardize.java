package transform;

import java.util.ArrayList;
import java.util.List;

import dataframe.Column;
import dataframe.DataFrame;
/**
 * Standardize turn a column of dataframe into standard form of z scores
 * @author logan.collier
 *
 */
public class Standardize {
	
	private DataFrame df;
	
	public Standardize(DataFrame df) {
		List<Character> nums = new ArrayList<Character>();
		nums.add('d');
		nums.add('i');
		this.df = df.include(nums);
	}
	
	/**
	 * standardize whole dataframe
	 */
	public void standardize_df() {
		for(Column i : df.columns) {
			standardize_col(i);
		}
	}
	/**
	 * standardize a column
	 * @param c
	 */
	public void standardize_col(Column c) {
		for(int i = 0; i < c.getLength(); i++) {
			c.getParticle_atIndex(i).setValue(zscore(c.mean(),c.standardDeviation(),(double)c.getParticle_atIndex(i).getValue()));
		}
	}
	/**
	 * Zero mean for all of data frame
	 */
	public void zeroMean_df() {
		for(Column i : df.columns) {
			zeroMean_col(i);
		}
	}
	/**
	 * zero the mean of a column
	 * @param c
	 */
	public void zeroMean_col(Column c) {
		for(int i = 0; i < c.getLength(); i++) {
			c.getParticle_atIndex(i).setValue(zeroMean(c.mean(),(double)c.getParticle_atIndex(i).getValue()));
		}
	}
	/**
	 * make data have a mean of zero
	 * @param mean
	 * @param x
	 * @return
	 */
	private double zeroMean(double mean, double x) {
		return x-mean;
	}
	/**
	 * compute z score
	 * @param mean
	 * @param std
	 * @param x
	 * @return
	 */
	private double zscore(double mean, double std, double x) {
		return (x - mean)/std;	
	}

}
