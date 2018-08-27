package imageshop;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

public class GRect extends JComponent{
	private int x;
	private int y;
	private int w;
	private int h;
	private Color color;
	
	public GRect(double x, double y, double w, double h) {
		this.x = (int)x;
		this.y = (int)y;
		this.w = (int)w; 
		this.h = (int)h;
		this.color = Color.BLACK;
		this.setSize(this.w, this.h);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(this.color);
		g.drawRect(x, y, w, h);
	}
}