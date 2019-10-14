package correlation;

import dataframe.Column;
/**
 * @author logan.collier
 * used to measure the ordinal association between two measured quantities.
 * It is a measure of rank correlation: the similarity of the orderings of the data when ranked by each of the quantities
 */
public class Tau extends Correlation{

	public Tau(Column x, Column y) {
		super(x, y);
		
	}

	@Override
	public double correlation() {
		// TODO Auto-generated method stub
		return 0;
	}

}
