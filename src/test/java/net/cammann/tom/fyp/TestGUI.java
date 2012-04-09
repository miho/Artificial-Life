package net.cammann.tom.fyp;

import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.gui.SimulationFrame;

import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestGUI {
	/**
	 * Used to setup logger in test mode
	 */
	@BeforeClass
	public static void before() {
		PropertyConfigurator.configure("src/test/resources/log4j.properties");
	}
	
	@Test
	public void guiTest() {
		
		EnvironmentMap map = TestUtils.getInstance().getBlankMap(400, 400);
		
		SimulationFrame sf = new SimulationFrame(map);
	}
}
