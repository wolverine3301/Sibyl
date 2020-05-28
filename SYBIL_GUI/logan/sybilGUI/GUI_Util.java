package logan.sybilGUI;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class GUI_Util {
	
    public static ImageIcon getIcon(String File,int w, int h) {
    	BufferedImage icon = null;
		try {
			//File file = new File("icons8_octahedron_50px_1.png")
			icon = ImageIO.read(new File(File));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Add custom icons to passwords
		JLabel picLabel = new JLabel();
		picLabel.setSize(w, h);
		Image dimg = icon.getScaledInstance(picLabel.getWidth(), picLabel.getHeight(),Image.SCALE_SMOOTH);
		ImageIcon imageIcon = new ImageIcon(dimg);
		return imageIcon;
    }
}
