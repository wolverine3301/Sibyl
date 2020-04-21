package transforming;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import dataframe.Column;
import particles.DoubleParticle;
import particles.Particle;
import regressionFunctions.ConfidenceIntervals;
import regressionFunctions.Regression;
import transform.Standardize;

/**
 * Contains methods for plotting a scatter plot, and regression functions.
 * @author Cade Reynoldson
 * @version 1.0
 */
public class Plot extends JPanel {

    
    private static final long serialVersionUID = -551974310810305557L;

    /** The two columns to plot against eachother. */
	private Column x,y;
	
	/** The plot which contains all the data. */
	private XYPlot masterPlot;
	
	/** The chartpanel which displays the scatter plot. */
	private ChartPanel panel;

	/** Notifies the plot that a change has been made. */
	private PropertyChangeSupport notifyPlot = new PropertyChangeSupport(this);
	
	/** The stack of colors to use for plotting data */
	private Stack<Color> colorsToUse;
	
	/** The regressions to skip when rendering the plot. */
	private HashSet<Regression> regressionsToSkip;
	
	/** The regressions plotted mapped to their color. */
	private HashMap<Regression, XYLineAndShapeRenderer> plottedRegressions;

	/** The intervals to skip when rendering the plot. */
	private HashSet<ConfidenceIntervals> intervalsToSkip;
	
	/** The plotted confidence intervals mapped to their color. */
	private HashMap<ConfidenceIntervals, XYLineAndShapeRenderer> plottedIntervals;
	
	private HashSet<DoublePoint> pointsToSkip;
	
	/** The plotted points on the chart mapped to their color. */
	private HashMap<DoublePoint, XYLineAndShapeRenderer> plottedPoints;
	
	
	
	/** The number of datasets contained in this chart. */
	private int datasetCount;
	
	private double xStart;
	
	private double xEnd;
	
	/**
	 * Creates a plot given two columns.
	 * @param x the x column.
	 * @param y the y column. 
	 */
	public Plot(Column x, Column y) {
		this.x = x;
		this.y = y;
		xStart = -4;
		xEnd = 4;
        this.setLayout(new BorderLayout());
		colorsToUse = createColorStack();
	    masterPlot = createPlot(null, 20);
	    initDataStructures();
	    datasetCount = 1;
        JFreeChart chart = new JFreeChart("ScatterPlot of " + x.getName() + " vs. " + y.getName(),
                JFreeChart.DEFAULT_TITLE_FONT, masterPlot, true);
	    // Create Panel
        ChartPanel view = new ChartPanel(chart);
        this.panel = view;
        this.add(panel, BorderLayout.CENTER);
	}
	
    /**
     * Plots a set of columns with a regression line.
     * @param x the x column.
     * @param y the y column.
     * @param regressionLine the regression line to plot. 
     */
    public Plot(Column x, Column y, HashSet<Regression> regressions, int functionSamples) {
        this.x = x;
        this.y = y;
        this.setLayout(new BorderLayout());
        xStart = -4;
        xEnd = 4;
        colorsToUse = createColorStack();
        masterPlot = createPlot(regressions, functionSamples);
        JFreeChart chart = new JFreeChart("ScatterPlot of " + x.getName() + " vs. " + y.getName(),
                JFreeChart.DEFAULT_TITLE_FONT, masterPlot, true);
        // Create Panel
        this.panel = new ChartPanel(chart);
        this.add(panel);
    }
    
    /**
     * Returns the created plot in the form of a chartpanel.
     * @return the created plot in the form of a chartpanel. 
     */
    public ChartPanel getPlot() {
        return panel;
    }
	
    /**
     * Creates a plot of the given data. 
     * @param regressions to plot upon creation. Can be null if there are no regressions to plot. 
     * @param functionSamples the number of samples to take from each regression.  
     * @return an XYPlot which contains all of the initialized data. 
     */
	private XYPlot createPlot(HashSet<Regression> regressions, int functionSamples) {
	    XYPlot plot = new XYPlot();
	    plot.setDataset(0, getColumnPlot());
	    plot.setRenderer(new XYLineAndShapeRenderer(false, true));
	    NumberAxis xAxis = new NumberAxis(x.getName());
	    NumberAxis yAxis = new NumberAxis(y.getName());
	    plot.setDomainAxis(0, xAxis);
        plot.setRangeAxis(0, yAxis);
	    datasetCount = 1;
	    if (regressions != null) {
	        for (Regression r : regressions) {
	            plot.setDataset(datasetCount, getRegressionPlot(r, functionSamples));
	            plot.setRenderer(datasetCount++, new XYLineAndShapeRenderer(true, false));
	        }
	    }
	    xAxis.setLowerBound(xStart);
	    yAxis.setLowerBound(-3);
	    xAxis.setUpperBound(xEnd);
	    yAxis.setUpperBound(3);
	    return plot;
	}
	
	/**
	 * Creates a plot for a given regression.
	 * @param r the regression.
	 * @param startValue the value to begin plotting at.
	 * @param endValue the value to end plotting at.
	 * @param numSamples the number of samples to take from the plot. Higher sample number, the smoother the line.
	 * @return An XYDataset containing a regression plot.
	 */
	private XYDataset getRegressionPlot(Regression r, int numSamples) {
	    XYSeries rLine = new XYSeries(r.getEquation(), false);
	    double step = (xEnd - xStart) / (numSamples - 1);
	    for (double i = xStart; i < xEnd; i += step) {
            double yVal = r.predictY(Particle.resolveType(i));
            rLine.add(i, yVal);
        }
	    XYSeriesCollection data = new XYSeriesCollection();
	    data.addSeries(rLine);
	    return data;
	}
	
	/**
	 * Plots a regression. 
	 * @param r the regression.
	 * @param numSamples the number of smaples to take from the plot. Higher sample number, the smoother the line. 
	 */
	public void plotRegression(Regression r, int numSamples) {
	    XYLineAndShapeRenderer render;
	    if (regressionsToSkip.contains(r)) {
	        regressionsToSkip.remove(r);
	        render = plottedRegressions.get(r);
	    } else {
	        render = customRenderer(true, false, 1);
	        plottedRegressions.put(r, render);
	    }
        XYSeries rLine = new XYSeries(r.getEquation(), false);
        double step = (xEnd - xStart) / (numSamples - 1);
        for (double i = xStart; i < xEnd; i += step) {
            double yVal = r.predictY(Particle.resolveType(i));
            rLine.add(i, yVal);
        }
        XYSeriesCollection data = new XYSeriesCollection();
        data.addSeries(rLine);
        masterPlot.setDataset(datasetCount, data);
        masterPlot.setRenderer(datasetCount++, render);
        updatePlot();
        System.out.println("PLOTTED REGRESSION");
        notifyPlot.firePropertyChange("PLOT", null, null);
	}
	
	/**
	 * Plots a given point on the chart. 
	 * @param x the x value to plot. 
	 * @param r the regression function to use to calculate y.
	 */
	public void plotPoint(double x, double y) {
	    XYLineAndShapeRenderer render;
	    DoublePoint p = containsSkippedPoint(x, y);
	    if (p == null) {
	        p = new DoublePoint(x, y);
	        render = customRenderer(false, true, 1);
	        plottedPoints.put(p, render);
	    } else {
	        render = plottedPoints.get(p);
	    }
	    masterPlot.setDataset(datasetCount, getPointPlot(x, y));
	    masterPlot.setRenderer(datasetCount++, render);
	    updatePlot();
        notifyPlot.firePropertyChange("PLOT", null, null);
	}
	
	private DoublePoint containsSkippedPoint(double x, double y) {
	    for (DoublePoint p : pointsToSkip) {
	        if (p.equals(x, y))
	            return p;
	    }
	    return null;
	}
	
	/**
	 * Plots the given columns in the axises. 
	 * @return an XYDataset containing the two columns plotted against eachother. 

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
	*/
	/**
	 * plots the normalized values, z-values, of 2 columns
	 * @return an XYDataset containing the normalized two columns plotted against eachother. 
	 */
	private XYDataset getColumnPlot() {
	    XYSeriesCollection xvsy = new XYSeriesCollection();
        XYSeries series = new XYSeries(x.getName() + " vs. " + y.getName());
        Column a = Standardize.standardize_col(x);
        Column b = Standardize.standardize_col(y);
        //Plot the points from the two columns. 
        for(int i = 0; i < a.getLength(); i++) {
            series.add(a.getDoubleValue(i), b.getDoubleValue(i));
        }
        xvsy.addSeries(series);
        return xvsy;
	}
	/**
	 * Cannot remove an already plotted regression. A new plot must be created.
	 * Upon creation, scatterplotview will be notifited to refresh. 
	 * @param r the regression to remove. 
	 */
	public void removeRegression(Regression toRemove, int functionSamples) {
        regressionsToSkip.add(toRemove);
        rebuildAfterRemoval(functionSamples);
        notifyPlot.firePropertyChange("PLOT", null, null);
	}
	
	public void removeInterval(ConfidenceIntervals interval, int functionSamples) {
	    intervalsToSkip.add(interval);
	    rebuildAfterRemoval(functionSamples);
	    notifyPlot.firePropertyChange("PLOT", null, null);
	}
	
	public void removePoint(double x, double y, int functionSamples) {
	    DoublePoint point = null;
	    for (DoublePoint p : plottedPoints.keySet()) {
	        if ((p.getX() == x) && (p.getY() == y)) {
	            point = p;
	            break;
	        }
	    }
	    if (point != null) {
	        pointsToSkip.add(point);
	        rebuildAfterRemoval(functionSamples);
	        notifyPlot.firePropertyChange("PLOT", null, null);
	    }
	}
	
	/**
	 * Rebuilds the plot after the removal of a plotted feature.
	 */
	private void rebuildAfterRemoval(int functionSamples) {
	    masterPlot = new XYPlot();
	    datasetCount = 0;
        masterPlot.setDataset(datasetCount++, getColumnPlot());
        masterPlot.setRenderer(new XYLineAndShapeRenderer(false, true));
        NumberAxis xAxis = new NumberAxis(x.getName());
        NumberAxis yAxis = new NumberAxis(y.getName());
        masterPlot.setDomainAxis(0, xAxis);
        masterPlot.setRangeAxis(0, yAxis);
        xAxis.setLowerBound(xStart);
        yAxis.setLowerBound(y.min - y.std);
        xAxis.setUpperBound(xEnd);
        yAxis.setUpperBound(y.max + y.std);
        for (Regression r : plottedRegressions.keySet()) {
            if (!regressionsToSkip.contains(r)) {
                   masterPlot.setDataset(datasetCount, getRegressionPlot(r, functionSamples));
                   masterPlot.setRenderer(datasetCount++, plottedRegressions.get(r));
            }
        }
        for (ConfidenceIntervals i : plottedIntervals.keySet()) {
            if (!intervalsToSkip.contains(i)) {
                masterPlot.setDataset(datasetCount, getIntervalPlot(i, functionSamples));
                masterPlot.setRenderer(datasetCount++, plottedIntervals.get(i));
            }
        }
        for (DoublePoint p : plottedPoints.keySet()) {
            if (!pointsToSkip.contains(p)) {
                masterPlot.setDataset(datasetCount, getPointPlot(p.getX(), p.getY()));
                masterPlot.setRenderer(datasetCount++, plottedPoints.get(p));
            }
        }
        updatePlot();
	}
	
	
	private XYDataset getPointPlot(double x, double y) {
	       XYSeries point = new XYSeries("(" + x + ", " + y + ")");
	       point.add(x, y);
	       XYSeriesCollection data = new XYSeriesCollection();
	       data.addSeries(point);
	       return data;
	}
	
	private XYDataset getIntervalPlot(ConfidenceIntervals interval, int numSamples) {
        XYSeriesCollection interv = new XYSeriesCollection();
        XYSeries lower = new XYSeries("Lower", false);
        XYSeries upper = new XYSeries("Upper", false);
        double step = (xEnd - xStart) / (numSamples - 1);
        for (double i = xStart; i < xEnd; i += step) {
            DoubleParticle x = new DoubleParticle(i);
            lower.add(i, interval.lower_intervalY(x));
            upper.add(i, interval.upper_intervalY(x));
        }
        interv.addSeries(lower);
        interv.addSeries(upper);
        return interv;
	}
	
	
	public void changePlot(Column x, Column y) {
        this.x = x;
        this.y = y;
        colorsToUse = createColorStack();
        masterPlot = createPlot(null, 20);
        clearDataStructures();
        datasetCount = 1;
        updatePlot();
        notifyPlot.firePropertyChange("PLOT", null, null);
	}
	
	private void initDataStructures() {
        regressionsToSkip = new HashSet<Regression>();
        plottedRegressions = new HashMap<Regression, XYLineAndShapeRenderer>();
        intervalsToSkip = new HashSet<ConfidenceIntervals>();
        plottedIntervals = new HashMap<ConfidenceIntervals, XYLineAndShapeRenderer>();
        pointsToSkip = new HashSet<DoublePoint>();
        plottedPoints = new HashMap<DoublePoint, XYLineAndShapeRenderer>();
	}
	
	private void clearDataStructures() {
	    regressionsToSkip.clear();
        plottedRegressions.clear();
        intervalsToSkip.clear();
        plottedIntervals.clear();
        pointsToSkip.clear();
        plottedPoints.clear();
	}
	
	/**
	 * Adds a confidence interval to the plot. 
	 * @param interval
	 * @param startValue
	 * @param endValue
	 * @param numSamples
	 */
	public void plotConfidenceInterval(ConfidenceIntervals interval, int numSamples) {
	    XYLineAndShapeRenderer render;
	    if (intervalsToSkip.contains(interval)) {
	        render = plottedIntervals.get(interval);
	    } else {
	        render = customRenderer(true, false, 2);
	        plottedIntervals.put(interval, render);
	    }
	    XYDataset interv = getIntervalPlot(interval, numSamples);
        masterPlot.setDataset(datasetCount, interv);
        masterPlot.setRenderer(datasetCount++, render);
        updatePlot();
        notifyPlot.firePropertyChange("PLOT", null, null);
	}
	
	private void updatePlot() {
	    this.remove(panel);
        JFreeChart chart = new JFreeChart("ScatterPlot of " + this.x.getName() + " vs. " + this.y.getName(),
                JFreeChart.DEFAULT_TITLE_FONT, masterPlot, true);
        panel = new ChartPanel(chart);
        this.add(panel, BorderLayout.CENTER);
	}
	
    /**
     * Adds a property change listener to the panel.
     * @param theListener the listener to be added.
     */
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        notifyPlot.addPropertyChangeListener(theListener);
    }
    
    private Stack<Color> createColorStack() {
        Stack<Color> colors = new Stack<Color>();
        colors.add(Color.GREEN);
        colors.add(Color.GRAY);
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.BLACK);
        return colors;
    }
    
    /** 
     * Creates a custom renderer for a given dataset. Allows "custom" colors. 
     * @param renderLines if the renderer is to render lines. 
     * @param renderShape if the renderer is to render shapes.
     * @param numLines the total number of lines (or datasets) this renderer is responsable to render. 
     * @return a custom renderer.
     */
    private XYLineAndShapeRenderer customRenderer(boolean renderLines, boolean renderShape, int numLines) {
        if (colorsToUse.isEmpty()) 
            colorsToUse = createColorStack();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(renderLines, renderShape);
        Color currentColor = colorsToUse.pop();
        for (int i = 0; i < numLines; i++) {
            renderer.setSeriesPaint(i, currentColor);
        }
        return renderer;
    }
    
    private class DoublePoint {
        private double x;
        private double y;
        
        public DoublePoint(double x, double y) {
            this.x = x;
            this.y = y;
        }
        
        public double getX() {
            return x;
        }
        
        public double getY() {
            return y;
        }
        
        public boolean equals(double x, double y) {
            return this.x == x && this.y == y;
        }
    }
}
