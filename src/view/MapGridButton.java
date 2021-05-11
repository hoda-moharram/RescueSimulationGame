package view;

import java.awt.Dimension;
import java.util.ArrayList;

import model.units.Unit;
import simulation.Address;

@SuppressWarnings("serial")
public class MapGridButton extends MapButton {

	private ArrayList<MapButton> subButtons;
	
	public MapGridButton(Address address) {
		super(address);
		this.subButtons = new ArrayList<MapButton>();
	}
	
	public ArrayList<MapButton> getSubButtons() {
		return this.subButtons;
	}
	
	public void updateSubButtons() {
		this.subButtons.clear();
		for(int i = 0 ; i<this.getSimulatables().size();) {
			if(this.getSimulatables().get(i) instanceof Unit) {
				i++;
			}
			else {
				MapButton b = new MapButton(this.getSimulatables().get(i));
				b.setPreferredSize(new Dimension(100,100));
				this.getSubButtons().add(b);
				i++;
			}
		}
	}
}