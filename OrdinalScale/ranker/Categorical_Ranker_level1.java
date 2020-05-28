package ranker;

import java.util.ArrayList;

import chi2.Chi2Independents;
import dataframe.Column;
import dataframe.DataFrame;
import info_gain.GainRatio;
import info_gain.GiniIndex;
import info_gain.InformationGain;

/**
 * Methods to compare and rank categorical variables against a categorical target
 * @author logan.collier
 *
 */
public class Categorical_Ranker_level1 {
	private DataFrame df;
	private int targetIndex;
	
	private GainRatio gain_ratio;
	private InformationGain gain;
	private GiniIndex gini;
	private Chi2Independents chi2;
	
	public ArrayList<Column> GAIN;
	public ArrayList<Column> GAIN_RATIO;
	public ArrayList<Column> GINI;
	public ArrayList<Column> CHI2;
	public final int rankers = 4; //number of ranker methods used
	public boolean ranked;
	/**
	 * Category Ranker
	 * @param df - the dataframe
	 * @param targetIndex - dataframe keeps a list of target columns, if there is one target it will be 0
	 */
	public Categorical_Ranker_level1(DataFrame df, int targetIndex) {
		if(df.numCategorical < 1) {
			ranked = false;
		}else {
			this.targetIndex = targetIndex;
			this.df = df;
			this.gain_ratio = new GainRatio(df);
			this.gain = new InformationGain(df);
			this.gini = new GiniIndex(df);
			this.chi2 = new Chi2Independents(df);
			makeGain();
			makeGini();
			makeGainRatio();
			makeChi2();
			ranked = true;
		}
	}
	public void printRankings() {
		if(ranked) {
			System.out.println("GAIN: ");
			for(int i = 0; i < GAIN.size(); i++) {
				System.out.print(GAIN.get(i).getName()+" ,");
			}
			System.out.println();
			System.out.println("GAIN RATIO: ");
			for(int i = 0; i < GAIN_RATIO.size(); i++) {
				System.out.print(GAIN_RATIO.get(i).getName()+" ,");
			}
			System.out.println();
			System.out.println("GINI: ");
			for(int i = 0; i < GINI.size(); i++) {
				System.out.print(GINI.get(i).getName()+" ,");
			}
			System.out.println();
			System.out.println("CHI2: ");
			for(int i = 0; i < CHI2.size(); i++) {
				System.out.print(CHI2.get(i).getName()+" ,");
			}
			System.out.println();
		}else {
			System.out.println("No categorical variables to rank");
		}
		
	}
	public void printRankings_detailed() {
		if(ranked) {
			System.out.println("GAIN: ");
			for(int i = 0; i < GAIN.size(); i++) {
				System.out.print(GAIN.get(i).getName()+" "+gain.info.get(i)+" ,");
			}
			System.out.println();
			System.out.println("GAIN RATIO: ");
			for(int i = 0; i < GAIN_RATIO.size(); i++) {
				System.out.print(GAIN_RATIO.get(i).getName()+" "+gain_ratio.info.get(i)+" ,");
			}
			System.out.println();
			System.out.println("GINI: ");
			for(int i = 0; i < GINI.size(); i++) {
				System.out.print(GINI.get(i).getName()+" "+gini.info.get(i)+" ,");
			}
			System.out.println();
			System.out.println("CHI2: ");
			for(int i = 0; i < CHI2.size(); i++) {
				System.out.print(CHI2.get(i).getName()+" "+chi2." ,");
			}
			System.out.println();
		}else {
			System.out.println("No categorical variables to rank");
		}
		
	}
	private void makeGain() {
		GAIN = gain.gain(targetIndex);
	}
	private void makeGainRatio() {
		GAIN_RATIO = gain_ratio.gain(targetIndex);
	}
	private void makeGini() {
		GINI = gini.gain(targetIndex);
	}
	private void makeChi2() {
		CHI2 = chi2.ranked(targetIndex);
	}
}
