package net.cammann.tom.fyp;

import net.cammann.tom.fyp.basicLife.BasicLifeFactory;
import net.cammann.tom.fyp.core.GeneLab;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test of genetic algorithms
 * 
 * @author TC
 * 
 */
public class TestGA {
	
	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(TestGA.class);
	
	/**
	 * Used to setup logger in test mode.
	 */
	@BeforeClass
	public static void before() {
		PropertyConfigurator.configure("src/test/resources/log4j.properties");
	}
	
	/**
	 * Simple test for GeneLab.
	 */
	@Test
	public void firstTest() {
		logger.trace("Testing Genelab");
		final BasicLifeFactory factory = new BasicLifeFactory();
		final GeneLab genelab = new GeneLab(factory);
		
	}
}
