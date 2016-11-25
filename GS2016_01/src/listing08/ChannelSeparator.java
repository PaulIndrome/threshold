package listing08;

public class ChannelSeparator {
	public static final int INDEX_RED = 0;
	public static final int INDEX_GREEN = 1;
	public static final int INDEX_BLUE = 2;

	public static int[] splitIntegerPixel(int rgb) {
		int[] colors = new int[3];
		int red = (rgb >> 16) & 0x000000FF;
		int green = (rgb >> 8) & 0x000000FF;
		int blue = (rgb) & 0x000000FF;
		colors[ChannelSeparator.INDEX_RED] = red;
		colors[ChannelSeparator.INDEX_GREEN] = green;
		colors[ChannelSeparator.INDEX_BLUE] = blue;
		return colors;
	}

	public static int combineColors(int red, int green, int blue) {
		return ((red << 16) & 0x00FF0000) + ((green << 8) & 0x0000FF00)
				+ (blue & 0x000000FF);
	}

	public static int combineColors(int[] colors) {
		return ChannelSeparator.combineColors(
				colors[ChannelSeparator.INDEX_RED],
				colors[ChannelSeparator.INDEX_GREEN],
				colors[ChannelSeparator.INDEX_BLUE]);
	}

}
