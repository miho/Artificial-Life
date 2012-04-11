package net.cammann.tom.fyp.tests;

import static org.junit.Assert.assertTrue;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.gui.SimulationFrame;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Used to test the GUI elements.
 * 
 * @author TC
 * 
 */
public final class TestGUI {
	
	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(TestGUI.class);
	
	/**
	 * Used to setup logger in test mode.
	 */
	@BeforeClass
	public static void before() {
		PropertyConfigurator.configure("src/test/resources/log4j.properties");
	}
	
	/**
	 * Test the simulation frame gui setup.
	 */
	@Test
	public void guiTest() {
		
		final EnvironmentMap map = TestUtils.getInstance()
				.getBlankMap(400, 400);
		
		final SimulationFrame sf = new SimulationFrame(map);
		
		assertTrue(sf.isStopped());
		assertTrue(sf.getMoveCount() == 0);
		sf.moveOnce();
		assertTrue(sf.getMoveCount() == 1);
		sf.moveOnce();
		assertTrue(sf.getMoveCount() == 2);
		sf.stop();
		logger.info("Move count = " + sf.getMoveCount());
		assertTrue(sf.getMoveCount() == 0);
		sf.moveOnce();
		assertTrue(sf.getMoveCount() == 1);
		assertTrue(sf.isStopped());
		sf.start();
		try {
			Thread.sleep(10);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		sf.pause();
		final int mc = sf.getMoveCount();
		sf.moveOnce();
		assertTrue(sf.getMoveCount() == mc + 1);
		
	}
	
	/**
	 * Test the bestLIfeLauncher.
	 */
	public void launcherTest() {
		
		final EnvironmentMap map = TestUtils.getInstance()
				.getBlankMap(400, 400);
		final ALife life = TestUtils.getInstance().getBlankLife(map);
		
		// BestLifeLauncher launcher = new BestLifeLauncher(evoLab, factory)
		
	}
}
