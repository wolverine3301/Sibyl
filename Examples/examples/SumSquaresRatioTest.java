package examples;

import dataframe.DataFrame;
import dataframe.DataFrame_Read;
import ranker.SumSquaresRatio;

public class SumSquaresRatioTest {

	public static void main(String[] args) {
		DataFrame df = new DataFrame();
		df = DataFrame_Read.loadcsv("testfiles/iris.txt");
		df.setColumnType(4, 'T');
		double[] h = SumSquaresRatio.of(df);
		for(int i =0; i < h.length;i++) {
			System.out.println("h: "+h[i]);
		}

	}

}
