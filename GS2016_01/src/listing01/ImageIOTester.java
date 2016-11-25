package listing01;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ImageIOTester {
	
	public static void showImage(BufferedImage img, String name) {
		if (img != null) {
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle(name);
			ImageIcon icon = new ImageIcon();
			icon.setImage(img);
			JLabel label = new JLabel();
			label.setIcon(icon);
			label.setText("");
			frame.getContentPane().add(label);
			frame.pack();
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		}
	}

	public static void main(String[] args) {
		try {
			File bitmapFile = new File("parrot.bmp");
			BufferedImage img = ImageIO.read(bitmapFile);
			if (img != null) {
				showImage(img, bitmapFile.getName());	
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
