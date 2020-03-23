package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import dataframe.Column;

public class Plot extends JPanel{
	
	private Column x,y;
	public ChartPanel panel;
	public Plot(Column x, Column y) {
		this.x = x;
		this.y = y;
	    XYDataset dataset = createDataset();
	    // Create chart
	    JFreeChart chart = ChartFactory.createScatterPlot(
	        x.getName() + " " + y.getName(), 
	        "X-Axis", "Y-Axis", dataset);
	    //Changes background color
	    XYPlot plot = (XYPlot)chart.getPlot();
	    plot.setBackgroundPaint(new Color(145,130,255));
	    // Create Panel
	    this.panel = new ChartPanel(chart);

	}
	private XYDataset createDataset() {
		   XYSeriesCollection dataset = new XYSeriesCollection();
		   XYSeries series = new XYSeries("r");
		   for(int i = 0; i < x.getLength();i++) {
			   series.add(x.getDoubleValue(i), y.getDoubleValue(i));
		   }
		   dataset.addSeries(series);
		   return dataset;
	}



}
