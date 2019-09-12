package transmute;

import saga.*;
import forensics.Stats;
/**
 * Principal Component Analysis
 * 
 * uses an orthoganal transform to conver sets of possibly correlated variables(Numeric) into
 * a set of linearly uncorrelated variables called principal components(PC).
 *  the 1st PC has the largest possible variance and followed by the next
 *  
 *  When to use
 *  Do you want to reduce the number of variables, but aren’t able to identify variables
 *  to completely remove from consideration?
 *  Do you want to ensure your variables are independent of one another?
 *  Are you comfortable making your independent variables less interpretable?
 * @author logan.collier
 *
 */
public class PCA {
	
	private DataFrame df;
	private Stats stat = new Stats();
	private Standardize standard;
	public PCA(DataFrame df) {
		this.df = df;
		standard = new Standardize(this.df);
		covariance_matrix();
	}
	/**
	 * produces a covariance matrix of all column combinations
	 * the diagonal of the matrix is simply var(columnK) because covar(colK,colK) = var(columnK)
	 * thus this matrix is orthogonal
	 * @return
	 */
	public double[][] covariance_matrix() {
		standard.zeroMean_df();
		double[][] covar = new double[df.numColumns][df.numColumns];
		//fill the columns of covariance matrix.
	    for (int i = 0; i < df.numColumns; i++) { 
	    	covar[i][i] = df.getColumn_byIndex(i).variance();
	    	for(int j = i+1; j < df.numColumns; j++) {
	    		covar[i][j] = stat.covariance(df.getColumn_byIndex(i), df.getColumn_byIndex(j));
	    		covar[j][i] = stat.covariance(df.getColumn_byIndex(j), df.getColumn_byIndex(i));
	    	}
	    }
	    return covar;
	}

	/**
	 * Singular value decomposition
	 * Calculate regression slope
	 * @param target
	 * @param x
	 * @return
	 */
	private double regression_slopeM(Column x, Column y) {
		return (stat.comean(x, y)  - (x.mean() * y.mean())) / (stat.squareMean(y) - Math.pow(y.mean(),2));
	}
	public double sum_of_squares(double[] vals) {
		double sum = 0;
		for(double i : vals) {
			sum += i * i;
		}
		return sum;
	}
	
	
	

}
