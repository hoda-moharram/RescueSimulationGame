package view;

import java.util.ArrayList;

import javax.swing.JButton;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.units.Evacuator;
import model.units.Unit;
import simulation.Address;
import simulation.Simulatable;

@SuppressWarnings("serial")
public class MapButton extends JButton{

	private Address address;
	private ArrayList<Simulatable> simultables;
	private Simulatable sim;
	private CompoundImage compoundIcon;
	
	public MapButton(Address address) {
		this.address = address;
		this.simultables = new ArrayList<Simulatable>();
		this.compoundIcon = new CompoundImage();
		this.add(compoundIcon);
		this.sim = null;
	}
	
	public MapButton(Simulatable sim) {
		this.sim = sim;
	}
	
	public Address getAddress() {
		return this.address;
	}
	
	public void setAddress(Address address) {
		this.address = address;
	}
	
	public ArrayList<Simulatable> getSimulatables() {
		return this.simultables;
	}
	
	public Simulatable getSimulatable() {
		return this.sim;
	}
	
	public CompoundImage getCompoundIcon() {
		return this.compoundIcon;
	}
	
	public boolean containsAttribute(Simulatable sim) {
		return this.sim == sim;
	}
	
	public String toString() {
		if(this.sim!=null) {
			return this.simToString(this.sim);
		}
		else {
			String output = "Info:\nAddress: " + this.addressToString(this.address) + "\n";
			for(Simulatable sim : this.simultables) {
				output += this.simToString(sim);
			}
			return output;
		}
	}
	
	private String addressToString(Address address) {
		String s = "(" + address.getX() + ", " + address.getY() +")";
		return s;
	}
	
	private String simToString(Simulatable sim) {
		String output = "";
		if(sim instanceof ResidentialBuilding) {
			ResidentialBuilding b = (ResidentialBuilding) sim;
			output += this.handleBuilding(b);
		}
		else {
	 		if(sim instanceof Citizen) {
				Citizen c = (Citizen) sim;
				output += this.handleCitizen(c);
			}
	 		else {
	 			if(sim instanceof Unit) {
	 				Unit u = (Unit) sim;
	 				output += this.handleUnit(u);
	 			}
	 		}
		}
 		return output;
	}
	
	protected String handleBuilding(ResidentialBuilding b) {
		String o = "\nResidential Building:\n" +
		"Building's location: " + addressToString(b.getLocation()) +
		"\nStructural Integrity: " + b.getStructuralIntegrity() +
		"\nFire Damage: " + b.getFireDamage() +
		"\nGas Level: " + b.getGasLevel()  +
		"\nFoundation Damage: " + b.getFoundationDamage() +
		"\nDisaster: " + ((b.getDisaster() == null) ? "no disasters" : b.getDisaster().getClass().getSimpleName()) +
		"\nNumber of Occupants: " + b.getOccupants().size() +
		"\nOccupants Info:" + ((b.getOccupants().size() == 0) ? "\n\nNo occupants to display.": "")
		+"\n";
		
		return o;
	}
	
	
	protected String handleCitizen(Citizen c) {
		String o = "\nCitizen:\n" +
		"Name: " + c.getName() +
		"\nNational ID: " + c.getNationalID() +
		"\nAge: " + c.getAge()  +
		"\nState: " + c.getState() +
		"\nCitizen's Location: " + this.addressToString(c.getLocation()) +
		"\nDisaster: " + ((c.getDisaster() == null) ? "no disasters" : c.getDisaster().getClass().getSimpleName()) +
		"\nHeath Points: " + c.getHp() +
		"\nBlood Loss: " + c.getBloodLoss() +
		"\nToxicity: " + c.getToxicity()
		+"\n";
		
		return o;
	}
	
	protected String handleUnit(Unit u) {
		String o = "\nUnit Info:\n" +
				((u == null)? "Unit not available." : (
		"\nUnit type: " + u.getClass().getSimpleName() +
		"\nUnit ID: " + u.getUnitID() +
		"\nUnit State: " + u.getState() +
		"\nUnit Location: " + addressToString(u.getLocation()) +
		"\nSteps per cycle: " + u.getStepsPerCycle() +
		"\nTarget info: " + ((u.getTarget() == null)? 
				"The selected unit does not have a target." : this.simToString((Simulatable) u.getTarget())) +
		((u instanceof Evacuator)?
			("\nNumber of Passengers: " + ((Evacuator) u).getPassengers().size() +
			"\nPassengers Info: " +	
			( (((Evacuator) u).getPassengers().size()==0)? "\n\nNo passengers to display."
					: handlePassengers( ((Evacuator) u).getPassengers()	)))
			: "")))
		+"\n";
		
		return o;
	}
	
	private String handlePassengers(ArrayList<Citizen> passengers) {
		String o = "";
		for(Citizen p : passengers) {
			o+= handleCitizen(p);
		}
		return o;
	}
}