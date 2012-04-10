package net.cammann.tom.fyp.core;

public interface Consumable {
	
	public boolean isConsumable();
	
	public abstract int getCalories();
	
	public abstract void consume();
}
