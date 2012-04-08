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
public class MapObjectMap implements Iterable<MapObject> {
	
	protected final HashMap<Point, MapObject> hashMap = new HashMap<Point, MapObject>();
	
	public void clear() {
		hashMap.clear();
	}
	
	public MapObject getObject(Point p) {
		return hashMap.get(p);
	}
	
	public boolean hasObject(Point p) {
		if (hashMap.get(p) == null) {
			return false;
		}
		return true;
	}
	
	// could use boolean to signify already something there
	public void addObject(MapObject r) {
		hashMap.put(r.getPosition(), r);
	}
	
	public boolean removeObject(MapObject r) {
		Point p = r.getPosition();
		
		return hashMap.remove(p) == null ? false : true;
	}
	
	public boolean removeObject(Point p) {
		return hashMap.remove(p) == null ? false : true;
	}
	
	public int size() {
		return hashMap.size();
	}
	
	public boolean isEmpty() {
		return hashMap.isEmpty();
	}
	
	@Override
	public Iterator<MapObject> iterator() {
		return hashMap.values().iterator();
	}
	
	public Set<Point> pointSet() {
		return hashMap.keySet();
	}
	
	public void addAll(MapObjectMap map) {
		this.hashMap.putAll(map.hashMap);
	}
	
	public void addAll(List<MapObject> list) {
		for (MapObject r : list) {
			hashMap.put(r.getPosition(), r);
		}
	}
	
}
