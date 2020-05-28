package logan.sybilGUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class logo extends JPanel{
	
	private BufferedImage image;
	
	public logo() {
	    try {                
	    	image = ImageIO.read(new File("sybilLOGO.png"));
	    } catch (IOException ex) {
	        // handle exception...
	    }
	}
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters            
    }

}
