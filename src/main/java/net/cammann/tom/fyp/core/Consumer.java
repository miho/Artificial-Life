package net.cammann.tom.fyp.core;

public interface Consumer {
	
	public boolean canConsumeResource(Resource r);
	
	public boolean consumeResource(Resource r);
	
	public int getEnergy();
}
