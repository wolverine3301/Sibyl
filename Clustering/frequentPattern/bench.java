package frequentPattern;

import dataframe.DataFrame;

public class bench {

	public static void main(String[] args) {
		String file = "testfiles/heart_disease.csv";
        DataFrame df = DataFrame.read_csv(file);
        FrequentPatternTree fp = new FrequentPatternTree(df);
	}

}
