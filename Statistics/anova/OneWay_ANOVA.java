package anova;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import dataframe.Column;
import dataframe.DataFrame;
import forensics.Stats;
/**
 * One-Way Anova:
 * http://sphweb.bumc.bu.edu/otlt/MPH-Modules/BS/BS704_HypothesisTesting-ANOVA/BS704_HypothesisTesting-Anova_print.html
 * A one way ANOVA is used to compare two means from two independent (unrelated) groups using the F-distribution.
 * The null hypothesis for the test is that the two means are equal. Therefore, a significant result means that the two means are unequal.
 * 
 * When to use it:
 * Situation 1: You have a group of individuals randomly split into smaller groups and completing different tasks.
 * For example, you might be studying the effects of tea on weight loss and form three groups: green tea, black tea, and no tea.
 * Situation 2: Similar to situation 1, but in this case the individuals are split into groups based on an attribute they possess.
 * For example, you might be studying leg strength of people according to weight. You could split participants into 
 * weight categories (obese, overweight and normal) and measure their leg strength on a weight machine.
 * 
 * @author logan.collier
 *
 */
public class OneWay_ANOVA {

	private DataFrame anova;
	private DataFrame df;
	private ArrayList<DataFrame[]> classes;
	private HashMap<String, HashMap<String, Double>> MSB; //mean sques between treatments(groups
	private HashMap<String, HashMap<String, Double>> meanSquaresError;
	private HashMap<String, HashMap<String, Double>> F_RATIO;
	
	public OneWay_ANOVA(DataFrame df) {
		//LOGGER.log(Level.FINE, "Construction of one-way ANOVA");
		//LOGGER.logp
		this.df = df;
		anova = new DataFrame();
		
		this.meanSquaresError = new HashMap<String, HashMap<String, Double>>();
		this.MSB = new HashMap<String, HashMap<String, Double>>();
		this.F_RATIO = new HashMap<String, HashMap<String, Double>>();
		classes = new ArrayList<DataFrame[]>();
		
	}
	public void invokeANOVA(){
		//LOGGER.log(Level.FINER, "INVOKE ANOVA");
		initializeAll_anova();
		HashMap<String, Double> MS;
		HashMap<String, Double> MSE;
		HashMap<String, Double> F;
		for(Column i : df.target_columns) {
			MS = new HashMap<String, Double>();
			MSE = new HashMap<String, Double>();
			F = new HashMap<String, Double>();
			for(int j = 0; j < df.numeric_columns.size(); j++) {
				for(DataFrame[] k : classes) {
					MS.put(i.getName(), sumOfSquaresOfTreatment(k, j));
					MSE.put(i.getName(), SumOfSquaresOfError(k,j));
					//System.out.println(i.getName());
					F.put(i.getName(), MS.get(df.numeric_columns.get(j).getName() ) / MSE.get(df.numeric_columns.get(j).getName() ) );
				}
			}
			this.MSB.put(i.getName(), MS);
			this.meanSquaresError.put(i.getName(), MSE);
			this.F_RATIO.put(i.getName(), F);
			//System.out.println("HERE"+MSB);
		}
	}
	/**
	 * initiallizing for all targets
	 */
	public void initializeAll_anova() {
		for(Column i : df.target_columns) {
			initiallize_anova(i);
		}
	}
	//break up dataframe into target groups
	private void initiallize_anova(Column target_index) {
		Set<Object> targets = target_index.getUniqueValues();
		DataFrame[] target_class = new DataFrame[targets.size()];
		int cnt = 0;
		String[] args = new String[3];
		args[0] = target_index.getName();
		args[1] = "==";
		for(Object i : targets) {
			args[2] = i.toString();
			target_class[cnt] = df.acquire(args);
			target_class[cnt].setStatistics();
			target_class[cnt].setName(i.toString());
			cnt++;
		}
		classes.add(target_class);
	}
	/**
	 * 1. calculate sum of squares of error for each class and add them up
	 * @param columnIndex
	 */
	private double SumOfSquaresOfError(DataFrame[] class_n ,int columnIndex) {
		double sumSSE = 0;
		for(int i = 0; i < class_n.length; i++) {
			sumSSE = sumSSE + Stats.zeroSquaredSum(class_n[i].getColumn(columnIndex));
		}
		return sumSSE/(df.getNumRows()-class_n.length);
	}
	/**
	 * 2. the squared deviations of each sample mean from the overall mean,
	 *  and multiply this number by one less than the number of populations
	 * @param columnIndex
	 */
	private double sumOfSquaresOfTreatment(DataFrame[] class_n, int columnIndex) {
		double sum = 0;
		for(int i = 0; i < class_n.length;i++) {
			sum = sum + (class_n[i].getNumRows() * Math.pow( class_n[i].getColumn(columnIndex).mean - df.getColumn(columnIndex).mean, 2));
		}
		return sum/(class_n.length-1);
	}
	
	public void printResults() {
		System.out.println("F-RATIOS:");
		for(String i : this.F_RATIO.keySet()) {
			System.out.println(i);
			for(String j : this.F_RATIO.get(i).keySet() ) {
				System.out.println(j +": "+ this.F_RATIO.get(i).get(j));
			}
		}
	}

}
