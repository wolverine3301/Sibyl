package main_gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Load_Panel extends JPanel{
    JPanel contentPane;
    JLabel imageLabel = new JLabel();
    JLabel headerLabel = new JLabel();

    public Load_Panel() {
        try {
            setSize(new Dimension(400, 300));
            // add the image label
            ImageIcon ii = new ImageIcon(this.getClass().getResource("unnamed.gif"));
            imageLabel.setIcon(ii);
            add(imageLabel, java.awt.BorderLayout.CENTER);
            this.setVisible(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
