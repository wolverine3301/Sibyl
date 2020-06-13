package sagaExamples;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Read;

public class NAN_handlingExample {

	public static void main(String[] args) {
        DataFrame df = DataFrame_Read.loadcsv("testfiles/nanhandlingtest.csv");
        //df.printAllData(true);
        Column a = df.getColumn(1);
        System.out.println(a.missingIndexes);
	}

}
