package machinations;

import java.util.HashMap;

import forensics.Pearson;
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
	/**
	 * optimizing start scalar for linear decomposition
	 * @return
	 */
	private double[] optimization_scalar_y() {
		Pearson pearsonCorr = new Pearson(null, null);
		double[] corr = pearsonCorr.correlationMatrix();
	}
	
	private double optimization_scalar_x(double[] means) {
		double sum = 0;
		for(int i = 0; i < means.length; i++) {
			sum += means[i];
		}
		return Math.sqrt(sum);
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
