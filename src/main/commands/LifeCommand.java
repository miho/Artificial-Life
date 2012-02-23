package commands;

import core.Commandable;

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
