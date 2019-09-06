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
	
	public HashMap<String , HashMap<Object, HashMap<String, Double[]>>> cont_Naive_Bayes;
	public HashMap<String , HashMap<Object, HashMap<String, HashMap<Object, Double>>>> cat_Naive_Bayes;
	
	public NaiveBayes(DataFrame df) {
		super(df);
		targets = new ArrayList<Column>();
		setTargets();
		cont_Naive_Bayes = continuous_naive_bayes();
		cat_Naive_Bayes = categorical_naive_bayes();
		
	}
	/**
	 * construct continuous Naive Bayes for all target columns
	 * @return
	 */
	public HashMap<String , HashMap<Object, HashMap<String, Double[]>>> continuous_naive_bayes(){
		HashMap<String , HashMap<Object, HashMap<String, Double[]>>> NaiveBayes =
				new HashMap<String , HashMap<Object, HashMap<String, Double[]>>>();
		for(int i = 0; i < targets.size(); i++) {
			NaiveBayes.put(targets.get(i).name, cont_naivebayes_i(i));	
		}
		return NaiveBayes;
	}
	/**
	 * Construct categorical Naive Bayes for all target columns
	 * @return Naive Bayes
	 */
	public HashMap<String , HashMap<Object, HashMap<String, HashMap<Object, Double>>>> categorical_naive_bayes(){
		HashMap<String , HashMap<Object, HashMap<String, HashMap<Object, Double>>>> NaiveBayes =
				new HashMap<String , HashMap<Object, HashMap<String, HashMap<Object, Double>>>>();
		for(int i = 0; i < targets.size(); i++) {
			NaiveBayes.put(targets.get(i).name, cat_naivebayes_i(i));	
		}
		return NaiveBayes;
	}
	/**
	 * naive bayes probability table for continuous columns
	 * @param targetNum
	 * @return
	 */
	public  HashMap<Object, HashMap<String, Double[]>> cont_naivebayes_i(int targetNum){
		HashMap<Object , HashMap<String , Double[]>> naive_bayes = new HashMap<Object , HashMap<String , Double[]>>();
		DataFrame[] classes = classes(targetNum);
		DataFrame continuous;
		
		List<String> numerics = new ArrayList<String>();
		//supported types
		numerics.add("Numerical");
		for(int i = 0; i < classes.length; i++) {
			continuous = classes[i].include(numerics);
			naive_bayes.put(classes[i].getColumn_byName(targets.get(targetNum).name).getParticle_atIndex(0).value,
					continuous_ProbabilityTable(continuous));
		}
		return naive_bayes;
	}
	/**
	 * naive bayes probability table for categorical columns
	 * @param targetNum
	 * @return
	 */
	public HashMap<Object, HashMap<String, HashMap<Object, Double>>> cat_naivebayes_i(int targetNum){
		HashMap<Object, HashMap<String, HashMap<Object, Double>>> naive_bayes = new HashMap<Object, HashMap<String, HashMap<Object, Double>>>();
		DataFrame[] classes = classes(targetNum);
		DataFrame categorical;
		
		List<String> categories = new ArrayList<String>();
		//supported types
		categories.add("categorical");
		categories.add("String");
		for(int i = 0; i < classes.length; i++) {
			categorical = classes[i].include(categories);
			
			naive_bayes.put(classes[i].getColumn_byName(targets.get(targetNum).name).getParticle_atIndex(0).value,
					categorical_ProbabilityTable(categorical));
		}
		return naive_bayes;
		
	}
	/**
	 * set table of information of continuous columns and being in Class k
	 * @param df_i - dataframe of only Class k
	 * @return HashMap<String, Double[]>
	 */
	private HashMap<String, Double[]> continuous_ProbabilityTable(DataFrame df_i){
		HashMap<String, Double[]> cont_columns_probabilities = new HashMap<String, Double[]>();
		for(Column i : df_i.columns) {
			if(i.type.contains("target")) {
				continue;
			}
			cont_columns_probabilities.put(i.name, set_continuousColumnProbability(i));
		}
		return cont_columns_probabilities;
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
	 * @return Double[] with mean and variance
	 */
	private Double[] set_continuousColumnProbability(Column c) {
		Double[] meanVar = {c.mean() , c.variance()};
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
	private DataFrame[] classes(int targetNum){
		Object[] targetClasses = targets.get(targetNum).uniqueValues().toArray();
		DataFrame[] classes = new DataFrame[targetClasses.length];
		String[] arg =new String[3];
		arg[1] = "==";
		//split dataframe based on current target columns classes, result is 
		//n dataframes each with rows containing 1 value in the target column
		for(int i = 0; i < targetClasses.length; i++) {
			arg[0] = targets.get(targetNum).name;
			arg[2] = targetClasses[i].toString();
			classes[i] = super.trainDF.acquire(arg);
		}
		return classes;
	}
	/**
	 * set targets
	 */
	private void setTargets() {
		for(Column c : super.trainDF.columns) {
			if(c.type.contains("target")) {
				targets.add(c);
			}
		}
	}
	
	@Override
	public HashMap<Object, Double> probability(Row row) {
		
		return null;
	}
	@Override
	public Object predict(Row row) {
		// TODO Auto-generated method stub
		return null;
	}

}
