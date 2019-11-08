package scout;

import java.util.ArrayList;

import chi2.Chi2Independents;
import dataframe.Column;
import dataframe.DataFrame;
import info_gain.GainRatio;
import info_gain.GiniIndex;
import info_gain.InformationGain;
/**
 * Various categorical column ranking methods
 * @author logan.collier
 *
 */
public class CategoryRanker {
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
	
	public CategoryRanker(DataFrame df, int targetIndex) {
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
	}
	public void printRankings() {
		System.out.println("GAIN: ");
		for(int i = 0; i < GAIN.size(); i++) {
			System.out.print(GAIN.get(i).getName()+" ");
		}
		System.out.println();
		System.out.println("GAIN RATIO: ");
		for(int i = 0; i < GAIN_RATIO.size(); i++) {
			System.out.print(GAIN_RATIO.get(i).getName()+" ");
		}
		System.out.println();
		System.out.println("GINI: ");
		for(int i = 0; i < GINI.size(); i++) {
			System.out.print(GINI.get(i).getName()+" ");
		}
		System.out.println();
		System.out.println("CHI2: ");
		for(int i = 0; i < CHI2.size(); i++) {
			System.out.print(CHI2.get(i).getName()+" ");
		}
		System.out.println();
		
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
