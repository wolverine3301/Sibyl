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
		int[] a = {1,2,3,4,45,9};
		int[] b = {3,4,5,6,7,34};
		//Column col1 = df.getColumn(0);
		//Column col2 = df.getColumn(1);
		Column col1 = new Column(a);
		Column col2 = new Column(b);
		T_Test tt = new T_Test(col1,col2);
		System.out.println(tt.get_Tvalue());
		System.out.println(col1.mean);
		System.out.println(col1.variance);
	}

}
