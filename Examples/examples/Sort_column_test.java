package examples;

import dataframe.Column;

public class Sort_column_test {

	public static void main(String[] args) {
		double[] arr = {1.1,5.34,2.123,3.4,1.01};
		Column col = new Column(arr);
		col.printCol();
		col.sort_column();
		System.out.println(col.getSortedValues());
	}

}
