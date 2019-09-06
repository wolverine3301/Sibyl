package machinations;

import java.util.HashMap;

import saga.DataFrame;
import saga.Row;

public class LinearRegression extends Model{
	/**
	 * simple linear regression
	 * @param theDataFrame
	 */
	public LinearRegression(DataFrame theDataFrame) {
		super(theDataFrame);
		
	}
	private double[] optimization_scalar() {
		double[] corr = new double[]
		for(int i = 0; i < super.trainingDataFrame.numColumns; i++) {
			
		}
	}
	

	@Override
	public HashMap<Object, Double> probability(Row row) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object predict(Row row) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
