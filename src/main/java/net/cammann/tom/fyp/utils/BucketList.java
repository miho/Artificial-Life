package net.cammann.tom.fyp.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A specialised list used for counting occurences of items.
 *
 * Derived name from bucket sort.
 *
 * @author TC
 * @param <E>
 * @version $Id: $
 */
public final class BucketList<E> extends ArrayList<E> {
	
	/**
	 * Hashmap to keep count.
	 */
	private final Map<E, Integer> count = new HashMap<E, Integer>();
	
	/**
	 * {@inheritDoc}
	 *
	 * Override list add to update count on every add.
	 */
	@Override
	public boolean add(final E element) {
		if (!count.containsKey(element)) {
			count.put(element, 1);
			return super.add(element);
		} else {
			count.put(element, count.get(element) + 1);
			return false;
		}
		
	}
	
	/**
	 * Return number of occurences of E in list.
	 *
	 * @param element
	 *            to find
	 * @return number of occurences
	 */
	public int getCount(final E element) {
		if (!count.containsKey(element)) {
			return 0;
		}
		return count.get(element);
	}
	
	/**
	 * Sort by count.
	 */
	public void sortByCount() {
		final Comparator<E> c = new Comparator<E>() {
			
			@Override
			public int compare(final E o1, final E o2) {
				final Integer c1 = getCount(o2);
				return c1.compareTo(getCount(o1));
			}
			
		};
		
		Collections.sort(this, c);
		
	}
	
}
