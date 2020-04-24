package plotting;

import javax.swing.JFrame;

import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Read;
import regressionFunctions.PolyRegression;

public class scatterplotRun {

	public static void main(String[] args) {
		String file = "testfiles/toy-test.txt";
//        double[] a1 = {-1,0,1,2,3,5,7,9};
//        double[] a2 = {-1,3,2.5,5,4,2,5,4};
//        Column x = new Column(a1);
//        Column y = new Column(a2);
//        PolyRegression p = new PolyRegression(x,y,4);
//        ScatterPlotView plot = new ScatterPlotView(x, y, p);
		DataFrame df = DataFrame_Read.loadtsv(file);
		System.out.println(df.getNumColumns());
		ScatterPlotView plot = new ScatterPlotView(df);
        JFrame frame = new JFrame("Test");
        frame.add(plot);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
	}

}
