package machinations;

import java.util.ArrayList;
import java.util.HashMap;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.Row;
import dataframe.Util;
import particles.Particle;

public class NaiveBayes2 extends Model{

	public HashMap<String , HashMap<Object, HashMap<String, Double[]>>> cont_Naive_Bayes;
	public HashMap<String , HashMap<Object, HashMap<String, HashMap<Object, Double>>>> cat_Naive_Bayes;
	private HashMap<String,DataFrame[]> classes = new HashMap<String,DataFrame[]>(); //key -> target column name, value df array of classes in target column
	private DataFrame df;
	
	public NaiveBayes2() {
	}
	public NaiveBayes2(DataFrame df) {
		super.train(df);
		this.df = df;
		setClasses();
		initialize_probability_tables();
		setProbabilityTable();
		
	}
	/**
	 * breaks up dataframe into an array based on targets
	 */
	private void setClasses() {
		for(Column i : df.target_columns) {
			this.classes.put(i.getName(),Util.splitOnTarget(df, i));
		}
	}
	/**
	 * set the hashmap keys which will act as probability tables
	 */
	private void initialize_probability_tables() {
		this.cont_Naive_Bayes = new HashMap<String , HashMap<Object, HashMap<String, Double[]>>>();
		this.cat_Naive_Bayes = new HashMap<String , HashMap<Object, HashMap<String, HashMap<Object, Double>>>>();
		//for each target column
		for(Column a : df.target_columns) {
			HashMap<Object, HashMap<String, Double[]>> cont_i = new HashMap<Object, HashMap<String, Double[]>>();
			HashMap<Object, HashMap<String, HashMap<Object, Double>>> cat_i = new HashMap<Object, HashMap<String, HashMap<Object, Double>>>();
			//for each value in a target column
			for(Object b : a.getUniqueValues()) {
				HashMap<String, Double[]> cont_ii = new HashMap<String, Double[]>();
				HashMap<String, HashMap<Object, Double>> cat_ii = new HashMap<String, HashMap<Object, Double>>();
				//for each categorical column
				for(Column c : df.categorical_columns) {
					HashMap<Object, Double> cat = new HashMap<Object, Double>();
					//for each value in a categorical column
					for(Object d : c.getUniqueValues()) {
						cat.put(d, (double) 0);
					}
					cat_ii.put(c.getName(), cat);
				}
				cat_i.put(b, cat_ii);
				//for each numeric column
				for(Column e : df.numeric_columns) {
					//mean and variance
					Double[] cont = new Double[2];
					cont_ii.put(e.getName(), cont);
				}
				cont_i.put(b, cont_ii);
			}
			cont_Naive_Bayes.put(a.getName(),cont_i);
			cat_Naive_Bayes.put(a.getName(), cat_i);
		}
		
	}
	/**
	 * fill the probability tables
	 */
	private void setProbabilityTable() {
		//for each target dataframe array
		for(String a : classes.keySet()) {
			//for each target class
			for(DataFrame b : classes.get(a)) {
				//for each categorical column in a traget class
				for(Column c : b.categorical_columns) {
					this.cat_Naive_Bayes.get(a).get(b.getName()).replace(c.getName(), set_categoryColumnProbability(c));
					//for each value in a categorical column
					//for(Object d : c.getUniqueValues()) {
					//	this.cat_Naive_Bayes.get(a).get(b.getName()).get(c.getName()).replace(d, value); 
					//}
				}
				for(Column e : b.numeric_columns) {
					this.cont_Naive_Bayes.get(a).get(b.getName()).get(e.getName())[0] = e.mean;
					this.cont_Naive_Bayes.get(a).get(b.getName()).get(e.getName())[1] = e.variance;
				}
			}
		}
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
	 * Returns the probability for a particular value in a categorical column for a specific target
	 * 
	 * @param targetName
	 * @param targetValue
	 * @param variable
	 * @param variableValue
	 * @return double - the probability of being a certain value in a particular variable
	 */
	public double getCategoricalProbability(String targetName, Object targetValue, String variable, Object variableValue) {
		return cat_Naive_Bayes.get(targetName).get(targetValue).get(variable).get(variableValue);
	}
	/**
	 * Returns the gaussian probability for a value in a numeric column for a specific target
	 * @param targetName
	 * @param targetValue
	 * @param variable
	 * @param x - the value
	 * @return double - the probability a number indicates a specific class in a target 
	 */
	public double getContinuousProbability(String targetName, Object targetValue, String variable, Particle x) {
		//System.out.println(targetName+" "+targetValue+" "+variable+" "+x.toString()+" "+gaussian_probability(x.getDoubleValue(), cont_Naive_Bayes.get(targetName).get(targetValue).get(variable)));
		return  gaussian_probability(x.getDoubleValue(), cont_Naive_Bayes.get(targetName).get(targetValue).get(variable)); 
	}
	/**
	 * fill probability table for each class in one target variable, with an input row to predict
	 * @param row - row to make a prediction
	 * @param target - the target column index to predict
	 * @return HashMap<Object , Double> classProb
	 */
	private HashMap<Object , Double> probabilityForClass(Row row, int target){
		
		HashMap<Object , Double> classProb = new HashMap<Object , Double>();
		//for each class in a target column
		for(Object z : this.df.target_columns.get(target).getUniqueValues()) {
			double prob = 1;
			//for each variable in a row
			for(int i = 0; i < row.rowLength; i++) {
				if(super.trainDF_variables.getColumn(i).getType() == 'N') {
					prob = prob * getContinuousProbability(this.df.target_columns.get(target).getName(),z,super.trainDF_variables.getColumn(i).getName(),row.getParticle(i));
				}else {
					//System.out.println(super.trainDF_variables.getColumn(i).getName()+" "+row.getParticle(i).getValue());
					prob = prob * cat_Naive_Bayes.get(this.df.target_columns.get(target).getName()).get(z).get(super.trainDF_variables.getColumn(i).getName()).get(row.getParticle(i).getValue());
				}
			}
			//System.out.println(z+" "+prob);
			classProb.put(z, prob);
		}
		return classProb;
		
	}
	/****************************************************/
	/*				PRINT METHODS						*/
	/****************************************************/
	public void printCategorical_probabilityTable() {
		System.out.println(this.cat_Naive_Bayes);
	}
	/****************************************************/
	/*				MODEL OVERRIDES						*/
	/****************************************************/
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
	@Override
	public Object predict(Row row) {
		HashMap<String , HashMap<Object , Double>> probs = probability(row);
		Object pred = null;
		double max = 0;
		//System.out.println(probs);
		for(String i : probs.keySet()) {
			for(Object j : probs.get(i).keySet()) {
				if(probs.get(i).get(j) > max) {
					max = probs.get(i).get(j);
					pred = j;
					//System.out.println(i+" "+pred);
				}
			}
		}
		
		return pred;
	}

	@Override
	public HashMap<String, ArrayList<Object>> predictDF(DataFrame testDF) {
		HashMap<String , ArrayList<Object>> preds = new HashMap<String , ArrayList<Object>> ();
		ArrayList<Object> p = new ArrayList<Object>();
		
		System.out.println("NB TOT PREDS: "+df.getNumRows());
		for(int j = 0; j < super.trainDF_targets.getNumColumns();j ++) {
			p.clear();
			
			for(int i = 0; i < df.getNumRows(); i++) {
				p.add(predict(df.getRow_byIndex(i)));
				
				//System.out.println("PRED: " +predict(df.getRow_byIndex(i)) + " ACTUAL: "+ this.trainDF_targets.getRow_byIndex(i).getParticle(0));
			}
			preds.put(super.trainDF_targets.getColumn(j).getName(), p);
		}
		return preds;
	}

	@Override
	public void initiallize() {
		// TODO Auto-generated method stub
		
	}

}
