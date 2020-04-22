package transforming;

import java.awt.BorderLayout;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

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
import regressionFunctions.ConfidenceIntervals;
import regressionFunctions.Regression;
import transform.Standardize;


public class StandardPlot extends Plot{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Plots a set of columns with a regression line.
     * @param x the x column.
     * @param y the y column.
     * @param regressionLine the regression line to plot. 
     */
    public StandardPlot(Column x, Column y) {
		super(x,y);
        setXStart();
        setXEnd();
        setYStart();
        setYEnd();
        this.setLayout(new BorderLayout());
		colorsToUse = createColorStack();
	    masterPlot = createPlot(null, 20);
	    initDataStructures();
	    datasetCount = 1;
        JFreeChart chart = new JFreeChart("Standardized Plot" + x.getName() + " vs. " + y.getName(),
                JFreeChart.DEFAULT_TITLE_FONT, masterPlot, true);
	    // Create Panel
        ChartPanel view = new ChartPanel(chart);
        this.panel = view;
        this.add(panel, BorderLayout.CENTER);
    }
	/**
	 * do a log transform on columns
	 * @return
	 */
    @Override
	protected XYDataset getColumnPlot() {
	    XYSeriesCollection xvsy = new XYSeriesCollection();
        XYSeries series = new XYSeries(super.x.getName() + " vs. " + super.y.getName());
        Column a = Standardize.standardize_col(super.x);
        Column b = Standardize.standardize_col(super.y);
        //Plot the points from the two columns. 
        for(int i = 0; i < a.getLength(); i++) {
            series.add(a.getDoubleValue(i), b.getDoubleValue(i));
        }
        xvsy.addSeries(series);
        return xvsy;
	}

	@Override
	protected void setXStart() {
		super.xStart = -3.5;
	}
	@Override
	protected void setXEnd() {
		super.xEnd = 3.5;
	}
	@Override
	protected void setYStart() {
		super.yStart = -3.5;
	}
	@Override
	protected void setYEnd() {
		super.yEnd = 3.5;	
	}
}
