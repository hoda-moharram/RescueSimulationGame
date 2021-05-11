package view;

import java.awt.Dimension;

import model.units.Unit;
import simulation.Address;

@SuppressWarnings("serial")
public class UnitButton extends MapButton {

	private Unit unit;
	private int distanceToTarget;
	private Address previousAddress;
	private Address currentAddress;
	
	public UnitButton(Address address, Unit unit) {
		super(address);
		this.setPreferredSize(new Dimension(200,100));
		this.unit = unit;
		this.getSimulatables().add(unit);
		this.setIconTextGap(10);
		this.currentAddress = address;
		this.previousAddress = address;
	}
	
	public String getUnitName() {
		String[] name = this.getText().split(" ");
		String o = "";
		for(int i=0;i<name.length;i++) {
			o+=name[i];
		}
		return o;
	}
	
	public Unit getUnit() {
		return this.unit;
	}
	
	public Address getCurrentAddress() {
		return this.currentAddress;
	}
	
	public void setCurrentAddress(Address currentAddress) {
		this.currentAddress = currentAddress;
	}
	
	public Address getPreviousAddress() {
		return this.previousAddress;
	}
	
	public void setPreviousAddress(Address previousAddress) {
		this.previousAddress = previousAddress;
	}
	
	@SuppressWarnings("unused")
	private int calculateDistanceToTarget(Unit u) {
		int x = u.getLocation().getX();
		int y = u.getLocation().getY();
		int x1 = u.getTarget().getLocation().getX();
		int y1 = u.getTarget().getLocation().getY();
		int d = Math.abs(x - x1) + Math.abs(y - y1);
		return d;
	}
	
	public int getDistanceToTarget() {
		return this.distanceToTarget;
	}
}
