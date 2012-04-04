package net.cammann.tom.fyp.core;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public class HashedResourceMap implements Iterable<Resource> {
	
	protected final HashMap<Point, Resource> hashMap = new HashMap<Point, Resource>();
	
	public void clear() {
		hashMap.clear();
	}
	
	public Resource getResource(Point p) {
		return hashMap.get(p);
	}
	
	public boolean hasResource(Point p) {
		if (hashMap.get(p) == null) {
			return false;
		}
		return true;
	}
	
	// could use boolean to signify already something there
	public void addResource(Resource r) {
		hashMap.put(r.getPosition(), r);
	}
	
	public boolean removeResource(Resource r) {
		Point p = r.getPosition();
		
		return hashMap.remove(p) == null ? false : true;
	}
	
	public boolean removeResource(Point p) {
		return hashMap.remove(p) == null ? true : false;
	}
	
	public int size() {
		return hashMap.size();
	}
	
	public boolean isEmpty() {
		return hashMap.isEmpty();
	}
	
	@Override
	public Iterator<Resource> iterator() {
		return hashMap.values().iterator();
	}
	
	public Set<Point> pointSet() {
		return hashMap.keySet();
	}
	
	public void addAll(HashedResourceMap map) {
		this.hashMap.putAll(map.hashMap);
	}
	
	public void addAll(List<Resource> list) {
		for (Resource r : list) {
			hashMap.put(r.getPosition(), r);
		}
	}
	
}
