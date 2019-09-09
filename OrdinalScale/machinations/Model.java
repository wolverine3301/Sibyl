package machinations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import saga.*;

public abstract class Model {
	
	protected List<Column> targets;
	
    public List<Object[]> predictions;
	
	public List<HashMap<Object,Double>> probabilities;
	
	public DataFrame trainDF;
	
	public Model(DataFrame theDataFrame) {
	    trainDF = theDataFrame;
	    targets = new ArrayList<Column>();
	    set_targets();
	}
	/**
	 * sets target list
	 */
	private void set_targets() {
		for(Column i : trainDF.columns) {
			if(i.type.contentEquals("target")) {
				targets.add(i);
			}
		}
	}
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
