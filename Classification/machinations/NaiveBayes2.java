package machinations;

import java.util.ArrayList;
import java.util.HashMap;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.Row;
import dataframe.Util;

public class NaiveBayes2 extends Model{

	public HashMap<String , HashMap<Object, HashMap<String, Double[]>>> cont_Naive_Bayes;
	public HashMap<String , HashMap<Object, HashMap<String, HashMap<Object, Double>>>> cat_Naive_Bayes;
	private HashMap<String,DataFrame[]> classes = new HashMap<String,DataFrame[]>(); //key -> target column name, value df array of classes in target column
	private DataFrame df;
	public NaiveBayes2() {
	}
	public NaiveBayes2(DataFrame df) {
		this.df = df;
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
					this.cont_Naive_Bayes.get(a).get(b.getName()).get(e.getName())[0] = e.variance;
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
	/****************************************************/
	/*				MODEL OVERRIDES						*/
	/****************************************************/
	@Override
	public HashMap<String, HashMap<Object, Double>> probability(Row row) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object predict(Row row) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap<String, ArrayList<Object>> predictDF(DataFrame testDF) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initiallize() {
		// TODO Auto-generated method stub
		
	}

}
