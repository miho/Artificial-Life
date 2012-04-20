package net.cammann.tom.fyp.core;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <p>MapObjectMap class.</p>
 *
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 */
public class MapObjectMap implements Iterable<MapObject> {
	
	protected final HashMap<Point, MapObject> hashMap = new HashMap<Point, MapObject>();
	
	/**
	 * <p>clear.</p>
	 */
	public void clear() {
		hashMap.clear();
	}
	
	/**
	 * <p>getObject.</p>
	 *
	 * @param p a {@link java.awt.Point} object.
	 * @return a {@link net.cammann.tom.fyp.core.MapObject} object.
	 */
	public MapObject getObject(final Point p) {
		return hashMap.get(p);
	}
	
	/**
	 * <p>hasObject.</p>
	 *
	 * @param p a {@link java.awt.Point} object.
	 * @return a boolean.
	 */
	public boolean hasObject(final Point p) {
		if (hashMap.get(p) == null) {
			return false;
		}
		return true;
	}
	
	// could use boolean to signify already something there
	/**
	 * <p>addObject.</p>
	 *
	 * @param r a {@link net.cammann.tom.fyp.core.MapObject} object.
	 */
	public void addObject(final MapObject r) {
		hashMap.put(r.getPosition(), r);
	}
	
	/**
	 * <p>removeObject.</p>
	 *
	 * @param r a {@link net.cammann.tom.fyp.core.MapObject} object.
	 * @return a boolean.
	 */
	public boolean removeObject(final MapObject r) {
		final Point p = r.getPosition();
		
		return hashMap.remove(p) == null ? false : true;
	}
	
	/**
	 * <p>removeObject.</p>
	 *
	 * @param p a {@link java.awt.Point} object.
	 * @return a boolean.
	 */
	public boolean removeObject(final Point p) {
		return hashMap.remove(p) == null ? false : true;
	}
	
	/**
	 * <p>size.</p>
	 *
	 * @return a int.
	 */
	public int size() {
		return hashMap.size();
	}
	
	/**
	 * <p>isEmpty.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isEmpty() {
		return hashMap.isEmpty();
	}
	
	/** {@inheritDoc} */
	@Override
	public Iterator<MapObject> iterator() {
		return hashMap.values().iterator();
	}
	
	/**
	 * <p>pointSet.</p>
	 *
	 * @return a {@link java.util.Set} object.
	 */
	public Set<Point> pointSet() {
		return hashMap.keySet();
	}
	
	/**
	 * <p>addAll.</p>
	 *
	 * @param map a {@link net.cammann.tom.fyp.core.MapObjectMap} object.
	 */
	public void addAll(final MapObjectMap map) {
		this.hashMap.putAll(map.hashMap);
	}
	
	/**
	 * <p>addAll.</p>
	 *
	 * @param list a {@link java.util.List} object.
	 */
	public void addAll(final List<MapObject> list) {
		for (final MapObject r : list) {
			hashMap.put(r.getPosition(), r);
		}
	}
	
}
