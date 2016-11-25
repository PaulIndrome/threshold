import java.awt.*;
import java.awt.image.*;
import java.util.Vector;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class SecondInvader extends JApplet implements KeyListener, Runnable {

	private GoodGuy player;
	private BadGuy enemy;
	private boolean left, right;
	private boolean left1, right1;
	private Vector<Bullet> bullets;
	private Vector<Bullet2> bullets2;

	private int state = PLAYING;
	public static final int P1WON = 1;
	public static final int P2WON = 2;
	public static final int PLAYING = 0;


	@Override
	public void init() {
		addKeyListener(this);
		setFocusable(true);

		player	= new GoodGuy(getWidth()/2, getHeight()-GoodGuy.HEIGHT/2, getWidth());
		enemy	= new BadGuy(getWidth()/2, BadGuy.HEIGHT/2, getWidth());
		bullets		= new Vector<Bullet>();
		bullets2	= new Vector<Bullet2>();

		new Thread(this).start();
	}



	@Override
	public void paint(Graphics graphics) {
		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		player.draw(g);
		enemy.draw(g);
		g.setColor(Color.GREEN);
		g.drawRect(5, 577, 110, 15);
		g.setColor(Color.RED);
		g.drawRect(685, 7, 110, 15);

		for(int i= 0; i < bullets.size(); i++) {
			bullets.get(i).draw(g);
		}
		for(int i= 0; i < bullets2.size(); i++) {
			bullets2.get(i).draw(g);
		}

		if(state==P1WON) {
			g.setColor(Color.GREEN);
			g.drawString("Player 1 won!", getWidth()/2-30, getHeight()/2);
			g.drawRect(getWidth()/2-45, getHeight()/2-15, 100, 20);
		}
		if(state==P2WON) {
			g.setColor(Color.RED);
			g.drawString("Player 2 won!", getWidth()/2-30, getHeight()/2);
			g.drawRect(getWidth()/2-45, getHeight()/2-15, 100, 20);
		}

		graphics.drawImage(image,0,0,null);
	}



	@Override
	public void keyPressed(KeyEvent ke) {
		switch(ke.getKeyCode()) {
		case KeyEvent.VK_LEFT: left = true; break;
		case KeyEvent.VK_RIGHT: right = true; break;
		case KeyEvent.VK_A: left1 = true; break;
		case KeyEvent.VK_D: right1 = true; break;
		}
	}



	@Override
	public void keyReleased(KeyEvent ke) {
		switch(ke.getKeyCode()) {
		case KeyEvent.VK_LEFT: left = false; break;
		case KeyEvent.VK_RIGHT: right = false; break;
		case KeyEvent.VK_A: left1 = false; break;
		case KeyEvent.VK_D: right1 = false; break;
		}
	}



	@Override
	public void keyTyped(KeyEvent ke) {
		if(ke.getKeyChar()==' '){
			player.shoot(bullets);
		}
		if(ke.getKeyChar()=='s'){
			enemy.shoot(bullets2);
		}
	}



	public void update(double dt) {
		if(state == PLAYING) {

			if(left) {
				player.moveLeft(dt);
			}
			if(right) {
				player.moveRight(dt);
			}
			if (left1) {
				enemy.moveLeft(dt);
			}
			if (right1){
				enemy.moveRight(dt);
			}

			for(int i = 0; i < bullets.size(); i++) {
				bullets.get(i).update(dt);
			}
			for(int j = 0; j < bullets2.size(); j++) {
				bullets2.get(j).update(dt);
			}

			enemyCollision();
			playerCollision();


			if(playerHasWon()) {
				state = P1WON;
			}
			else {
				if(enemyHasWon()) {
					state = P2WON;
				}
			}
		}
	}



	public boolean playerHasWon() {
		return (enemy.getHealth() <= 0);
	}

	public boolean enemyHasWon() {
		return (player.getHealth() <= 0);
	}



	public void enemyCollision() {
		for(int i = 0; i < bullets.size(); i++){
			if(enemy.hitBy(bullets.get(i))){
				bullets.remove(i);
				enemy.hit();
			}
		}
	}



	public void playerCollision() {
		for(int i = 0; i < bullets2.size(); i++){
			if(player.hitBy(bullets2.get(i))){
				bullets2.remove(i);
				player.hit();
			}
		}
	}



	@Override
	public void run() {
		long time = System.nanoTime();
		while(true) {

			try {
				Thread.sleep(10);
			}
			catch (Throwable t){
			}
			double dt = (System.nanoTime()-time)*1E-9;
			time = System.nanoTime();
			update(dt);

			repaint();
		}
	}
}