package discreteize;

import java.util.ArrayList;

import dataframe.Column;

public class EqualFrequencyBinning {
	
	private Column col;
	private int numbins;
	private ArrayList<Bin> BINS;
	/**
	 * make numerical data into categorical
	 * make bins that contain an equal number of instances(data) 
	 * Example:
	 * say 100, there might be 100 people between 25-30 so thats a bin,
	 * then theres 100 people between 31-32, thats a bin
	 * @param freq - number of instances required for a bin
	 * @param col - column to bin (must be numeric)
	 */
	public EqualFrequencyBinning(int freq,Column col) {
		this.numbins = bins;
		this.col = col;
		makeBins();
	}
}
