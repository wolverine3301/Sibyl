package logan.sybilGUI;

/**
 * 
 * @author logan collier
 *
 */
public abstract class Tertiary_View extends javax.swing.JPanel{
	protected int W;
	protected int H;
	protected int side_panel_W;
	protected java.awt.Color main_bg_color;
	protected java.awt.Color main_side_color;
	
	public Tertiary_View(int width,int height,java.awt.Color main_bg_color, java.awt.Color main_side_color,int side_panel_W) {
		   this.main_bg_color = main_bg_color;
		   this.main_side_color = main_side_color;
		   this.side_panel_W = side_panel_W;
		   W = width;
		   H = height;
		   side_panel = new javax.swing.JPanel();
		   center_panel = new javax.swing.JPanel();
		   
	       setBackground(main_bg_color);
	       setLayout(new java.awt.BorderLayout());
	       setPreferredSize(new java.awt.Dimension(W, H));

	       side_panel.setBackground(main_side_color);
	       side_panel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
	       side_panel.setPreferredSize(new java.awt.Dimension(side_panel_W, H));
	       
	       center_panel.setBackground(main_bg_color);
	       center_panel.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
	       center_panel.setPreferredSize(new java.awt.Dimension(W-side_panel_W, H));
	       
	       initComponents();
	   }  
	protected abstract void initComponents();
	

	// SIDE PANEL
	protected javax.swing.JPanel side_panel;
	// CENTER PANEL
	protected javax.swing.JPanel center_panel;

}
