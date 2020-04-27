package hypothesis;

import dataframe.DataFrame;
import dataframe.DataFrame_Read;

public class bench {

	public static void main(String[] args) {
		DataFrame df = new DataFrame();
		df = DataFrame_Read.loadcsv("testfiles/iris.txt");
		df.setColumnType(4, 'T');
		Hypothesis h = new Hypothesis(df);

	}

}
