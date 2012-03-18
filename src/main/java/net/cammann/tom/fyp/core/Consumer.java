package net.cammann.tom.fyp.core;

public interface Consumer {
	
	public abstract boolean canConsumeResource(Resource r);
	
	public abstract boolean consumeResource(Resource r);
}
