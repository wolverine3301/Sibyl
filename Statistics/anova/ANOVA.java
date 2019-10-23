package anova;

import java.util.Set;

import dataframe.DataFrame;

public class ANOVA {
	private DataFrame df;
	/**
	 * Use anova for all dataframe
	 * @param df
	 */
	public ANOVA(DataFrame df) {
		this.df = df;
		
	}
	public void initiallize_ANOVA() {
		for(Integer i : df.targetIndexes) {
			Set<Object> targets = df.getColumn(i).getUniqueValues();
		}
		
	}
	/**
	 * analasys of determination on a column
	 * @param c
	 */
	public void anova(Column c) {
		
	}

}
