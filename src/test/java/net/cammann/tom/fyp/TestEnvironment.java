package net.cammann.tom.fyp;

import java.awt.Point;

import net.cammann.tom.fyp.commands.LifeCommand;
import net.cammann.tom.fyp.core.ABug;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.Commandable;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.MapObject;
import net.cammann.tom.fyp.core.Obstacle;
import net.cammann.tom.fyp.core.Resource;
import net.cammann.tom.fyp.core.SimpleMap;
import net.cammann.tom.fyp.core.SimpleResource;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestEnvironment extends Assert {
	static Logger logger = Logger.getLogger(TestEnvironment.class);
	
	/**
	 * Class used for testing.
	 * 
	 * Does not init any resources
	 * 
	 * 
	 */
	class BlankMap extends SimpleMap {
		
		private BlankMap(int w, int h) {
			super(w, h);
		}
		
		@Override
		public void resetMap() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void initResources() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * Simple class for testing ALife objects
	 * 
	 * Does not init any commands.
	 */
	private class BlankLife extends ABug {
		private BlankLife(EnvironmentMap map) {
			super(map);
		}
		
		@Override
		public LifeCommand[] getCommandList() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void reset() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public ALife clone() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public void initBrain() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public int getMemoryLength() {
			// TODO Auto-generated method stub
			return 10;
		}
	}
	
	/**
	 * Used to setup logger in test mode
	 */
	@BeforeClass
	public static void before() {
		PropertyConfigurator.configure("src/test/resources/log4j.properties");
	}
	
	/**
	 * Simple test to assure resources are correctly placed
	 * 
	 * Checks resources are correctly placed on the map. Checks their type,
	 * checks that they can be accessed Makes sure duplicate resources cannot be
	 * added Checks resource cannot be added off the map.
	 */
	
	public void resourceTest() {
		// TODO remove 'magic numbers'
		EnvironmentMap map = new BlankMap(100, 200);
		Resource r = new SimpleResource(new Point(10, 30));
		// Check normalcy of resource
		assertTrue(r.getPosition().equals(new Point(10, 30)));
		assertTrue(r.getResourceType().equals(Resource.ResourceType.SIMPLE));
		
		// Check added correctly
		assertTrue(map.addResource(r));
		MapObject ro = map.getResourceIterator().next();
		assertTrue(ro.equals(ro));
		
		// Check no duplicates can be added
		assertFalse(map.addResource(new SimpleResource(new Point(10, 30))));
		assertFalse(map.addResource(r));
		
		// Assert no off map objects can be added
		assertFalse(map.addResource(new SimpleResource(new Point(-10, 10))));
		
		// Check resources cannot be added over an obstacle
		map.addObstacle(new Obstacle(new Point(50, 40), 5));
		assertFalse(map.addResource(new SimpleResource(new Point(50, 40))));
		
		// Check calories
		r.setCalories(100);
		assertTrue(r.getCalories() == 100);
		
		// Check removals
		// This is checked more by obstacleTest, uses same underlying object
		assertTrue(map.removeResource(r));
		assertTrue(map.addResource(r));
		assertTrue(map.removeResource(new Point(10, 30)));
		
	}
	
	/**
	 * Test validity when adding an obstacle
	 * 
	 * no duplicate positions, not off map,not on top of resource
	 */
	@Test
	public void obstacleTest() {
		// TODO remove 'magic numbers'
		EnvironmentMap map = new BlankMap(100, 200);
		
		Obstacle o = new Obstacle(new Point(50, 60), 5);
		
		// Check normal add
		assertTrue(map.addObstacle(o));
		// Make sure no duplicates
		assertFalse(map.addObstacle(o));
		
		Obstacle o2 = new Obstacle(new Point(-10, 10), 5);
		// Check cant be added off map
		assertFalse(map.addObstacle(o2));
		
		Resource r = new SimpleResource(new Point(80, 40));
		Obstacle o3 = new Obstacle(new Point(80, 40), 5);
		map.addResource(r);
		// check cannot be added on top of resource
		assertFalse(map.addObstacle(o3));
		
		// Check removal of obstacle
		assertFalse(map.removeObstacle(o3));
		assertFalse(map.removeObstacle(o2.getPosition()));
		assertTrue(map.removeObstacle(o.getPosition()));
		assertTrue(map.removeResource(r));
		assertTrue(map.addObstacle(o3));
		assertTrue(map.removeObstacle(o3.getPosition()));
		
	}
	
	/**
	 * Test validity of Life object
	 * 
	 */
	@Test
	public void lifeTest() {
		// TODO remove 'magic numbers'
		EnvironmentMap map = new BlankMap(100, 200);
		
		ALife life = new BlankLife(map);
		// Check add to map
		assertTrue(map.addLife(life));
		assertTrue(map.validPosition(life.getPosition()));
		
		// Check add to map is bad when
		life.setX(-10);
		assertFalse(map.validPosition(life.getPosition()));
		
		life.setPosition(new Point(40, 70));
		logger.debug("Life is at: " + life.getPosition());
		assertTrue(life.getPosition().equals(new Point(40, 70)));
		// Point p = life.getPosition();
		// Check normalcy
		life.setOrientation(Commandable.ORIENTATION.UP);
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.UP));
		
		// Check move forward
		life.moveForward();
		assertTrue(life.getPosition().equals(new Point(40, 60)));
		
		// Check turns
		life.turnLeft();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.LEFT));
		life.turnLeft();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.DOWN));
		life.turnLeft();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.RIGHT));
		life.turnLeft();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.UP));
		
		life.turnRight();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.RIGHT));
		life.turnRight();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.DOWN));
		life.turnRight();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.LEFT));
		life.turnRight();
		assertTrue(life.getOrientation().equals(Commandable.ORIENTATION.UP));
		
		// Check energy, and energy consumption
		life.setEnergy(100);
		assertTrue(life.getEnergy() == 100);
		life.decrementEnegery(50);
		assertTrue(life.getEnergy() == 50);
		life.incrementEnergy(40);
		assertTrue(life.getEnergy() == 90);
		
		life.setOrientation(Commandable.ORIENTATION.UP);
		Point p = life.getPosition();
		p.setLocation(p.x, p.y - 10);
		Resource r = new SimpleResource(p);
		map.addResource(r);
		r.setCalories(100);
		life.moveForward();
		logger.debug("Life position: " + life.getPosition());
		logger.debug("Resource position: " + r.getPosition());
		assertTrue(life.getPosition().equals(r.getPosition()));
		
		int curNrg = life.getEnergy();
		assertTrue(map.hasResource(life.getPosition()));
		assertTrue(life.consume());
		assertTrue(life.getEnergy() == curNrg + r.getCalories());
		assertTrue(life.getEnergy() == curNrg + 100);
		assertFalse(map.hasResource(life.getPosition()));
		assertFalse(life.consume());
		
		// Check removal of life
		assertTrue(map.hasLife(life.getPosition()));
		assertTrue(map.removeLife(life));
		assertTrue(map.addLife(life));
		assertTrue(map.removeLife(life.getPosition()));
		
	}
}
