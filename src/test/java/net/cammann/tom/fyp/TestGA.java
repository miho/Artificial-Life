package net.cammann.tom.fyp;

import net.cammann.tom.fyp.basicLife.BasicLifeFactory;
import net.cammann.tom.fyp.core.GeneLab;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestGA {
	static Logger logger = Logger.getLogger(TestGA.class);
	
	/**
	 * Used to setup logger in test mode
	 */
	@BeforeClass
	public static void before() {
		PropertyConfigurator.configure("src/test/resources/log4j.properties");
	}
	
	@Test
	public void firstTest() {
		BasicLifeFactory factory = new BasicLifeFactory();
		GeneLab genelab = new GeneLab(factory);
		
	}
}
