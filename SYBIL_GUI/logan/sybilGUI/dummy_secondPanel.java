package logan.sybilGUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class dummy_secondPanel extends Secondary_View{
	
	/**
	 * Contructor
	 * @param width
	 * @param height
	 * @param main_bg_color
	 * @param main_side_color
	 */
	public dummy_secondPanel(int width, int height, Color main_bg_color, Color main_side_color) {
		super(width, height, main_bg_color, main_side_color);
	}

	@Override
	protected void initComponents() {
		third_view = new dummy_thirdPanel(W, H, main_bg_color, main_bg_color, 200);
		btn = new javax.swing.JButton("BUTTON");
		btn.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
      			center_panel.removeAll();
      			center_panel.add(third_view);
      			center_panel.repaint();
      			center_panel.revalidate();
         	}
      });
		side_panel.add(btn);
	    add(side_panel, java.awt.BorderLayout.WEST);
	    add(center_panel, java.awt.BorderLayout.CENTER);
		
	}
	private javax.swing.JButton btn;
	private javax.swing.JPanel third_view;
}
