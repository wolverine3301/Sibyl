package guiComponents;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;

public class Bar_Meter extends JPanel{
	private int progress = 0;
	private Color barcolor;
	private Color faceColor;
	private int width;
	private int height;
	private String percent_label;
	private String fraction_label = null;
	public Bar_Meter(Color barColor, Color faceColor,int width,int height) {
		this.barcolor = barColor;
		this.faceColor = faceColor;
		this.width = width;
		this.height = height;
		percent_label = progress+"%";
	}
	public void updateProgress(int progress_val) {
		this.progress = progress_val;
		percent_label = progress+"%";
	}
	public void updateProgress2(int a, int b) {
		fraction_label = a +" / "+ b;
	}
	public void updateProgress3(int a,int progress_val) {
		this.progress = progress_val;
		percent_label = "LEVEL "+ a;
	}
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		RoundRectangle2D.Float barface = new RoundRectangle2D.Float(0,0,width+2,height+8, 30, 30);
		RoundRectangle2D.Float bar = new RoundRectangle2D.Float(2,4,progress*((float)width/100),height-((float)height/3), 30, 30);
		RoundRectangle2D.Float bar_s = new RoundRectangle2D.Float(2,4,progress*((float)width/100)+4,height-((float)height/3), 30, 30);
		g2.setColor(this.faceColor);
		g2.draw(barface);
		g2.fill(barface);
		g2.setColor(this.barcolor.darker());
		g2.draw(bar_s);
		g2.fill(bar_s);
		g2.setColor(this.barcolor);
		g2.draw(bar);
		g2.fill(bar);

		g2.setColor(this.barcolor);
		g2.setColor(this.barcolor.darker().darker().darker());
		//g2.rotate(Math.toRadians(90));
		g.setFont(new Font("Courier New", 0, height/3));
		if(fraction_label == null)
			g2.drawString(percent_label, (int)(((double)width/2)-(double)width/10), height-(int)((double)height/2));
		else {
			g2.drawString(percent_label, (int)(((double)width/2)-(double)width/10), height-(int)((double)height/6));
			g2.drawString(fraction_label, (int)(((double)width/2)-(double)width/6), height-(int)((double)height/2));
		}

	}
	public void setLabel1(int a) {
		percent_label = "LEVEL "+a;
	}
	public void setLabel2(int a, int b) {
		fraction_label = a +" / "+ b;
	}
	

}