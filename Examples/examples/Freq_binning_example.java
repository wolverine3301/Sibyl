package examples;

import dataframe.Column;
import dataframe.DataFrame;
import discreteize.EqualFrequencyBinning;
import discreteize.EqualWidthBinning;

public class Freq_binning_example {
	public static void main(String[] args) {
		double[] arr = {1.1,1.2,1.3,2.1,2.1,2.3,2.4,3.2,3.3,3.6,4.3,4.4,5.5,8.7,4.32,6.6,3.55,11.2,12,11.33,10.4,4.56,7.8,8.99,9.02,5.55,6.7,12.33,11.67};
		System.out.println(arr.length);
		Column col = new Column(arr);
		col.sort_column();
		EqualFrequencyBinning e = new EqualFrequencyBinning(3,col);
		e.printBins();
		Column col2 = e.binColumn();
		System.out.println(col2.getUniqueValueCounts());
		col2.printCol();
	}
}
