package discreteize;

import java.util.ArrayList;

import dataframe.Column;
/**
 * 
 * @author logan.collier
 *
 */
public class EqualWidthBinning extends Binning{
	
	
	/**
	 * make numerical data into categorical
	 * make bins that are an even distance of units apart and place numerical data into them
	 * @param bins - number of bins desired
	 * @param col - column to bin (must be numeric)
	 */
	public EqualWidthBinning(int bins,Column col) {
		super(bins,col,"Equal Width");
		makeBins();
	}
	
	/**
	 * make bin objects
	 */
	@Override
	protected void makeBins() {
		double width = col.range / numbins;
		ArrayList<Bin> Bins = new ArrayList<Bin>(numbins);
		Bins.add(new Bin(col.min-0.001,col.min + width));
		double iter = col.min + width;
		for(int i = 0; i < this.numbins-2; i++) {
			Bins.add(new Bin(iter,iter+width));
			iter = iter + width;
		}
		Bins.add(new Bin(iter,col.max + width));
		super.BINS = Bins;
	}

}
