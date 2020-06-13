package logan.sybilGUI;

import java.awt.Color;

public class dummy_thirdPanel extends Tertiary_View{

	public dummy_thirdPanel(int width, int height, Color main_bg_color, Color main_side_color, int side_panel_W) {
		super(width, height, main_bg_color, main_side_color, side_panel_W);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initComponents() {
	    add(side_panel, java.awt.BorderLayout.WEST);
	    add(center_panel, java.awt.BorderLayout.CENTER);
		
	}

}
