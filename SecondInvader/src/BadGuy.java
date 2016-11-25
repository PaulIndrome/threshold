import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

public class BadGuy {
	public static final double SHOT_FREQUENCY = 5;
	public static final double SPEED = 250;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	public static final int HEALTH = 10;

	private double x,y,maxX;
	private long lastShot = 0;
	private int health;


	public BadGuy(double xWert, double yWert, double maxXWert) {

		x = xWert;
		y = yWert;
		maxX= maxXWert;
		health = HEALTH;
	}



	public void moveRight(double dt) {
		x+=dt*SPEED;
		if(x > maxX-GoodGuy.WIDTH/2) {
			x = maxX-GoodGuy.WIDTH/2;
		}
	}

	public void moveLeft(double dt){
		x-=dt*SPEED;
		if(x < GoodGuy.WIDTH/2)	{			//Spieler2 läuft nur bis Rand
			x = GoodGuy.WIDTH/2;
		}
	}


	public void shoot(Vector<Bullet2> bullets2) {
		double dt = (System.nanoTime()-lastShot)*1E-9;
		if(dt>1/SHOT_FREQUENCY) {
			bullets2.add(new Bullet2(x, y+HEIGHT, false));
		}
		lastShot = System.nanoTime();
	}


	public boolean hitBy(Bullet b) {
		Rectangle bounds = new Rectangle((int)x-WIDTH/2, (int)y, WIDTH, HEIGHT);
		if(bounds.contains(new Point((int)b.getX(), (int)b.getY()))){
			return true;
		}
		else {
			return false;
		}
	}


	public double getY(){
		return y;
	}

	public double getX(){
		return x;
	}



	public void hit(){
		health-=1;
	}


	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)x-WIDTH/2, (int)y, WIDTH, HEIGHT);
		g.setColor(Color.RED);
		g.fillRect(690, 10, (health*100)/HEALTH, 10);
		g.drawString("Player 2", 690, 35);
	}


	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
	}
}