package discreteize;

import java.util.ArrayList;

import dataframe.Column;

public class EqualWidthBinning {
	
	private Column col;
	private int numbins;
	private ArrayList<Bin> BINS;
	
	public EqualWidthBinning(int bins,Column col) {
		this.numbins = bins;
		this.col = col;
		makeBins();
	}
	private void makeBins() {
		double width = col.range / numbins;
		ArrayList<Bin> Bins = new ArrayList<Bin>(numbins);
		Bins.add(new Bin(col.min-0.001,col.min + width));
		double iter = col.min + width;
		for(int i = 0; i < this.numbins-2; i++) {
			Bins.add(new Bin(iter,iter+width));
			iter = iter + width;
		}
		Bins.add(new Bin(iter,col.max + width));
		this.BINS = Bins;
	}
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
