package gui;

import java.util.ArrayList;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import dataframe.Column;
import particles.Particle;
import regressionFunctions.Regression;

/**
 * Contains methods for plotting a scatter plot, and regression functions.
 * @author Cade Reynoldson
 * @version 1.0
 */
public class Plot extends JPanel{
	
    /** Serial bullshit */
    private static final long serialVersionUID = 1031343868335382809L;

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
	    XYPlot plot = createPlot(null, 20);
        JFreeChart chart = new JFreeChart("ScatterPlot of " + x.getName() + " vs. " + y.getName(),
                JFreeChart.DEFAULT_TITLE_FONT, plot, true);
	    // Create Panel
        ChartPanel view = new ChartPanel(chart);
        this.panel = view;
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
	
    /**
     * Creates a plot of the given data. 
     * @param regressions
     * @param functionSamples
     * @return
     */
	private XYPlot createPlot(ArrayList<Regression> regressions, int functionSamples) {
	    XYPlot plot = new XYPlot();
	    plot.setDataset(0, getColumnPlot());
	    plot.setRenderer(new XYLineAndShapeRenderer(false, true));
	    NumberAxis xAxis = new NumberAxis(x.getName());
	    NumberAxis yAxis = new NumberAxis(y.getName());
	    plot.setDomainAxis(0, xAxis);
        plot.setRangeAxis(0, yAxis);
	    int count = 1;
	    if (regressions != null) {
	        for (Regression r : regressions) {
	            plot.setDataset(count, getRegressionPlot(r, x.min - x.std, x.max + x.std, functionSamples));
	            plot.setRenderer(count++, new XYLineAndShapeRenderer(true, false));
	        }
	    }
	    xAxis.setLowerBound(x.min - x.std);
	    yAxis.setLowerBound(y.min - y.std);
	    xAxis.setUpperBound(x.max + x.std);
	    yAxis.setUpperBound(y.max + y.std);
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
}
