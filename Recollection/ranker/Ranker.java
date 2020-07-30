package ranker;

import java.util.ArrayList;
import java.util.HashMap;

import dataframe.Column;
import dataframe.DataFrame;

public abstract class Ranker {
	
	protected HashMap<String, ArrayList<Column>> ranks;
	protected DataFrame df;
	
	public Ranker(DataFrame df) {
		this.df = df;
		this.ranks = new HashMap<String, ArrayList<Column>>();
		initiallize();
	}
	protected abstract void initiallize();
	
	/**
	 * generates a dataframe with a set number of best columns based on ranking method
	 * @param df
	 * @param vars - number of best columns to include
	 * @return dataframe
	 */
	public abstract DataFrame Rank(DataFrame df,int vars,String target);
	
	public HashMap<String, ArrayList<Column>> getRanks() {
		return ranks;
	}
	public ArrayList<Column> getRankTarget(String target){
		return ranks.get(target);
	}
}
