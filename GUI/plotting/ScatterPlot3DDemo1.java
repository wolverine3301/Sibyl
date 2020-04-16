package plotting;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.orsoncharts.Chart3DPanel;
import com.orsoncharts.Chart3D;
import com.orsoncharts.Chart3DFactory;
import com.orsoncharts.Colors;
import com.orsoncharts.data.xyz.XYZDataset;
import com.orsoncharts.data.xyz.XYZSeries;
import com.orsoncharts.data.xyz.XYZSeriesCollection;
import com.orsoncharts.graphics3d.Dimension3D;
import com.orsoncharts.graphics3d.ViewPoint3D;
import com.orsoncharts.graphics3d.swing.DisplayPanel3D;
import com.orsoncharts.label.StandardXYZLabelGenerator;
import com.orsoncharts.plot.XYZPlot;
import com.orsoncharts.renderer.xyz.ScatterXYZRenderer;

import dataframe.Column;
import dataframe.DataFrame;

/**
 * A demonstration of a scatter plot in 3D.
 */
@SuppressWarnings("serial")
public class ScatterPlot3DDemo1 extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public ScatterPlot3DDemo1(String title) {
        super(title);
        addWindowListener(null);
        getContentPane().add(createDemoPanel());
    }

    /**
     * Returns a panel containing the content for the demo.  This method is
     * used across all the individual demo applications to allow aggregation 
     * into a single "umbrella" demo (OrsonChartsDemo).
     * 
     * @return A panel containing the content for the demo.
     */
    public static JPanel createDemoPanel() {
        Plot_3D_Panel content = new Plot_3D_Panel(new BorderLayout());
        content.setPreferredSize(new Dimension(760, 480));
        XYZDataset dataset = createDataset();
        Chart3D chart = createChart(dataset);
        Chart3DPanel chartPanel = new Chart3DPanel(chart);
        content.setChartPanel(chartPanel);
        chartPanel.zoomToFit(new Dimension(760, 480));
        content.add(new DisplayPanel3D(chartPanel));
        return content;
    }
    
    /**
     * Creates a scatter chart based on the supplied dataset.
     * 
     * @param dataset  the dataset.
     * 
     * @return A scatter chart. 
     */
    private static Chart3D createChart(XYZDataset dataset) {
        Chart3D chart = Chart3DFactory.createScatterChart("IRIS", 
                "species", dataset, "X", "Y", "Z");
        XYZPlot plot = (XYZPlot) chart.getPlot();
        plot.setDimensions(new Dimension3D(10.0, 4.0, 4.0));
        plot.setLegendLabelGenerator(new StandardXYZLabelGenerator(
                StandardXYZLabelGenerator.COUNT_TEMPLATE));
        ScatterXYZRenderer renderer = (ScatterXYZRenderer) plot.getRenderer();
        renderer.setSize(0.15);
        renderer.setColors(Colors.createIntenseColors());
        chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));
        return chart;
    }
    
    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    private static XYZDataset<String> createDataset() {
		String file = "testfiles/iris.txt";
        DataFrame df = DataFrame.read_csv(file);
		Column col2 = df.getColumn(1);
		Column col3 = df.getColumn(2);
    	XYZSeries<String> s1 = createRandomSeries("S1", 10);
        //XYZSeries<String> s2 = createRandomSeries("S2", 50);
        //XYZSeries<String> s3 = createRandomSeries("S3", 150);	
        ///XYZSeries<String> s1 = createRandomSeries("S1", 10);
        //XYZSeries<String> s2 = createRandomSeries("S2", 50);
        //XYZSeries<String> s3 = createRandomSeries("S3", 150);
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();
        dataset.add(s1);
        //dataset.add(s2);
        //dataset.add(s3);
        return dataset;
    }
    
    private static XYZSeries<String> createRandomSeries(String name, int count) {
        XYZSeries<String> s = new XYZSeries<String>(name);
        for (int i = 0; i < count; i++) {
            s.add(Math.random() * 100, Math.random() / 100, Math.random() * 100);
        }
        return s;
    }


    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        ScatterPlot3DDemo1 app = new ScatterPlot3DDemo1(
                "OrsonCharts : ScatterPlot3DDemo1.java");
        app.pack();
        app.setVisible(true);
    }
}
