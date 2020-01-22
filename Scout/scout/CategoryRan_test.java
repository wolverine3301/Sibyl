package scout;

import dataframe.DataFrame;

public class CategoryRan_test {

	public static void main(String[] args) {
		String file = "testfiles/iris.txt";
		DataFrame  df = DataFrame.read_csv(file);
        df.setColumnType("species", 'T');//set target column
        CategoryRanker CR = new CategoryRanker(df, 0);
		//NR = new NumericRanker(df);
		CR.printRankings();

	}

}
