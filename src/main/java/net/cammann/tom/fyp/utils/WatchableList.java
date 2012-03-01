package net.cammann.tom.fyp.utils;

import java.util.ArrayList;
import java.util.List;

public class WatchableList<E> extends ArrayList<E> {
	private final List<ListChangeListener> listeners;
	
	// TOOD add alllllll the listeners
	// TODO not finished
	
	public WatchableList() {
		super();
		listeners = new ArrayList<ListChangeListener>();
	}
	
	public void addListChangeListener(ListChangeListener listener) {
		listeners.add(listener);
	}
	
	public void removeListChangeListener(ListChangeListener listener) {
		listeners.remove(listener);
	}
	
	public void removeListChangeListener(int idx) {
		listeners.remove(idx);
	}
	
	@Override
	public boolean add(E e) {
		
		ListChangeEvent event = new ListChangeEvent(e, size() - 1);
		for (ListChangeListener i : listeners) {
			i.dataAdded(event);
			i.dataChanged(event);
		}
		
		return super.add(e);
	}
	
	@Override
	public void add(int index, E e) {
		ListChangeEvent event = new ListChangeEvent(e, index);
		for (ListChangeListener i : listeners) {
			i.dataAdded(event);
			i.dataChanged(event);
		}
		super.add(index, e);
	}
	
	@Override
	public E remove(int idx) {
		E e = super.remove(idx);
		ListChangeEvent event = new ListChangeEvent(e, idx);
		for (ListChangeListener i : listeners) {
			i.dataRemoved(event);
			i.dataChanged(event);
		}
		
		return e;
	}
	
	@Override
	public boolean remove(Object o) {
		
		ListChangeEvent event = new ListChangeEvent(o, -1);
		for (ListChangeListener i : listeners) {
			i.dataRemoved(event);
			i.dataChanged(event);
		}
		
		return super.remove(o);
	}
}