package exceptions;

import model.units.Unit;
import simulation.Rescuable;

@SuppressWarnings("serial")
public abstract class UnitException extends SimulationException {

	private Unit unit;
	private Rescuable target;
	
	public UnitException(Unit unit, Rescuable target) {
		super();
		this.unit = unit;
		this.target = target;
	}
	public UnitException(Unit unit, Rescuable target, String message) {
		super(message);
		this.unit = unit;
		this.target = target;
	}
	public Unit getUnit() {
		return this.unit;
	}
	public Rescuable getTarget() {
		return this.target;
	}
}