package twistThe8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddPlayerPanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AddPlayersFrame window;
	Player player;
	JPanel titleTextBoxPanel;
	JLabel titleLabel;
	JTextField textBox;
	JButton addButton;
	
	public AddPlayerPanel(AddPlayersFrame parentWindow){
		window = parentWindow;
		setPreferredSize(new Dimension(200, 75));
		setLayout(new BorderLayout());
		
		titleLabel = new JLabel("Legg til spiller", JLabel.CENTER);
		titleLabel.setFont(this.getFont().deriveFont(Font.BOLD, 16f));
		add(titleLabel, BorderLayout.NORTH);
		
		textBox = new JTextField();
		textBox.addActionListener(this);
		add(textBox, BorderLayout.CENTER);
		
		addButton = new JButton("Legg til");
		addButton.setFont(getFont().deriveFont(16f));
		addButton.addActionListener(this);
		add(addButton, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent arg0) {
		if(textBox.getText().length() < 1) return;
		for(Iterator<Player> it = window.players.iterator(); it.hasNext();)
			if(it.next().toString().equalsIgnoreCase(textBox.getText())){
				JOptionPane.showMessageDialog(window, "Det finnes allerede en spiller med dette navnet");
				textBox.setText("");
				return;
			}
		
		window.players.add(new Player(textBox.getText()));
		
		titleLabel.setText("Spiller lagt til");
		titleLabel.setFont(this.getFont().deriveFont(16f));
		
		remove(textBox);
		
		JLabel nameLabel = new JLabel(textBox.getText(), JLabel.CENTER);
		nameLabel.setFont(this.getFont().deriveFont(Font.BOLD, 20f));
		add(nameLabel, BorderLayout.CENTER);
		
		addButton.setEnabled(false);
	}
}
