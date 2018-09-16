package imageshop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;


public class GImage extends JComponent{
	public BufferedImage image = null;
	public double theta;
	
	
	public GImage(File file) {
		this.theta = 0;
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
		// Rotate by theta
		AffineTransform tx = AffineTransform.getRotateInstance(theta, image.getWidth()/2, image.getHeight()/2);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		this.image = op.filter(image, null);
		this.theta = 0;
	}
	
	public int getWidth() {
		return this.image.getWidth();
	}
	
	public int getHeight() {
		return this.image.getHeight();
	}
	
	public void flipHorizontal() {
		AffineTransform tx = AffineTransform.getScaleInstance(-1,  1);
		tx.translate(-image.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		this.image = op.filter(this.image, null);
	}
	
	public void negative() {
		int height = this.image.getHeight();
		int width = this.image.getWidth();
		for(int i = 0; i< width;i++) { 
			for(int j=0; j < height; j++) {
				Color col = new Color(this.image.getRGB(i, j), true);
				col = new Color(255 - col.getRed(), 255 - col.getGreen(), 255 - col.getBlue());
				this.image.setRGB(i, j, col.getRGB());	
			}
		}
	}
	
	public void translate(int dx, int dy) {
		AffineTransform tx = new AffineTransform();
		tx.translate(dx, dy);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		this.image = op.filter(this.image, null);
	}
	
	public void greenScreen() {
		int height = this.image.getHeight();
		int width = this.image.getWidth();
//		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		for(int i = 0; i< width;i++) { 
			for(int j=0; j < height; j++) { 

//				int rgb = this.image.getRGB(i, j);
//				int r = (0xff&rgb);
//				int g = (0xff&(rgb>>8));
//				int b = (0xff&(rgb>>16));
//				if(g > 2 * Math.max(r, b)) {
//					rgb = r + (g<<8) + (b << 16);
////					newImage.setRGB(i, j, rgb);
//					this.image.setRGB(i, j, rgb);
//				} 

				int rgb = this.image.getRGB(i, j);
				Color col = new Color(rgb);
				if(col.getGreen() > 2 * Math.max(col.getBlue(), col.getRed())){
//					newImage.setRGB(i, j, (rgb & 0x00ffffff));
					this.image.setRGB(i, j, (rgb & 0x00ffffff));
				}
				
			}
		}
//		this.image = newImage;
	}
	public int computeLuminosity(int r, int g, int b) {
		return (int)Math.round(0.299 * r + 0.587 * g + 0.114 * b);
	}
	
	public void equalize() {
		int height = this.image.getHeight();
		int width = this.image.getWidth();
		int[] lumiHist = new int[256];
		int[] lumiCumuHist = new int[256];
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				Color col = new Color(this.image.getRGB(x, y));
				int lumi = computeLuminosity(col.getRed(), col.getGreen(), col.getBlue());
				if(lumi < 0) {
					lumi = 0;
				} else if(lumi > 255) {
					lumi = 255;
				}
				lumiHist[lumi]++;
			}
		}
		
		lumiCumuHist[0] = lumiHist[0];
		for(int i=1; i < 256; i++) {
			lumiCumuHist[i] = lumiCumuHist[i-1] + lumiHist[i];
			System.out.println(lumiCumuHist[i]);
		}
		


		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				Color col = new Color(this.image.getRGB(x, y));
				int lumi = computeLuminosity(col.getRed(), col.getGreen(), col.getBlue());
				float R = (float)lumiCumuHist[lumi]/(height * width);
				if(R>1 ) {
					R = 1;
				} else if(R<0) {
					R = 0;
				}
//				System.out.println(R);
				this.image.setRGB(x, y, new Color(R,R,R).getRGB());
			}
		}
		
	}
	
	public void blur() {
		int halfpanel = 3;
		int height = this.image.getHeight();
		int width = this.image.getWidth();
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				int iStart = Math.max(0, x-halfpanel);
				int iEnd = Math.min(width, x + halfpanel);
				int jStart = Math.max(0, y-halfpanel);
				int jEnd = Math.min(height, y + halfpanel);
				int sumRed = 0;
				int sumGreen = 0;
				int sumBlue = 0;
				for(int i = iStart; i<iEnd ; i++) {
					for(int j = jStart; j< jEnd; j++) {
						Color col = new Color(this.image.getRGB(i, j));
						sumRed += col.getRed();
						sumGreen += col.getGreen();
						sumBlue += col.getBlue();
					}
				}
				int r = sumRed / ((iEnd-iStart)*(jEnd-jStart));
				int g = sumGreen / ((iEnd-iStart)*(jEnd-jStart));
				int b = sumBlue / ((iEnd-iStart)*(jEnd-jStart));
				newImage.setRGB(x, y, (new Color(r,g,b).getRGB()));
			}
		}
		this.image = newImage;
	}
	
	public int getRGB(int x, int y) {
		return this.image.getRGB(x, y);
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

//		System.out.println("paintComponent.");
//		int i = 10;
//		int j = 10;
//		int rgb = this.image.getRGB(i, j);
//		int a = (0xff&(rgb>>24));
//		System.out.println("In paintComponent, a = " + a);
	
		g2d.drawImage(image, 0, 0, null);
	}

}
