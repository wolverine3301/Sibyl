package correlation;

import dataframe.Column;
/**
 * 
 * @author logan.collier
 *  It measures the strength of association of the cross tabulated data when both variables are measured at the ordinal level. 
 *  It makes no adjustment for either table size or ties. Values range from −1 (100% negative association, or perfect inversion)
 *  to +1 (100% positive association, or perfect agreement). A value of zero indicates the absence of association.
 *  
 *  is a measure of rank correlation, i.e., the similarity of the orderings of the data when ranked by each of the quantities.
 *  
 *
 */
public class Gamma extends Correlation{

	public Gamma(Column x, Column y) {
		super(x, y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double correlation() {
		// TODO Auto-generated method stub
		return 0;
	}

}
