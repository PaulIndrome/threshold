package listing05;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ChannelSeperator {

	public static final String[] CHANNEL_NAMES = { "Red", "Green", "Blue " };

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
	
	public static BufferedImage extractChannel(BufferedImage img, int channel) {
		final int red = 0; final int green = 1; final int blue = 2;
		BufferedImage grayImage = null;
		int width = img.getWidth();
		int height = img.getHeight();
		int[] pixels = new int[width * height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int i = (width * y) + x;
				pixels[i] = img.getRGB(x, y);
			}
		}
		grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		byte[] gray = ((DataBufferByte) grayImage.getRaster().getDataBuffer()).getData();
		for (int i = 0; i < pixels.length; i++) {
			switch (channel) {
				case red:
					gray[i] = (byte)(pixels[i] >> 16 & 0x000000FF);
					break;
				case green: 
					gray[i] = (byte)(pixels[i] >> 8 & 0x000000FF);
					break;
				case blue:
					gray[i] = (byte)(pixels[i] & 0x000000FF);
					break;
			}
		}
		return grayImage;
	}

	public static void main(String[] args) {
		try {
			File bitmapFile = new File("parrot.bmp");
			int channel = JOptionPane.showOptionDialog(null,
					"Select a channel", "Color Channels",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, CHANNEL_NAMES, null);
			BufferedImage img = ImageIO.read(bitmapFile);
			if (img != null && channel > -1) {
				BufferedImage grayImage = extractChannel(img, channel);
				showImage(grayImage, bitmapFile.getName() + " (" + CHANNEL_NAMES[channel] + " channel)");
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
