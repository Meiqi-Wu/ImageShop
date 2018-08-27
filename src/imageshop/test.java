package imageshop;

import java.io.File;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.OverlayLayout;

public class test extends JFrame{
	private GImage image1;
	private GImage image2;
	
	public test() {
		this.setSize(800,  500);
		this.setLayout(new OverlayLayout(this.getContentPane()));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		test program = new test();
		program.run();
	}
	
	public void run() {
		this.image1 = new GImage(new File(System.getProperty("user.dir") + "/res/Countryside.png"));
		this.getContentPane().add(this.image1);
		this.revalidate();
//		GRect rect = new GRect(100, 100, 200, 200);
//		this.getContentPane().add(rect);
//		this.revalidate();
//		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Put a number: ");
		int x = sc.nextInt();
		sc.close();
		
//		this.getContentPane().remove(rect);
////		GRect rect2 = new GRect(50, 160, 200, 200);
////		this.getContentPane().add(rect2);
//		
//		this.revalidate();
		
		
		this.getContentPane().remove(this.image1);
		this.image2 = new GImage(new File(System.getProperty("user.dir") + "/res/karel.png"));
		this.getContentPane().add(this.image2);

		this.revalidate();
		this.repaint();
	}
	

}
