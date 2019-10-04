package transform;

import java.util.ArrayList;
import java.util.List;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.NumericColumn;
/**
 * Standardize turn a column of dataframe into standard form of z scores
 * @author logan.collier
 *
 */
public class Standardize {
	
	private DataFrame df;
	
	public Standardize(DataFrame df) {
		List<Character> nums = new ArrayList<Character>();
		nums.add('N');
		this.df = DataFrame.shallowCopy_columnTypes(df, nums);
	}
	
	/**
	 * standardize whole dataframe
	 */
	public void standardize_df() {
		for(int i =0; i < df.getNumColumns(); i++) {
			standardize_col((NumericColumn) df.getColumn_byIndex(i));
		}
	}
	/**
	 * standardize a column
	 * @param c
	 */
	public void standardize_col(NumericColumn c) {
		for(int i = 0; i < c.getLength(); i++) {
			c.getParticle(i).setValue(zscore(c.mean,c.std,(double)c.getParticle(i).getValue()));
		}
	}
	/**
	 * Zero mean for all of data frame
	 */
	public void zeroMean_df() {
		for(int i =0; i < df.getNumColumns(); i++) {
			zeroMean_col((NumericColumn) df.getColumn_byIndex(i));
		}
	}
	/**
	 * zero the mean of a column
	 * @param c
	 */
	public void zeroMean_col(NumericColumn c) {
		for(int i = 0; i < c.getLength(); i++) {
			c.getParticle(i).setValue(zeroMean(c.mean,(double)c.getParticle(i).getValue()));
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
