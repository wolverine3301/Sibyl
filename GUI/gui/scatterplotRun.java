package gui;

import javax.swing.JFrame;

import dataframe.DataFrame;
import dataframe.DataFrame_Read;

public class scatterplotRun {

	public static void main(String[] args) {
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame_Read.loadcsv_new(file);
        ScatterPlot plot = new ScatterPlot(df);
        JFrame frame = new JFrame("Test");
        frame.add(plot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
	}

}
