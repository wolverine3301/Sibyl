package machinations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import dataframe.DataFrame;
import dataframe.DataFrameTools;
import dataframe.Row;
import particles.Particle;

public abstract class Model {

    public List<Object[]> predictions;
	
	public List<HashMap<Object,Double>> probabilities;
	
	public DataFrame rawTrain;
	/** The target data for the data frame. */
	public DataFrame trainDF_targets;
	
	/** The training data used by predictive models. */
	public DataFrame trainDF_variables;
	
	/**
	 * Abstract model constructor.
	 * @param theDataFrame
	 */
	public Model() {

	}
	
	/**
	 * Sets the targets list.
	 * @return a tree set of targets to predict.
	 */
	private TreeSet<Character> set_targets() {
		TreeSet<Character> target = new TreeSet<Character>();
		target.add('T');
		return target;
	}
	/**
	 * Sets the variables list.
	 * @return a tree set of the variables used to train data.
	 */
	private TreeSet<Character> set_variables() {
		TreeSet<Character> vars = new TreeSet<Character>();
		vars.add('C');
		vars.add('G');
		vars.add('O');
		vars.add('N');
		return vars;
	}
	public  void train(DataFrame df) {
		this.rawTrain = df;
	    this.trainDF_targets = DataFrameTools.shallowCopy_columnTypes(df, set_targets());
	    this.trainDF_variables = DataFrameTools.shallowCopy_columnTypes(df, set_variables());
	    this.trainDF_variables.setStuff();
	}
	/**
	 * Returns the probabilities of a row being a part of each class
	 * @return
	 */
	public abstract HashMap<String , HashMap<Object , Double>>  probability(Row row);
	
	/**
	 * Make a prediction on a row of data
	 * @param row
	 * @return
	 */
	public abstract Object predict(Row row);
	
	public abstract HashMap<String , ArrayList<Object>> predictDF(DataFrame testDF);
	
	public void setTrain(DataFrame trainDF) {
		this.trainDF_targets = DataFrameTools.shallowCopy_columnTypes(trainDF, set_targets());
	    this.trainDF_variables = DataFrameTools.shallowCopy_columnTypes(trainDF, set_variables());   	
	}
	public abstract void initiallize();
	

}
