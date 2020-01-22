package examples;

import java.util.HashMap;

import chi2.Chi2Independents;
import dataframe.DataFrame;

public class Chi2_example {

	public static void main(String[] args) {
		String file = "testfiles/heart_disease.csv";
		DataFrame  df = DataFrame.read_csv(file);
        df.setColumnType("gender", 'T');//set target column
		Chi2Independents chi2 = new Chi2Independents(df);
		
		
		HashMap<String, HashMap<String, Double>> map = chi2.chi2IndependentsAll();
		for(String i : map.keySet()) {
			System.out.println(i);
			for(String j : map.get(i).keySet()) {
				System.out.println(j + " "+ map.get(i).get(j));
			}
		}

	}

}
