package logan.sybilGUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 
 * @author logan collier
 *
 */
public class Evaluate_Panel extends Secondary_View{

	
	
    public Evaluate_Panel(int width, int height, Color main_bg_color, Color main_side_color) {
		super(width, height, main_bg_color, main_side_color);
		// TODO Auto-generated constructor stub
	}
    
	@Override
	protected void initComponents() {
        testScore_btn = new javax.swing.JButton();
        confuse_matrix_btn = new javax.swing.JButton();
        predict_btn = new javax.swing.JButton();
        roc_btn = new javax.swing.JButton();
        lift_curve_btn = new javax.swing.JButton();
        calibration_plt_btn = new javax.swing.JButton();
        
        SCORE_VIEW = new ConfusionMatrix_Panel(W-side_panel.getPreferredSize().width,H,main_bg_color,main_side_color,100);
        
        int btn_size = 50;
        testScore_btn.setBackground(main_side_color);
        testScore_btn.setIcon(GUI_Util.getIcon("SYBIL_GUI/evaluate_icons/icons8_test_tube_50px.png",btn_size,btn_size)); // NOI18N
        testScore_btn.setToolTipText("all seeing eye");
        
        testScore_btn.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
      			testAction(e);
         	}
        });

        side_panel.add(testScore_btn);

        confuse_matrix_btn.setBackground(main_side_color);
        confuse_matrix_btn.setIcon(GUI_Util.getIcon("SYBIL_GUI/evaluate_icons/icons8_gantt_chart_50px_1.png",btn_size,btn_size)); // NOI18N // NOI18N 
        confuse_matrix_btn.setToolTipText("scatter");
        confuse_matrix_btn.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		confuse_Action(e);
         	}
      });
        side_panel.add(confuse_matrix_btn);

        predict_btn.setBackground(main_side_color);
        predict_btn.setIcon(GUI_Util.getIcon("SYBIL_GUI/evaluate_icons/icons8_magic_crystal_ball_50px.png",btn_size,btn_size)); // NOI18N
        predict_btn.setToolTipText("predict_btn");
        predict_btn.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		predict_btnAction(e);
         	}
      });
        side_panel.add(predict_btn);

        roc_btn.setBackground(main_side_color);
        roc_btn.setIcon(GUI_Util.getIcon("SYBIL_GUI/evaluate_icons/icons8_cosine_50px.png",btn_size,btn_size)); // NOI18N
        side_panel.add(roc_btn);

        lift_curve_btn.setBackground(main_side_color);
        lift_curve_btn.setIcon(GUI_Util.getIcon("SYBIL_GUI/evaluate_icons/icons8_long_position_50px.png",btn_size,btn_size)); // NOI18N
        side_panel.add(lift_curve_btn);

        calibration_plt_btn.setBackground(main_side_color);
        calibration_plt_btn.setIcon(GUI_Util.getIcon("SYBIL_GUI/evaluate_icons/icons8_long_position_50px.png",btn_size,btn_size)); // NOI18N
        side_panel.add(calibration_plt_btn);

        
        add(side_panel, java.awt.BorderLayout.WEST);
        add(center_panel,java.awt.BorderLayout.CENTER);
	}
	private void testAction(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	private void predict_btnAction(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	private void confuse_Action(ActionEvent e) {
		center_panel.removeAll();
		center_panel.add(SCORE_VIEW);
		center_panel.repaint();
		center_panel.revalidate();
		
	}
	// Variables declaration - do not modify                     
    private javax.swing.JButton lift_curve_btn;
    private javax.swing.JButton roc_btn;
    private javax.swing.JButton calibration_plt_btn;
    private javax.swing.JButton predict_btn;
    private javax.swing.JButton confuse_matrix_btn;
    private javax.swing.JButton testScore_btn;
    // End of variables declaration 
    private javax.swing.JPanel SCORE_VIEW;

}
