package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import exceptions.UnitException;
import model.disasters.Collapse;
import model.disasters.Disaster;
import model.disasters.Fire;
import model.disasters.GasLeak;
import model.disasters.Infection;
import model.disasters.Injury;
import model.events.SOSResponder;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public abstract class Unit implements Simulatable, SOSResponder {
	private String unitID;
	private UnitState state;
	private Address location;
	private Rescuable target;
	private int distanceToTarget;
	private int stepsPerCycle;
	private WorldListener worldListener;

	public Unit(String unitID, Address location, int stepsPerCycle, WorldListener worldListener) {
		this.unitID = unitID;
		this.location = location;
		this.stepsPerCycle = stepsPerCycle;
		this.state = UnitState.IDLE;
		this.worldListener = worldListener;
	}

	public void setWorldListener(WorldListener listener) {
		this.worldListener = listener;
	}

	public WorldListener getWorldListener() {
		return worldListener;
	}

	public UnitState getState() {
		return state;
	}

	public void setState(UnitState state) {
		this.state = state;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address location) {
		this.location = location;
	}

	public String getUnitID() {
		return unitID;
	}

	public Rescuable getTarget() {
		return target;
	}

	public int getStepsPerCycle() {
		return stepsPerCycle;
	}

	public void setDistanceToTarget(int distanceToTarget) {
		this.distanceToTarget = distanceToTarget;
	}

	@Override
	public void respond(Rescuable r) throws UnitException {

		if (isTargetCompatible(r) && !canTreat(r)) {
			throw new CannotTreatException(this, r, "Target is already safe.");
		}
		
		if (!isTargetCompatible(r)) {
			throw new IncompatibleTargetException(this, r, "Target cannot be treated by this unit. Please select another unit.");
		}

		if (!isDisasterCompatible(r)) {
			throw new CannotTreatException(this, r, "Target cannot be treated by this unit. Please select another unit.");
		}

		if (target != null && state == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);
	}

	public void reactivateDisaster() throws UnitException {
		Disaster curr = target.getDisaster();
		curr.setActive(true);
	}

	public void finishRespond(Rescuable r) throws UnitException {
		target = r;
		state = UnitState.RESPONDING;
		Address t = r.getLocation();
		distanceToTarget = Math.abs(t.getX() - location.getX()) + Math.abs(t.getY() - location.getY());
	}

	public abstract void treat();

	public void cycleStep() {
		if (state == UnitState.IDLE)
			return;
		if (distanceToTarget > 0) {
			distanceToTarget = distanceToTarget - stepsPerCycle;
			if (distanceToTarget <= 0) {
				distanceToTarget = 0;
				Address t = target.getLocation();
				worldListener.assignAddress(this, t.getX(), t.getY());
			}
		} else {
			state = UnitState.TREATING;
			treat();
		}
	}

	public void jobsDone() {
		target = null;
		state = UnitState.IDLE;

	}

	public boolean canTreat(Rescuable r)
	{
		if (r instanceof Citizen) {
			Citizen c = (Citizen) r;
			if (c.getState().equals(CitizenState.SAFE) || c.getState().equals(CitizenState.RESCUED)) {
				return false;
			}
		} else {
			ResidentialBuilding b = (ResidentialBuilding) r;
			if (b.getDisaster() == null || (b.getDisaster() != null && b.getDisaster().isActive() == false)) {
				return false;
			}
		}
		return true;
	}

	public boolean isDisasterCompatible(Rescuable r)
	{
		if (this instanceof FireTruck && !(r.getDisaster() instanceof Fire)) {
			return false;
		}
		if (this instanceof GasControlUnit && !(r.getDisaster() instanceof GasLeak)) {
			return false;
		}
		if (this instanceof Evacuator && !(r.getDisaster() instanceof Collapse)) {
			return false;
		}
		if (this instanceof Ambulance && !(r.getDisaster() instanceof Injury)) {
			return false;
		}
		if (this instanceof DiseaseControlUnit && !(r.getDisaster() instanceof Infection)) {
			return false;
		}
		return true;
	}

	public boolean isTargetCompatible(Rescuable r)
	{
		if ((this instanceof FireTruck || this instanceof GasControlUnit || this instanceof Evacuator)
				&& r instanceof Citizen) {
			return false;
		}
		if ((this instanceof MedicalUnit) && r instanceof ResidentialBuilding) {
			return false;
		}
		return true;
	}
}
