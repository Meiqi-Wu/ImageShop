package imageshop;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class GImage extends JComponent{
	public BufferedImage image = null;
	public double theta;
	public boolean hFlipFlag;
	
	public GImage(File file) {
		this.theta = 0;
		this.hFlipFlag = false;
		try {
			image = ImageIO.read(file);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public GImage(GImage image2) {
		this.image = image2.image;
		this.theta = image2.theta;
	}
	
	public void rotate(double angle) {
		this.theta = Math.toRadians(angle);
	}
	
	public int getWidth() {
		return this.image.getWidth();
	}
	
	public int getHeight() {
		return this.image.getHeight();
	}
	
	public void flipHorizontal(boolean bool) {
		this.hFlipFlag = bool;
	}
	
	public void saveImage(File file) {
		String fileName = file.getName();
		String formatName = fileName.substring(fileName.indexOf('.')+1);
		try {
			ImageIO.write(this.image, formatName, file);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void paintComponent(Graphics g) { 
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
//		g2d.rotate(theta);
//		g2d.drawImage(this.image, 0, 0, 200, 200, null);
		AffineTransform tx = AffineTransform.getRotateInstance(theta, image.getWidth()/2, image.getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		this.image = op.filter(image, null);
		
		if(this.hFlipFlag) {
			AffineTransform tx2 = AffineTransform.getScaleInstance(-1,  1);
			tx2.translate(-image.getWidth(null), 0);
			AffineTransformOp op2 = new AffineTransformOp(tx2, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			this.image = op2.filter(this.image, null);
		}
		g2d.drawImage(image, 0, 0, null);
	}

}
