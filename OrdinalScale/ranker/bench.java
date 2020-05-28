package ranker;

import dataframe.DataFrame;
import dataframe.DataFrame_Read;

public class bench {

	public static void main(String[] args) {
		DataFrame df = new DataFrame();
		df = DataFrame_Read.loadcsv("testfiles/catTest.txt");
		df.setColumnType(0, 'T');
		Categorical_Ranker_level1 cr = new Categorical_Ranker_level1(df,0);
		cr.printRankings_detailed();

	}

}
