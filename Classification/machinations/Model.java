package machinations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import dataframe.DataFrame;
import dataframe.Row;
import particles.Particle;

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
	    trainDF_targets = theDataFrame.dataFrameFromColumns_ShallowCopy(set_targets());
	    trainDF_variables = theDataFrame.dataFrameFromColumns_ShallowCopy(set_variables());    
	    System.out.println("Targets: ");
	    trainDF_targets.printDataFrame();
	    System.out.println("Training Data: ");
	    trainDF_variables.printDataFrame();
	}
	
	/**
	 * sets target list
	 */
	private TreeSet<Character> set_targets() {
		TreeSet<Character> target = new TreeSet<Character>();
		target.add('T');
		return target;
	}
	
	private TreeSet<Character> set_variables() {
		TreeSet<Character> vars = new TreeSet<Character>();
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
	public abstract ArrayList<ArrayList<Particle>> predict(Row row);

}
