package net.cammann.tom.fyp;

import net.cammann.tom.fyp.commands.LifeCommand;
import net.cammann.tom.fyp.core.ABug;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.SimpleMap;

public class TestUtils {
	private static TestUtils utils = new TestUtils();
	
	private TestUtils() {
	}
	
	public static TestUtils getInstance() {
		return utils;
	}
	
	public EnvironmentMap getBlankMap(int width, int height) {
		
		return new BlankMap(width, height);
	}
	
	/**
	 * Class used for testing.
	 * 
	 * Does not init any resources
	 * 
	 * 
	 */
	private class BlankMap extends SimpleMap {
		
		private BlankMap(int w, int h) {
			super(w, h);
		}
		
		@Override
		public void initResources() {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public ALife getBlankLife(EnvironmentMap map) {
		return new BlankLife(map);
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
	}
}
