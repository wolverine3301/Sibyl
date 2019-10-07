package transform;

import java.util.ArrayList;
import java.util.List;

import dataframe.Column;
import dataframe.DataFrame;
import particles.DoubleParticle;
import particles.Particle;
/**
 * Standardize turn a column of dataframe into standard form of z scores
 * @author logan.collier
 *
 */
public class Standardize {
	
	public DataFrame std_df;
	private DataFrame df;
	public Standardize(DataFrame data) {
		this.df = DataFrame.shallowCopy_columnIndexes(data, data.numericIndexes);
		this.std_df = new DataFrame();
	}
	/**
	 * standardize whole dataframe
	 */
	public void standardize_df() {
		for(int i =0; i < df.getNumColumns(); i++) {
			standardize_col(df.getColumn_byIndex(i), i);
		}
	}
	/**
	 * standardize a column
	 * @param c
	 */
	public void standardize_col(Column c, int index) {
		Column a  = new Column(c.getName(),'N');
		for(int i = 0; i < c.getLength(); i++) {
			Particle p = new DoubleParticle((Double) zscore(c.mean,c.std,c.getParticle(i)));
			a.add(p);
		}
		std_df.addColumn(a);
	}
	/**
	 * Zero mean for all of data frame
	 */
	public void zeroMean_df() {
		for(int i =0; i < df.getNumColumns(); i++) {
			zeroMean_col(df.getColumn_byIndex(i));
		}
	}
	/**
	 * zero the mean of a column
	 * @param c
	 */
	public void zeroMean_col(Column c) {
		for(int i = 0; i < c.getLength(); i++) {
		    //df.getColumn_byIndex(i).getParticle(i).resolveType(zeroMean(c.mean,c.getParticle(i)));
            //df.getColumn_byIndex(i).changeValue(i, p);
            //df.getRow_byIndex(i).changeValue(i, df.getColumn_byIndex(i).getParticle(i));
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
		if(x.type == 'i') {
			return ((int)x.getValue()- mean)/std;
		}
		else {
			return ((double)x.getValue()- mean)/std;
		}
	}

}
