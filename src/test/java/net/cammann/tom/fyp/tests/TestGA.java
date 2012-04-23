package net.cammann.tom.fyp.tests;

import net.cammann.tom.fyp.basicLife.BasicLifeFactory;
import net.cammann.tom.fyp.core.GeneticAlgorithmRunner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test of genetic algorithms.
 * 
 * @author TC
 * 
 */
public final class TestGA {
	
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
		final GeneticAlgorithmRunner genelab = new GeneticAlgorithmRunner(factory);
		
	}
}
