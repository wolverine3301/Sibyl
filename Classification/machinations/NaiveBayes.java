package machinations;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import dataframe.Column;
import dataframe.DataFrame;
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
	private ArrayList<DataFrame[]> classes = new ArrayList<DataFrame[]>();
	
	public NaiveBayes() {

	}
	@Override
	public void initiallize() {
		setClasses();
		cont_Naive_Bayes = continuous_naive_bayes();
		cat_Naive_Bayes = categorical_naive_bayes();
	}
	/**
	 * 1)construct continuous Naive Bayes for all target columns
	 * continuous numbers are handled differently than categorical in a naive bayes 
	 * Specifically this is using Gaussian probability
	 * STRUCTURE: 
	 * HASHMAP<Target Column name> -> HASHMAP< Nth unique value within Target Column > -> HASHMAP< Variable Column Name > -> Double[] -> [0] = column mean , [1] = column variance
	 * mean and variance used to make probability for a numeric column.
	 * @return HashMap<String , HashMap<Object, HashMap<String, Double[]>>>
	 */
	public HashMap<String , HashMap<Object, HashMap<String, Double[]>>> continuous_naive_bayes(){
		HashMap<String , HashMap<Object, HashMap<String, Double[]>>> NaiveBayes =
				new HashMap<String , HashMap<Object, HashMap<String, Double[]>>>();
		for(int i = 0; i < super.trainDF_targets.getNumColumns(); i++) {
			NaiveBayes.put(super.trainDF_targets.getColumn(i).getName(), cont_naivebayes_i(i));	
		}
		return NaiveBayes;
	}
	/**
	 * 2)
	 * naive bayes probability table for continuous columns
	 * HASHMAP< Nth unique value within Target Column > -> HASHMAP< Variable Column Name > -> Double[] -> [0] = column mean , [1] = column variance
	 * @param targetNum
	 * @return
	 */
	public HashMap<Object, HashMap<String, Double[]>> cont_naivebayes_i(int targetNum){
		HashMap<Object , HashMap<String , Double[]>> naive_bayes = new HashMap<Object , HashMap<String , Double[]>>();
		int cnt = 0;
		for(Object i : super.trainDF_targets.getColumn(targetNum).getUniqueValues()) {
			naive_bayes.put(i,continuous_ProbabilityTable(targetNum,cnt));
			cnt++;
		}
		return naive_bayes;
	}
	/**
	 * 3)
	 * set table of information of continuous columns and being in Class k
	 * @param df_i - dataframe of only Class k
	 * @return HashMap<String, Double[]>
	 */
	private HashMap<String, Double[]> continuous_ProbabilityTable(int target ,int index){
		HashMap<String, Double[]> cont_columns_probabilities = new HashMap<String, Double[]>();
		for(int i = 0; i < super.trainDF_variables.getNumColumns();i++) {
			cont_columns_probabilities.put(super.trainDF_variables.getColumn(i).getName(), set_continuousColumnProbability(classes.get(target)[index].getColumn(i)));
		}
		return cont_columns_probabilities;
	}
	/**
	 * return the probability of a continuous variable of Class k
	 * @param x the value
	 * @param meanVar - double[] -> meanVar[0] = mean of column in Class K, meanVar[1] = variance of column in Class k
	 * @return double - Gaussian probability
	 */
	private double gaussian_probability(double x, Double[] meanVar) {
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
	 * 1)
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
			NaiveBayes.put(super.trainDF_targets.getColumn(i).getName(), cat_naivebayes_i(i));	
		}
		return NaiveBayes;
	}
	
	/**
	 * 2)
	 * naive bayes probability table for categorical columns
	 * @param targetNum
	 * @return
	 */
	
	public HashMap<Object, HashMap<String, HashMap<Object, Double>>> cat_naivebayes_i(int targetNum){
		
		HashMap<Object, HashMap<String, HashMap<Object, Double>>> naive_bayes = new HashMap<Object, HashMap<String, HashMap<Object, Double>>>();
		int cnt = 0;
		for(Object i : super.trainDF_targets.getColumn(targetNum).getUniqueValues()) {
				naive_bayes.put(i, categorical_ProbabilityTable(targetNum , cnt));
				cnt++;
			}
		return naive_bayes;
	}
	
	/**
	 * sets table of probabilities of categorical columns and being Class k
	 * @param df_i - dataframe of only Class k
	 * @return HashMap<String, HashMap<Object, Double>>
	 */
	private HashMap<String, HashMap<Object, Double>> categorical_ProbabilityTable(int target, int index){
		HashMap<String, HashMap<Object, Double>> cat_columns_probabilities = new HashMap<String, HashMap<Object, Double>>();
		for(int i = 0; i < super.trainDF_variables.getNumColumns();i++) {
			cat_columns_probabilities.put(super.trainDF_variables.getColumn(i).getName(), set_categoryColumnProbability(classes.get(target)[index].getColumn(i).getName()));
		}
		return cat_columns_probabilities;
	}

	/**
	 * returns a hashmap of values in a column and the proportion the make up of Class k
	 * @param c - The column
	 * @return HashMap<Object, Double>
	 */
	private HashMap<Object, Double> set_categoryColumnProbability(String colName){
		return super.trainDF_variables.getColumn_byName(colName).getFeatureStats();
	}
	//arraylist of df[] for a df for every unique value in a target column
	private void setClasses() {
		for(int i = 0; i < super.trainDF_targets.getNumColumns();i++) {
			classes(i);
		}
	}
	/**
	 * Split dataframe based on 1 target columns unique values(Classes)
	 * @param targetNum the target column
	 * @return DataFrame[]
	 */
	public void classes(int targetNum){
		Object[] targetClasses = super.trainDF_targets.getColumn(targetNum).getUniqueValues().toArray();
		DataFrame[] classes = new DataFrame[targetClasses.length];
		String[] arg = new String[3];
		arg[1] = "==";
		//split dataframe based on current target columns classes, result is 
		//n dataframes each with rows containing 1 value in the target column
		for(int i = 0; i < targetClasses.length; i++) {
			arg[0] = super.trainDF_targets.getColumn(targetNum).getName();
			arg[2] = targetClasses[i].toString();
			classes[i] = super.rawTrain.acquire(arg);
		}
		this.classes.add(classes);
	}
	public double getCategoricalProbability(String targetName, Object targetValue, String variable, Object variableValue) {
		return cat_Naive_Bayes.get(targetName).get(targetValue).get(variable).get(variableValue);
	}
	public double getContinuousProbability(String targetName, Object targetValue, String variable, Particle x) {
		return  gaussian_probability(x.getDoubleValue(), cont_Naive_Bayes.get(targetName).get(targetValue).get(variable)); 
	}
	private HashMap<Object , Double> probabilityForClass(Row row, int target){
		HashMap<Object , Double> classProb = new HashMap<Object , Double>();
		for(Object z : super.trainDF_targets.getColumn(target).getUniqueValues()) {
			double prob = 1;
			for(int i = 0; i < row.rowLength; i++) {
				if(super.trainDF_variables.getColumn(i).getType() == 'N') {
					prob = prob * getContinuousProbability(super.trainDF_targets.getColumn(target).getName(),z,super.trainDF_variables.getColumn(i).getName(),row.getParticle(i));
				}else {
					System.out.println(super.trainDF_variables.getColumn(i).getName());
					prob = prob * cat_Naive_Bayes.get(super.trainDF_targets.getColumn(target).getName()).get(z).get(super.trainDF_variables.getColumn(i).getName()).get(row.getParticle(i).getValue());
				}
				if(super.trainDF_variables.getColumn(i).getType() == 'N') {
					prob = prob * getContinuousProbability(super.trainDF_targets.getColumn(target).getName(),z,super.trainDF_variables.getColumn(i).getName(),row.getParticle(i));
				}else
					prob = prob * cat_Naive_Bayes.get(super.trainDF_targets.getColumn(target).getName()).get(z).get(super.trainDF_variables.getColumn(i).getName()).get(row.getParticle(i).getValue());
			}
			classProb.put(z, prob);
		}
		return classProb;
		
	}
	public void printProbTable() {
		for(String i : cont_Naive_Bayes.keySet()) {
			System.out.println(i);
			for(Object j : cont_Naive_Bayes.get(i).keySet()) {
				System.out.println(j+ "  : " );
				for(String z : cont_Naive_Bayes.get(i).get(j).keySet()) {
					System.out.print(z + " == ");
					System.out.println(cont_Naive_Bayes.get(i).get(j).get(z)[0] + " , " + cont_Naive_Bayes.get(i).get(j).get(z)[1]);
				}
				System.out.println();
			}
		}
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
			probabilities.put(super.trainDF_targets.getColumn(j).getName(), probabilityForClass(row,j));
		}
		return probabilities;
	}
	public ArrayList<HashMap<String , HashMap<Object , Double>> > probabilityDF(DataFrame df){
		ArrayList<HashMap<String , HashMap<Object , Double>> > p = new ArrayList<HashMap<String , HashMap<Object , Double>> >();
		for(int i = 0; i < df.getNumRows(); i++) {
			p.add(probability(df.getRow_byIndex(i)));
			//System.out.println("PRED: " +probability(df.getRow_byIndex(i)) + " ACTUAL: "+ df.getRow_byIndex(i).getParticle(4));
		}
		return p;
	}
	@Override
	public Object predict(Row row) {
		HashMap<String , HashMap<Object , Double>> probs = probability(row);
		Object pred = null;
		double max = 0;
		for(String i : probs.keySet()) {
			for(Object j : probs.get(i).keySet()) {
				if(probs.get(i).get(j) > max) {
					max = probs.get(i).get(j);
					pred = j;
				}
			}
		}
		
		return pred;
	}
	@Override
	public HashMap<String , ArrayList<Object>> predictDF(DataFrame df) {
		HashMap<String , ArrayList<Object>> preds = new HashMap<String , ArrayList<Object>> ();
		ArrayList<Object> p = new ArrayList<Object>();
		for(int j = 0; j < super.trainDF_targets.getNumColumns();j ++) {
			p.clear();
			for(int i = 0; i < df.getNumRows(); i++) {
				p.add(predict(df.getRow_byIndex(i)));
				//System.out.println("PRED: " +predict(df1.getRow_byIndex(i)) + " ACTUAL: "+ df.getRow_byIndex(i).getParticle(4));
			}
			preds.put(super.trainDF_targets.getColumn(j).getName(), p);
		}
		return preds;
	}

}
