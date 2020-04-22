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
import transform.LogTransform;


public class LogPlot extends Plot{

    /**
     * Plots a set of columns with a regression line.
     * @param x the x column.
     * @param y the y column.
     * @param regressionLine the regression line to plot. 
     */
    public LogPlot(Column x, Column y) {
        super(x,y);
        setXStart();
        setXEnd();
        setYStart();
        setYEnd();
        this.setLayout(new BorderLayout());
        colorsToUse = createColorStack();
        masterPlot = createPlot(null, 20);
        JFreeChart chart = new JFreeChart("Logrithmic transform of " + x.getName() + " vs. " + y.getName(),
                JFreeChart.DEFAULT_TITLE_FONT, masterPlot, true);
        // Create Panel
        this.panel = new ChartPanel(chart);
        this.add(panel);
    }
	/**
	 * do a log transform on columns
	 * @return
	 */
    @Override
	protected XYDataset getColumnPlot() {
	    XYSeriesCollection xvsy = new XYSeriesCollection();
        XYSeries series = new XYSeries(x.getName() + " vs. " + y.getName());
        Column a = LogTransform.transformNaturalLog(x);
        Column b = LogTransform.transformNaturalLog(y);
        //Plot the points from the two columns. 
        for(int i = 0; i < a.getLength(); i++) {
            series.add(a.getDoubleValue(i), b.getDoubleValue(i));
        }
        xvsy.addSeries(series);
        return xvsy;
	}

	@Override
	protected void setXStart() {
		super.xStart = Math.log(x.min)-Math.log(x.min)/20;;
	}
	@Override
	protected void setXEnd() {
		super.xEnd = Math.log(x.max)+ Math.log(x.max)/20;
	}
	@Override
	protected void setYStart() {
		super.yStart = Math.log(y.min) - Math.log(y.min)/20;
	}
	@Override
	protected void setYEnd() {
		super.yEnd = Math.log(y.max)+Math.log(y.max)/20;
		
	}
}
