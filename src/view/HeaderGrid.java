package view;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class HeaderGrid extends JPanel {

	private JLabel currentCycleLabel;
	private JLabel casualtiesLabel;
	private JPanel nextCyclePanel;
	private JButton nextCycleButton;
	private int cycleCount;
	
	public HeaderGrid() {
		this.setLayout(new GridLayout(1,3,0,2));
		constructCasualtiesLabel();
		constructCurrentCycleLabel();
		constructNextCyclePanel();
		cycleCount = 0;
		this.add(casualtiesLabel);
		this.add(currentCycleLabel);
		this.add(nextCyclePanel);
		
		Border outsideBorder = BorderFactory.createMatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY);
		Border insideBorder = BorderFactory.createEmptyBorder(5, 20, 5, 20);
		this.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
	}
	
	private void constructCasualtiesLabel() {
		casualtiesLabel = new JLabel("Casualties Count: 0", JLabel.CENTER);
		casualtiesLabel.setBackground(Color.WHITE);
		casualtiesLabel.setAlignmentX(CENTER_ALIGNMENT);
	}
	
	private void constructCurrentCycleLabel() {
		currentCycleLabel = new JLabel("Current Cycle: " + cycleCount, JLabel.CENTER);
		currentCycleLabel.setBackground(Color.WHITE);
		currentCycleLabel.setAlignmentX(CENTER_ALIGNMENT);
	}
	
	private void constructNextCyclePanel() {
		nextCyclePanel = new JPanel();
		nextCycleButton = new JButton("Next Cycle");
		nextCyclePanel.add(nextCycleButton);
	}
	
	public JLabel getCasualtiesLabel() {
		return this.casualtiesLabel;
	}
	
	public JLabel getCurrentCycleLabel() {
		return this.currentCycleLabel;
	}
	
	public JPanel getNextCyclePanel() {
		return this.nextCyclePanel;
	}
	
	public int getCycleCount() {
		return this.cycleCount;
	}
	
	public void setCycleCount(int cycleCount) {
		this.cycleCount = cycleCount;
	}
	
	public JButton getNextCycleButton() {
		return this.nextCycleButton;
	}
}