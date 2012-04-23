package net.cammann.tom.fyp.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Point;
import java.lang.reflect.Method;

import junit.framework.Assert;
import net.cammann.tom.fyp.basicLife.BasicResource;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.AbstractEnvironmentMap;
import net.cammann.tom.fyp.core.Commandable.ORIENTATION;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.GENE_TYPE;
import net.cammann.tom.fyp.core.Obstacle;
import net.cammann.tom.fyp.core.Resource;
import net.cammann.tom.fyp.ga.BasicBrain;
import net.cammann.tom.fyp.ga.BasicLife;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Test for BasicBrain.
 * 
 * Asser correctness of all methods.
 * 
 * @author TC
 * 
 */
public final class TestBrain {
	
	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(TestBrain.class);
	
	/**
	 * Test method canSeeLife in BasicBrain.
	 */
	@Test
	public void testSeeLife() {
		
		final int x1 = 50;
		final int y1 = 140;
		final int x2 = 50;
		final int y2 = 130;
		
		final int sightRange = 4;
		
		final EnvironmentMap map = TestUtils.getInstance()
				.getBlankMap(200, 200);
		
		final int ord = GENE_TYPE.SEE_LIFE_RANGE.ordinal();
		
		final int[] genes = new int[ord + 1];
		
		for ( int i = 0 ; i < ord + 1 ; i++ ) {
			genes[i] = sightRange;
		}
		
		final ALife life = new BasicLife(genes, map);
		final ALife life2 = new BasicLife(genes, map);
		map.addLife(life);
		map.addLife(life2);
		
		life.setOrientation(ORIENTATION.UP);
		life.setX(x1);
		life.setY(y1);
		life2.setOrientation(ORIENTATION.UP);
		life2.setX(x2);
		life2.setY(y2);
		assertTrue(map.hasLife(life.getPosition()));
		assertTrue(map.hasLife(life2.getPosition()));
		
		assertTrue(map.hasLife(new Point(x1, y1)));
		assertTrue(map.hasLife(new Point(x2, y2)));
		try {
			final Method canSeeLife = BasicBrain.class
					.getDeclaredMethod("canSeeLife");
			canSeeLife.setAccessible(true);
			
			Object out = canSeeLife.invoke(life.getBrain());
			assertTrue((Boolean) out);
			
			out = canSeeLife.invoke(life2.getBrain());
			assertFalse((Boolean) out);
			
			life2.setOrientation(ORIENTATION.DOWN);
			out = canSeeLife.invoke(life2.getBrain());
			assertTrue((Boolean) out);
			
			life2.setOrientation(ORIENTATION.LEFT);
			out = canSeeLife.invoke(life2.getBrain());
			assertFalse((Boolean) out);
			
			for ( int i = 1 ; i < sightRange * 2 ; i++ ) {
				logger.trace("loop i: " + i);
				life2.setY(y2 - i * AbstractEnvironmentMap.STEP_SIZE);
				out = canSeeLife.invoke(life.getBrain());
				if (i < sightRange) {
					assertTrue((Boolean) out);
				} else {
					assertFalse((Boolean) out);
				}
			}
			
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		
	}
	
	/**
	 * Test of can see obstacle in basicbrain.
	 */
	@Test
	public void testSeeObstacle() {
		final int x = 50;
		final int y = 140;
		
		final int ox = 50;
		final int oy = 130;
		
		final int sightRange = 4;
		
		final EnvironmentMap map = TestUtils.getInstance()
				.getBlankMap(200, 200);
		
		final int ord = GENE_TYPE.SEE_WALL_RANGE.ordinal();
		
		final int[] genes = new int[ord + 1];
		
		for ( int i = 0 ; i < ord + 1 ; i++ ) {
			genes[i] = sightRange;
		}
		
		final ALife life = new BasicLife(genes, map);
		map.addLife(life);
		
		life.setOrientation(ORIENTATION.UP);
		life.setX(x);
		life.setY(y);
		
		assertTrue(map.hasLife(new Point(x, y)));
		assertFalse(map.hasLife(new Point(x, x)));
		try {
			final Method canSeeObstacle = BasicBrain.class
					.getDeclaredMethod("canSeeObstacle");
			canSeeObstacle.setAccessible(true);
			
			final Method addObstacle = AbstractEnvironmentMap.class
					.getDeclaredMethod("addObstacle",
							new Class<?>[] { Obstacle.class });
			addObstacle.setAccessible(true);
			
			Object out = canSeeObstacle.invoke(life.getBrain());
			assertFalse((Boolean) out);
			
			final Obstacle r = new Obstacle(ox, oy, 5);
			addObstacle.invoke(map, r);
			
			out = canSeeObstacle.invoke(life.getBrain());
			assertTrue((Boolean) out);
			
			for ( int i = 1 ; i < sightRange * 2 ; i++ ) {
				logger.trace("loop i: " + i);
				life.setY(y + i * AbstractEnvironmentMap.STEP_SIZE);
				out = canSeeObstacle.invoke(life.getBrain());
				if (i < sightRange) {
					assertTrue((Boolean) out);
				} else {
					assertFalse((Boolean) out);
				}
			}
			
			life.setX(AbstractEnvironmentMap.STEP_SIZE);
			life.setY(AbstractEnvironmentMap.STEP_SIZE);
			
			for ( int i = 1 ; i < (sightRange * 2) ; i++ ) {
				logger.trace("Position: " + life.getPosition());
				logger.trace("loop i: " + i);
				life.setY(AbstractEnvironmentMap.STEP_SIZE
						+ (i * AbstractEnvironmentMap.STEP_SIZE));
				out = canSeeObstacle.invoke(life.getBrain());
				if (i < sightRange - 1) {
					assertTrue((Boolean) out);
					
				} else {
					assertFalse((Boolean) out);
				}
			}
			
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	/**
	 * Test of can see resource in basicbrain.
	 */
	@Test
	public void testSeeResource() {
		final int x = 50;
		final int y = 140;
		
		final int rx = 50;
		final int ry = 130;
		
		final int sightRange = 4;
		
		final EnvironmentMap map = TestUtils.getInstance()
				.getBlankMap(200, 200);
		
		final int ord = GENE_TYPE.SEE_FOOD_RANGE.ordinal();
		
		final int[] genes = new int[ord + 1];
		
		for ( int i = 0 ; i < ord + 1 ; i++ ) {
			genes[i] = sightRange;
		}
		
		final ALife life = new BasicLife(genes, map);
		map.addLife(life);
		
		life.setOrientation(ORIENTATION.UP);
		life.setX(x);
		life.setY(y);
		
		assertTrue(map.hasLife(new Point(x, y)));
		assertFalse(map.hasLife(new Point(x, x)));
		try {
			final Method canSeeResource = BasicBrain.class
					.getDeclaredMethod("canSeeResource");
			canSeeResource.setAccessible(true);
			
			final Method addResource = AbstractEnvironmentMap.class
					.getDeclaredMethod("addResource",
							new Class<?>[] { Resource.class });
			addResource.setAccessible(true);
			
			Object out = canSeeResource.invoke(life.getBrain());
			assertFalse((Boolean) out);
			
			final Resource r = new BasicResource(rx, ry);
			addResource.invoke(map, r);
			
			out = canSeeResource.invoke(life.getBrain());
			assertTrue((Boolean) out);
			
			for ( int i = 1 ; i < sightRange * 2 ; i++ ) {
				logger.trace("loop i: " + i);
				life.setY(y + i * AbstractEnvironmentMap.STEP_SIZE);
				out = canSeeResource.invoke(life.getBrain());
				if (i < sightRange) {
					assertTrue((Boolean) out);
				} else {
					assertFalse((Boolean) out);
				}
			}
			
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
