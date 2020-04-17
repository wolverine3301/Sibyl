package examples;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Read;
import transform.Standardize;

public class Standardize_example {

	public static void main(String[] args) {
		
		DataFrame iris = new DataFrame();
		iris = DataFrame_Read.loadcsv("testfiles/iris.txt");
		for(int i = 0; i < iris.getNumColumns();i++) {
			System.out.println(i+"  "+iris.getColumn(i).getType());
		}
		DataFrame df = Standardize.standardize_df(iris);

		for(Column i : df.columns) {
			System.out.println(i.getName() + " Mean: "+ i.mean);
			System.out.println(i.getName() + " STD: "+ i.std);
		}

	}

}
