package ships;

/*
 * Wrapper class for one of four directions, determined by a single integer beginning with 0 for north and incrementing clockwise
 */

public class Direction {

	int xD;
	int yD;
	int id;

	public Direction(int direction) {
		id = direction;
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
