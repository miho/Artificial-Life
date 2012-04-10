package net.cammann.tom.fyp.ants;

import net.cammann.tom.fyp.commands.LifeCommand;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.AbstactLife;
import net.cammann.tom.fyp.core.Resource;

public class Ant extends AbstactLife {
	
	@Override
	public LifeCommand[] getCommandList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void initBrain() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public ALife clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean consume() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public int getMemoryLength() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean dropResource() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean canConsumeResource(final Resource r) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
