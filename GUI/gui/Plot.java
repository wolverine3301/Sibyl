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
import org.jfree.data.function.Function2D;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import dataframe.Column;
import particles.DoubleParticle;
import particles.Particle;
import regressionFunctions.Regression;

public class Plot extends JPanel{
	
    /** The two columns to plot against eachother. */
	private Column x,y;
	
	/** The chartpanel which displays the scatter plot. */
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
    public Plot(Column x, Column y, ArrayList<Regression> regression, double regPlotDistance) {
        this.x = x;
        this.y = y;
        XYDataset dataset = regressionLinesAndPlot(x.min - x.std, x.max + x.std, regression, regPlotDistance);
        JFreeChart chart = ChartFactory.createScatterPlot(
                x.getName() + " vs. " + y.getName(), 
                "X-Axis", "Y-Axis", dataset);
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
	public Plot(Column x, Column y, ArrayList<Regression> regression) {
	    this.x = x;
	    this.y = y;
	    XYDataset dataset = regressionLinesAndPlot(x.min - x.std, x.max + x.std, regression, 0.1);
        JFreeChart chart = ChartFactory.createScatterPlot(
                x.getName() + " vs. " + y.getName(), 
                "X-Axis", "Y-Axis", dataset);
        XYPlot plot = (XYPlot)chart.getPlot();
        plot.setBackgroundPaint(new Color(145,130,255));
        // Create Panel
        this.panel = new ChartPanel(chart);
	}
	
	private XYPlot createPlot(ArrayList<Regression> regressions) {
	    XYPlot plot = new XYPlot();
	    XYDataset scatter = getColumnPlot();
	    plot.setDataset(0, );
	}
	
	
	private XYDataset getColumnPlot() {
	    XYSeriesCollection xvsy = new XYSeriesCollection();
	    for (int i = 0; i < x.getLength(); i++) {
	        
	    }
	}
	
	
	/**
	 * Creates an XYDataset with a regression line and scatterplot. 
	 * @param startValue the start value of the regression line. 
	 * @param endValue the end value of the regression line.
	 * @param regression the regression object. 
	 * @return an XYDataset with a regression line and scatterplot. 
	 */
	private XYDataset regressionLinesAndPlot(double startValue, double endValue, ArrayList<Regression> regressions, double regDistance) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries(x.getName() + " vs. " + y.getName());
        //Plot the points from the two columns. 
        for(int i = 0; i < x.getLength(); i++) {
            series.add(x.getDoubleValue(i), y.getDoubleValue(i));
        }
        dataset.addSeries(series);
        //Plot each individual regression line. 
        for (Regression r : regressions) {
            XYDataset rLine = new XYSeries(r.getEquation());
            for (double i = startValue; i < endValue; i += regDistance) {
                double yVal = r.predictY(Particle.resolveType(i));
                try {
                    rLine.add(i, yVal);
                } catch (Exception e) {
                    
                }
            }
            
        }
        return dataset;
    }
	
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

	class RegressionHelper implements Function2D {
	    
	    Regression regression;
	    
	    public RegressionHelper(Regression r) {
	        regression = r;
	    }
	    
        @Override
        public double getValue(double x) {
            return regression.predictY(new DoubleParticle(x));
        }
	    
	}

}
