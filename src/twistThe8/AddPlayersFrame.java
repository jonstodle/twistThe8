package twistThe8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class AddPlayersFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Player> players = new ArrayList<Player>();
	
	public AddPlayersFrame(){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch(Exception e){};
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Vri Åtter - Legg til spillere");
		setLayout(new BorderLayout(0, 20));
		
		JPanel gridPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		gridPanel.add(new AddPlayerPanel(this));
		gridPanel.add(new AddPlayerPanel(this));
		gridPanel.add(new AddPlayerPanel(this));
		gridPanel.add(new AddPlayerPanel(this));
		add(gridPanel, BorderLayout.NORTH);
		
		JButton startGameButton = new JButton("Start spill");
		startGameButton.setFont(this.getContentPane().getFont().deriveFont(16f));
		startGameButton.setPreferredSize(new Dimension(400, 40));
		startGameButton.addActionListener(this);
		add(startGameButton, BorderLayout.CENTER);
		
		//TODO remove before release
		players.add(new Player("Jon"));
		players.add(new Player("Simen"));
		players.add(new Player("Ola"));
		players.add(new Player("Tor"));
		
		pack();
		centerUI();
	}

	public void actionPerformed(ActionEvent e) {
		if(players.size() < 2){
			JOptionPane.showMessageDialog(this, "Du må legge til minst to spillere");
			return;
		}
		
		GameFrame gameFrame = new GameFrame(players);
		gameFrame.setVisible(true);
		dispose();
	}
	
	public void centerUI(){
		Dimension windowSize = this.getSize();
		Dimension screenSize = getToolkit().getScreenSize();
		setLocation((int)((screenSize.getWidth() - windowSize.getWidth()) / 2), 100);
	}
}
