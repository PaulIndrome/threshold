package ships;

public interface Ship {
	
	void set(int[][][] boards, int direction);
	void markAsDestroyed();
	boolean isDestroyed();
	
	Direction getDirection();
	int getBowPosition(int xy);
	int getType();
	String getName();
	int getSize();
	int getTeam();
	
	void checkHit(int x, int y);
	
}
