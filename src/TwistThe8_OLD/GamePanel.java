package TwistThe8_OLD;

import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Game window;
	GamePanel panel = this;
	
	public GamePanel(Game parentWindow){
		window = parentWindow;
		panel.setPreferredSize(new Dimension(600, 600));
		window.pack();
	}
}
