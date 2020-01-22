package examples;

import dataframe.Column;
import dataframe.DataFrame;
import transform.Standardize;

public class Standardize_example {

	public static void main(String[] args) {
		
		DataFrame iris = new DataFrame();
		iris = DataFrame.read_csv("testfiles/iris.txt");
		DataFrame df = Standardize.standardize_df(iris);
		for(Column i : df.columns) {
			System.out.println(i.getName() + " Mean: "+ i.mean);
			System.out.println(i.getName() + " STD: "+ i.std);
		}

	}

}
