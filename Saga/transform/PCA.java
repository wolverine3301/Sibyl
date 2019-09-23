package transform;

import dataframe.Column;
import dataframe.DataFrame;
import forensics.Stats;
import linearRegression.Multi_LinearRegression;
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
	private Multi_LinearRegression regressions;
	/**
	 * 
	 * @param df
	 */
	public PCA(DataFrame df) {
		Standardize standard = new Standardize(df);
		standard.zeroMean_df();
		this.df = df;

	}
	private void setRegressions() {
		
		Column[] cols = new Column[df.getNumColumns()-1];
		//x columns
		for(int j = 1; j < df.getNumColumns(); j++) {
			cols[j] = df.getColumn_byIndex(j);
		}
		regressions= new Multi_LinearRegression(cols, df.getColumn_byIndex(0));
	}
	


}
