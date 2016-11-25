package listing06;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PCXHeaderReader {

	public static void main(String[] args) {
		try {
			InputStream stream = new FileInputStream("parrot.pcx");
			byte[] bytes;
			int length;
			length = stream.available();
			bytes = new byte[length];
			stream.read(bytes, 0, length);
			stream.close();
			ByteBuffer buffer = ByteBuffer.wrap(bytes);
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			short ident = (short) (buffer.get() & 0x00FF);
			short version = (short) (buffer.get() & 0x00FF);
			short encoding = (short) (buffer.get() & 0x00FF);
			short bpp = (short) (buffer.get() & 0x00FF);
			int x_min = buffer.getShort() & 0x0000FFFF;
			int y_min = buffer.getShort() & 0x0000FFFF;
			int x_max = buffer.getShort() & 0x0000FFFF;
			int y_max = buffer.getShort() & 0x0000FFFF;
			int h_dpi = buffer.getShort() & 0x0000FFFF;
			int v_dpi = buffer.getShort() & 0x0000FFFF;
			byte[] color_palette = new byte[48];
			buffer.get(color_palette);
			
			System.out.println("ident: " + ident);
			System.out.println("version: " + version);
			System.out.println("encoding: " + encoding);
			System.out.println("bits per pixel: " + bpp);
			System.out.println("min x coordinate: " + x_min);
			System.out.println("min y coordinate: " + y_min);
			System.out.println("max x coordinate: " + x_max);
			System.out.println("max y coordinate: " + y_max);
			System.out.println("dpi horizontal: " + h_dpi);
			System.out.println("dpi vertical: " + v_dpi);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
