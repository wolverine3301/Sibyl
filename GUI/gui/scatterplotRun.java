package gui;

import dataframe.DataFrame;

public class scatterplotRun {

	public static void main(String[] args) {
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
        ScatterPlot plot = new ScatterPlot(df);
        plot.start();

	}

}
