package net.cammann.tom.fyp.utils;

public interface ListChangeListener {
	public void dataAdded(ListChangeEvent e);
	
	public void dataRemoved(ListChangeEvent e);
	
	public void dataChanged(ListChangeEvent e);
	
}
