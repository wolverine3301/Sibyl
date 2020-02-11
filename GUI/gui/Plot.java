package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import dataframe.Column;
import javafx.scene.shape.Circle;

public class Plot extends JPanel{
	private List<Circle> coordinates = new ArrayList<Circle>();
	
	public Plot(Column x, Column y) {
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension(500, 500));
		for(int i = 0; i < x.getLength(); i++) {
			addPoint(x.getDoubleValue(i),y.getDoubleValue(i));
		}
	}
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.red);
        
        for (Circle s : coordinates) {
               g2d.draw((Shape) s);
        }
    }
    public void addPoint(double x, double y) {
        coordinates.add(new Circle((int)x, (int)y, 2));
        repaint();
    }

}
