package view;


import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StartPage extends JFrame{
	private JLabel startPanel;
	private JPanel startTextPanel;
	private JPanel buttonPanel;
	private JButton startButton;
	private JButton exitButton;
	
	public StartPage() {
		this.setTitle("Rescue Simulation");
		this.setSize(1400, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ImageIcon img = new ImageIcon("resources/start3.jpg");
		Image image = img.getImage().getScaledInstance(1400, 800, Image.SCALE_SMOOTH);
		//https://stackoverflow.com/questions/1064977/setting-background-images-in-jframe
		//https://docs.oracle.com/javase/7/docs/api/java/awt/Image.html
		ImageIcon appIcon = new ImageIcon("resources/apartments.png");
		this.setIconImage(appIcon.getImage());
		//https://stackoverflow.com/questions/1614772/how-to-change-jframe-icon
		
		this.setContentPane(new JLabel(new ImageIcon(image)));
		this.constructStartPanel();
		this.constructStartTextPanel();
		this.constructButtonPanel();
		
		this.setVisible(true); 
		this.validate();
	}
	
	private void constructStartPanel() {
		this.startPanel = new JLabel();
		this.startPanel.setSize(1400, 800);
		this.startPanel.setLayout(new GridLayout(2,1));
		this.startPanel.setOpaque(false);
		this.add(this.startPanel);
	}
	
	private void constructStartTextPanel() {
		this.startTextPanel= new JPanel();
		this.startTextPanel.setSize(1400, 400);
		this.startTextPanel.setOpaque(false);
		this.startPanel.add(startTextPanel);
	}
	
	private void constructButtonPanel() {
		this.buttonPanel = new JPanel(new GridLayout(1,2));
		JPanel leftPanel = new JPanel(new GridLayout(3,3));
		JPanel rightPanel = new JPanel(new GridLayout(3,3));
		this.buttonPanel.setSize(1400,400);
		this.buttonPanel.setOpaque(false);
		leftPanel.setOpaque(false);
		rightPanel.setOpaque(false);
		for(int i =0;i<3;i++) {
			for(int j =0;j<3;j++) {
				if(i==1 && j==1) {
					this.constuctStartButton();
					leftPanel.add(startButton);
				}
				else {
					JLabel temp = new JLabel();
					temp.setOpaque(false);
					leftPanel.add(temp);
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i==1 && j ==1) {
					this.constuctExitButton();
					rightPanel.add(exitButton);
				}
				else {
					JLabel temp = new JLabel();
					temp.setOpaque(false);
					rightPanel.add(temp);
				}
			}
		}
		
		this.buttonPanel.add(leftPanel);
		this.buttonPanel.add(rightPanel);
		this.startPanel.add(buttonPanel);
	}
	
	private void constuctStartButton() {
		this.startButton = new JButton();
		this.startButton.setOpaque(false);
		this.startButton.setContentAreaFilled(false);
		this.startButton.setBorderPainted(false);
	}
	
	private void constuctExitButton() {
		this.exitButton = new JButton();
		
		this.exitButton.setOpaque(false);
		this.exitButton.setContentAreaFilled(false);
		this.exitButton.setBorderPainted(false);
	}
	
	public JButton getStartButton() {
		return this.startButton;
	}
	
	public JButton getExitButton() {
		return this.exitButton;
	}
}
