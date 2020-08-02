package guiComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class Bar extends JPanel{
	private int progress = 0;
	private Color bar;
	private Color face;
	public Bar(int width,int height, Color barColor, Color faceColor) {
		this.bar = barColor;
		this.face = faceColor;
		
	}
	public void updateProgress(int progress_val) {
		this.progress = progress_val;
	}
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.translate(this.getWidth()/2, this.getHeight()/2);
		g2.rotate(Math.toRadians(270));
		Arc2D.Float arc = new Arc2D.Float(Arc2D.PIE);
		Ellipse2D circle = new Ellipse2D.Float(0, 0, 110, 110);
		arc.setFrameFromCenter(new Point(0,0), new Point(120,120));
		circle.setFrameFromCenter(new Point(0,0), new Point(110,110));
		arc.setAngleStart(1);
		arc.setAngleExtent(-progress*3.6); // 360/100 = 3.6
		g2.setColor(this.bar);
		g2.draw(arc);
		g2.fill(arc);
		g2.setColor(this.face);
		g2.draw(circle);
		g2.fill(circle);
		g2.setColor(this.bar);
		g2.rotate(Math.toRadians(90));
		g.setFont(new Font("Courier New", 0, 38));
		g2.drawString(progress+"%", -32, 0);
	}
}
