package net.cammann.tom.fyp.tests;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import junit.framework.Assert;
import net.cammann.tom.fyp.basicLife.BasicFitnessFunction;
import net.cammann.tom.fyp.basicLife.BasicLife;
import net.cammann.tom.fyp.basicLife.BasicLifeFactory;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.EvolutionFactory;
import net.cammann.tom.fyp.core.MapObject;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jgap.Chromosome;
import org.jgap.Configuration;
import org.jgap.Gene;
import org.jgap.Genotype;
import org.jgap.InvalidConfigurationException;
import org.jgap.impl.DefaultConfiguration;
import org.jgap.impl.IntegerGene;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test of the 'basic' life objects.
 * 
 * @author TC
 * 
 */
public final class TestBasicLife {
	
	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(TestBasicLife.class);
	
	/**
	 * Used to setup logger in test mode.
	 */
	@BeforeClass
	public static void before() {
		PropertyConfigurator.configure("src/test/resources/log4j.properties");
	}
	
	/**
	 * Test correctness of basicFactory.
	 * 
	 * Tests creation of map and Life and its parameters
	 */
	@Test
	public void testBasicFactory() {
		// TODO remove 'magic numbers'
		final int numResources = 40;
		final int mapWidth = 200;
		final int mapHeight = 400;
		final int numObstacles = 5;
		
		final EvolutionFactory factory = new BasicLifeFactory();
		factory.setNumOfObstacles(numObstacles);
		factory.setNumOfResources(numResources);
		factory.setMapHeight(mapHeight);
		factory.setMapWidth(mapWidth);
		
		final EnvironmentMap map = factory.createMap();
		final ALife life = TestUtils.getInstance().getBlankLife(map);
		factory.createLife(life, map);
		
		final Iterator<MapObject> resourceIterator = map.getResourceIterator();
		int resourceCount = 0;
		while (resourceIterator.hasNext()) {
			resourceIterator.next();
			resourceCount++;
		}
		logger.info("resouce count: " + resourceCount);
		assertTrue(resourceCount == numResources);
		
		final Iterator<MapObject> obstacleIterator = map.getObstacleIterator();
		int obstacleCount = 0;
		while (obstacleIterator.hasNext()) {
			obstacleIterator.next();
			obstacleCount++;
		}
		logger.info("obstacle count: " + obstacleCount);
		assertTrue(obstacleCount == numObstacles);
		
		assertTrue(map.getWidth() == mapWidth);
		assertTrue(map.getHeight() == mapHeight);
		
	}
	
	/**
	 * Method of asserting correctness of BasicLife.
	 */
	@Test
	public void testBasicLife() {
		
		final int[] genes = { 0, 1, 2, 3, 4, 5, 6 };
		final EnvironmentMap map = TestUtils.getInstance()
				.getBlankMap(400, 400);
		ALife life = new BasicLife(genes, map);
		
		assertTrue(life.getGenes().equals(genes));
		
		for ( int i = 0; i < genes.length; i++ ) {
			assertTrue(life.getGene(i) == i);
		}
		
		life.reset();
		
		final Configuration conf = new DefaultConfiguration();
		
		final Gene[] geneArray = new Gene[10];
		try {
			for ( int i = 0; i < geneArray.length; i++ ) {
				geneArray[i] = new IntegerGene(conf, i, i);
			}
			
			final Chromosome sampleChromosome = new Chromosome(conf, geneArray);
			
			conf.setSampleChromosome(sampleChromosome);
			
			conf.setPopulationSize(1);
			
			conf.setFitnessFunction(new BasicFitnessFunction(
					new BasicLifeFactory()));
			
			final Genotype genotype = Genotype.randomInitialGenotype(conf);
			
			life = new BasicLife(genotype.getChromosomes()[0], map);
			
			for ( int i = 0; i < geneArray.length; i++ ) {
				assertTrue(life.getGene(i) == i);
			}
			
		} catch (final InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Assert.fail();
		}
		
	}
}
