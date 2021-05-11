package view;

import java.util.ArrayList;

import exceptions.SimulationException;
import model.units.Unit;

public interface GUIListener {

	public void nextCycle() throws SimulationException;
	public int calculateCasualties();
	public boolean checkGameOver();
	public ArrayList<Unit> getEmergencyUnits();
	public void addExcutedDisastersToMap();

}