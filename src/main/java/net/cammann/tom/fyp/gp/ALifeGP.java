package net.cammann.tom.fyp.gp;

import java.awt.Point;
import java.util.ArrayList;

import net.cammann.tom.fyp.commands.LifeCommand;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.AbstactLife;
import net.cammann.tom.fyp.core.Beta;
import net.cammann.tom.fyp.core.Brain;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.core.Resource;

import org.apache.log4j.Logger;
import org.jgap.gp.IGPProgram;

/**
 * This class is used for creating the GP ALife.
 * 
 * Takes in IGPProgram from JGAP and converts it into ALife
 * 
 * @author TC
 * 
 */
public final class ALifeGP extends AbstactLife {
	
	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(ALifeGP.class);
	
	/**
	 * Energy to init life with.
	 */
	private static final int START_ENERGY = 1000;
	
	/**
	 * Length of memory.
	 */
	private static final int MEMORY_LENGTH = 10;
	
	/**
	 * Used in Brain for controlling the ALife.
	 */
	private final IGPProgram gp;
	
	/**
	 * Create ALife from IGPProgram.
	 * 
	 * Uses JGAP GPProgram to create life
	 * 
	 * @param igp
	 *            from JGAP
	 * @param map
	 *            used as reference by ALife
	 */
	public ALifeGP(final IGPProgram igp, final EnvironmentMap map) {
		super(map);
		this.gp = igp;
		
		initBrain();
		orientation = ORIENTATION.UP;
		
		energy = START_ENERGY;
		
		this.moveMemory = new ArrayList<Point>();
		
	}
	
	@Override
	public LifeCommand[] getCommandList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void initBrain() {
		logger.trace("Init brain");
		setBrain(new Brain(this) {
			
			@Override
			public int think() {
				final Object[] o = { life };
				final double x = gp.execute_double(0, o);
				if (x < 0) {
					moveForward();
				} else if (x > 0 && x < 10) {
					turnLeft();
				} else if (x > 10 && x < 20) {
					turnRight();
				}
				moveCount++;
				energy -= 5;
				return 1;
			}
		});
		
	}
	
	@Override
	public ALife clone() {
		return new ALifeGP(gp, getMap());
	}
	
	@Override
	public int getMemoryLength() {
		// TODO add limit in genes?
		return MEMORY_LENGTH;
	}
	
	@Override
	@Beta
	public boolean dropResource() {
		return false;
	}
	
	@Override
	public boolean canConsumeResource(final Resource r) {
		// Eat anything
		return true;
	}
	
	@Override
	protected int getStartEnergy() {
		// TODO Auto-generated method stub
		return START_ENERGY;
	}
	
}
