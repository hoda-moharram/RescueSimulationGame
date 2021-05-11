package view;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CompoundImage extends JPanel {
	
	private JLabel building;
	private JLabel citizen;
	private JLabel ambulance;
	private JLabel diseaseControl;
	private JLabel fireTruck;
	private JLabel evacuator;
	private JLabel gasControl;
	private ArrayList<JLabel> labels;
	private ArrayList<String> labelSimpleNames;

	public CompoundImage() {
		this.setLayout(new GridLayout(3,3));
		this.setOpaque(false);
		this.setVisible(true);
		
		this.labels = new ArrayList<JLabel>();
		this.labelSimpleNames = new ArrayList<String>();
		initializeLabels();
		initializeLabelSimpleNames();
		setLabelSizes();
		addLabels();
		makeAllInvisible();
	}
	
	private void initializeLabels() {
		building = new JLabel();
		labels.add(this.building);
		
		citizen = new JLabel();
		labels.add(this.citizen);
		
		ambulance = new JLabel();
		labels.add(this.ambulance);
		
		diseaseControl = new JLabel();
		labels.add(this.diseaseControl);
		
		evacuator = new JLabel();
		labels.add(this.evacuator);
		
		fireTruck = new JLabel();
		labels.add(this.fireTruck);
		
		gasControl = new JLabel();
		labels.add(this.gasControl);	
	}
	
	private void initializeLabelSimpleNames() {
		this.labelSimpleNames.add("ResidentialBuilding");
		this.labelSimpleNames.add("Citizen");
		this.labelSimpleNames.add("Ambulance");
		this.labelSimpleNames.add("DiseaseControlUnit");
		this.labelSimpleNames.add("Evacuator");
		this.labelSimpleNames.add("FireTruck");
		this.labelSimpleNames.add("GasControlUnit");
	}
	
	private void setLabelSizes() {
		for(JLabel label : this.labels) {
			label.setSize(25,25);
		}
	}
	
	private void addLabels() {
		for(JLabel label : this.labels) {
			this.add(label);
		}
	}
	
	public void addDefaultIcons(ArrayList<BufferedImage> imgs) {
		int i=0;
		for(JLabel label : this.labels) {
			label.setIcon(new ImageIcon(imgs.get(i).getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
			i++;
		}
	}
	
	public JLabel getLabelByName(String name) {
		int i = this.labelSimpleNames.indexOf(name);
		return this.labels.get(i);
	}
	
	public JLabel getBuilding() {
		return this.building;
	}
	
	public JLabel getCitizen() {
		return this.citizen;
	}
	
	public JLabel getAmbulance() {
		return this.ambulance;
	}
	
	public JLabel getDiseaseControl() {
		return this.diseaseControl;
	}
	
	public JLabel getFireTruck() {
		return this.fireTruck;
	}
	
	public JLabel getEvacuator() {
		return this.evacuator;
	}
	
	public JLabel getGasControl() {
		return this.gasControl;
	}
	
	public void makeAllInvisible() {
		for(JLabel label : this.labels) {
			label.setVisible(false);
		}
	}
}