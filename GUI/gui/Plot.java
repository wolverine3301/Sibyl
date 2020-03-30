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
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
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
	    plot.setBackgroundPaint(new Color(255, 255, 255));
	    // Create Panel
	    this.panel = new ChartPanel(chart);

	}
	
    /**
     * Plots a set of columns with a regression line.
     * @param x the x column.
     * @param y the y column.
     * @param regressionLine the regression line to plot. 
     */
    public Plot(Column x, Column y, ArrayList<Regression> regressions, int functionSamples) {
        this.x = x;
        this.y = y;
        XYPlot plot = createPlot(regressions, functionSamples);
        JFreeChart chart = new JFreeChart("ScatterPlot of " + x.getName() + " vs. " + y.getName(),
                JFreeChart.DEFAULT_TITLE_FONT, plot, true);
        // Create Panel
        ChartPanel view = new ChartPanel(chart);
        this.panel = view;
    }
	
	private XYPlot createPlot(ArrayList<Regression> regressions, int functionSamples) {
	    XYPlot plot = new XYPlot();
	    plot.setDataset(0, getColumnPlot());
	    plot.setRenderer(new XYLineAndShapeRenderer(false, true));
	    plot.setDomainAxis(0, new NumberAxis("Scatterplot domain"));
        plot.setRangeAxis(0, new NumberAxis("Scatterplot range"));
	    int count = 1;
	    for (Regression r : regressions) {
	        plot.setDataset(count, getRegressionPlot(r, x.min - x.std, x.max + x.std, functionSamples));
	        plot.setRenderer(count++, new XYLineAndShapeRenderer(true, false));
	    }
	    return plot;
	}
	
	private XYDataset getRegressionPlot(Regression r, double startValue, double endValue, int numSamples) {
	    XYSeries rLine = new XYSeries(r.getEquation(), false);
	    double step = (endValue - startValue) / (numSamples - 1);
	    for (double i = startValue; i < endValue; i += step) {
            double yVal = r.predictY(Particle.resolveType(i));
            rLine.add(i, yVal);
        }
	    XYSeriesCollection data = new XYSeriesCollection();
	    data.addSeries(rLine);
	    return data;
	}
	
	private XYDataset getColumnPlot() {
	    XYSeriesCollection xvsy = new XYSeriesCollection();
        XYSeries series = new XYSeries(x.getName() + " vs. " + y.getName());
        //Plot the points from the two columns. 
        for(int i = 0; i < x.getLength(); i++) {
            series.add(x.getDoubleValue(i), y.getDoubleValue(i));
        }
        xvsy.addSeries(series);
        return xvsy;
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
