package forensics;

import dataframe.Column;
import dataframe.ColumnTools;
import dataframe.DataFrame;
import transform.Standardize;

public class Stats {
	//TODO
	public static double standardError() {
		return 0;
	}
	public static double zeroSquaredSum(Column x) {
		double sum = 0;
		for(int i = 0; i < x.getLength(); i++) {
			if(x.getParticle(i).getValue() instanceof Double) {
				sum = sum + Math.pow(((Double)x.getParticle(i).getValue() - x.mean), 2);
			}
			else if(x.getParticle(i).getValue() instanceof Integer) {
				sum = sum + Math.pow(( (Integer)x.getParticle(i).getValue() - x.mean ), 2);
			}
		}
		return sum;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static double zeroSquaredSum_between(Column x, Column y) {
		double sum = 0;
		double x_1 = 0;
		double y_1 = 0;
		for(int i = 0; i < x.getLength();i++) {
			if(x.getParticle(i).getValue() instanceof Double) {
				x_1 = (double)x.getParticle(i).getValue();
			}
			else if(x.getParticle(i).getValue() instanceof Integer) {
				x_1 = (int)x.getParticle(i).getValue();
			}
			if(y.getParticle(i).getValue() instanceof Double) {
				y_1 = (double)y.getParticle(i).getValue();
			}
			else if(y.getParticle(i).getValue() instanceof Integer) {
				y_1 = (int)y.getParticle(i).getValue();
			}
			sum = sum + Math.pow(((x_1 - x.mean) * (y_1- y.mean) ),2);
		}
		return sum;
	}
	/**
	 * @return the covariance of 2 columns
	 */
	public static double covariance(Column x, Column y) {
		double covar = 0;
		double x_1=0;
		double y_1=0;
		for(int i = 0;i < x.getLength(); i++) {
			
			if(x.getParticle(i).getValue() instanceof Double) {
				x_1 = (double)x.getParticle(i).getValue();
			}
			else if(x.getParticle(i).getValue() instanceof Integer) {
				x_1 = (int)x.getParticle(i).getValue();
			}
			if(y.getParticle(i).getValue() instanceof Double) {
				y_1 = (double)y.getParticle(i).getValue();
			}
			else if(y.getParticle(i).getValue() instanceof Integer) {
				y_1 = (int)y.getParticle(i).getValue();
			}
			covar = covar + (x_1 - x.mean) * (y_1 - y.mean);
			
		}
		return covar/x.getLength();	
	}
	/**
	 * returns zero sum multiple of 2 columns , E (x_i - x_mean) * (y_i - y_mean)
	 * @param x
	 * @param y
	 * @return
	 */
	public static double zeroSumMultiple_Columns(Column x, Column y) {
		double sum = 0;
		double x_1 = 0;
		double y_1 = 0;
		for(int i = 0; i < x.getLength();i++) {
			if(x.getParticle(i).getValue() instanceof Double) {
				x_1 = (double)x.getParticle(i).getValue();
			}
			else if(x.getParticle(i).getValue() instanceof Integer) {
				x_1 = (int)x.getParticle(i).getValue();
			}
			if(y.getParticle(i).getValue() instanceof Double) {
				y_1 = (double)y.getParticle(i).getValue();
			}
			else if(y.getParticle(i).getValue() instanceof Integer) {
				y_1 = (int)y.getParticle(i).getValue();
			}
			sum = sum + ((x_1 - x.mean) * (y_1- y.mean));
		}
		return sum;
	}

	/**
	 * returns the sum of x * y , E x_i * y_i
	 * @param x
	 * @param y
	 * @return
	 */
	public static double sumMultiple_Columns(Column x, Column y) {
		double sum = 0;
		for(int i = 0; i < x.getLength();i++) {
			sum = sum + ((double)x.getParticle(i).getValue() * (double)y.getParticle(i).getValue());
		}
		return sum;
	}
	/**
	 * return coaverage of 2 columns
	 * @param x
	 * @param y
	 * @return
	 */
	public static double comean(Column x, Column y) {
		return sumMultiple_Columns(x, y) / x.getLength();
	}
	/**
	 * return square mean of a column , E (x_i)^2 / n
	 * @param x
	 * @return
	 */
	public static double squareMean(Column x) {
		double s = 0;
		for(int i = 0; i < x.getLength(); i++) {
			s = s + Math.pow((double)x.getParticle(i).getValue(),2);
		}
		return s / x.getLength();
	}
	/**
	 * produces a covariance matrix of all column combinations
	 * the diagonal of the matrix is simply var(columnK) because covar(colK,colK) = var(columnK)
	 * thus this matrix is orthogonal
	 * @return
	 */
	public double[][] covariance_matrix(DataFrame df) {
		Standardize standard = new Standardize(df);
		standard.zeroMean_df();
		double[][] covar = new double[df.getNumColumns()][df.getNumColumns()];
		//fill the columns of covariance matrix.
	    for (int i = 0; i < df.getNumColumns(); i++) { 
	    	covar[i][i] = df.getColumn_byIndex(i).variance;
	    	for(int j = i+1; j < df.getNumColumns(); j++) {
	    		covar[i][j] = covariance(df.getColumn_byIndex(i), df.getColumn_byIndex(j));
	    		covar[j][i] = covariance(df.getColumn_byIndex(j), df.getColumn_byIndex(i));
	    	}
	    }
	    return covar;
	}
	

}
