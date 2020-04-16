package examples;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Read;
import forensics.T_Test;
import transform.Standardize;


public class TTest_example {

	public static void main(String[] args) {
		DataFrame df = new DataFrame();
		df = DataFrame_Read.loadcsv("testfiles/iris.txt");
		Column col1 = df.getColumn(0);
		Column col2 = df.getColumn(1);
		T_Test tt = T_Test.test(col1,col2);
		System.out.println(tt.method);
		System.out.println(tt.t);
		System.out.println(col1.mean);
		System.out.println(col1.variance);
	}

}
