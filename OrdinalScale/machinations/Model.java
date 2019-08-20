package machinations;

import java.util.HashMap;
import java.util.List;

import saga.*;

public abstract class Model {
	public List<Object[]> predictions;
	public List<HashMap<Object,Double>> probabilities;
	
	/**
	 * Returns the probabilities of a row being a part of each class
	 * @return
	 */
	public abstract HashMap<Object,Double> probability(Row row);
	/**
	 * Make a prediction on a row of data
	 * @param row
	 * @return
	 */
	public abstract Object predict(Row row);

}
