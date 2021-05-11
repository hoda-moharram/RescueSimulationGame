package view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class OverlayPanel extends JPanel {

	private JPanel buttonsPanel;
	private JScrollPane scroll;
	
	public OverlayPanel() {
		this.setSize(new Dimension(300,130));
		this.setVisible(false);
		constructButtonsPanel();
		this.validate();
		this.repaint();
	}
	
	private void constructButtonsPanel() {
		
		this.buttonsPanel = new JPanel();
		this.buttonsPanel.setLayout(new FlowLayout());
		scroll = new JScrollPane(buttonsPanel);
		scroll.setSize(new Dimension(300,130));
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(scroll);
	}
	
	public void addSubButtons(ArrayList<MapButton> arrayList) {
		for(MapButton b: arrayList) {
			buttonsPanel.add(b);
		}
	}

	public boolean isOptimizedDrawingEnabled() {
		return false;
	}
	
	public JPanel getButtonsPanel() {
		return this.buttonsPanel;
	}
}