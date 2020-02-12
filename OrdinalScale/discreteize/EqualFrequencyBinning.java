package discreteize;

import java.util.ArrayList;

import dataframe.Column;
import particles.Particle;

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
	public EqualFrequencyBinning(int bins,Column col) {
		this.numbins = bins;
		this.col = col;
		makeBins();
	}
	private void makeBins() {
		this.BINS = new ArrayList<Bin>(numbins);
		ArrayList<Particle> s = col.getSortedValues();
		int n = (int)(col.getLength()/numbins);
		int w = 0;
		double iter = col.min-0.0001;
		//BINS.add(new Bin(col.min,s.get(w).getDoubleValue()-0.0001));
		for(int j = 0; j < numbins-1;j++) {
			if(w+n>=col.getLength()) break;
			BINS.add(new Bin(s.get(w).getDoubleValue()-0.00001,s.get(w+n).getDoubleValue()));
			w = w + n;
		}
		BINS.add(new Bin(s.get(w).getDoubleValue()-0.00001,col.max));
		
	}
	/**
	 * place column into bins and return new column
	 * @return
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
		newCol.setStatistics();
		return newCol;
	}
	public void printBins() {
		System.out.print("BINS = {");
		for(Bin i : this.BINS) {
			i.printBin();
			System.out.print(", ");
		}
		System.out.println("}");
	}
}
