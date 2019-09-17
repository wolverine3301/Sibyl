package machinations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import saga.*;

public abstract class Model {

    public List<Object[]> predictions;
	
	public List<HashMap<Object,Double>> probabilities;
	
	public DataFrame trainDF_targets;
	public DataFrame trainDF_variables;
	
	/**
	 * Abstract model constructor
	 * @param theDataFrame
	 */
	public Model(DataFrame theDataFrame) {
	    trainDF_targets = theDataFrame.include(set_targets());
	    trainDF_variables = theDataFrame.include(set_variables());    
	}
	/**
	 * sets target list
	 */
	private List<Character> set_targets() {
		List<Character> target = new ArrayList<Character>();
		target.add('T');
		return target;
	}
	private List<Character> set_variables() {
		List<Character> vars = new ArrayList<Character>();
		vars.add('C');
		vars.add('G');
		vars.add('O');
		vars.add('N');
		return vars;
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
