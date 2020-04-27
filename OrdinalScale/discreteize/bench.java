package discreteize;

import dataframe.Column;
import dataframe.DataFrame;

public class bench {

	public static void main(String[] args) {
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
        EqualWidthBinning b = new EqualWidthBinning(10,df.numeric_columns.get(0));
        b.printBins();
        Column c = b.binColumn();
        System.out.println(c.getUniqueValueCounts());
	}

}
