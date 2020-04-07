package main_gui;

import javax.swing.*;
import java.awt.*;
public class SplashScreen extends JWindow{

   Image splashScreen;
   ImageIcon imageIcon;
   JLabel imageLabel = new JLabel();
   public SplashScreen() {
      splashScreen = Toolkit.getDefaultToolkit().getImage("thing.gif");
      // Create ImageIcon from Image
      imageIcon = new ImageIcon(this.getClass().getResource("thing.gif"));
      imageLabel.setIcon(imageIcon);
      // Set JWindow size from image size
      setSize(imageIcon.getIconWidth(),imageIcon.getIconHeight());
      add(imageLabel, java.awt.BorderLayout.CENTER);
      // Get current screen size
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      // Get x coordinate on screen for make JWindow locate at center
      int x = (screenSize.width-getSize().width)/2;
      // Get y coordinate on screen for make JWindow locate at center
      int y = (screenSize.height-getSize().height)/2;
      // Set new location for JWindow
      setLocation(x,y);
      // Make JWindow visible
      setVisible(true);
   }
   // Paint image onto JWindow
   public void paint(Graphics g) {
      super.paint(g);
      g.drawImage(splashScreen, 0, 0, this);
   }
   public static void main(String[]args) {
      SplashScreen splash = new SplashScreen();
      try {
         // Make JWindow appear for 10 seconds before disappear
         Thread.sleep(5000);
         splash.dispose();
      } catch(Exception e) {
         e.printStackTrace();
      }
   }
}

