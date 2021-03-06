import java.awt.Color;
import java.awt.Graphics;

public class Bullet {

	public static final int SPEED = 200;
	private double x,y;
	private boolean up;

	public Bullet(double xWert, double yWert, boolean upWert) {

		x = xWert;
		y = yWert;
		up= upWert;
	}


	public void update(double dt) {
		if(up) {
			y-=dt*SPEED;
		}
		else {
			y+=dt*SPEED;
		}
	}


	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawLine((int)x, (int)y-5,(int)x, (int)y+5);
	}


	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}