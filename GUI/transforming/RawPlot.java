package transforming;

import java.awt.BorderLayout;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;

import dataframe.Column;

public class RawPlot extends Plot{

	public RawPlot(Column x, Column y) {
		super(x, y);
        setXStart();
        setXEnd();
        setYStart();
        setYEnd();
        this.setLayout(new BorderLayout());
		colorsToUse = createColorStack();
	    masterPlot = createPlot(null, 20);
	    initDataStructures();
	    datasetCount = 1;
        JFreeChart chart = new JFreeChart("Scatter Plot of " + x.getName() + " vs. " + y.getName(),
                JFreeChart.DEFAULT_TITLE_FONT, masterPlot, true);
	    // Create Panel
        ChartPanel view = new ChartPanel(chart);
        this.panel = view;
        this.add(panel, BorderLayout.CENTER);
	}

	@Override
	protected void setXStart() {
		super.xStart = x.min - x.std;
	}

	@Override
	protected void setXEnd() {
		super.xEnd = x.max + x.std;
	}

	@Override
	protected void setYStart() {
		super.yStart = y.min - y.std;
	}

	@Override
	protected void setYEnd() {
		super.yEnd = y.max + y.std;
	}

	@Override
	protected XYDataset getColumnPlot() {
		// TODO Auto-generated method stub
		return null;
	}

}
