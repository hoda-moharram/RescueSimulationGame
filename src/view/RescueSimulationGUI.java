package view;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;

import controller.CommandCenter;
import exceptions.SimulationException;
import exceptions.UnitException;
import model.disasters.Disaster;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class RescueSimulationGUI implements ActionListener, SimulationListener {
	
	private StartPage startWindow;
	private MapPage mapWindow;
	private GameOverPage gameOver;
	private ArrayList<JButton> startButtons;
	private ArrayList<MapGridButton> mapButtons;
	private ArrayList<MapButton> mapSubButtons;
	private ArrayList<UnitButton> unitButtons;
	private ArrayList<JButton> headerButtons;
	private ArrayList<Disaster> disasters;
	private ArrayList<BufferedImage> imgs;
	private ArrayList<String> imgsByName;
	private JButton lastClicked;
	private JButton previousLastClicked;
	private CommandCenter command;
	private GUIListener listener;
	
	public RescueSimulationGUI() throws Exception {
		startButtons = new ArrayList<JButton>();
		mapButtons = new ArrayList<MapGridButton>();
		mapSubButtons = new ArrayList<MapButton>();
		unitButtons = new ArrayList<UnitButton>();
		headerButtons = new ArrayList<JButton>();
		this.disasters = new ArrayList<Disaster>();
		imgs = new ArrayList<BufferedImage>();
		imgsByName = new ArrayList<String>();
		loadImages();
		
		startWindow = new StartPage();
		startWindow.setIconImage(imgs.get(imgs.size()-1));
		setStartButtonsListeners();
		
		mapWindow = new MapPage();
		mapWindow.setIconImage(imgs.get(imgs.size()-1));
		this.command = new CommandCenter();
		this.command.setCommandListener(this);
		
		adjustMapHeader();
		addUnitButtonsToMap();
		addSubButtonsToMapButtons();
		addDefaultIconsToMap();
		
		startWindow.setVisible(true);
	}
	
	
	private void addSubButtonsToMapButtons() {
		for(MapGridButton gridButton : this.mapButtons) {
			for(Simulatable sim : gridButton.getSimulatables()) {
				MapButton b = new MapButton(sim);
				b.setPreferredSize(new Dimension(100,100));
				gridButton.getSubButtons().add(b);
				b.addActionListener(this);
				this.mapSubButtons.add(b);
			}
		}
	}
	
	private void adjustMapHeader() {
		JButton nextCycleBtn = mapWindow.getHeaderGrid().getNextCycleButton();
		nextCycleBtn.addActionListener(this);
		headerButtons.add(nextCycleBtn);
	}
	
	private void removeSubButtonsFromArrayList(MapGridButton b) {
		for(MapButton bSub : b.getSubButtons()) {
			if(this.mapSubButtons.contains(bSub)) {
				this.mapSubButtons.remove(bSub);
			}
		}
	}
	
	private void updateActionListeners(MapGridButton b) {
		for(MapButton bSub : b.getSubButtons()) {
			bSub.addActionListener(this);
			if(!this.mapButtons.contains(bSub))
				this.mapSubButtons.add(bSub);
		}
	}

	private void setStartButtonsListeners() {
		JButton b1 = this.startWindow.getStartButton();
		b1.addActionListener(this);
		this.startButtons.add(b1);
		
		b1 = this.startWindow.getExitButton();
		b1.addActionListener(this);
		this.startButtons.add(b1);
	}
	
	private void loadImages() throws IOException {
		try {
			File imgfile = new File("resources/apartment.png");
			        
			BufferedImage img = ImageIO.read(imgfile);
			imgs.add(img);
			imgsByName.add("ResidentialBuilding");
			
			imgfile = new File("resources/citizen.png");
			img = ImageIO.read(imgfile);
			imgs.add(img);
			imgsByName.add("Citizen");
			
			imgfile = new File("resources/ambulance.png");
			img = ImageIO.read(imgfile);
			imgs.add(img);
			imgsByName.add("Ambulance");
			
			imgfile = new File("resources/diseasecontrol.png");
			img = ImageIO.read(imgfile);
			imgs.add(img);
			imgsByName.add("DiseaseControlUnit");
			
			imgfile = new File("resources/policecar.png");
			img = ImageIO.read(imgfile);
			imgs.add(img);
			imgsByName.add("Evacuator");
			
			imgfile = new File("resources/firetruck.png");
			img = ImageIO.read(imgfile);
			imgs.add(img);
			imgsByName.add("FireTruck");
			
			imgfile = new File("resources/gascontrol.png");
			img = ImageIO.read(imgfile);
			imgs.add(img);
			imgsByName.add("GasControlUnit");
			
			imgfile = new File("resources/fire.png");
			img = ImageIO.read(imgfile);
			imgs.add(img);
			imgsByName.add("Fire");
			
			imgfile = new File("resources/gasleak.png");
			img = ImageIO.read(imgfile);
			imgs.add(img);
			imgsByName.add("GasLeak");
			
			imgfile = new File("resources/collapse.png");
			img = ImageIO.read(imgfile);
			imgs.add(img);
			imgsByName.add("Collapse");
			
			imgfile = new File("resources/injury.png");
			img = ImageIO.read(imgfile);
			imgs.add(img);
			imgsByName.add("Injury");
			
			imgfile = new File("resources/infection.png");
			img = ImageIO.read(imgfile);
			imgs.add(img);
			imgsByName.add("Infection");
			
			imgfile = new File("resources/demolished.png");
			img = ImageIO.read(imgfile);
			imgs.add(img);
			imgsByName.add("Demolished");
			
			imgfile = new File("resources/dead.png");
			img = ImageIO.read(imgfile);
			imgs.add(img);
			imgsByName.add("Dead");
			
			imgfile = new File("resources/village.png");
			img = ImageIO.read(imgfile);
			imgs.add(img);
			imgsByName.add("window icon");
		}
		catch(IOException e) {
			System.out.println("missing file");
		}
	}
	
	private void addDefaultIconsToMap() {
		for(MapGridButton mapGridButton : this.mapButtons) {
			mapGridButton.getCompoundIcon().addDefaultIcons(this.imgs);
		}
		
		for(MapGridButton mapGridButton : this.mapButtons) {
			for(Simulatable sim : mapGridButton.getSimulatables()) {
				mapGridButton.getCompoundIcon().getLabelByName(sim.getClass().getSimpleName()).setVisible(true);
			}
		}
	}
	
	private void updateIcons() {
		for(MapGridButton mapGridButton : this.mapButtons) {
			for(Simulatable sim : mapGridButton.getSimulatables()) {
				(mapGridButton.getCompoundIcon().getLabelByName(sim.getClass().getSimpleName())).setVisible(true);
				
				if(sim instanceof ResidentialBuilding) {
					ResidentialBuilding target = (ResidentialBuilding) sim;
					Disaster d = ((ResidentialBuilding) sim).getDisaster();
					if(d != null && d.isActive()==true && target.getStructuralIntegrity()!=0) {
						mapGridButton.getCompoundIcon().getBuilding().setIcon(new ImageIcon(this.imgs.get(imgsByName.indexOf(d.getClass().getSimpleName())).getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
					}
					else if(target.getStructuralIntegrity()==0) {
						mapGridButton.getCompoundIcon().getBuilding().setIcon(new ImageIcon(this.imgs.get(imgsByName.indexOf("Demolished")).getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
					}
					else if(d!=null && d.isActive()==false && target.getStructuralIntegrity()!=0 && target.getFoundationDamage() == 0) {
						mapGridButton.getCompoundIcon().getBuilding().setIcon(new ImageIcon(this.imgs.get(0).getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
					}
				}
					
				else if(sim instanceof Citizen) {
					Citizen target = (Citizen) sim;
					Disaster d = ((Citizen) sim).getDisaster();
					if(d != null && d.isActive()==true && !target.getState().equals(CitizenState.DECEASED)) {
						mapGridButton.getCompoundIcon().getCitizen().setIcon(new ImageIcon(this.imgs.get(imgsByName.indexOf(d.getClass().getSimpleName())).getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
					}
					else if(target.getState().equals(CitizenState.DECEASED)) {
						mapGridButton.getCompoundIcon().getCitizen().setIcon(new ImageIcon(this.imgs.get(imgsByName.indexOf("Dead")).getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
					}
					else if(d!=null && d.isActive()==false && !target.getState().equals(CitizenState.DECEASED)) {
						mapGridButton.getCompoundIcon().getCitizen().setIcon(new ImageIcon(this.imgs.get(1).getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
					}
				}
			}
		}
	}
	
	private void addSubButtonIcons(MapGridButton mapGridButton) {
		for(MapButton subButton : mapGridButton.getSubButtons()) {
			if(subButton.getSimulatable()!=null) {
				if(subButton.getSimulatable() instanceof ResidentialBuilding) {
					ResidentialBuilding target = (ResidentialBuilding) subButton.getSimulatable();
					Disaster d = target.getDisaster();
					if(d != null && d.isActive()==true && target.getStructuralIntegrity()!=0) {
						subButton.setIcon(new ImageIcon(this.imgs.get(imgsByName.indexOf(d.getClass().getSimpleName())).getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
					}	
					else if(target.getStructuralIntegrity()==0) {
						subButton.setIcon(new ImageIcon((this.imgs.get(imgsByName.indexOf("Demolished"))).getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
					}
					else {
						subButton.setIcon(new ImageIcon((this.imgs.get(0)).getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
					}
				}
				else {
					if(subButton.getSimulatable() instanceof Citizen) {
						Citizen target = (Citizen) subButton.getSimulatable();
						Disaster d = target.getDisaster();
						if(d != null && d.isActive()==true && !target.getState().equals(CitizenState.DECEASED)) {
							subButton.setIcon(new ImageIcon(this.imgs.get(imgsByName.indexOf(d.getClass().getSimpleName())).getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
						}
						else if(target.getState().equals(CitizenState.DECEASED)) {
							subButton.setIcon(new ImageIcon((this.imgs.get(imgsByName.indexOf("Dead"))).getScaledInstance(60, 60, Image.SCALE_SMOOTH)));
						}
						else {
							subButton.setIcon(new ImageIcon((this.imgs.get(1)).getScaledInstance(60, 60, Image.SCALE_SMOOTH))); 
						}
					}
				}
			}
		}
	}
	
	public void constructWorld(MapGridButton b) {
		mapWindow.getRescuePanel().add(b);
		b.addActionListener(this);
		this.mapButtons.add(b);
		this.mapWindow.getRescuePanel().add(b);
	}
	
	private void addUnitButtonsToMap() {
		for(UnitButton unitButton : this.unitButtons) {
			unitButton.addActionListener(this);
			addUnitIcon(unitButton, unitButton.getUnit());
			this.mapWindow.getUnitsPanel().add(unitButton);
			this.mapButtons.get(0).getSimulatables().add(unitButton.getUnit());
		}
	}
	
	private void addUnitIcon(UnitButton b, Unit u) {
		if(u instanceof Ambulance) {
			b.setIcon(new ImageIcon(this.imgs.get(2)));
			b.setText("Ambulance");
			return;
		}
		if(u instanceof DiseaseControlUnit) {
			b.setIcon(new ImageIcon(this.imgs.get(3)));
			b.setText("Disease Control Unit");
			return;
		}
		if(u instanceof Evacuator) {
			b.setIcon(new ImageIcon(this.imgs.get(4)));
			b.setText("Evacuator");
			return;
		}
		if(u instanceof FireTruck) {
			b.setIcon(new ImageIcon(this.imgs.get(5)));
			b.setText("Fire Truck");
			return;
		}
		if(u instanceof GasControlUnit) {
			b.setIcon(new ImageIcon(this.imgs.get(6)));
			b.setText("Gas Control Unit");
			return;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b1 = (JButton) e.getSource();
		if(this.startButtons.contains(b1)) {
			JButton clickedButton = this.startButtons.get(this.startButtons.indexOf(b1));
			if(clickedButton == this.startWindow.getStartButton()) {
				this.startWindow.setVisible(false);
				this.mapWindow.setVisible(true);
			}
			else if(clickedButton == this.startWindow.getExitButton()) {
				System.exit(0);
			}
		}
		else if(this.mapButtons.contains(b1)) {
			MapGridButton clickedButton = this.mapButtons.get(this.mapButtons.indexOf(b1));
			this.mapWindow.getInfoText().setText(clickedButton.toString());
			this.lastClicked = clickedButton;
			
			if(!clickedButton.getSubButtons().isEmpty()) {
				OverlayPanel overlay = this.mapWindow.getOverlayPanel();
				overlay.getButtonsPanel().removeAll();
				overlay.addSubButtons(clickedButton.getSubButtons());
				overlay.setLocation((int) clickedButton.getLocationOnScreen().getX(),(int) clickedButton.getLocationOnScreen().getY());
				addSubButtonIcons(clickedButton);
				overlay.setVisible(true);
			}
			else {
				if(this.mapWindow.getOverlayPanel().isVisible()) {
					this.mapWindow.getOverlayPanel().setVisible(false);
				}
			}
		}
		else if(this.mapSubButtons.contains(b1)) {
			MapButton clickedButton = this.mapSubButtons.get(this.mapSubButtons.indexOf(b1));
			this.mapWindow.getInfoText().setText(clickedButton.toString());
			if(this.lastClicked instanceof MapGridButton) {
				this.previousLastClicked = this.lastClicked;	//the only case this is updated
				this.lastClicked = clickedButton;
			}
		}
		else if(this.unitButtons.contains(b1)) {
			UnitButton clickedButton = this.unitButtons.get(this.unitButtons.indexOf(b1));
			this.mapWindow.getInfoText().setText(clickedButton.toString());
			if(this.mapSubButtons.contains(this.lastClicked))
				try {
					clickedButton.getUnit().respond( (Rescuable) (((MapButton) this.lastClicked).getSimulatable()) );
					this.mapWindow.getInfoText().setText(clickedButton.getUnitName() + 
							" successfully dispatched to target.\n" + clickedButton.toString());
					this.lastClicked = clickedButton;
					
				} catch (UnitException e1) {
					this.mapWindow.getInfoText().setText(e1.getMessage());
				}
			else {
				this.lastClicked = clickedButton;
			}
		}
		else if(this.headerButtons.contains(b1)) {
			JButton clickedButton = this.headerButtons.get(this.headerButtons.indexOf(b1));
			if(clickedButton == this.mapWindow.getHeaderGrid().getNextCycleButton()) {
				try {
					listener.nextCycle();
					this.listener.addExcutedDisastersToMap();
					this.updateDisasterLog();
					HeaderGrid mapHeader = this.mapWindow.getHeaderGrid();
					mapHeader.setCycleCount(mapHeader.getCycleCount() + 1);
					mapHeader.getCurrentCycleLabel().setText("Current Cycle: " + mapHeader.getCycleCount());
					mapHeader.getCasualtiesLabel().setText("Casualties Count: " + this.listener.calculateCasualties());
					
					for(UnitButton unitButton: this.unitButtons) {
						if(unitButton.getUnit() != null) {
							Unit unit = unitButton.getUnit();
							MapGridButton base = this.mapButtons.get(0);
							if(unit.getTarget()!=null && unit.getLocation() == unit.getTarget().getLocation() 
									|| unit.getLocation() == base.getAddress() && unitButton.getPreviousAddress()!=unit.getLocation()) {
								MapGridButton targetButton = this.getMapButtonByAddress(unit.getLocation());
								MapGridButton previousTargetButton = this.getMapButtonByAddress(unitButton.getPreviousAddress());
								if(!targetButton.getSimulatables().contains(unit) && previousTargetButton.getSimulatables().contains(unit)) {
									previousTargetButton.getSimulatables().remove(unit);
									previousTargetButton.getCompoundIcon().getLabelByName(unit.getClass().getSimpleName()).setVisible(false);
									targetButton.getSimulatables().add(unit);
									unitButton.setCurrentAddress(unit.getLocation());
									unitButton.setPreviousAddress(unit.getLocation());
								}
							}
							
							if (unit instanceof Evacuator && unit.getTarget()!=null && unit.getLocation() == base.getAddress()) {
								Evacuator evacuator = (Evacuator) unit;
								MapGridButton previousTargetButton = this.getMapButtonByAddress(unit.getTarget().getLocation());
								for(Citizen passenger : evacuator.getPassengers()) {
									if(previousTargetButton.getSimulatables().contains(passenger) && !base.getSimulatables().contains(passenger)) {
										previousTargetButton.getSimulatables().remove(passenger);
										base.getSimulatables().add(passenger);
									}
								}
								this.removeSubButtonsFromArrayList(previousTargetButton);
								previousTargetButton.updateSubButtons();
								this.updateActionListeners(previousTargetButton);
								
								this.removeSubButtonsFromArrayList(base);
								base.updateSubButtons();
								this.updateActionListeners(base);
								
								boolean hasCitizensLeft = false;
								for(MapButton bSub : previousTargetButton.getSubButtons()) {
									if(bSub.getSimulatable() instanceof Citizen) {
										hasCitizensLeft = true;
									}
								}
								if(!hasCitizensLeft) {
									previousTargetButton.getCompoundIcon().getCitizen().setVisible(false);
								}
							}
						}
					}
					
					updateIcons();
					this.mapWindow.repaint();
					
					if(this.lastClicked != null) {
						this.mapWindow.getOverlayPanel().setVisible(false);
						if(this.mapSubButtons.contains(lastClicked) && this.previousLastClicked != null && this.previousLastClicked instanceof MapGridButton) {
							this.mapWindow.getInfoText().setText(this.previousLastClicked.toString());
						}
						else {
							if(this.lastClicked instanceof MapGridButton && this.previousLastClicked == null) {
								this.mapWindow.getInfoText().setText(this.lastClicked.toString());
							}
							else {
								if(this.lastClicked instanceof UnitButton)
									this.mapWindow.getInfoText().setText(this.lastClicked.toString());
								else
									this.mapWindow.getInfoText().setText(this.lastClicked.toString());
							}
						}
							
					}
					if (this.listener.checkGameOver()) {
						constructGameOver();
					}
				} 
				catch (SimulationException e1) {
					this.mapWindow.getInfoText().setText(e1.getMessage());
				}
			}
		}
	}
	
	private void constructGameOver() {
		this.gameOver = new GameOverPage();
		this.mapWindow.setEnabled(false);
		this.gameOver.getScoreText().setText(this.gameOver.getScoreText().getText() + "" + this.listener.calculateCasualties());
		this.gameOver.setVisible(true);
	}
	
	public ArrayList<MapGridButton> getMapButtons() {
		return this.mapButtons;
	}
	
	public ArrayList<UnitButton> getUnitButtons() {
		return this.unitButtons;
	}
	
	public void setListener(GUIListener listener) {
		this.listener = listener;
	}
	
	public GUIListener getListener() {
		return this.listener;
	}
	
	public MapGridButton getMapButtonByAddress(Address location) {
		for(MapGridButton mapButton : this.mapButtons) {
			if(mapButton.getAddress() == location) {
				return mapButton;
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		new RescueSimulationGUI();
	}

	@Override
	public void addExcutedDisastersToMap(ArrayList<Disaster> executedDisasters) {
		this.disasters = executedDisasters;
	}
	
	private void updateDisasterLog() {
		this.mapWindow.getDisasterText().setText("");
		JTextArea disasterLog = this.mapWindow.getDisasterText();
		int i = this.disasters.size()-1;
		for( ; i>=0 ;i--) {
			Disaster disaster = this.disasters.get(i);
			if(disaster.getStartCycle() == this.mapWindow.getHeaderGrid().getCycleCount() && disaster.isActive() == true) {
				String o = this.handleDisaster(disaster);
				disasterLog.setText(disasterLog.getText() + o);
			}
			else if(disaster.getStartCycle() != this.mapWindow.getHeaderGrid().getCycleCount() && disaster.isActive() == true) {
				String o = this.handleDisaster(disaster);
				disasterLog.setText(disasterLog.getText() + o);
				if(disaster.getTarget() instanceof ResidentialBuilding && ((ResidentialBuilding) disaster.getTarget()).getStructuralIntegrity() == 0) {
					for(Citizen c : ((ResidentialBuilding) disaster.getTarget()).getOccupants()) {
						disasterLog.setText(disasterLog.getText() + this.mapButtons.get(0).handleCitizen(c));
					}
				}
			}
		}
	}
	
	private String handleDisaster(Disaster d) {
		String o = "\nDisaster Type: " + d.getClass().getSimpleName() +
		"\nStriked in cycle: " + d.getStartCycle() +
		"\nTarget info: " + 
		((d.getTarget() instanceof ResidentialBuilding)? this.mapButtons.get(0).handleBuilding((ResidentialBuilding) d.getTarget()) : 
			this.mapButtons.get(0).handleCitizen((Citizen) d.getTarget()))
		+"\n";
		
		return o;
	}
}