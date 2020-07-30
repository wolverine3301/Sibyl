package ranker;

import java.util.ArrayList;

import chi2.Chi2Independents;
import dataframe.DataFrame;
import info_gain.GainRatio;
import info_gain.GiniIndex;
import info_gain.InformationGain;

public class Recollection {
	
	private DataFrame df;
	private Ranker chi2;
	private Ranker infoGain;
	private Ranker gainRatio;
	private Ranker gini;
	
	private String target;
	
	public Recollection(DataFrame df) {
		this.df = df;
	}
	
	private void initiallize(boolean chi2On ,boolean gainOn, boolean gainRatioOn, boolean giniOn) {
		if(chi2On) {
			chi2 = new Chi2Ranker(df);
		}
		if(gainOn) {
			infoGain = new InfoGainRanker(df);
		}
		if(gainRatioOn) {
			gainRatio = new GainRatioRanker(df);
		}
		if(giniOn) {
			gini = new GiniRanker(df);
		}
	}
	/**
	 * generates an array of dataframes with a varying number of columns to test models on.
	 * every data frame has between initialNumColumns and terminate number of columns varying by the stepsize
	 * 
	 * @param df
	 * @param initialNumColumns -the minimum amount of columns a df will have in the array
	 * @param terminate-the maximum amount of columns a df will have in the array
	 * @param stepSize - the number of columns each dataframe will vary between the min and max
	 * @return ArrayList<DataFrame>
	 */
	private ArrayList<DataFrame> Chi2Recollection(DataFrame df,int initialNumColumns, int terminate,int stepSize){
		//int mid = ((terminate - initialNumColumns)/2)+initialNumColumns;
		String tar;
		if(this.target == null) {
			tar = df.target_columns.get(0).getName();
		}else {
			tar = target;
		}
		ArrayList<DataFrame> recollection = new ArrayList<DataFrame>();
		for(int i = initialNumColumns; i < terminate; i+=stepSize) {
			recollection.add(chi2.Rank(df, i, tar));
		}
		return recollection;
	}
	private ArrayList<DataFrame> infoGainRecollection(DataFrame df,int initialNumColumns, int terminate,int stepSize){
		//int mid = ((terminate - initialNumColumns)/2)+initialNumColumns;
		String tar;
		if(this.target == null) {
			tar = df.target_columns.get(0).getName();
		}else {
			tar = target;
		}
		ArrayList<DataFrame> recollection = new ArrayList<DataFrame>();
		for(int i = initialNumColumns; i < terminate; i+=stepSize) {
			recollection.add(infoGain.Rank(df, i, tar));
		}
		
		return recollection;
	}
	private ArrayList<DataFrame> gainRatioRecollection(DataFrame df,int initialNumColumns, int terminate,int stepSize){
		//int mid = ((terminate - initialNumColumns)/2)+initialNumColumns;
		String tar;
		if(this.target == null) {
			tar = df.target_columns.get(0).getName();
		}else {
			tar = target;
		}
		ArrayList<DataFrame> recollection = new ArrayList<DataFrame>();
		for(int i = initialNumColumns; i < terminate; i+=stepSize) {
			recollection.add(gainRatio.Rank(df, i, tar));
		}
		
		return recollection;
	}
	public void setPriorityTarget(String target) {
		this.target = target;
	}
}
