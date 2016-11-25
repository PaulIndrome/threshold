package listing07;


import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PCXReader {

	public static final int MANUFACTURER_Z_SOFT = 10;
	public static final int VERSION_0_PC_PAINTBRUSH_V2_5 = 0;
	public static final int VERSION_2_PC_PAINTBRUSH_V2_8_WITH_PALETTE = 2;
	public static final int VERSION_3_PC_PAINTBRUSH_V2_8_WITHOUT_PALETTE = 3;
	public static final int VERSION_4_PC_PAINTBRUSH_WINDOWS = 4;
	public static final int VERSION_5_PC_PAINTBRUSH_V3_0 = 5;
	public static final int ENCODING_RLE = 1;
	public static final int PALETTE_RGB = 1;
	public static final int PALETTE_GRAYSCALE = 2;
	public static final byte BEGIN_OF_VGA_PALETTE = 12;

	public static final int SIZE_OF_PCX_HEADER = 128;
	public static final int SIZE_OF_PCX_COLOR_PALETTE = 48;
	public static final int SIZE_OF_PCX_EMPTY = 54;
	public static final int SIZE_OF_VGA_PALETTE = 768;

	static class PCX {
		short ident;
		short version;
		short encoding;
		short bpp;
		int x_min;
		int y_min;
		int x_max;
		int y_max;
		int h_dpi;
		int v_dpi;
		byte[] color_palette;
		short reserved;
		short ncp; // Number of color planes
		int nbs; // Number of bytes/scanline
		int palette;
		int h_size;
		int v_size;
		byte[] data;
	}

	File pcxFile;

	public PCXReader(String filename) throws FileNotFoundException {
		this.pcxFile = new File(filename);
	}

	public PCXReader(File f) throws FileNotFoundException {
		this.pcxFile = f;
	}



	public BufferedImage readImage() throws IOException {
		BufferedImage img = null;
		if (this.pcxFile != null) {
			InputStream stream = new FileInputStream(this.pcxFile);
			byte[] bytes;
			int length;
			length = stream.available();
			bytes = new byte[length];
			stream.read(bytes, 0, length);
			ByteBuffer buffer = ByteBuffer.wrap(bytes);
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			PCX p = new PCX();
			p.ident = (short) (buffer.get() & 0x00FF);
			p.version = (short) (buffer.get() & 0x00FF);
			p.encoding = (short) (buffer.get() & 0x00FF);
			p.bpp = (short) (buffer.get() & 0x00FF);
			p.x_min = buffer.getShort() & 0x0000FFFF;
			p.y_min = buffer.getShort() & 0x0000FFFF;
			p.x_max = buffer.getShort() & 0x0000FFFF;
			p.y_max = buffer.getShort() & 0x0000FFFF;
			p.h_dpi = buffer.getShort() & 0x0000FFFF;
			p.v_dpi = buffer.getShort() & 0x0000FFFF;
			p.color_palette = new byte[SIZE_OF_PCX_COLOR_PALETTE];
			buffer.get(p.color_palette);
			p.reserved = (short) (buffer.get() & 0x00FF);
			p.ncp = (short) (buffer.get() & 0x00FF);
			p.nbs = buffer.getShort() & 0x0000FFFF;
			p.palette = buffer.getShort() & 0x0000FFFF;
			p.h_size = buffer.getShort() & 0x0000FFFF;
			p.v_size = buffer.getShort() & 0x0000FFFF;
			buffer.position(buffer.position() + SIZE_OF_PCX_EMPTY);
			p.data = new byte[buffer.remaining()];
			buffer.get(p.data);
			if (p.ident == PCXReader.MANUFACTURER_Z_SOFT) {
				switch (p.version) {
				case PCXReader.VERSION_5_PC_PAINTBRUSH_V3_0:
					img = PCXReader.decodeVersion5wPalette(p);
					break;
				}
			}
			stream.close();
		}
		return img;
	}

	static BufferedImage decodeVersion5wPalette(PCX p) {
		BufferedImage img = null;
		int width = p.x_max + 1 - p.x_min;
		int height = p.y_max + 1 - p.y_min;
		byte[] pixels = new byte[width * height];
		int iData = 0;
		int iPixels = 0;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width;) {
				byte dataByte = p.data[iData];
				iData++;
				byte color;
				int lengthS = 1; // length of scanline
				if ((dataByte & 0xC0) == 0xC0) { // first two bits set?
					lengthS = dataByte & 0x0000003F;
					byte colorByte = p.data[iData];
					iData++;
					color = colorByte;
				} else {
					color = dataByte;
				}
				for (int i = 0; i < lengthS; i++) {
					pixels[iPixels] = color;
					iPixels++;
					x++;
				}
			}
		}

		if (iData < p.data.length) {
			byte nextByte = p.data[iData];
			iData++;
			if (nextByte == PCXReader.BEGIN_OF_VGA_PALETTE) {
				byte[] vga_palette = new byte[PCXReader.SIZE_OF_VGA_PALETTE];
				for (int i = 0; i < vga_palette.length && iData < p.data.length; i++, iData++) {
					vga_palette[i] = p.data[iData];
				}
				int[] pixelsRgb = new int[pixels.length];
				for (int i = 0; i < pixelsRgb.length; i++) {
					int colorIndex = (pixels[i] & 0x000000FF) * 3;
					byte r = vga_palette[colorIndex];
					byte g = vga_palette[colorIndex + 1];
					byte b = vga_palette[colorIndex + 2];
					int rgb = ((r << 16) & 0x00FF0000)
							+ ((g << 8) & 0x0000FF00) + (b & 0x000000FF);
					pixelsRgb[i] = rgb;
				}
				img = new BufferedImage(width, height,
						BufferedImage.TYPE_INT_RGB);
				WritableRaster wr = img.getRaster();
				wr.setDataElements(0, 0, width, height, pixelsRgb);
				return img;
			}
		}

		img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
		WritableRaster wr = img.getRaster();
		wr.setDataElements(0, 0, width, height, pixels);
		return img;
	}
}