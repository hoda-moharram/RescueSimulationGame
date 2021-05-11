package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.DisasterException;
import model.infrastructure.ResidentialBuilding;


public class Collapse extends Disaster {

	public Collapse(int startCycle, ResidentialBuilding target) {
		super(startCycle, target);
		
	}
	public void strike () throws DisasterException 
	{
		//try {
			ResidentialBuilding target= (ResidentialBuilding)getTarget();
			if(target.getStructuralIntegrity() == 0) {
				throw new BuildingAlreadyCollapsedException(this, "Building already collapsed");
			}
			target.setFoundationDamage(target.getFoundationDamage()+10);
			super.strike();
		//}
		//catch(BuildingAlreadyCollapsedException e) {
		//	System.out.println(e.getMessage());
		//}
	}
	public void cycleStep()
	{
		ResidentialBuilding target= (ResidentialBuilding)getTarget();
		target.setFoundationDamage(target.getFoundationDamage()+10);
	}

}
