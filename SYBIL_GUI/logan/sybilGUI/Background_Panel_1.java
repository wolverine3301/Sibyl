package logan.sybilGUI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Background_Panel_1 extends JPanel{
	
    private JLabel imageLabel = new JLabel();
    private JLabel headerLabel = new JLabel();
    Image splashScreen;
    ImageIcon imageIcon;
    public Background_Panel_1(int W, int H) {
        try {
        	
            splashScreen = Toolkit.getDefaultToolkit().getImage("SYBIL_GUI/bg1.gif");
            // Create ImageIcon from Image
            imageIcon = new ImageIcon(this.getClass().getResource("SYBIL_GUI/bg1.gif"));
            imageLabel.setIcon(imageIcon);
            // add the image label
            //ImageIcon ii = new ImageIcon(this.getClass().getResource("SYBIL_GUI/ggg.gif"));
            //imageLabel.setIcon(ii);
            add(imageLabel, java.awt.BorderLayout.CENTER);
            setSize(new Dimension(W, H));
            this.setVisible(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    // Paint image onto JWindow
    public void paint(Graphics g) {
       super.paint(g);
       g.drawImage(splashScreen, 0, 0, this);
    }

}
