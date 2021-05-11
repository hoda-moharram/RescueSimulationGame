package view;

import java.util.ArrayList;

import model.disasters.Disaster;
import simulation.Address;

public interface SimulationListener {

	public void constructWorld(MapGridButton b);
	public ArrayList<MapGridButton> getMapButtons();
	public ArrayList<UnitButton> getUnitButtons();
	public void setListener(GUIListener simulator);
	public MapGridButton getMapButtonByAddress(Address location);
	public void addExcutedDisastersToMap(ArrayList<Disaster> executedDisasters);
}
