package ships;

public class Direction {

	int xD;
	int yD;

	public Direction(int direction) {
		switch (direction) {
		case 0:
			xD = 0;
			yD = -1;
			break;
		case 1:
			xD = 1;
			yD = 0;
			break;
		case 2:
			xD = 0;
			yD = 1;
			break;
		case 3:
			xD = -1;
			yD = 0;
			break;
		default:
			xD = 0;
			yD = 0;
			throw new IllegalArgumentException("Invalid Direction integer");
		}
	}

}
