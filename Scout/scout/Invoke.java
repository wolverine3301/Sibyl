package scout;

import java.util.ArrayList;

import dataframe.Column;
import dataframe.DataFrame;

public class Invoke {
	private static CategoryRanker CR; 
	private static NumericRanker NR; 
	private static DataFrame  df;
	public static void main(String[] args) {
		String file = "testfiles/heart_disease.csv";
        df = DataFrame.read_csv(file);
        df.setColumnType("thal", 'T');//set target column
        CR = new CategoryRanker(df, 0);
		NR = new NumericRanker(df);

	}
	private DataFrame[] evocation() {
		ArrayList<DataFrame> recollection = new ArrayList<DataFrame>();
		DataFrame memory;
		for(int cnt = 1; cnt < df.getNumColumns();cnt++) {
			memory = new DataFrame();
			for(int i = 0; i < cnt; i++) {
				
			}
			
		}
		
	}

}
