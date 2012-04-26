package net.cammann.tom.fyp.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import junit.framework.Assert;
import net.cammann.tom.fyp.basicLife.BasicLifeFactory;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.EvolutionCycleListener;
import net.cammann.tom.fyp.core.EvolutionModule;
import net.cammann.tom.fyp.gui.BestLifeLauncher;
import net.cammann.tom.fyp.gui.LoggingFrame;
import net.cammann.tom.fyp.gui.SimulationFrame;
import net.cammann.tom.fyp.utils.VisibilityEvent;
import net.cammann.tom.fyp.utils.VisibilityListener;

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
	 * Will test the BestLifeLauncher class.
	 * 
	 * 
	 */
	@Test
	public void testBestLifeLauncher() {

		final BasicLifeFactory blf = new BasicLifeFactory();

		final EnvironmentMap map = TestUtils.getInstance()
				.getBlankMap(400, 400);

		final EvolutionModule g = new EvolutionModule() {

			@Override
			public ALife getFittestLife() {
				return TestUtils.getInstance().getBlankLife(map);
			}

			@Override
			public void addEvolutionCycleListener(
					final EvolutionCycleListener ecl) {
				// TODO Auto-generated method stub

			}

			@Override
			public void removeEvolutionCycleListener(
					final EvolutionCycleListener ecl) {
				// TODO Auto-generated method stub

			}

			@Override
			public void start() {
				// TODO Auto-generated method stub

			}

			@Override
			public int getPopulationSize() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getNumGenerations() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void setMaxGenerations(final int i) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setPopulationSize(final int i) {
				// TODO Auto-generated method stub

			}

			@Override
			public void startpause() {
				// TODO Auto-generated method stub

			}
		};

		final BestLifeLauncher bestLL = new BestLifeLauncher(g, blf);

		assertEquals(g.getFittestLife().getMap(), map);

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

		LoggingFrame.getInstance().setVisible("guiTest", false);
		assertFalse(LoggingFrame.getInstance().isVisible());
		sf.hideLogFrame();
		assertFalse(LoggingFrame.getInstance().isVisible());
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

	@Test
	public void testLoggingFrame() {

		final LoggingFrame loggingFrame = LoggingFrame.getInstance();

		loggingFrame.setVisible("firstLogTest", true);
		loggingFrame.setVisible("2ndLogTest", false);
		final ArrayList<Boolean> checkList = new ArrayList<Boolean>();
		loggingFrame.addVisibilityListener(new VisibilityListener() {

			@Override
			public void VisibilityChanged(final VisibilityEvent e) {
				checkList.add(e.isVisible());
			}
		});
		logger.debug("CheckList size: " + checkList.size());
		logger.debug("Set logger visible");
		loggingFrame.setVisible("3rdLogTest", true);

		logger.debug("CheckList size: " + checkList.size());
		assertTrue(checkList.size() == 1);
		assertTrue(checkList.get(0));
		assertTrue(loggingFrame.isVisible());
		logger.debug("Set logger NOT visible");

		loggingFrame.setVisible("4thLogTest", false);
		logger.debug("CheckList size: " + checkList.size());
		assertTrue(checkList.size() == 2);
		assertFalse(checkList.get(1));
		assertFalse(loggingFrame.isVisible());
		try {

			final Field dropBoxField = LoggingFrame.getInstance().getClass()
					.getDeclaredField("dropList");

			dropBoxField.setAccessible(true);

			final JComboBox ref = (JComboBox) dropBoxField.get(loggingFrame);
			ref.setSelectedIndex(0);

			final Field loggingLinesField = LoggingFrame.class
					.getDeclaredField("loggingStrings");
			loggingLinesField.setAccessible(true);

			final String checkLog = "CHECK LOG";
			logger.info(checkLog);
			// sleep as log is concurrent
			Thread.sleep(10);
			final List<String> loggingStrings = (List<String>) loggingLinesField
					.get(loggingFrame);

			final String last = loggingStrings.get(loggingStrings.size() - 1);
			assertTrue(last.contains(checkLog));

		} catch (final Exception e) {
			logger.fatal("Bad reflection...", e);
			Assert.fail();
		}

	}
}
