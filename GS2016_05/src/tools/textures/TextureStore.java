package tools.textures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TextureStore {
	
	public enum TextureImage {
		EARTH_COLOR("earth_color_4k.bmp"),
		EARTH_NORMAL("earth_normal_4k.bmp"),
		MARS_COLOR("mars_color_4k.bmp"),
		MARS_NORMAL("mars_normal_4k.bmp"),
		WOOD("wood.bmp"),
		MARBLE("marble.bmp"),
		CHEST("chest.png");
		
		TextureImage(String filename) {
			this.filename = filename;
		}
		private final String filename;
	
	}
	
	public static BufferedImage readImage(TextureImage texture) throws IOException {
		BufferedImage img = null;
		// read BufferedImage from Stream
		BufferedInputStream bufferedStream = new BufferedInputStream(
				TextureStore.class.getResourceAsStream("/"
						+ TextureStore.class.getPackage().getName()
								.replace('.', '/') + "/" + texture.filename));
		img = ImageIO.read(bufferedStream);
		bufferedStream.close();
		
		// flip BufferedImage vertically
		AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
		tx.translate(0, -img.getHeight());
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		img = op.filter(img, null);
		
		return img;
	}
	
	public static BufferedImage createChessboardImage(int imageSize,
			int numberOfSquares) {
		BufferedImage img = new BufferedImage(imageSize, imageSize,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();
		Color backgroundColor = Color.WHITE;
		Color foregroundColor = Color.BLACK;
		int squareSize = imageSize / numberOfSquares;
		g.setColor(backgroundColor);
		g.fillRect(0, 0, imageSize, imageSize);
		g.setColor(foregroundColor);
		for (int i = 0; i < numberOfSquares; i++) {
			for (int j = 0; j < numberOfSquares; j++) {
				if ((i + j) % 2 == 0) {
					int x = i * squareSize;
					int y = j * squareSize;
					g.fillRect(x, y, squareSize, squareSize);
				}
			}
		}

		return img;
	}
}
