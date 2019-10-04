package transform;

import java.util.ArrayList;
import java.util.List;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.NumericColumn;
import particles.DoubleParticle;
import particles.Particle;
/**
 * Standardize turn a column of dataframe into standard form of z scores
 * @author logan.collier
 *
 */
public class Standardize {
	
	public DataFrame df;
	
	public Standardize(DataFrame data) {
		this.df = DataFrame.deepCopy_columnIndexes(data, data.numericIndexes);

	}
	/**
	 * standardize whole dataframe
	 */
	public void standardize_df() {
		for(int i =0; i < df.getNumColumns(); i++) {
			standardize_col((NumericColumn) df.getColumn_byIndex(i), i);
		}
	}
	/**
	 * standardize a column
	 * @param c
	 */
	public void standardize_col(NumericColumn c, int index) {
		for(int i = 0; i < c.getLength(); i++) {
			Particle p = new DoubleParticle(zscore(c.mean,c.std,c.getParticle(i)));
			df.replaceParticle(i, index, p);
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
		    df.getColumn_byIndex(i).getParticle(i).resolveType(zeroMean(c.mean,c.getParticle(i)));
            //df.getColumn_byIndex(i).changeValue(i, p);
            df.getRow_byIndex(i).changeValue(i, df.getColumn_byIndex(i).getParticle(i));
		}
	}
	/**
	 * make data have a mean of zero
	 * @param mean
	 * @param x
	 * @return
	 */
	private double zeroMean(double mean, Particle x) {
		if(x.type == 'i')
			return (int)x.getValue()-mean;
		else 
			return (double)x.getValue()-mean;
	}
	/**
	 * compute z score
	 * @param mean
	 * @param std
	 * @param x
	 * @return
	 */
	private double zscore(double mean, double std, Particle x) {
		if(x.type == 'i')
			return ((int)x.getValue()- mean)/std;	
		else
			return ((double)x.getValue()- mean)/std;	
	}

}
