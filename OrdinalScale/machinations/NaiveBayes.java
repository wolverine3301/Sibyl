package machinations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import saga.*;
/**
 * Naive Bayes Classifier
 * @version 1.0
 * @author logan.collier
 *
 */
public class NaiveBayes extends Model{
	
	private List<Column> targets;
	public NaiveBayes(DataFrame df) {
		super(df);
		targets = new ArrayList<Column>();
		
	}
	public HashMap<Object, HashMap<String, HashMap<Object, Double>>> naivebayes(){
		
	}
	/**
	 * sets table of probabilities of categorical columns and being Class k
	 * @param df_i - dataframe of only Class k
	 * @return HashMap<String, HashMap<Object, Double>>
	 */
	private HashMap<String, HashMap<Object, Double>> categorical_ProbabilityTable(DataFrame df_i){
		HashMap<String, HashMap<Object, Double>> cat_columns_probabilities = new HashMap<String, HashMap<Object, Double>>();
		for(Column i : df_i.columns) {
			if(i.type.contains("target")) {
				continue;
			}
			cat_columns_probabilities.put(i.name, set_categoryColumnProbability(i));
		}
		return cat_columns_probabilities;
	}
	/**
	 * return the probability of a continuous variable of Class k
	 * @param x the value
	 * @param meanVar - double[] -> meanVar[0] = mean of column in Class K, meanVar[1] = variance of column in Class k
	 * @return double - Gaussian probability
	 */
	private double gaussian_probability(double x, double[] meanVar) {
		return (1/(Math.sqrt(2 * Math.PI * meanVar[1]))) * 
				(Math.pow(Math.E, ( -1 * ( (Math.pow((x - meanVar[0]),2)) / (2 * meanVar[1]) ) )));
		
	}
	/**
	 * sets the mean and variance of a continuous columns values within a certain class,
	 * because the number of values a continuous entry can take on is infinite we need the mean
	 * and variance of it when associated with a particular target variable to calculate its exact 
	 * probability.
	 * @param c - column in Class k
	 * @return double[] with mean and variance
	 */
	private double[] set_continuousColumnProbability(Column c) {
		double[] meanVar = {c.mean() , c.variance()};
		return meanVar;
	}
	/**
	 * returns a hashmap of values in a column and the proportion the make up of Class k
	 * @param c - The column
	 * @return HashMap<Object, Double>
	 */
	private HashMap<Object, Double> set_categoryColumnProbability(Column c){
		return c.feature_stats;
	}
	/**
	 * Split dataframe based on 1 target columns unique values(Classes)
	 * @param targetNum the target column
	 * @return DataFrame[]
	 */
	private DataFrame[] setClasses(int targetNum){
		Object[] targetClasses = targets.get(targetNum).uniqueValues().toArray();
		DataFrame[] classes = new DataFrame[targetClasses.length];
		String[] arg =new String[3];
		arg[1] = "==";
		//split dataframe based on current target columns classes, result is 
		//n dataframes each with rows containing 1 value in the target column
		for(int i = 0; i < targetClasses.length; i++) {
			arg[0] = targets.get(targetNum).name;
			arg[2] = targetClasses[i].toString();
			classes[i] = super.trainingDataFrame.acquire(arg);
		}
		return classes;
	}
	/**
	 * set targets
	 */
	private void setTargets() {
		for(Column c : super.trainingDataFrame.columns) {
			if(c.type.contains("target")) {
				targets.add(c);
			}
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
