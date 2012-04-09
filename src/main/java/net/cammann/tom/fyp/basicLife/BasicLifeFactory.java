package net.cammann.tom.fyp.basicLife;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.EvolutionFactory;
import net.cammann.tom.fyp.gp.ALifeGP;
import net.cammann.tom.fyp.gp.GPLifeFitFunc;

import org.jgap.FitnessFunction;
import org.jgap.IChromosome;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;

public class BasicLifeFactory implements EvolutionFactory {
	
	private final int NUM_FIT_FUNC_CYCLES = 3;
	private final int NUM_CLONES = 1;
	private final int RUN_LEN = 400;
	private int numOfResources = 200;
	private int numOfObstacles = 0;
	private int mapWidth = 400;
	private int mapHeight = 200;
	
	@Override
	public int getNumOfResources() {
		return numOfResources;
	}
	
	@Override
	public void setNumOfResources(int numOfResources) {
		if (numOfResources < 0) {
			throw new IllegalArgumentException(
					"num resource cannot be less than 0");
		}
		this.numOfResources = numOfResources;
	}
	
	@Override
	public int getNumOfObstacles() {
		return numOfObstacles;
	}
	
	@Override
	public void setNumOfObstacles(int numOfObstacles) {
		if (numOfObstacles < 0) {
			throw new IllegalArgumentException(
					"num obstaces cannot be less than 0");
		}
		this.numOfObstacles = numOfObstacles;
	}
	
	@Override
	public int getMapWidth() {
		return mapWidth;
	}
	
	@Override
	public void setMapWidth(int mapWidth) {
		if (mapWidth < 0) {
			throw new IllegalArgumentException(
					"map width cannot be less than 0");
		}
		this.mapWidth = mapWidth;
	}
	
	@Override
	public int getMapHeight() {
		return mapHeight;
	}
	
	@Override
	public void setMapHeight(int mapHeight) {
		if (mapHeight < 0) {
			throw new IllegalArgumentException(
					"map height cannot be less than 0");
		}
		this.mapHeight = mapHeight;
	}
	
	@Override
	public ALife createLife(int[] genes, EnvironmentMap map) {
		return new BasicLife(genes, map);
	}
	
	@Override
	public ALife createLife(IChromosome chromo, EnvironmentMap map) {
		return new BasicLife(chromo, map);
	}
	
	@Override
	public FitnessFunction getFitnessFunction() {
		return new BugFitnessFunction(this);
	}
	
	@Override
	public ALife nullInstance() {
		return new BasicLife();
	}
	
	@Override
	public ALife createLife(IGPProgram gp, EnvironmentMap map) {
		return new ALifeGP(gp, map);
	}
	
	@Override
	public EnvironmentMap createMap() {
		return new BasicMap(mapWidth, mapHeight, numOfResources, numOfObstacles);
	}
	
	@Override
	public int getNumClones() {
		return NUM_CLONES;
	}
	
	@Override
	public int getFitnessFunctionRuns() {
		return NUM_FIT_FUNC_CYCLES;
	}
	
	@Override
	public GPFitnessFunction getGPFitnessFunction() {
		return new GPLifeFitFunc(this);
	}
	
	@Override
	public int getLenOfFitFuncRun() {
		return RUN_LEN;
	}
	
	@Override
	public ALife createLife(ALife life, EnvironmentMap map) {
		ALife second_life = life.clone();
		second_life.reset();
		second_life.setMap(map);
		return second_life;
	}
}
