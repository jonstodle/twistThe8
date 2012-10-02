package guiIntroduction;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.sun.xml.internal.ws.api.config.management.policy.ManagementAssertion.Setting;

public class vri8Design extends JFrame {
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 800;
	private JLabel deck, card, topCard;
	
	public vri8Design(){
		
		deck = new JLabel("Kortstokk", SwingConstants.CENTER);
		card = new JLabel("Kort", SwingConstants.CENTER);
		topCard = new JLabel("Øverste kort", SwingConstants.CENTER);
		
		setTitle("Vri Åtter Design");
		
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(1, 2));
		
		
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args){
		vri8Design test = new vri8Design();
	}
}
