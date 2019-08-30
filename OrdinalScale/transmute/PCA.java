package transmute;

import saga.*;

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
	
	public PCA(DataFrame df) {
		this.df = df;
		covariance_matrix();
	}
	
	public void covariance_matrix() {
		DataFrame cov = new DataFrame();
	    for (int i = 0; i < df.numColumns; i++) { //Initializes the columns of covariance matrix.
	    	for(int j = i+1; j < df.numColumns; j++) {
	    		cov.add_blank_Column(df.columnNames.get(i) +" "+ df.columnNames.get(j));
	    		System.out.println(df.columnNames.get(i) +" "+ df.columnNames.get(j));
	    	}
	    }
	        
		
	}

}
