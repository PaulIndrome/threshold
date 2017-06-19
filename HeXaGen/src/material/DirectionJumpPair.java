package material;

public class DirectionJumpPair {
	int direction;
	boolean jump;
	
	public DirectionJumpPair(int direction, boolean jump){
		this.direction = direction;
		this.jump = jump;
	}
	
	public int getDirection(){
		return direction;
	}
	
	public boolean isJump(){
		return jump;
	}
}
