package twistThe8;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class GameFrame extends JFrame {
	/**
	 * The frame containing the different parts of the main game interface
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Player> players;
	Deck freshDeck;
	Deck playedDeck;
	Card topCard;
	Player previousPlayer;
	Player currentPlayer;
	Iterator<Player> playerIterator;
	int cardsDrawn = 0;
	Boolean cardsVisible = false;
	JLabel messageLabel = new JLabel("Spillstatus", JLabel.CENTER);
	JLabel message2Label = new JLabel("", JLabel.CENTER); 
	
	/**
	 * Constructor
	 * @param addedPlayers	The players added in AddPlayersFrame.
	 */
	public GameFrame(ArrayList<Player> addedPlayers){
		setFrameProperties();
		players = addedPlayers;
		freshDeck = new Deck(true);
		playedDeck = new Deck(false);
		dealCards();
		playerIterator = players.iterator();
		playCard(null);
		playedDeck.addCard(freshDeck.takeCard());
		topCard = playedDeck.topCard();
		
		messageLabel.setFont(getContentPane().getFont().deriveFont(14f));
		messageLabel.setText(String.format("Det er %s som starter", currentPlayer));
		buildUI();
	}
	
	public GameFrame(ArrayList<Player> loadedPlayers, Deck loadedFreshdeck, Deck loadedPlayedDeck, String loadedCurrentPlayer){
		setFrameProperties();
		players = loadedPlayers;
		freshDeck = loadedFreshdeck;
		playedDeck = loadedPlayedDeck;
		playerIterator = players.iterator();
		while(playerIterator.hasNext()){
			Player p = playerIterator.next();
			if(p.getName().equals(loadedCurrentPlayer)){
				currentPlayer = p;
				break;
			}
		}
		sortCards(currentPlayer);
		if(!playerIterator.hasNext()) playerIterator = players.iterator();
		playedDeck.addCard(freshDeck.takeCard());
		topCard = playedDeck.topCard();
		setTitle(String.format("Vri Åtter - %s sin tur", currentPlayer));
		messageLabel.setFont(getContentPane().getFont().deriveFont(14f));
		message2Label.setFont(getContentPane().getFont().deriveFont(12f));
		messageLabel.setText(String.format("Det er %s som fortsetter", currentPlayer));
		buildUI();
	}
	
	private void setFrameProperties(){
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());} catch(Exception e){};
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setLocation(300, 100);
		setTitle("Vri Åtter");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				int chosenAction = JOptionPane.showConfirmDialog(GameFrame.this, "Vil du lagre spillet før du avslutter?", "Lagre spill?", JOptionPane.YES_NO_CANCEL_OPTION);
				if(chosenAction == JOptionPane.YES_OPTION ){
					try {
						if(saveGame()) dispose();
					} catch (XMLStreamException e1) {
						e1.printStackTrace();
						if(JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(GameFrame.this, "En feil oppstod under lagringen. Vil du fortsatt avslutte?", "Feil under lagring", JOptionPane.YES_NO_OPTION))
							dispose();
					}
				} else if(chosenAction == JOptionPane.NO_OPTION){
					dispose();
				}
			}
		});
	}
	
	/**
	 * Constructs the UI
	 */
	public void buildUI(){
		JPanel contentPanel = new JPanel(new BorderLayout(0, 30));
		JPanel labelPanel = new JPanel(new BorderLayout());
		labelPanel.add(messageLabel, BorderLayout.NORTH);
		labelPanel.add(message2Label, BorderLayout.SOUTH);
		contentPanel.add(labelPanel, BorderLayout.NORTH);
		contentPanel.add(new GamePanel(this), BorderLayout.CENTER);
		contentPanel.add(new PlayerPanel(this), BorderLayout.SOUTH);
		setContentPane(contentPanel);
		pack();
		centerUI();
	}
	
	/**
	 * Centers the window horizontally
	 */
	public void centerUI(){
		Dimension windowSize = this.getSize();
		Dimension screenSize = getToolkit().getScreenSize();
		setLocation((int)((screenSize.getWidth() - windowSize.getWidth()) / 2), 100);
	}
	
	/**
	 * Deals cards to the players in players
	 */
	public void dealCards(){
		freshDeck.shuffleCards();
		for(int i = 0; i < 5; i++){
			Iterator<Player> playerIt = players.iterator();
			while(playerIt.hasNext()){
				playerIt.next().addCard(freshDeck.takeCard());
			}
		}
		playedDeck.addCard(freshDeck.takeCard());
		if(playedDeck.topCard().getFace() == 8) playedDeck.addCard(freshDeck.takeCard());
		topCard = playedDeck.topCard();
	}
	
	/**
	 * Plays the card supplied, if the player's hand is empty it's appointed the winner.
	 * Checks whether the supplied card has the face value of 8, if so, it asks which suit to change to
	 * @param cardPlayed	The card played by the player
	 */
	public void playCard(Card cardPlayed){
		if(cardPlayed != null){
			if(currentPlayer.getHand().isEmpty()){
				JOptionPane.showMessageDialog(this, String.format("Gratulerer %s, du har vunnet Vri Åtter", currentPlayer));
				dispose();
				return;
			}
			
			String message;
			if(cardPlayed.getFace() == 8){
				String[] suits = {"Hjerter", "Kløver", "Ruter", "Spar"};
				int selectedSuit = -1;
				while(selectedSuit < 0) selectedSuit = JOptionPane.showOptionDialog(this, "Hvilken farge vil du vri til?", "Vri sort", 0, JOptionPane.QUESTION_MESSAGE,null, suits, JOptionPane.CANCEL_OPTION);
				message = String.format("%s vridde til %s", currentPlayer, suits[selectedSuit]);
				playedDeck.addCard(cardPlayed);
				topCard = new Card(selectedSuit, 8);
			} else {
				message = String.format("%s spilte %s", currentPlayer, cardPlayed);
				playedDeck.addCard(cardPlayed);
				topCard = playedDeck.topCard();
			}
			
			messageLabel.setText(message);
			message2Label.setText(hasOneCardLeft().length() == 0 ? "" : String.format("%s har bare ett kort igjen", hasOneCardLeft()));
		}
		
		cardsDrawn = 0;
		cardsVisible = false;
		currentPlayer = playerIterator.next();
		sortCards(currentPlayer);
		if(!playerIterator.hasNext()) playerIterator = players.iterator();
		setTitle(String.format("Vri Åtter - %s sin tur", currentPlayer));
		buildUI();
	}
	
	/**
	 * Checks whether the supplied card is playable against topCard
	 * @param cardPlayed	The card supplied for comparison
	 * @return				A boolean to tell whether the supplied card is playable
	 */
	public Boolean isCardPlayable(Card cardPlayed){
		if(cardPlayed.getFace() == 8) return true;
		else if(cardPlayed.getFace() == topCard.getFace()) return true;
		else if(cardPlayed.getSuit() == topCard.getSuit()) return true;
		else return false;
	}
	
	public String hasOneCardLeft(){
		String names = "";
		Iterator<Player> it = players.iterator();
		while(it.hasNext()){
			Player p = it.next();
			if(p.getHand().size() == 1){
				names += p;
				break;
			}
		}
		while(it.hasNext()){
			Player p = it.next();
			if(p.getHand().size() == 1) names +=", " + p;
		}
		return names;
	}
	
	public void sortCards(Player p){
		Comparator<Card> c = new Comparator<Card>() {
			public int compare(Card c1, Card c2){
				int comparison = c1.getSuit().compareTo(c2.getSuit());
				if(comparison == 0) comparison = Integer.compare(c1.getFace(), c2.getFace());
				return comparison;
			}
		};
		
		Collections.sort(p.getHand(), c);
	}
	
	public Boolean saveGame() throws XMLStreamException{
		JFileChooser fileChooser = new JFileChooser(){
			private static final long serialVersionUID = 1L;
			public void approveSelection(){
				if(getSelectedFile().exists() && getDialogType() == SAVE_DIALOG){
					if(JOptionPane.NO_OPTION == JOptionPane.showConfirmDialog(this, "Filen finnes allerede. Overskrive?", "Overskrive?", JOptionPane.YES_NO_OPTION)) return;
				}
				super.approveSelection();
			}
		};
		fileChooser.setSelectedFile(new File("save.tt8"));
				
		if(JFileChooser.APPROVE_OPTION != fileChooser.showSaveDialog(this)) return false;
		
		XMLOutputFactory outFactory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer;
		try {writer = outFactory.createXMLStreamWriter(new FileOutputStream(fileChooser.getSelectedFile()));}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Det er ikke en gyldig lagringslokasjon");
			return false;
		}
		
		writer.writeStartDocument();
		writer.writeCharacters("\n");
		writer.writeStartElement("saveFile");
		writer.writeCharacters("\n");
		
		insertTabs(writer, 1);
		writer.writeStartElement("players");
		writer.writeCharacters("\n");
		for(Iterator<Player> it = players.iterator(); it.hasNext();) createPlayerNode(writer, 2, it.next());
		insertTabs(writer, 1);
		writer.writeEndElement();
		writer.writeCharacters("\n");
		
		insertTabs(writer, 1);
		writer.writeStartElement("freshDeck");
		writer.writeCharacters("\n");
		for(Iterator<Card> it = freshDeck.getCardIterator(); it.hasNext();) createCardNode(writer, 2, it.next());
		insertTabs(writer, 1);
		writer.writeEndElement();
		writer.writeCharacters("\n");
		
		insertTabs(writer, 1);
		writer.writeStartElement("playedDeck");
		writer.writeCharacters("\n");
		for(Iterator<Card> it = playedDeck.getCardIterator(); it.hasNext();) createCardNode(writer, 2, it.next());
		insertTabs(writer, 1);
		writer.writeEndElement();
		writer.writeCharacters("\n");
		
		insertTabs(writer, 1);
		writer.writeStartElement("currentPlayer");
		writer.writeAttribute("name", currentPlayer.getName());
		writer.writeEndElement();
		writer.writeCharacters("\n");
		
		writer.writeEndElement();
		writer.writeEndDocument();
		
		writer.close();
		return true;
	}
	
	private void createPlayerNode(XMLStreamWriter writer, int tabs, Player player) throws XMLStreamException{
		insertTabs(writer, tabs);
		writer.writeStartElement("player");
		writer.writeAttribute("name", player.getName());
		writer.writeCharacters("\n");
		
		for(Iterator<Card> it = player.getHand().iterator(); it.hasNext();) createCardNode(writer, tabs + 1, it.next());
		
		insertTabs(writer, tabs);
		writer.writeEndElement();
		writer.writeCharacters("\n");
	}
	
	private void createCardNode(XMLStreamWriter writer, int tabs, Card card) throws XMLStreamException{
		insertTabs(writer, tabs);
		writer.writeStartElement("card");
		writer.writeAttribute("suit", Integer.toString(card.getSuitAsInt()));
		writer.writeAttribute("face", Integer.toString(card.getFace()));
		writer.writeEndElement();
		writer.writeCharacters("\n");
	}
	
	private void insertTabs(XMLStreamWriter writer, int tabs) throws XMLStreamException{
		for(int i = 0; i < tabs; i++) writer.writeCharacters("\t");
	}
}
