package net.cammann.tom.fyp.utils;

public class ListChangeEvent {
	
	private final Object o;
	private final int index;
	
	public ListChangeEvent(Object o, int index) {
		this.o = o;
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Object getElement() {
		return o;
	}
	
}
