package listing13;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

public class DrawingFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	DrawingPane _pane;
	JScrollPane _scrollablePane;
	JMenuBar _mainMenuBar;
	JMenu _mainMenu;
	JMenuItem _newImageMenuItem;
	JMenuItem _drawLineMenuItem;

	public DrawingFrame() {
		super();
		this.setTitle("Polygons");
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this._pane = new DrawingPane();
		this._scrollablePane = new JScrollPane(this._pane);
		this._scrollablePane.setPreferredSize(new Dimension(700, 500));

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(this._scrollablePane, BorderLayout.CENTER);

		this._newImageMenuItem = new JMenuItem();
		this._newImageMenuItem.setText("New Image");
		this._newImageMenuItem.addActionListener(this);

		this._drawLineMenuItem = new JMenuItem();
		this._drawLineMenuItem.setText("Draw polygon");
		this._drawLineMenuItem.addActionListener(this);

		this._mainMenu = new JMenu();
		this._mainMenu.setText("Actions");
		this._mainMenu.add(this._newImageMenuItem);
		this._mainMenu.add(this._drawLineMenuItem);

		this._mainMenuBar = new JMenuBar();
		this._mainMenuBar.add(this._mainMenu);
		this.setJMenuBar(this._mainMenuBar);

		this.newImage(640, 480);
	}

	public void newImage(int width, int height) {
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		this._pane.setPreferredSize(new Dimension(image.getWidth(), image
				.getHeight()));
		this._pane.revalidate();
		this._pane.setImage(image);
		this._pane.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object sender = e.getSource();
		if (sender == this._newImageMenuItem) {
			String input = JOptionPane.showInputDialog(this,
					"Please enter image size (\"width;height\")", "",
					JOptionPane.QUESTION_MESSAGE);
			if (input != null) {
				Matcher m = Pattern.compile("(\\d+);(\\d+)").matcher(input);
				if (m.find()) {
					int width = Integer.parseInt(m.group(1));
					int height = Integer.parseInt(m.group(2));
					this.newImage(width, height);
				}
			}
		} else if (sender == this._drawLineMenuItem) {
			String input = JOptionPane.showInputDialog(this,
					"Please enter polygon vertices (\"x1;y1;x2;y2;...\")",
					"100;50;150;150;50;170");
					//"50;50;80;100;100;130;60;270");
			
			if (input != null) {
				Matcher m = Pattern.compile("(^|;)(\\d+);(\\d+)")
						.matcher(input);
				ArrayList<Point> vertices = new ArrayList<Point>();
				while (m.find()) {
					Point vertex = new Point();
					vertex.x = Integer.parseInt(m.group(2));
					vertex.y = Integer.parseInt(m.group(3));
					System.out.println(vertex);
					vertices.add(vertex);
				}
				PatternFiller.fillPattern(this._pane.getImage(), vertices);
				this._pane.repaint();
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				DrawingFrame frame = new DrawingFrame();
				frame.setVisible(true);
			}
		});
	}

}
