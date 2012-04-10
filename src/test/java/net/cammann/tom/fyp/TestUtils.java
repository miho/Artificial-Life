package net.cammann.tom.fyp;

import net.cammann.tom.fyp.commands.LifeCommand;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.AbstactLife;
import net.cammann.tom.fyp.core.AbstactMap;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.Resource;

public class TestUtils {
	
	private static TestUtils utils = new TestUtils();
	
	private TestUtils() {}
	
	public static TestUtils getInstance() {
		return utils;
	}
	
	public EnvironmentMap getBlankMap(final int width, final int height) {
		
		return new BlankMap(width, height);
	}
	
	/**
	 * Class used for testing.
	 * 
	 * Does not init any resources
	 * 
	 */
	private class BlankMap extends AbstactMap {
		
		private BlankMap(final int w, final int h) {
			super(w, h);
		}
		
		@Override
		public void initResources() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public ALife getBlankLife(final EnvironmentMap map) {
		return new BlankLife(map);
	}
	
	/**
	 * Simple class for testing ALife objects
	 * 
	 * Does not init any commands.
	 */
	private class BlankLife extends AbstactLife {
		
		private BlankLife(final EnvironmentMap map) {
			super(map);
		}
		
		@Override
		public LifeCommand[] getCommandList() {
			return new LifeCommand[] {};
		}
		
		@Override
		public void reset() {

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
		public boolean dropResource() {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean canConsumeResource(final Resource r) {
			// TODO Auto-generated method stub
			return true;
		}
	}
}
