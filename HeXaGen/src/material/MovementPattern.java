package material;

public class MovementPattern {

	boolean[] jumps;
	int[] directions;

	public MovementPattern(DirectionJumpPair... directions) {
		this.jumps = new boolean[directions.length];
		this.directions = new int[directions.length];

		for (int i = 0; i < directions.length; i++) {
			jumps[i] = directions[i].isJump();
			this.directions[i] = directions[i].getDirection();
		}
	}

	public boolean getJump(int step) {
		if (step > jumps.length)
			return false;
		else
			return jumps[step];
	}
	
	public int getDirection(int step){
		if (step > directions.length)
			return -1;
		else
			return directions[step];
	}
	
	public DirectionJumpPair getPair(int step){
		if(step > directions.length || step > jumps.length)
			return null;
		else
			return new DirectionJumpPair(directions[step], jumps[step]);
	}

}
