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
import particles.Particle;
import regressionFunctions.Regression;

public class Plot extends JPanel{
	
	private Column x,y;
	
	public ChartPanel panel;
	
	/**
	 * Creates a plot given two columns.
	 * @param x the x column.
	 * @param y the y column. 
	 */
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
	
	/**
	 * Plots a set of columns with a regression line.
	 * @param x the x column.
	 * @param y the y column.
	 * @param regressionLine the regression line to plot. 
	 */
	public Plot(Column x, Column y, Regression regression) {
	    this.x = x;
	    this.y = y;
	    XYDataset dataset = regressionLineAndPlot(x.min - x.std, x.max + x.std, regression);
        JFreeChart chart = ChartFactory.createScatterPlot(
                x.getName() + " " + y.getName(), 
                "X-Axis", "Y-Axis", dataset);
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(145,130,255));
        // Create Panel
        this.panel = new ChartPanel(chart);
	}
	
	/**
	 * Creates an XYDataset with a regression line and scatterplot. 
	 * @param startValue the start value of the regression line. 
	 * @param endValue the end value of the regression line.
	 * @param regression the regression object. 
	 * @return an XYDataset with a regression line and scatterplot. 
	 */
	private XYDataset regressionLineAndPlot(double startValue, double endValue, Regression regression) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries(x.getName() + " vs. " + y.getName());
        for(int i = 0; i < x.getLength(); i++) {
            series.add(x.getDoubleValue(i), y.getDoubleValue(i));
        }
        dataset.addSeries(series);
        XYSeries rLine = new XYSeries(regression.getEquation());
        for (double i = startValue; i < endValue; i += 0.1) {
            double yVal = regression.predictY(Particle.resolveType(i));
            rLine.add(i, yVal);
        }
        dataset.addSeries(rLine);
        return dataset;
    }
	
//	private XYDataset regressionLine(double startValue, double endValue, Regression regression) {
//	    XYSeriesCollection dataset = new XYSeriesCollection();
//	    XYSeries series = new XYSeries(regression.getEquation());
//	    double[] formula = regression.function;
//	    for (double i = startValue; i < endValue; i += 0.2) {
//	        
//	    }
//	}
	
	
	/**
	 * Creates an XYDataset containing the values from a data frame.
	 * @return an XYDataset containing the values form a data frame. 
	 */
	private XYDataset createDataset() {
		   XYSeriesCollection dataset = new XYSeriesCollection();
		   XYSeries series = new XYSeries(x.getName() + " vs. " + y.getName());
		   for(int i = 0; i < x.getLength(); i++) {
			   series.add(x.getDoubleValue(i), y.getDoubleValue(i));
		   }
		   dataset.addSeries(series);
		   return dataset;
	}



}
