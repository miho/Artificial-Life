package net.cammann.tom.fyp.tests;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import junit.framework.Assert;
import net.cammann.tom.fyp.core.AbstractEnvironmentMap;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.MapObject;
import net.cammann.tom.fyp.core.Obstacle;
import net.cammann.tom.fyp.core.Resource;
import net.cammann.tom.fyp.core.SimpleResource;
import net.cammann.tom.fyp.utils.MapUtils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Class for testing correctness of map utils.
 * 
 * @author tc
 * 
 */
public class TestMapUtils {

	/**
	 * Logger.
	 */
	private static final Logger logger = Logger.getLogger(TestMapUtils.class);

	/**
	 * Used to setup logger in test mode.
	 */
	@BeforeClass
	public static void before() {
		PropertyConfigurator.configure("src/test/resources/log4j.properties");
	}

	/**
	 * Test for correct export and import of custom map;
	 */
	@Test
	public void testExportImportMap() {
		EnvironmentMap map = TestUtils.getInstance().getBlankMap(400, 400);
		List<Resource> initResources = new ArrayList<Resource>();
		List<Obstacle> initObstacles = new ArrayList<Obstacle>();

		try {

			final Method addResource = AbstractEnvironmentMap.class
					.getDeclaredMethod("addResource",
							new Class<?>[] { Resource.class });
			addResource.setAccessible(true);

			final Method addObstacle = AbstractEnvironmentMap.class
					.getDeclaredMethod("addObstacle",
							new Class<?>[] { Obstacle.class });
			addObstacle.setAccessible(true);

			Random rnd = new Random();

			for (int i = 0; i < 10; i++) {
				int x = rnd.nextInt(map.getWidth()
						/ AbstractEnvironmentMap.STEP_SIZE);
				int y = rnd.nextInt(map.getHeight()
						/ AbstractEnvironmentMap.STEP_SIZE);

				Resource r = new SimpleResource(x, y);
				initResources.add(r);
				addResource.invoke(map, r);
			}

			for (int i = 0; i < 10; i++) {
				int x = rnd.nextInt(map.getWidth()
						/ AbstractEnvironmentMap.STEP_SIZE);
				int y = rnd.nextInt(map.getHeight()
						/ AbstractEnvironmentMap.STEP_SIZE);

				Obstacle o = new Obstacle(x, y, 5);
				initObstacles.add(o);
				addObstacle.invoke(map, o);
			}

			File tmp = File.createTempFile("mapfile", ".map");

			MapUtils.saveMap(tmp, map);

			EnvironmentMap loadedMap = MapUtils.loadMap(tmp);
			loadedMap.resetMap();

			List<Resource> loadedResources = new ArrayList<Resource>();
			Iterator<MapObject> it = loadedMap.getResourceIterator();
			while (it.hasNext()) {
				Resource r = (Resource) it.next();
				loadedResources.add(r);
			}

			List<Obstacle> loadedObstacles = new ArrayList<Obstacle>();
			it = loadedMap.getObstacleIterator();
			while (it.hasNext()) {
				Obstacle o = (Obstacle) it.next();
				loadedObstacles.add(o);
			}

			Collections.sort(initObstacles);
			Collections.sort(loadedObstacles);
			Collections.sort(loadedResources);
			Collections.sort(initResources);

			for (int i = 0; i < initResources.size(); i++) {
				logger.trace("Compare- " + initResources.get(i).getPosition()
						+ " and " + loadedResources.get(i).getPosition());
				assertEquals(initResources.get(i), loadedResources.get(i));
			}

			for (int i = 0; i < initObstacles.size(); i++) {
				logger.trace("Compare- " + initObstacles.get(i).getPosition()
						+ " and " + loadedObstacles.get(i).getPosition());
				assertEquals(initObstacles.get(i), loadedObstacles.get(i));
			}

		} catch (Exception e) {
			logger.fatal("testExportImport map failed", e);
			Assert.fail();
		}
	}
}
