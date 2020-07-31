package ranker;

import java.util.logging.Level;

import dataframe.DataFrame;
import log.Loggers;

public class Recollection2 {
	private DataFrame df;
	private Ranker chi2;
	private Ranker infoGain;
	private Ranker gainRatio;
	private Ranker gini;
	
	private String target;
	private int total_recollections = 0;
	
	public Recollection2(DataFrame df) {
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
	public DataFrame[][] releaseRecollection(DataFrame df,int initialNumColumns, int terminate,int stepSize){
		
		Thread t0 = null;
		Thread t1 = null;
		Thread t2 = null;
		Thread t3 = null;

		DataFrame[][] reco = new DataFrame[total_recollections][];
		int cnt =0;
		if(chi2 != null) {
			final int a = cnt;
			t0 = new Thread(() -> reco[a] = Chi2Recollection(df,initialNumColumns, terminate,stepSize));
			t0.setName("RCOLLECTION CHI");
			Loggers.recollection_Logger.config(t0.getName()+" START");
			t0.start();
			cnt++;
			//reco.add(Chi2Recollection(df,initialNumColumns, terminate,stepSize));
		}
		if(infoGain != null) {
			final int b = cnt;
			t1 = new Thread(() -> reco[b] = infoGainRecollection(df,initialNumColumns, terminate,stepSize));
			t1.setName("RCOLLECTION GAIN");
			Loggers.recollection_Logger.config(t1.getName()+" START");
			t1.start();
			//reco.add(infoGainRecollection(df,initialNumColumns, terminate,stepSize));
			cnt++;
		}
		if(gainRatio != null) {
			final int c = cnt;
			t2 = new Thread(() -> reco[c] = infoGainRecollection(df,initialNumColumns, terminate,stepSize));
			t2.setName("RCOLLECTION RATIO");
			Loggers.recollection_Logger.config(t2.getName()+" START");
			t2.start();
			//reco.add(gainRatioRecollection(df,initialNumColumns, terminate,stepSize));
			cnt++;
		}
		if(gini != null) {
			final int d = cnt;
			t3 = new Thread(() -> reco[d] = infoGainRecollection(df,initialNumColumns, terminate,stepSize));
			t3.setName("RCOLLECTION GINI");
			Loggers.recollection_Logger.config(t3.getName()+" START");
			t3.start();
			//reco.add(giniRecollection(df,initialNumColumns, terminate,stepSize));
			cnt++;
		}
		System.out.println(t0.getName()+" "+t0.isAlive());
		System.out.println(t1.getName()+" "+t1.isAlive());
		System.out.println(t2.getName()+" "+t2.isAlive());
		System.out.println(t3.getName()+" "+t3.isAlive());
		try {
		    t0.join();
		    t1.join();
		    t2.join();
		    t3.join();
		} catch (InterruptedException e) { /* NOP */ }
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
	public DataFrame[] Chi2Recollection(DataFrame df,int initialNumColumns, int terminate,int stepSize){
		//int mid = ((terminate - initialNumColumns)/2)+initialNumColumns;
		int size = (int)Math.ceil((double)(terminate - initialNumColumns)/stepSize)+1;
		DataFrame[] recollection = new DataFrame[size];
		Loggers.recollection_Logger.config("RELEASE CHI: "+recollection.length+" SIZE: "+size);
		String tar;
		if(this.target == null) {
			tar = df.target_columns.get(0).getName();
		}else {
			tar = target;
		}
		int cnt = 0;
		//ArrayList<DataFrame> recollection = new ArrayList<DataFrame>();
		for(int i = initialNumColumns; i < terminate; i+=stepSize) {
			recollection[cnt] = chi2.Rank(df, i, tar);
			cnt++;
		}
		return recollection;
	}
	public DataFrame[] infoGainRecollection(DataFrame df,int initialNumColumns, int terminate,int stepSize){
		int size = (int)Math.ceil((double)(terminate - initialNumColumns)/stepSize)+1;
		//int mid = ((terminate - initialNumColumns)/2)+initialNumColumns;
		DataFrame[] recollection = new DataFrame[size];
		String tar;
		if(this.target == null) {
			tar = df.target_columns.get(0).getName();
		}else {
			tar = target;
		}
		int cnt = 0;
		//ArrayList<DataFrame> recollection = new ArrayList<DataFrame>();
		for(int i = initialNumColumns; i < terminate; i+=stepSize) {
			recollection[i] = infoGain.Rank(df, i, tar);
			cnt++;
		}
		
		return recollection;
	}
	public DataFrame[] gainRatioRecollection(DataFrame df,int initialNumColumns, int terminate,int stepSize){
		int size = (int)Math.ceil((double)(terminate - initialNumColumns)/stepSize)+1;
		//int mid = ((terminate - initialNumColumns)/2)+initialNumColumns;
		DataFrame[] recollection = new DataFrame[size];
		String tar;
		if(this.target == null) {
			tar = df.target_columns.get(0).getName();
		}else {
			tar = target;
		}
		int cnt = 0;
		//ArrayList<DataFrame> recollection = new ArrayList<DataFrame>();
		for(int i = initialNumColumns; i < terminate; i+=stepSize) {
			recollection[i] = gainRatio.Rank(df, i, tar);
			cnt++;
		}
		
		return recollection;
	}
	public DataFrame[] giniRecollection(DataFrame df,int initialNumColumns, int terminate,int stepSize){
		
		int size = (int)Math.ceil((double)(terminate - initialNumColumns)/stepSize)+1;
		DataFrame[] recollection = new DataFrame[size];
		//int mid = ((terminate - initialNumColumns)/2)+initialNumColumns;
		String tar;
		if(this.target == null) {
			tar = df.target_columns.get(0).getName();
		}else {
			tar = target;
		}
		int cnt = 0;
		//ArrayList<DataFrame> recollection = new ArrayList<DataFrame>();
		for(int i = initialNumColumns; i < terminate; i+=stepSize) {
			recollection[i] = gini.Rank(df, i, tar);
			cnt++;
		}
		
		return recollection;
	}
	public void setPriorityTarget(String target) {
		this.target = target;
	}
}
