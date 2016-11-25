import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

public class GoodGuy {
	public static final double SHOT_FREQUENCY = 5;
	public static final double SPEED = 250;
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	public static final int HEALTH = 10;

	private double x,y,maxX;
	private long lastShot = 0;
	private int health;


	public GoodGuy(double xWert, double yWert, double maxXWert) {

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
		if(x < GoodGuy.WIDTH/2)	{
			x = GoodGuy.WIDTH/2;
		}
	}


	public void shoot(Vector<Bullet> bullets) {
		double dt = (System.nanoTime()-lastShot)*1E-9;
		if(dt>1/SHOT_FREQUENCY) {
			bullets.add(new Bullet(x, y-HEIGHT, true));
		}
		lastShot = System.nanoTime();
	}


	public boolean hitBy(Bullet2 b) {
		Rectangle bounds = new Rectangle((int)x-WIDTH/2, (int)y-HEIGHT, WIDTH, HEIGHT);
		if(bounds.contains(new Point((int)b.getX(), (int)b.getY()))) {
			return true;
		}
		else {
			return false;
		}
	}



	public void hit(){
		health-=1;
	}



	public double getY(){
		return y;
	}

	public double getX(){
		return x;
	}


	public int getHealth() {
		// TODO Auto-generated method stub
		return health;
	}



	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillOval((int)x-WIDTH/2, (int)y-HEIGHT, WIDTH, HEIGHT);
		g.setColor(Color.GREEN);
		g.fillRect(10, 580, (health*100)/HEALTH, 10);
		g.drawString("Player 1", 10, 575);
	}
}