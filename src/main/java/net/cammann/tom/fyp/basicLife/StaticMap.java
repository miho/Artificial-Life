package net.cammann.tom.fyp.basicLife;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.AbstactMap;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.Resource;
import net.cammann.tom.fyp.core.Resource.ResourceType;
import net.cammann.tom.fyp.core.ResourceFactory;
import net.cammann.tom.fyp.core.SimpleMap;

/**
 * <p>
 * StaticMap class.
 * </p>
 * 
 * @author tc
 * @version $Id: $
 */
public final class StaticMap extends AbstactMap {

	/** Constant <code>RESOURCE_LIST</code> */
	public static List<Resource> RESOURCE_LIST;
	private final int numResources;
	/**
	 * Static start x position for all life.
	 */
	private static int x = -1;
	/**
	 * Static start y position for all life.
	 */
	private static int y = -1;

	/**
	 * <p>
	 * Constructor for StaticMap.
	 * </p>
	 * 
	 * @param width
	 *            a int.
	 * @param height
	 *            a int.
	 * @param numResources
	 *            a int.
	 */
	public StaticMap(final int width, final int height, final int numResources) {
		super(width, height);
		this.numResources = numResources;
	}

	private static synchronized List<Resource> getResourceList(
			EnvironmentMap map, int numOfResources) {
		if (RESOURCE_LIST == null) {
			RESOURCE_LIST = new ArrayList<Resource>();
			final ResourceFactory r = new ResourceFactory(map);
			for (int i = 0; i < numOfResources; i++) {
				RESOURCE_LIST.add(r.createResource(ResourceType.BASIC));
			}
		}
		return RESOURCE_LIST;
	}

	/** {@inheritDoc} */
	@Override
	public void initResources() {
		for (final Resource r : getResourceList(this, numResources)) {
			addResource(r);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void placeLife(final ALife life) {
		if (x == -1) {
			x = new Random().nextInt((life.getMap().getWidth() + 1)
					/ SimpleMap.STEP_SIZE)
					* SimpleMap.STEP_SIZE;
			y = new Random().nextInt((life.getMap().getHeight() + 1)
					/ SimpleMap.STEP_SIZE)
					* SimpleMap.STEP_SIZE;
		}
		life.setX(x);
		life.setY(y);
	}

	@Override
	protected void initObstacles() {
		// ssss33saasd

	}
}
