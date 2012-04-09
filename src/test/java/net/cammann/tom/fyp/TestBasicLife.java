package net.cammann.tom.fyp;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import net.cammann.tom.fyp.basicLife.BasicLifeFactory;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.EvolutionFactory;
import net.cammann.tom.fyp.core.MapObject;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBasicLife {
	static Logger logger = Logger.getLogger(TestBasicLife.class);
	
	/**
	 * Used to setup logger in test mode
	 */
	@BeforeClass
	public static void before() {
		PropertyConfigurator.configure("src/test/resources/log4j.properties");
	}
	
	@Test
	public void testBasicFactory() {
		// TODO remove 'magic numbers'
		EvolutionFactory factory = new BasicLifeFactory();
		factory.setNumOfObstacles(5);
		factory.setNumOfResources(40);
		factory.setMapHeight(400);
		factory.setMapWidth(200);
		
		EnvironmentMap map = factory.createMap();
		ALife life = TestUtils.getInstance().getBlankLife(map);
		factory.createLife(life, map);
		
		Iterator<MapObject> resourceIterator = map.getResourceIterator();
		int resourceCount = 0;
		while (resourceIterator.hasNext()) {
			resourceIterator.next();
			resourceCount++;
		}
		logger.info("resouce count: " + resourceCount);
		assertTrue(resourceCount == 40);
		
		Iterator<MapObject> obstacleIterator = map.getResourceIterator();
		int obstacleCount = 0;
		while (obstacleIterator.hasNext()) {
			obstacleIterator.next();
			obstacleCount++;
		}
		assertTrue(obstacleCount == 5);
		
		assertTrue(map.getWidth() == 200);
		assertTrue(map.getHeight() == 400);
		
	}
}
