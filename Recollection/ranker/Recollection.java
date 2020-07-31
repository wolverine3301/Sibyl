package ranker;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.apache.logging.log4j.core.Logger;

import chi2.Chi2Independents;
import dataframe.DataFrame;
import info_gain.GainRatio;
import info_gain.GiniIndex;
import info_gain.InformationGain;
import log.Loggers;

public class Recollection{
	
	private DataFrame df;
	private Ranker chi2;
	private Ranker infoGain;
	private Ranker gainRatio;
	private Ranker gini;
	
	private String target;
	private int total_recollections = 0;
	
	public Recollection(DataFrame df) {
		this.df = df;
		Loggers.logHTML(Loggers.recollection_Logger,Level.FINE);
	}
	
	public void initiallize(boolean chi2On ,boolean gainOn, boolean gainRatioOn, boolean giniOn) {
		Loggers.recollection_Logger.config("CHI: "+chi2On+" GAIN: "+gainOn+" GAIN RATIO: "+gainRatioOn+ " GINI: "+giniOn);
		if(chi2On) {
			chi2 = new Chi2Ranker(df);
			total_recollections++;
		}
		if(gainOn) {
			infoGain = new InfoGainRanker(df);
			total_recollections++;
		}
		if(gainRatioOn) {
			gainRatio = new GainRatioRanker(df);
			total_recollections++;
		}
		if(giniOn) {
			gini = new GiniRanker(df);
			total_recollections++;
		}
		Loggers.recollection_Logger.info("RECOLLECTION TOTAL: "+ total_recollections);
	}
	public List<ArrayList<DataFrame>> releaseRecollection(DataFrame df,int initialNumColumns, int terminate,int stepSize){
		
		List<ArrayList<DataFrame>> reco = new ArrayList<ArrayList<DataFrame>>(total_recollections);
		int cnt =0;
		if(chi2 != null) {
			reco.add(Chi2Recollection(df,initialNumColumns, terminate,stepSize));
			cnt++;
			//reco.add(Chi2Recollection(df,initialNumColumns, terminate,stepSize));
		}
		if(infoGain != null) {
			final int b = cnt;
			reco.add(infoGainRecollection(df,initialNumColumns, terminate,stepSize));
			//reco.add(infoGainRecollection(df,initialNumColumns, terminate,stepSize));
			cnt++;
		}
		if(gainRatio != null) {
			reco.add(infoGainRecollection(df,initialNumColumns, terminate,stepSize));
			cnt++;
		}
		if(gini != null) {
			reco.add(giniRecollection(df,initialNumColumns, terminate,stepSize));
			cnt++;
		}

		return reco;
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
	public ArrayList<DataFrame> Chi2Recollection(DataFrame df,int initialNumColumns, int terminate,int stepSize){
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
	public ArrayList<DataFrame> infoGainRecollection(DataFrame df,int initialNumColumns, int terminate,int stepSize){
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
	public ArrayList<DataFrame> gainRatioRecollection(DataFrame df,int initialNumColumns, int terminate,int stepSize){
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
	public ArrayList<DataFrame> giniRecollection(DataFrame df,int initialNumColumns, int terminate,int stepSize){
		String tar;
		if(this.target == null) {
			tar = df.target_columns.get(0).getName();
		}else {
			tar = target;
		}
		ArrayList<DataFrame> recollection = new ArrayList<DataFrame>();
		for(int i = initialNumColumns; i < terminate; i+=stepSize) {
			recollection.add(gini.Rank(df, i, tar));
		}
		
		return recollection;
	}
	public void setPriorityTarget(String target) {
		this.target = target;
	}

}
