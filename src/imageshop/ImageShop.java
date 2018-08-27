package imageshop;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageShop extends JFrame implements ActionListener, MouseMotionListener{
	
	// Valid file extensions for images that we can write.
	private static final String[] SAVE_IMAGE_EXTENSIONS = new String[] {"png", "bmp", "wbmp"};
	
	// Valid file extensions for images that we can read 
	private static final String[] LOAD_IMAGE_EXTENSIONS = new String[] {"png", "bmp", "wbmp",
			"jpg", "gif", "jpeg"};
	
	// The general info label displayed at the top of the window
	private JLabel infolabel;
	
	// The x/y/r/g/b label displayed at the bottom of the window
	private JLabel statsLabel;
	
	// The current image displayed on the canvas (or null if no image)
	private GImage currentImage; 
	
	// The image algorithms object that runs the algorithms
	private ImageShopAlgorithms algorithms;

	// Size of the window
	private final int CANVAS_WIDTH = 800;
	private final int CANVAS_HEIGHT = 500;
	
	public static void main(String[] args) {
		ImageShop program = new ImageShop();
		program.run();
//		System.out.println('A'+1);
//		System.out.println("ABCDE".substring(2,3) );
	}
	
	public void run() {
		this.addMouseMotionListener(this);
		

	}
	
	/* Constructor. */
	public ImageShop() {
		this.setSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("CS 106A Image Shop");
		this.setVisible(true);
		
		addButtons();
		this.infolabel = new JLabel("Welcome to CS 106A ImageShop!");
		this.getContentPane().add(this.infolabel, BorderLayout.NORTH);
		this.statsLabel = new JLabel(" ");
		this.getContentPane().add(this.statsLabel, BorderLayout.SOUTH);
		this.revalidate();		
		this.algorithms = new ImageShopAlgorithms();
	}
	
	// Add the interactors to the screen. 
	private void addButtons() {
		JPanel panel = new JPanel(new GridLayout(12, 1));
		JButton button1 = new JButton("Load Image");
		JButton button2 = new JButton("Save Image");
		JButton button3 = new JButton("Overlay Image");
//		JButton button3 = new JButton("Compare to Image");
		JButton button4 = new JButton("Negative");
		JButton button5 = new JButton("Green Screen");
		JButton button6 = new JButton("Rotate left");
		JButton button7 = new JButton("Rotate right");
		JButton button8 = new JButton("Flip Horizontal");
		JButton button9 = new JButton("Translate");
		JButton button10 = new JButton("blur");
		JButton button11 = new JButton("Equalize");
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);
		button8.addActionListener(this);
		button9.addActionListener(this);
		button10.addActionListener(this);
		button11.addActionListener(this);
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(new JSeparator());
		panel.add(button4);
		panel.add(button5);
		panel.add(button6);
		panel.add(button7);
		panel.add(button8);
		panel.add(button9);
		panel.add(button10);
		panel.add(button11);
		this.getContentPane().add(panel, BorderLayout.WEST);
	}
	
	// Respond to one of the buttons on the left side being clicked
	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("Load Image")) {
			loadImage();
		} else if(command.equals("Save Image")) {
			saveImage();
		} else if(command.equals("Overlay Image")) {
			overlayImage();
//		} else if(command.equals("Compare To Image")) {
//			diffImage();
		} else if(this.currentImage == null) {
			showErrorPopup("Please load an image. ");
		} else if(command.equals("Flip Horizontal")) {
			GImage newImage = algorithms.flipHorizontal(currentImage);
			setImage(newImage);
			this.infolabel.setText(command + " filter applied.");
		} else if(command.equals("Rotate left")) {
			GImage newImage = algorithms.rotateLeft(currentImage);
			setImage(newImage);
			this.infolabel.setText(command + " filter applied.");
		} else if(command.equals("Rotate right")) {
			GImage newImage = algorithms.rotateRight(currentImage);
			setImage(newImage);
			this.infolabel.setText(command + " filter applied.");
		} else if(command.equals("Green Screen")) {
			GImage newImage = algorithms.greenScreen(currentImage);
			setImage(newImage);
			this.infolabel.setText(command + " filter applied.");
		} else if(command.equals("Equalize")) {
			GImage newImage = algorithms.greenScreen(currentImage);
			setImage(newImage);
			this.infolabel.setText(command + " filter applied.");
		} else if(command.equals("Negative")) {
			GImage newImage = algorithms.equalize(currentImage);
			setImage(newImage);
			this.infolabel.setText(command + " filter applied.");
		} else if(command.equals("Translate")) {
			int dx = readInteger("dx ? ");
			int dy = readInteger("dy ? ");
			GImage newImage = algorithms.translate(currentImage, dx, dy);
			setImage(newImage);
			this.infolabel.setText(command + " filter applied.");
		} else if(command.equals("Blur")) {
			GImage newImage = algorithms.blur(currentImage);
			setImage(newImage);
			this.infolabel.setText(command + " filter applied.");
		} else {
			this.infolabel.setText("Unknown command "+ command + ".");
		}
		
		this.revalidate();
		this.repaint();
//		if(this.currentImage!= null) {
//			println("X: "+currentImage.getX());
//			println("Y: "+currentImage.getY());
//		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {}
	
	// When the mouse moves in the image, update the info label at the bottom
	@Override
	public void mouseMoved(MouseEvent e) {
//		if(inImageBounds(e.getX(), e.getY())) {
//			String status = "(x=" + e.getX() + ", y="+e.getY()+")";
//			
//		}
		String status = "(x=" + e.getX() + ", y="+e.getY()+")";
		this.statsLabel.setText(status);
	}
	
	// Shows a file prompt to load in a new image, and displays the chosen image on screen.
	private void loadImage() {
		// Initialize the file chooser prompt
//		println("Load the image. ");
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Image files", LOAD_IMAGE_EXTENSIONS));
		chooser.setCurrentDirectory(getImageDirectory());
		
		if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			// Load the image and add it to the window
			File currentFile = chooser.getSelectedFile();
			GImage image = new GImage(new File(currentFile.getAbsolutePath()));
			
			setImage(image);
			this.infolabel.setText("Loaded image "+ currentFile.getName()+".");
		}
	}
	
	/*
	 * Returns a File representing the image directory, which is either the res/ directory
	 * or the user directory. 
	 */
	private File getImageDirectory() {
		File dir = new File(System.getProperty("user.dir") + "/res");
		if(!dir.isDirectory()) {
			// the directory where java was run from.
			dir = new File(System.getProperty("user.dir")); 
		}
		return dir;
	}
	
	// Shows a file prompt to save the current image (if any) to a file.
	private void saveImage() {
		if(currentImage == null) {
			showErrorPopup("no image to save.");
			return;
		}
		// Initialize the file chooser prompt
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter(".png, .bmp, and .wbmp files", SAVE_IMAGE_EXTENSIONS));
		chooser.setCurrentDirectory(getOutputDirectory());
		
		if(chooser.showSaveDialog(this)==JFileChooser.APPROVE_OPTION) {
			// If they are overwriting and made a mistake, cancel
			if(chooser.getSelectedFile().exists()) {
				if(JOptionPane.showConfirmDialog(this, 
						"File already exists. Overwrite? \n (You probably shouldn't overwrite the instrctor-provided images; save them with a different name)",
						"Overwrite?", JOptionPane.YES_NO_CANCEL_OPTION) != JOptionPane.YES_OPTION){
					return;
				}
			}
		}
		// Save the image to File
		currentImage.saveImage(chooser.getSelectedFile());
		this.infolabel.setText("Saved image to "+chooser.getSelectedFile().getName()+".");
	}
	
	/* Returns a File representing the output directory, which is either the output/
	 * directory or the user directory.
	 */
	private File getOutputDirectory() {
		File dir = new File(System.getProperty("user.dir") + "/output");
		if (!dir.isDirectory()) {
			dir = new File(System.getProperty("user.dir"));
		}
		return dir;
	}
	
	private void overlayImage() {
		if(currentImage == null) {
			showErrorPopup("no image on which to overlay. Please load an image. ");
			return; 
		}
		// Initialize the file chooser prompt
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Image files", LOAD_IMAGE_EXTENSIONS));
		chooser.setCurrentDirectory(getImageDirectory());
		if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
//			GImage newImage = new GImage(chooser.getSelectedFile());
//			this.getContentPane().add(newImage, BorderLayout.CENTER);
			
			BufferedImage newImage = null;
			try {
				newImage = ImageIO.read(chooser.getSelectedFile());
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
			int x = (currentImage.getWidth()-newImage.getWidth())/2;
			int y = (currentImage.getHeight()-newImage.getHeight())/2;
			Graphics g = currentImage.getGraphics();
			g.drawImage(newImage, x, y, null);
		}
		
	}
	
//	// Show a "Diff image" window to compare the pixels of two images
//	private void diffImage() {
//		if (currentImage == null) {
//			showErrorPopup("no image currently displayed.");
//			return;
//		}
//		// Initialize the file chooser prompt
//		JFileChooser chooser = new JFileChooser();
//		chooser.setFileFilter(new FileNameExtensionFilter("Image files", LOAD_IMAGE_EXTENSIONS));
//		chooser.setCurrentDirectory(getImageDirectory());
//		if(chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
//			File imageFile2 = chooser.getSelectedFile();
//		}
//	}
	
	// Show a Error message popup
	private void showErrorPopup(String text) {
		JOptionPane.showMessageDialog(this, "Error: "+text, "Error", JOptionPane.ERROR_MESSAGE);
		this.infolabel.setText("Error: "+text);
	}
	
	// Sets the given image as the current image in the window
	private void setImage(GImage image) {
		if(this.currentImage != null) {
			this.getContentPane().remove(this.currentImage);
//			println("Old image removed. ");
		}
		this.currentImage = new GImage(image);
		this.getContentPane().add(this.currentImage, BorderLayout.CENTER);
	}
	
	/*
	 *  Pops up dialog boxes asking the user to type an integer repeatedly until
	 *  the user types a valid integer. 
	 */
	private int readInteger(String prompt) {
		while(true) {
			try {
				String result = JOptionPane.showInputDialog(prompt);
				int num = Integer.parseInt(result);
				return num;
			} catch(NumberFormatException e) {
				// empty; re-prompt
			} catch(NullPointerException e) {
				// empty; re-prompt
			}
		}
	}

	// Returns whether or not the given coordinate is in the current image
	private boolean inImageBounds(int x, int y) {
		if(this.currentImage == null) {
			return false;
		}
		return false;
	}
	
	// Print a line
	private void println(String text) {
		System.out.println(text);
	}

}
