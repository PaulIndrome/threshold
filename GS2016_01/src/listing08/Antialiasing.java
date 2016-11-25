package listing08;

import java.awt.image.BufferedImage;

public class Antialiasing {

	public static void drawAntialiasedLine(BufferedImage image, int rgb,
			int x0, int y0, int xn, int yn) {
		int dx, dy, incrE, incrNE, d, x, y, flag = 0;
		float D = 0, sin_a, cos_a, sin_cos_a, Denom;
		if (xn < x0) {
			int temp = x0;
			x0 = xn;
			xn = temp;

			temp = y0;
			y0 = yn;
			yn = temp;
		}
		if (yn < y0) {
			y0 = -y0;
			yn = -yn;
			flag = 10;
		}
		dy = yn - y0;
		dx = xn - x0;

		if (dx < dy) {
			int temp = x0;
			x0 = y0;
			y0 = temp;

			temp = xn;
			xn = yn;
			yn = temp;

			temp = dy;
			dy = dx;
			dx = temp;

			flag++;
		}
		x = x0;
		y = y0;
		d = 2 * dy - dx;
		incrE = 2 * dy;
		incrNE = 2 * (dy - dx);

		Denom = (float) Math.sqrt((double) (dx * dx + dy * dy));
		sin_a = dy / Denom;
		cos_a = dx / Denom;
		sin_cos_a = sin_a - cos_a;

		while (x < xn + 1) {
			intensifyPixel(image, rgb, x, y, D, flag);
			intensifyPixel(image, rgb, x, y + 1, D - cos_a, flag);
			intensifyPixel(image, rgb, x, y - 1, D + cos_a, flag);

			x++;

			if (d <= 0) {
				D += sin_a; // distance to the line from E
				d += incrE;
			} else {
				D += sin_cos_a; // distance to the line: NE
				y++;
				d += incrNE;
			}
		}
	}

	static void intensifyPixel(BufferedImage image, int rgb, int x, int y,
			float D, int flag) {
		if (D < 0) {
			D = -D;
		}

		int[] oldPixel = ChannelSeparator.splitIntegerPixel(image.getRGB(x, y));
		int[] newPixel = ChannelSeparator.splitIntegerPixel(rgb);
		newPixel[ChannelSeparator.INDEX_RED] = (int) (newPixel[ChannelSeparator.INDEX_RED]
				* (1.0 - D / 1.5) + oldPixel[ChannelSeparator.INDEX_RED]
				* (D / 1.5));
		newPixel[ChannelSeparator.INDEX_GREEN] = (int) (newPixel[ChannelSeparator.INDEX_GREEN]
				* (1.0 - D / 1.5) + oldPixel[ChannelSeparator.INDEX_GREEN]
				* (D / 1.5));
		newPixel[ChannelSeparator.INDEX_BLUE] = (int) (newPixel[ChannelSeparator.INDEX_BLUE]
				* (1.0 - D / 1.5) + oldPixel[ChannelSeparator.INDEX_BLUE]
				* (D / 1.5));
		drawPixel(image, ChannelSeparator.combineColors(newPixel), x, y, flag);
	}

	static void drawPixel(BufferedImage image, int rgb, int x, int y, int flag) {
		int xf = x, yf = y;
		if (flag == 1) {
			xf = y;
			yf = x;
		} else if (flag == 10) {
			xf = x;
			yf = -y;
		} else if (flag == 11) {
			xf = y;
			yf = -x;
		}
		image.setRGB(xf, yf, rgb);
	}
}
