package transmute;

import java.util.ArrayList;
import java.util.List;

import saga.*;
/**
 * Standardize turn a column of dataframe into standard form of z scores
 * @author logan.collier
 *
 */
public class Standardize {
	
	private DataFrame df;
	public Standardize(DataFrame df) {
		List<String> nums = new ArrayList<String>();
		nums.add("Numeric");
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
