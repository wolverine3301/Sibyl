package transmute;

import saga.*;

/**
 * Principal Component Analysis
 * 
 * uses an orthoganal transform to conver sets of possibly correlated variables(Numeric) into
 * a set of linearly uncorrelated variables called principal components(PC).
 *  the 1st PC has the largest possible variance and followed by the next
 * @author logan.collier
 *
 */
public class PCA {
	private DataFrame df;
	
	public void covariance_matrix() {
		DataFrame cov = new DataFrame();
	    for (int i = 0; i < theDataFrame.numRows; i++) //Initializes the columns of the new data frame. 
	        dataFrame.add_blank_Column("R" + i);
		for(Column i : df.columns) {
			for(Column)
		}
		
	}

}
