package twistThe8;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class StartFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	StartFrame window = this;
	
	public StartFrame(){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch(Exception e){};
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setTitle("Vri Åtter");
		setLayout(new GridLayout(0,1));
		
		JLabel titleLabel = new JLabel("Velkommen til Vri Åtter!", JLabel.CENTER);
		titleLabel.setPreferredSize(new Dimension(200, 40));
		titleLabel.setFont(this.getContentPane().getFont().deriveFont(Font.BOLD, 16f));
		add(titleLabel);
		
		JButton startLocalButton = new JButton("Nytt lokalt spill");
		startLocalButton.setFont(this.getContentPane().getFont().deriveFont(16f));
		startLocalButton.setAlignmentX(CENTER_ALIGNMENT);
		startLocalButton.addActionListener(this);
		add(startLocalButton);
		
		JButton startOnlineButton = new JButton("Nytt nettspill");
		startOnlineButton.setFont(this.getContentPane().getFont().deriveFont(16f));
		startOnlineButton.setAlignmentX(CENTER_ALIGNMENT);
		startOnlineButton.addActionListener(this);
//		add(startOnlineButton);
		
		JButton loadButton = new JButton("Last inn spill");
		loadButton.setFont(this.getContentPane().getFont().deriveFont(16f));
		loadButton.setAlignmentX(CENTER_ALIGNMENT);
		loadButton.addActionListener(this);
		add(loadButton);
		
		JButton quitButton = new JButton("Avslutt");
		quitButton.setFont(this.getContentPane().getFont().deriveFont(16f));
		quitButton.setAlignmentX(CENTER_ALIGNMENT);
		quitButton.addActionListener(this);
		add(quitButton);
		
		pack();
		centerUI();
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand() == "Nytt lokalt spill"){
			AddPlayersFrame addPlayers = new AddPlayersFrame();
			addPlayers.setVisible(true);
		} else if(e.getActionCommand() == "Nytt nettspill"){
			//TODO add web functionality
		} else if(e.getActionCommand() == "Last inn spill"){
			try {
				loadGame();
			} catch (XMLStreamException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, "Det oppstod en feil under innlastinga.", "Innlasting feilet", JOptionPane.ERROR_MESSAGE);
			}
			
		} else System.exit(0);
	}
	
	public void centerUI(){
		Dimension windowSize = this.getSize();
		Dimension screenSize = getToolkit().getScreenSize();
		setLocation((int)((screenSize.getWidth() - windowSize.getWidth()) / 2), 100);
	}
	
	public void loadGame() throws XMLStreamException{
		JFileChooser fileChooser = new JFileChooser(){
			private static final long serialVersionUID = 1L;
			public void approveSelection(){
				if(!getSelectedFile().getName().contains("tt8") && getDialogType() == OPEN_DIALOG)
					JOptionPane.showMessageDialog(this, "Vri Åtter støtter kun filer av typen \"tt8\".", "Filtype ikke støttet", JOptionPane.INFORMATION_MESSAGE);
				else
					super.approveSelection();
			}
		};
		if(JFileChooser.APPROVE_OPTION != fileChooser.showOpenDialog(this)) return;
		
		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		XMLStreamReader reader;
		try {reader = inputFactory.createXMLStreamReader(new FileInputStream(fileChooser.getSelectedFile()));}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Filen kunne ikke innlastes", "Filinnlasting feilet", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		ArrayList<Player> players = new ArrayList<Player>();
		Deck freshDeck = new Deck(false);
		Deck playedDeck = new Deck(false);
		String currentPlayer = null;
		while(reader.hasNext()){
			if(reader.getEventType() == XMLStreamReader.START_ELEMENT){
				if(reader.getLocalName().equals("player"))
					players.add(readPlayer(reader));
				else if(reader.getLocalName().equals("freshDeck"))
					freshDeck = readFreshDeck(reader);
				else if(reader.getLocalName().equals("playedDeck"))
					playedDeck = readPlayedDeck(reader);
				else if(reader.getLocalName().equals("currentPlayer")){
					currentPlayer = reader.getAttributeValue(0);
				}
			}
			reader.next();
		}
		reader.close();
		GameFrame gameFrame = new GameFrame(players, freshDeck, playedDeck, currentPlayer);
		gameFrame.setVisible(true);
	}
	
	private Player readPlayer(XMLStreamReader reader) throws XMLStreamException{
		Player p = null;
		while(reader.hasNext()){
			if(reader.getEventType() == XMLStreamReader.START_ELEMENT){
				if(reader.getLocalName().equals("player")) p = new Player(reader.getAttributeValue(0));
				else if(reader.getLocalName().equals("card")) p.addCard(readCard(reader));
			} else if(reader.getEventType() == XMLStreamReader.END_ELEMENT)
				break;
			
			reader.next();
		}
		return p;
	}
	
	private Deck readFreshDeck(XMLStreamReader reader) throws XMLStreamException{
		Deck d = new Deck(false);
		while(true){
			if(reader.getEventType() == XMLStreamReader.START_ELEMENT){
				if(reader.getLocalName().equals("card"))
					d.addCard(readCard(reader));
			} else if(reader.getEventType() == XMLStreamReader.END_ELEMENT)
				if(reader.getLocalName().equals("freshDeck"))
					return d;
			
			reader.next();
		}
	}
	
	private Deck readPlayedDeck(XMLStreamReader reader) throws XMLStreamException{
		Deck d = new Deck(false);
		while(true){
			if(reader.getEventType() == XMLStreamReader.START_ELEMENT){
				if(reader.getLocalName().equals("card"))
					d.addCard(readCard(reader));
			} else if(reader.getEventType() == XMLStreamReader.END_ELEMENT)
				if(reader.getLocalName().equals("playedDeck"))
					return d;
			
			reader.next();
		}
	}
	
	private Card readCard(XMLStreamReader reader) throws XMLStreamException{
		int suit = 0;
		int face = 0;
		while(true){
			if(reader.getEventType() == XMLStreamReader.START_ELEMENT){
				suit = Integer.parseInt(reader.getAttributeValue(0));
				face = Integer.parseInt(reader.getAttributeValue(1));
			} else if(reader.getEventType() == XMLStreamReader.END_ELEMENT)
				if(reader.getLocalName().equals("card")){
					return new Card(suit, face);
				}
			
			reader.next();
		}
	}
}