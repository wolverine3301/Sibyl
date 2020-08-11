package stats_and_graph_components;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import Controllers.Data_Controller;
import dataframe.Column;
import dataframe.DataFrame;
import dataframe.DataFrame_Read;
import dataframe.Util;
import logan.sybilGUI.Tertiary_View;
import plotting.Plot_3D_Panel;
import regressionFunctions.Multi_PolynomialRegression;

/**
 * @author logan collier
* A demonstration of a scatter plot in 3D.
*/
@SuppressWarnings("serial")
public class ThreeD_Scatter_Panel extends Tertiary_View {
		private DataFrame df;
		private DataFrame[] classes;
		
		public ThreeD_Scatter_Panel(int width, int height, Color main_bg_color, Color main_side_color,int side_panel_W,Data_Controller controller) {
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
	        return dataset;
	    }
	    /**
	     * Creates a sample dataset (hard-coded for the purpose of keeping the
	     * demo self-contained - in practice you would normally read your data
	     * from a file, database or other source).
	     * 
	     * @return A sample dataset.
	     */
	    private XYZDataset<String> createDataset_regression() {
			
	        
	        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();
			for(DataFrame i : classs) {
				Column[] cols = {i.getColumn(0),i.getColumn(1),i.getColumn(2)};
				XYZSeries<String> s = createSeries(i.getName(), i.getNumRows(),cols);
				dataset.add(s);
			}
			dataset.add(addRegression());
	        return dataset;
	    }
	    private XYZDataset<String> createDataset_indReg() {

	        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<String>();
	        String[] ar = {"species","==","setosa","species","==","versicolor"};
	        DataFrame ddf = df.acquire(ar);
	        ddf.setName("SVM1");
	        //Coloumn[]
	        dataset.add(add_independentRegression(ddf));
	        
	        String[] ar2 = {"species","==","virginica","species","==","versicolor"};
	        DataFrame ddf1 = df.acquire(ar2);
	        ddf1.setName("SVM2");
	        dataset.add(add_independentRegression(ddf1));
	        
			for(DataFrame i : classs) {
				Column[] cols = {i.getColumn(0),i.getColumn(1),i.getColumn(2)};
				XYZSeries<String> s = createSeries(i.getName(), i.getNumRows(),cols);
				dataset.add(s);
				//dataset.add(add_independentRegression(i));
			}
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
			polybtn = new javax.swing.JButton();
			
			df = DataFrame_Read.loadcsv("testfiles/iris.txt");
			df.printDataFrame();
	        content = new Plot_3D_Panel(new BorderLayout());
	        
	        content.setPreferredSize(new Dimension(center_panel.getPreferredSize().width, center_panel.getPreferredSize().height));
	        df.setColumnType(4, 'T');
	        classs = Util.splitOnTarget(df, df.getColumn(4));
	        dataset = createDataset(classs);
	        chart = createChart(dataset);
	        
	        chartPanel = new Chart3DPanel(chart);
	        
	        content.setChartPanel(chartPanel);
	        chartPanel.zoomToFit(new Dimension(center_panel.getPreferredSize().width, center_panel.getPreferredSize().height));
	        content.add(new DisplayPanel3D(chartPanel));
	        center_panel = content;
	        
	        content.repaint();
	        polybtn.setText("POLY");
	        polybtn.addActionListener(new ActionListener() {
	         	public void actionPerformed(ActionEvent e) {
	         		polyAction(e);
	         	}
	      });
	        side_panel.add(polybtn);
	        add(center_panel, java.awt.BorderLayout.CENTER);
			add(side_panel,java.awt.BorderLayout.WEST);
		}
		private void polyAction(java.awt.event.ActionEvent evt) {
			content.removeAll();
			dataset = createDataset_indReg();
			chart = createChart(dataset);
			chartPanel = new Chart3DPanel(chart);
			content.setChartPanel(chartPanel);
	        chartPanel.zoomToFit(new Dimension(center_panel.getPreferredSize().width, center_panel.getPreferredSize().height));
	        content.add(new DisplayPanel3D(chartPanel));
	        content.repaint();
	    }
		
	    private XYZSeries<String> addRegression() {
			Column[] x_cols = {df.getColumn(0),df.getColumn(1)};
			Column col3 = df.getColumn(2);
			//Multi_LinearRegression test3 = new Multi_LinearRegression(x_cols,col3);
			
			Multi_PolynomialRegression test3 = new Multi_PolynomialRegression(x_cols,col3,3);
			
			double c = x_cols[0].min;
			double interval_x =  x_cols[0].range/col3.getLength();
			double d = x_cols[1].min;
			double interval_y =  x_cols[1].range/col3.getLength();
	    	XYZSeries<String> s = new XYZSeries<String>("Polynomial regression");
	        for (int i = 0; i < col3.getLength(); i++) {
	        	//double[] x = {x_cols[0].getDoubleValue(i) , x_cols[1].getDoubleValue(i)};
	        	double[] x = {c , d};
	            s.add(c, d, test3.predict(x));
	            System.out.println(test3.predict(x));
	            c = c +interval_x;
	            d = d +interval_y;
	        }
	        return s;
	    }
	    private XYZSeries<String> add_independentRegression(DataFrame dd) {
	    	XYZSeries<String> s = new XYZSeries<String>("Poly-"+dd.getName());
			Column[] x_cols = {dd.getColumn(0),dd.getColumn(1)};
			Column col3 = dd.getColumn(2);
			//Multi_LinearRegression test3 = new Multi_LinearRegression(x_cols,col3);
			
			Multi_PolynomialRegression test3 = new Multi_PolynomialRegression(x_cols,col3,3);
			
			double c = x_cols[0].min;
			double interval_x =  x_cols[0].range/col3.getLength();
			double d = x_cols[1].min;
			double interval_y =  x_cols[1].range/col3.getLength();
			
	        for (int j = 0; j < col3.getLength(); j++) {
	        	//double[] x = {x_cols[0].getDoubleValue(i) , x_cols[1].getDoubleValue(i)};
	        	double[] x = {c , d};
	            s.add(c, d, test3.predict(x));
	            System.out.println(test3.predict(x));
	            c = c +interval_x;
	            d = d +interval_y;
		        
	    	}
	        return s;
	    }
	    private DataFrame[] classs;
	    private Plot_3D_Panel content;
	    private Chart3DPanel chartPanel;
	    private Chart3D chart;
	    private XYZDataset dataset;
	    private XYZSeriesCollection<String> dataset_series;
	    private javax.swing.JButton polybtn;
}
