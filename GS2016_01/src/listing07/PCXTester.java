package listing07;


import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PCXTester {
	
	public static void showPCX(BufferedImage img, String name) {
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			PCXReader r = new PCXReader("parrot.pcx");
			BufferedImage img = r.readImage();
			if (img != null) {
				PCXTester.showPCX(img, "parrot.pcx");	
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
