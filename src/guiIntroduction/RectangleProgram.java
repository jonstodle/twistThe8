package guiIntroduction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RectangleProgram extends JFrame {
	
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	
	private JLabel lengthL, widthL, areaL, perimeterL;
	private JTextField lengthTF, widthTF, areaTF, perimeterTF;
	private JButton calculateB, exitB;
	
	private CalculateButtonHandler cbHandler;
	private ExitButtonHandler ebHandler;
	
	public RectangleProgram(){
		
		lengthL = new JLabel("Enter the length: ", SwingConstants.RIGHT);
		widthL = new JLabel("Enter the width: ", SwingConstants.RIGHT);
		areaL = new JLabel("Area: ", SwingConstants.RIGHT);
		perimeterL = new JLabel("Perimeter: ", SwingConstants.RIGHT);
		
		lengthTF = new JTextField(10);
		widthTF = new JTextField(10);
		areaTF = new JTextField(10);
		perimeterTF = new JTextField(10);
		
		calculateB = new JButton("Calculate");
		cbHandler = new CalculateButtonHandler();
		calculateB.addActionListener(cbHandler);
		exitB = new JButton("Exit");
		ebHandler = new ExitButtonHandler();
		exitB.addActionListener(ebHandler);
		
		setTitle("Sample Title: Area of a rectangle");
		
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(4, 2));
		
		pane.add(lengthL);
		pane.add(lengthTF);
		pane.add(widthL);
		pane.add(widthTF);
		pane.add(areaL);
		pane.add(areaTF);
		pane.add(calculateB);
		pane.add(exitB);
		
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
				
	}
	
	
	private class CalculateButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e){
			double width, length, area = 0;
			length = Double.parseDouble(lengthTF.getText());
			width = Double.parseDouble(widthTF.getText());
			areaTF.setText("" + area);
		}
	}
	private class ExitButtonHandler implements ActionListener {
		public void actionPerformed(ActionEvent e){
			System.exit(0);
		}
	}
	public static void main(String[] args){
		RectangleProgram rectObj = new RectangleProgram();
	}
}
