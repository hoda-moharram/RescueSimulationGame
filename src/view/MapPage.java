package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.OverlayLayout;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.text.DefaultCaret;

@SuppressWarnings("serial")
public class MapPage extends JFrame {

	private HeaderGrid headerGrid;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel infoPanel;
	private JPanel disasterPanel;
	private JTextArea infoText;
	private JTextArea disasterText;
	private JPanel rescuePanel;
	private OverlayPanel overlayPanel;
	private JPanel unitsPanel;
	
	public MapPage() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Map");
		this.setSize(1400, 800);
		this.setLocationRelativeTo(null);
		
		constructHeaderGrid();
		constructLeftPanel();
		constructOverlayPanel();
		constructRescuePanel();
		constructRightPanel();

		this.validate();
	}
	
	public  void constructOverlayPanel() {
		this.overlayPanel = new OverlayPanel();
		OverlayLayout l = new OverlayLayout(this.overlayPanel);
		this.overlayPanel.setLayout(l);
		this.add(overlayPanel);
	}

	private void constructHeaderGrid() {
		headerGrid = new HeaderGrid();
		headerGrid.setOpaque(false);
		this.add(headerGrid, BorderLayout.BEFORE_FIRST_LINE);
	}
	
	private void constructLeftPanel() {
		this.leftPanel = new JPanel(new GridLayout(2,1));
		constructInfoPanel();
		constructInfoText();
		constructDisasterPanel();
		constructDisasterText();
		this.leftPanel.add(this.infoPanel);
		this.leftPanel.add(this.disasterPanel);
		this.add(leftPanel, BorderLayout.WEST);
	}
	
	private void constructRightPanel() {
		this.rightPanel = new JPanel(new GridLayout(0,1));
		constructUnitsPanel();
		this.rightPanel.add(this.unitsPanel);
		this.add(rightPanel, BorderLayout.EAST);
	}
	
	private void constructInfoPanel() {
		infoPanel = new JPanel();
		infoPanel.setSize(300,350);
		infoPanel.setPreferredSize(new Dimension(300,350));
	}
	
	private void constructInfoText() {
		infoText = new JTextArea();
		infoText.setSize(250,350);
		infoText.setText("click on any item on the grid to view its info.");
		infoText.setEditable(false);
		infoText.setLineWrap(true);
		infoText.setWrapStyleWord(true);
		//https://stackoverflow.com/questions/4019981/auto-end-line-in-jtextarea/4019995
		
		infoText.setFont(new Font(Font.MONOSPACED, 0, 12));
		DefaultCaret caret = (DefaultCaret)infoText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		//https://stackoverflow.com/questions/3972337/java-swing-jtextarea-in-a-jscrollpane-how-to-prevent-auto-scroll
		
		Border outsideBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true);
		Border insideBorder = BorderFactory.createEmptyBorder(20, 10, 20, 10);
		
		JScrollPane scroll = new JScrollPane(infoText);
		scroll.setPreferredSize(new Dimension(250,350));
		//https://stackoverflow.com/questions/1052473/scrollbars-in-jtextarea
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
		infoPanel.add(scroll);
	}
	
	private void constructDisasterPanel() {
		disasterPanel = new JPanel();
		disasterPanel.setSize(300,400);
		disasterPanel.setPreferredSize(new Dimension(300,400));
	}
	
	private void constructDisasterText() {
		disasterText = new JTextArea();
		disasterText.setSize(250,350);
		disasterText.setText("disasters that have striked will appear here");
		disasterText.setEditable(false);
		disasterText.setLineWrap(true);
		disasterText.setWrapStyleWord(true);
		disasterText.setFont(new Font(Font.MONOSPACED, 0, 12));
		DefaultCaret caret = (DefaultCaret)disasterText.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		
		Border outsideBorder = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true);
		Border insideBorder = BorderFactory.createEmptyBorder(20, 10, 20, 10);
		
		JScrollPane scroll = new JScrollPane(disasterText);
		scroll.setPreferredSize(new Dimension(250,350));
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
		disasterPanel.add(scroll);
	}
	
	private void constructRescuePanel() {
		rescuePanel = new JPanel();
		rescuePanel.setPreferredSize(new Dimension(800,800));
		rescuePanel.setLayout(new GridLayout(10,10));
		//rescuePanel.setVisible(true);
		this.add(rescuePanel, BorderLayout.CENTER);
	}
	
	private void constructUnitsPanel() {
		unitsPanel = new JPanel();
		unitsPanel.setPreferredSize(new Dimension(300,800));
		unitsPanel.setVisible(true);
	}
	
	public OverlayPanel getOverlayPanel() {
		return this.overlayPanel;
	}

	public HeaderGrid getHeaderGrid() {
		return this.headerGrid;
	}
	
	public JPanel getInfoPanel() {
		return this.infoPanel;
	}
	
	public JTextArea getInfoText() {
		return this.infoText;
	}
	
	public JPanel getRescuePanel() {
		return this.rescuePanel;
	}
	
	public JPanel getUnitsPanel() {
		return this.unitsPanel;
	}
	
	public JPanel getDisasterPanel() {
		return this.disasterPanel;
	}
	
	public JTextArea getDisasterText() {
		return this.disasterText;
	}
}