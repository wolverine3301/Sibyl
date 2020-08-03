package machinations;


import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import dataframe.DataFrame;
import dataframe.DataFrame_Copy;
import dataframe.Row;
import particles.Particle;

public abstract class Model implements java.io.Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

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
	
	public void train(DataFrame df) {
		this.rawTrain = df;
	    this.trainDF_targets = DataFrame_Copy.shallowCopy_columnTypes(df, set_targets());
	    this.trainDF_variables = DataFrame_Copy.shallowCopy_columnTypes(df, set_variables());
	}
	
	/**
	 * Returns the probabilities of a row being a part of each class
	 * @return
	 */
	public abstract HashMap<String , HashMap<Object , Double>>  probability(Row row);
	
	/**
	 * Prints the probabilities along with the values of the row. 
	 * @param probablility the probability data structure. 
	 * @param row the row print as well. 
	 */
	public static void printProbablility(HashMap<String, HashMap<Object, Double>> probability, Row row) {
	    System.out.print("Row Values: ");
	    for (int i = 0; i < row.rowLength; i++) { //Print the row
	        if (i != row.rowLength - 1) 
	            System.out.print(row.getParticle(i) + ", ");
	        else
	            System.out.println(row.getParticle(i));
	    }
	    System.out.println("---------------------");
	    for (String target : probability.keySet()) { //Print each target.
	        System.out.println("Predictions for target \"" + target + "\": ");
	        for (Object o : probability.get(target).keySet()) {
	            System.out.println("Value - " + o + ": Probability - " + probability.get(target).get(o));
	        }
	        System.out.println("---------------------");
	    }
	    
	}
	
	/**
	 * Make a prediction on a row of data
	 * @param row
	 * @return HashMap<String,Object> key = target name -> object = prediction for target
	 */
	public abstract Object predict(String target, Row row);
	
	
	public abstract HashMap<String, ArrayList<Object>> predictDF(DataFrame testDF);
	
	public void setTrain(DataFrame trainDF) {
		this.trainDF_targets = DataFrame_Copy.shallowCopy_columnTypes(trainDF, set_targets());
	    this.trainDF_variables = DataFrame_Copy.shallowCopy_columnTypes(trainDF, set_variables());   	
	}
	
	public abstract void initiallize();
	
	public abstract Model copy();
	
	public abstract void saveModel(String fileName);

}
