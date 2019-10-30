package anova;

import java.util.ArrayList;
import java.util.Set;

import dataframe.Column;
import dataframe.DataFrame;
import forensics.Stats;
/**
 * One-Way Anova:
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
	
	private DataFrame df;
	private ArrayList<DataFrame[]> classes;
	private int[] degreesOfFreedom;
	private double[] meanSquaresTreatment;
	private double[] meanSquaresError;
	public OneWay_ANOVA(DataFrame df) {
		this.df = df;
		classes = new ArrayList<DataFrame[]>();
		initializeAll_anova();
		setDegreesFreedom();
	}
	private void cybernization() {
		for(DataFrame[] i : classes) {
			for(int j = 0; j < )
			SumOfSquaresOfError(i,)
			
		}
	}
	/**
	 * initiallizing for all targets
	 */
	private void initializeAll_anova() {
		for(Integer i : df.targetIndexes) {
			initiallize_anova(i);
		}
	}
	//break up dataframe into target groups
	private void initiallize_anova(int target_index) {
		Set<Object> targets = df.getColumn(target_index).getUniqueValues();
		DataFrame[] target_class = new DataFrame[targets.size()];
		int cnt = 0;
		String[] args = new String[3];
		args[0] = df.getColumn(target_index).getName();
		args[1] = "==";
		for(Object i : targets) {
			args[2] = i.toString();
			target_class[cnt] = df.acquire(args);
			target_class[cnt].setStatistics();
			cnt++;
		}
		classes.add(target_class);
	}
	/**
	 * 1. calculate sum of squares of error for each class and add them up
	 * @param columnIndex
	 */
	private void SumOfSquaresOfError(DataFrame[] class_n ,int columnIndex) {
		double sumSSE = 0;
		for(int i = 0; i < class_n.length; i++) {
			sumSSE = sumSSE + Stats.zeroSquaredSum(class_n[i].getColumn(columnIndex));
		}
	}
	/**
	 * 2. the squared deviations of each sample mean from the overall mean,
	 *  and multiply this number by one less than the number of populations
	 * @param columnIndex
	 */
	private void sumOfSquaresOfTreatment(DataFrame[] class_n, int columnIndex) {
		double sum = 0;
		for(int i = 0; i < class_n.length;i++) {
			sum = sum + Math.pow( class_n[i].getColumn(columnIndex).mean - df.getColumn(columnIndex).mean, 2);
		}
		sum = sum * (class_n.length - 1);	
	}
	/**
	 * calculate degees freedom: TotalRows - #classes
	 * @return
	 */
	private void setDegreesFreedom() {
		int[] degFree = new int[classes.size()];
		int cnt = 0;
		for(DataFrame[] i : classes) {
			degFree[cnt] = df.getNumRows() - i.length;
		}
		this.degreesOfFreedom = degFree;
		
	}

}
