package listing12;

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
		setTitle("Polygons");
		setSize(800, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_pane = new DrawingPane();
		_scrollablePane = new JScrollPane(_pane);
		_scrollablePane.setPreferredSize(new Dimension(700, 500));

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(_scrollablePane, BorderLayout.CENTER);

		_newImageMenuItem = new JMenuItem();
		_newImageMenuItem.setText("New Image");
		_newImageMenuItem.addActionListener(this);

		_drawLineMenuItem = new JMenuItem();
		_drawLineMenuItem.setText("Draw polygon");
		_drawLineMenuItem.addActionListener(this);

		_mainMenu = new JMenu();
		_mainMenu.setText("Actions");
		_mainMenu.add(_newImageMenuItem);
		_mainMenu.add(_drawLineMenuItem);

		_mainMenuBar = new JMenuBar();
		_mainMenuBar.add(_mainMenu);
		setJMenuBar(_mainMenuBar);

		newImage(640, 480);
	}

	public void newImage(int width, int height) {
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		_pane.setPreferredSize(new Dimension(image.getWidth(), image
				.getHeight()));
		_pane.revalidate();
		_pane.setImage(image);
		_pane.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object sender = e.getSource();
		if (sender == _newImageMenuItem) {
			String input = JOptionPane.showInputDialog(this,
					"Please enter image size (\"width;height\")", "",
					JOptionPane.QUESTION_MESSAGE);
			if (input != null) {
				Matcher m = Pattern.compile("(\\d+);(\\d+)").matcher(input);
				if (m.find()) {
					int width = Integer.parseInt(m.group(1));
					int height = Integer.parseInt(m.group(2));
					newImage(width, height);
				}
			}
		} else if (sender == _drawLineMenuItem) {
			String input = JOptionPane.showInputDialog(this,
					"Please enter polygon vertices (\"x1;y1;x2;y2;...\")",
					"100;50;150;150;50;170");
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
				ScanLine.fillPolygon(_pane.getImage(), Color.GREEN
						.getRGB(), vertices);
				_pane.repaint();
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
