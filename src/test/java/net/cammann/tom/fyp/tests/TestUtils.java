package net.cammann.tom.fyp.tests;

import net.cammann.tom.fyp.commands.LifeCommand;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.AbstractEnvironmentMap;
import net.cammann.tom.fyp.core.AbstractLife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.Resource;

/**
 * Helper class for testing.
 * 
 * @author TC
 * 
 */
public final class TestUtils {

	/**
	 * Singleton instance.
	 */
	private static TestUtils utils = new TestUtils();

	/**
	 * Empty private constructor.
	 */
	private TestUtils() {
	}

	/**
	 * d Gets singleton instance of TestUtils.
	 * 
	 * @return singleton
	 */
	public static TestUtils getInstance() {
		return utils;
	}

	/**
	 * Creates map.
	 * 
	 * @param width
	 *            of map
	 * @param height
	 *            of map
	 * @return map
	 */
	public EnvironmentMap getBlankMap(final int width, final int height) {

		return new BlankMap(width, height);
	}

	/**
	 * Class used for testing.
	 * 
	 * Does not init any resources
	 * 
	 */
	private final class BlankMap extends AbstractEnvironmentMap {

		/**
		 * Just calls super constructor.
		 * 
		 * @param w
		 *            width
		 * @param h
		 *            height
		 */
		private BlankMap(final int w, final int h) {
			super(w, h);
		}

		@Override
		public void initResources() {

		}

		@Override
		protected void initObstacles() {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * Gets empty life.
	 * 
	 * @param map
	 *            reference for life
	 * @return ALife
	 */
	public ALife getBlankLife(final EnvironmentMap map) {
		return new BlankLife(map);
	}

	/**
	 * Simple class for testing ALife objects
	 * 
	 * Does not init any commands.
	 */
	private final class BlankLife extends AbstractLife {

		/**
		 * Just calls super constructor.
		 * 
		 * @param map
		 *            map
		 */
		private BlankLife(final EnvironmentMap map) {
			super(map);
		}

		@Override
		public LifeCommand[] getCommandList() {
			return new LifeCommand[] {};
		}

		@Override
		public ALife clone() {
			return new BlankLife(map);
		}

		@Override
		public void initBrain() {

		}

		@Override
		public int getMemoryLength() {
			return 10;
		}

		@Override
		public boolean canConsumeResource(final Resource r) {
			return true;
		}

		@Override
		public int getStartEnergy() {
			return 100;
		}

		@Override
		public boolean dropResource() {
			return false;
		}
	}
}
