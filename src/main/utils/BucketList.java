package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class BucketList<E> extends ArrayList<E> {
	
	// This is private. It is not visible from outside.
	private final Map<E, Integer> count = new HashMap<E, Integer>();
	
	// There are several entry points to this class
	// this is just to show one of them.
	@Override
	public boolean add(E element) {
		if (!count.containsKey(element)) {
			count.put(element, 1);
			return super.add(element);
		} else {
			count.put(element, count.get(element) + 1);
			return false;
		}
		
	}
	
	// This method belongs to CountItemList interface ( or class )
	// to used you have to cast.
	public int getCount(E element) {
		if (!count.containsKey(element)) {
			return 0;
		}
		return count.get(element);
	}
	
	// Sort by count
	public void sortByCount() {
		Comparator<E> c = new Comparator<E>() {
			
			@Override
			public int compare(E o1, E o2) {
				Integer c1 = getCount(o2);
				return c1.compareTo(getCount(o1));
			}
			
		};
		
		Collections.sort(this, c);
		
	}
	
}
