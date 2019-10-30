package anova;

import java.util.Set;

import dataframe.Column;
import dataframe.DataFrame;

public class ANOVA {
	private DataFrame df;
	private DataFrame[] classes;
	/**
	 * Use anova for all dataframe
	 * @param df
	 */
	public ANOVA(DataFrame df) {
		this.df = df;
		
		
	}
	public void initiallize_ANOVA(int target_index) {
		Set<Object> targets = df.getColumn(target_index).getUniqueValues();
		classes = new DataFrame[targets.size()];
		int cnt = 0;
		String[] args = new String[3];
		args[0] = df.getColumn(target_index).getName();
		args[1] = "==";
		for(Object i : targets) {
			args[2] = i.toString();
			classes[cnt] = df.acquire(args);
			classes[cnt].setStatistics();
			cnt++;
		}
		
	}
	/**
	 * analasys of determination on a column
	 * @param c
	 */
	public void anova(Column c) {
		
	}

}
