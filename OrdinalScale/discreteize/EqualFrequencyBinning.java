package discreteize;

import java.util.ArrayList;

import dataframe.Column;
import particles.Particle;
/**
 * discreteize a numeric column into equal frequency bins
 * @author logan.collier
 *
 */
public class EqualFrequencyBinning extends Binning{
	
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
		super(bins,col,"Equal Frequency");
		makeBins();
	}
	@Override
	protected void makeBins() {
		this.BINS = new ArrayList<Bin>(numbins);
		col.sort_column();
		ArrayList<Particle> s = col.getSortedValues();
		int n = (int)(col.getLength()/numbins);
		int w = 0;
		//BINS.add(new Bin(col.min,s.get(w).getDoubleValue()-0.0001));
		for(int j = 0; j < numbins-1;j++) {
			if(w+n>=col.getLength()) break;
			BINS.add(new Bin(s.get(w).getDoubleValue()-0.00001,s.get(w+n).getDoubleValue()));
			w = w + n;
		}
		BINS.add(new Bin(s.get(w).getDoubleValue()-0.00001,col.max));
		
	}
}
