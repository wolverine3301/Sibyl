package logan.sybilGUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

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

import Controllers.Data_Label_Controller;
import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Read;
import dataframe.Util;
import plotting.Plot_3D_Panel;
import plotting.ScatterPlot3DDemo1;

	/**
	 * A demonstration of a scatter plot in 3D.
	 */
	@SuppressWarnings("serial")
public class ThreeD_Scatter_Panel extends Tertiary_View {
		private DataFrame df;
		private DataFrame[] classes;
		
		public ThreeD_Scatter_Panel(int width, int height, Color main_bg_color, Color main_side_color,int side_panel_W,Data_Label_Controller controller) {
			super(width, height, main_bg_color, main_side_color, side_panel_W);
			//df = controller.getDF();

			initComponents();
		}
	    /**
	     * Creates a scatter chart based on the supplied dataset.
	     * 
	     * @param dataset  the dataset.
	     * 
	     * @return A scatter chart. 
	     */
	    private Chart3D createChart(XYZDataset dataset) {

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
	    private static XYZDataset<String> createDataset(DataFrame[] c) {
			
	        
	        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();
			for(DataFrame i : c) {
				Column[] cols = {i.getColumn(0),i.getColumn(1),i.getColumn(2)};
				XYZSeries<String> s = createSeries(i.getName(), i.getNumRows(),cols);
				dataset.add(s);
			}
	        //XYZSeries<String> s2 = createRandomSeries("S2", 50);
	        //XYZSeries<String> s3 = createRandomSeries("S3", 150);	
	        ///XYZSeries<String> s1 = createRandomSeries("S1", 10);
	        //XYZSeries<String> s2 = createRandomSeries("S2", 50);
	        //XYZSeries<String> s3 = createRandomSeries("S3", 150);
	        
	        
	        //dataset.add(s2);
	        //dataset.add(s3);
	        return dataset;
	    }
	    private static XYZSeries<String> createSeries(String name, int count,Column[] cols) {
	    	XYZSeries<String> s = new XYZSeries<String>(name);
	        for (int i = 0; i < count; i++) {
	            s.add(cols[0].getDoubleValue(i), cols[1].getDoubleValue(i), cols[2].getDoubleValue(i));
	        }
	        return s;
	    }
	    private static XYZSeries<String> createRandomSeries(String name, int count) {
	        XYZSeries<String> s = new XYZSeries<String>(name);
	        for (int i = 0; i < count; i++) {
	            s.add(Math.random() * 100, Math.random() / 100, Math.random() * 100);
	        }
	        return s;
	    }

		@Override
		protected void initComponents() {
			df = DataFrame_Read.loadcsv("testfiles/iris.txt");
			df.printDataFrame();
	        Plot_3D_Panel content = new Plot_3D_Panel(new BorderLayout());
	        content.setPreferredSize(new Dimension(760, 480));
	        System.out.println(df.columnNamesToString());
	        df.setColumnType(4, 'T');
	        DataFrame[] classs = Util.splitOnTarget(df, df.getColumn(4));
	        XYZDataset dataset = createDataset(classs);
	        Chart3D chart = createChart(dataset);
	        Chart3DPanel chartPanel = new Chart3DPanel(chart);
	        content.setChartPanel(chartPanel);
	        chartPanel.zoomToFit(new Dimension(760, 480));
	        content.add(new DisplayPanel3D(chartPanel));
	        center_panel = content;
	        add(center_panel, java.awt.BorderLayout.CENTER);
			
		}
}
