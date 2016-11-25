package listing13;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class DrawingPane extends JPanel 
{
	

	private static final long serialVersionUID = 1L;
	
	private BufferedImage _image = null;
	
	public BufferedImage getImage() {
		return _image;
	}

	public void setImage(BufferedImage img) {
		this._image = img;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponents(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (_image != null)
		{
			g.drawImage(this._image, 0, 0, null);
		}
	}
}
