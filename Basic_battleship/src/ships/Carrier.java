package ships;

public class Carrier implements Ship {

	private int type = 0;
	private int size = 5;
	private String name = "Carrier";

	private int team;
	private int bowX;
	private int bowY;
	private Direction direction;
	private boolean isDestroyed = false;

	public Carrier(int bowX, int bowY, int direction, int team) {
		this.team = team;
		this.bowX = bowX;
		this.bowY = bowY;
		this.direction = new Direction(direction);
	}

	@Override
	public void set(int[][][] boards, int direction) {

	}

	@Override
	public void markAsDestroyed() {
		isDestroyed = true;
	}

	@Override
	public Direction getDirection() {
		return direction;
	}

	@Override
	public int getBowPosition(int xy) {
		switch (xy) {
		case 1:
			return bowX;
		case 2:
			return bowY;
		default:
			return -1;
		}

	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public boolean isDestroyed() {
		return isDestroyed;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getTeam() {
		return team;
	}
	
	@Override
	public boolean checkHit(int x, int y) {
		for(int i=0; i <= size; i++){
			
		}
	}

}
