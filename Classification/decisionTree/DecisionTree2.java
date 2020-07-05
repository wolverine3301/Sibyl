package decisionTree;

import java.util.ArrayList;
import java.util.HashMap;

import dataframe.DataFrame;
import dataframe.Row;
import info_gain.Gain;
import info_gain.GainRatio;
import info_gain.GiniIndex;
import info_gain.InformationGain;
import machinations.Model;
import ranker.Rank;

public class DecisionTree2 extends Model{
	
	private Rank rankMethod = Rank.GINI_INDEX;
	private DataFrame df;
	private Gain ranker;
	public DecisionTree2(DataFrame df,Rank rankMethod) {
		this.rankMethod = rankMethod;
		this.df = df;
		setRanker(rankMethod);

	}
	/**
	 * initiallize the ranker method
	 * @param rank
	 */
	private void setRanker(Rank rank) {
		if(rankMethod == Rank.GINI_INDEX) {
			ranker = new GiniIndex(df);
		}
		else if(rankMethod == Rank.GAIN_RATIO) {
			ranker = new GainRatio(df);
		}else {	
			ranker = new InformationGain(df);
		}
	}
	private void rankColumns() {
		ranker.gain(0);
	}
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

	@Override
	public void saveModel(String fileName) {
		// TODO Auto-generated method stub
		
	}

}
