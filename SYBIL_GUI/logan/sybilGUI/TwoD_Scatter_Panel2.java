package logan.sybilGUI;

import java.awt.BorderLayout;
import java.awt.Color;

import Controllers.Data_Label_Controller;
import dataframe.DataFrame;
import dataframe.DataFrame_Read;
import plotting.ScatterPlotView;

public class TwoD_Scatter_Panel2 extends Tertiary_View{

	private Data_Label_Controller controller;
	private DataFrame df;
	public TwoD_Scatter_Panel2(int width, int height, Color main_bg_color, Color main_side_color, int side_panel_W,Data_Label_Controller controller) {
		super(width, height, main_bg_color, main_side_color, side_panel_W);
		this.controller = controller;
		String file = "testfiles/iris.txt";
		df = DataFrame_Read.loadcsv(file);
	}

	@Override
	protected void initComponents() {
		ScatterPlotView plot = new ScatterPlotView(df);
		center_panel.add(plot);
		add(center_panel, BorderLayout.CENTER);
	}

}
