package discreteize;

import java.util.ArrayList;

import dataframe.Column;

/**
 * abstract binning class.
 * used to make numerical columns into categorical ones
 * by constructing groups and changing numeric values into the group the fall in
 * 
 * @author logan.collier
 *
 */
public abstract class Binning {
	
	protected Column col; //the column to transform
	protected int numbins; //number of bins
	protected ArrayList<Bin> BINS; // list of bin objects
	protected String method; //the method of binning
	
	/**
	 * Constructor 
	 * @param n -number of desired bins
	 * @param c -column to be binned
	 * @param method - method of binning used
	 */
	protected Binning(int n,Column c,String method) {
		this.col = c;
		this.numbins = n;
		this.method = method;
		
	}
	/*
	 * Determine the start and end of bins to place numbers in 
	 */
	protected abstract void makeBins();
	
	/**
	 * place column into bins and return new column
	 * @return new column
	 */
	public Column binColumn() {
		Column newCol = new Column(col.getName()+"_"+numbins);
		for(int i = 0; i < this.col.getLength(); i++) {
			for(Bin j : BINS) {
				if(j.inBinDouble(this.col.getParticle(i).getDoubleValue())){
					newCol.add(j.toString());
					break;
				}
			}
		}
		newCol.setType('C');
		newCol.setStatistics();
		return newCol;
	}
	
	/**
	 * print reprepresentation of bins used
	 */
	public void printBins() {
		System.out.print("BINS = {");
		for(Bin i : this.BINS) {
			i.printBin();
			System.out.print(", ");
		}
		System.out.println("}");
	}
	public int getNumBins() {
		return this.numbins;
	}
	public String getMethod() {
		return this.method;
	}
}
