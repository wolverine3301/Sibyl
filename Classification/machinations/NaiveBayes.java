package machinations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrameTools;
import dataframe.Row;
import particles.Particle;
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
	}
	@Override
	public void train() {
		cont_Naive_Bayes = continuous_naive_bayes();
		cat_Naive_Bayes = categorical_naive_bayes();
	}
	/**
	 * construct continuous Naive Bayes for all target columns
	 * STRUCTURE: 
	 * HASHMAP<Target Column name> -> HASHMAP< Nth unique value within Target Column > -> HASHMAP< Variable Column Name > -> Double[] -> [0] = column mean , [1] = column variance
	 * mean and variance used to make probability for a numeric column.
	 * @return
	 */
	public HashMap<String , HashMap<Object, HashMap<String, Double[]>>> continuous_naive_bayes(){
		HashMap<String , HashMap<Object, HashMap<String, Double[]>>> NaiveBayes =
				new HashMap<String , HashMap<Object, HashMap<String, Double[]>>>();
		for(int i = 0; i < super.trainDF_targets.getNumColumns(); i++) {
			NaiveBayes.put(super.trainDF_targets.getColumn_byIndex(i).getName(), cont_naivebayes_i(i));	
		}
		return NaiveBayes;
	}
	/**
	 * Construct categorical Naive Bayes for all target columns
	 * STRUCTURE: 
	 * HASHMAP<Target Column name> -> HASHMAP< Nth unique value within Target Column >
	 *  -> HASHMAP< Variable Column Name > -> HASHMAP< Nth unique value within variable Column > -> Double(probability
	 * @return Naive Bayes
	 */
	public HashMap<String , HashMap<Object, HashMap<String, HashMap<Object, Double>>>> categorical_naive_bayes(){
		HashMap<String , HashMap<Object, HashMap<String, HashMap<Object, Double>>>> NaiveBayes =
				new HashMap<String , HashMap<Object, HashMap<String, HashMap<Object, Double>>>>();
		for(int i = 0; i < super.trainDF_targets.getNumColumns(); i++) {
			NaiveBayes.put(super.trainDF_targets.getColumn_byIndex(i).getName(), cat_naivebayes_i(i));	
		}
		return NaiveBayes;
	}
	/**
	 * naive bayes probability table for continuous columns
	 * 
	 * @param targetNum
	 * @return
	 */
	public HashMap<Object, HashMap<String, Double[]>> cont_naivebayes_i(int targetNum){
		HashMap<Object , HashMap<String , Double[]>> naive_bayes = new HashMap<Object , HashMap<String , Double[]>>();
		DataFrame[] classes = classes(targetNum);
		
		List<Character> numerics = new ArrayList<Character>();
		//supported types
		numerics.add('i');
		numerics.add('d');
		for(int i = 0; i < classes.length; i++) {
			naive_bayes.put(classes[i].getColumn_byName(super.trainDF_targets.getColumn_byIndex(targetNum).getName()).getParticle(0).value,
					continuous_ProbabilityTable());
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
		//DataFrame[] classes = classes(targetNum);
		DataFrame categorical;
		
		List<Character> categories = new ArrayList<Character>();
		//supported types
		categories.add('C');
		categories.add('S');
		categories.add('M');
		HashMap<String, HashMap<Object, Double>> classes;
		HashMap<Object, Double> clas;
		for(int i = 0; i < super.trainDF_targets.getColumn_byIndex(targetNum).getUniqueValues().size(); i++) {
			classes = new  HashMap<String, HashMap<Object, Double>>();
			for(int j = 0; j < super.trainDF_variables; j++) {
				clas = new HashMap<Object, Double>();
			}
		}
		for(int i = 0; i < classes.length; i++) {

			categorical = DataFrameTools.shallowCopy_columnTypes(classes[i], categories);
			
			naive_bayes.put(classes[i].getColumn_byName(super.trainDF_targets.getColumn_byIndex(targetNum).getName()).getParticle(0).value,
					categorical_ProbabilityTable());
		}
		return naive_bayes;
		
	}
	/**
	 * set table of information of continuous columns and being in Class k
	 * @param df_i - dataframe of only Class k
	 * @return HashMap<String, Double[]>
	 */
	private HashMap<String, Double[]> continuous_ProbabilityTable(){
		HashMap<String, Double[]> cont_columns_probabilities = new HashMap<String, Double[]>();
		for(int i = 0; i < super.trainDF_variables.getNumColumns();i++) {
			cont_columns_probabilities.put(super.trainDF_variables.getColumn_byIndex(i).getName(), set_continuousColumnProbability(super.trainDF_variables.getColumn_byIndex(i)));
		}
		return cont_columns_probabilities;
	}
	
	/**
	 * sets table of probabilities of categorical columns and being Class k
	 * @param df_i - dataframe of only Class k
	 * @return HashMap<String, HashMap<Object, Double>>
	 */
	private HashMap<String, HashMap<Object, Double>> categorical_ProbabilityTable(){
		HashMap<String, HashMap<Object, Double>> cat_columns_probabilities = new HashMap<String, HashMap<Object, Double>>();
		for(int i = 0; i < super.trainDF_variables.getNumColumns();i++) {
			cat_columns_probabilities.put(super.trainDF_variables.getColumn_byIndex(i).getName(), set_categoryColumnProbability(super.trainDF_variables.getColumn_byIndex(i)));
		}
		return cat_columns_probabilities;
	}
	/**
	 * return the probability of a continuous variable of Class k
	 * @param x the value
	 * @param meanVar - double[] -> meanVar[0] = mean of column in Class K, meanVar[1] = variance of column in Class k
	 * @return double - Gaussian probability
	 */
	private double gaussian_probability(double x, Double[] meanVar) {
		System.out.println(x);
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
		Double[] meanVar = {c.mean , c.variance};
		return meanVar;
	}
	/**
	 * returns a hashmap of values in a column and the proportion the make up of Class k
	 * @param c - The column
	 * @return HashMap<Object, Double>
	 */
	private HashMap<Object, Double> set_categoryColumnProbability(Column c){
		return c.getFeatureStats();
	}
	/**
	 * Split dataframe based on 1 target columns unique values(Classes)
	 * @param targetNum the target column
	 * @return DataFrame[]
	 */
	private DataFrame[] classes(int targetNum){
		Object[] targetClasses = super.trainDF_targets.getColumn_byIndex(targetNum).getUniqueValues().toArray();
		DataFrame[] classes = new DataFrame[targetClasses.length];
		String[] arg = new String[3];
		arg[1] = "==";
		//split dataframe based on current target columns classes, result is 
		//n dataframes each with rows containing 1 value in the target column
		for(int i = 0; i < targetClasses.length; i++) {
			arg[0] = super.trainDF_targets.getColumn_byIndex(targetNum).getName();
			arg[2] = targetClasses[i].toString();
			classes[i] = DataFrame.acquire(super.trainDF_targets,arg);
		}
		return classes;
	}
	public double getCategoricalProbability(String targetName, Object targetValue, String variable, Object variableValue) {
		return cat_Naive_Bayes.get(targetName).get(targetValue).get(variable).get(variableValue);
	}
	public double getContinuousProbability(String targetName, Object targetValue, String variable, Particle x) {
		System.out.println(targetName);
		System.out.println(targetValue);
		System.out.println(variable);
		System.out.println(x.getType() + " "+ x.getValue());
		System.out.println(x.getDoubleValue());
		System.out.println(cont_Naive_Bayes.get(targetName).size());
		return  gaussian_probability(x.getDoubleValue(), cont_Naive_Bayes.get(targetName).get(targetValue).get(variable)); 
	}
	private HashMap<Object , Double> probabilityForClass(Row row, int target){
		HashMap<Object , Double> classProb = new HashMap<Object , Double>();
		for(Object z : super.trainDF_targets.getColumn_byIndex(target).getUniqueValues()) {
			double prob = 1;
			for(int i = 0; i < row.rowLength; i++) {
				if(super.trainDF_variables.getColumn_byIndex(i).getType() == 'N') {
					System.out.println("HERE "+row.getParticle(0) +" HERE "+super.trainDF_variables.getColumn_byIndex(0).getName());
					prob = prob * getContinuousProbability(super.trainDF_targets.getColumn_byIndex(target).getName(),z,super.trainDF_variables.getColumn_byIndex(i).getName(),row.getParticle(i));
				}else
					prob = prob * cat_Naive_Bayes.get(super.trainDF_targets.getColumn_byIndex(target).getName()).get(z).get(super.trainDF_variables.getColumn_byIndex(i).getName()).get(row.getParticle(i).getValue());
			}
			classProb.put(z, prob);
		}
		return classProb;
		
	}
	/**
	 * targetName -> unique -> probability
	 */
	@Override
	public HashMap<String , HashMap<Object , Double>> probability(Row row) {
		// [num of target columns] [num of unique vals in target column]
		HashMap<String , HashMap<Object , Double>> probabilities = new HashMap<String , HashMap<Object , Double>>();
		//for each target to predict
		for(int j = 0; j < super.trainDF_targets.getNumColumns(); j++) {
			probabilities.put(super.trainDF_targets.getColumn_byIndex(j).getName(), probabilityForClass(row,j));
		}
		return probabilities;
	}
	public ArrayList<HashMap<String , HashMap<Object , Double>> > probabilityDF(DataFrame df){
		ArrayList<HashMap<String , HashMap<Object , Double>> > p = new ArrayList<HashMap<String , HashMap<Object , Double>> >();
		for(int i = 0; i < df.getNumRows(); i++) {
			p.add(probability(df.getRow_byIndex(i)));
			System.out.println(p.get(i));
		}
		return p;
	}
	@Override
	public Particle predict(Row row) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<ArrayList<Particle>> predictDF(DataFrame testDF) {
		// TODO Auto-generated method stub
		return null;
	}

}
