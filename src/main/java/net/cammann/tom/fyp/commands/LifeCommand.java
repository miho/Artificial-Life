package net.cammann.tom.fyp.commands;

import net.cammann.tom.fyp.core.Commandable;

public abstract class LifeCommand {
	
	protected final String name;
	
	public LifeCommand(String name) {
		this.name = name;
	}
	
	public abstract void execute(Commandable life);
	
	@Override
	public String toString() {
		return name;
	}
}
