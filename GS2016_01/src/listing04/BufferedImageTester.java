package listing04;


import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BufferedImageTester {
	
	public static void showImage(BufferedImage img, String name) {
		if (img != null) {
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setTitle(name + " (" + imageTypeToString(img.getType()) + ")");
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
	
	public static void drawRectAngle(BufferedImage img) {
		Raster r = img.getRaster();
		int width = img.getWidth();
		DataBuffer dBuffer = r.getDataBuffer();
		System.out.println(dBuffer.getClass().toString());	
		if (dBuffer instanceof DataBufferInt) {
			DataBufferInt iBuffer = (DataBufferInt)dBuffer;
			int[] pixels = iBuffer.getData();
			System.out.println(pixels.length + " pixels");
			// only works for ARGB_8_8_8_8 !
			// Windows Bitmap is Litte Endian, Integer needs to be reversed
			int black = Integer.reverseBytes(Color.BLACK.getRGB());
			for (int x = 50; x <= 150; x++) {
				for (int y = 50; y <= 150; y++) {
					int i = (width * y) + x;
					pixels[i] = black;
				}
			}
		}
	}
	
	public static String imageTypeToString(int type) {
		String typeString = null;
		switch (type) {
		case BufferedImage.TYPE_3BYTE_BGR:
			typeString = "TYPE_3BYTE_BGR";
			break;
		case BufferedImage.TYPE_4BYTE_ABGR:
			typeString = "TYPE_4BYTE_ABGR";
			break;
		case BufferedImage.TYPE_4BYTE_ABGR_PRE:
			typeString = "TYPE_4BYTE_ABGR_PRE";
			break;
		case BufferedImage.TYPE_BYTE_BINARY:
			typeString = "TYPE_BYTE_BINARY";
			break;
		case BufferedImage.TYPE_BYTE_GRAY:
			typeString = "TYPE_BYTE_GRAY";
			break;
		case BufferedImage.TYPE_BYTE_INDEXED:
			typeString = "TYPE_BYTE_INDEXED";
			break;
		case BufferedImage.TYPE_CUSTOM:
			typeString = "TYPE_CUSTOM";
			break;
		case BufferedImage.TYPE_INT_ARGB:
			typeString = "TYPE_INT_ARGB";
			break;
		case BufferedImage.TYPE_INT_ARGB_PRE:
			typeString = "TYPE_INT_ARGB_PRE";
			break;
		case BufferedImage.TYPE_INT_BGR:
			typeString = "TYPE_INT_BGR";
			break;
		case BufferedImage.TYPE_INT_RGB:
			typeString = "TYPE_INT_RGB";
			break;
		case BufferedImage.TYPE_USHORT_555_RGB:
			typeString = "TYPE_USHORT_555_RGB";
			break;
		case BufferedImage.TYPE_USHORT_565_RGB:
			typeString = "TYPE_USHORT_565_RGB";
			break;
		case BufferedImage.TYPE_USHORT_GRAY:
			typeString = "TYPE_USHORT_GRAY";
			break;		
		}
		return typeString;
	}

	public static void main(String[] args) {
		try {
			File bitmapFile = new File("p_a8r8g8b8.bmp");
			BufferedImage img = ImageIO.read(bitmapFile);
			drawRectAngle(img);
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
