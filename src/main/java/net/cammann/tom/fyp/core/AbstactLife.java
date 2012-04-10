package net.cammann.tom.fyp.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.jgap.Gene;
import org.jgap.IChromosome;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public abstract class AbstactLife extends ALife {
	
	/**
	 * Logger.
	 */
	private static Logger logger = Logger.getLogger(AbstactLife.class);
	
	// TODO make uMC private
	/**
	 * Tracks number of moves which are 'unique', moves that have are not in the
	 * move memory. Used in fitness function
	 */
	private int uniqueMoveCount = 0;
	
	/**
	 * Amount to decrease energy on consumption of a resource.
	 */
	private static final int ENERGY_LOSS_ON_CONSUME = 15;
	/**
	 * Amount of enery got decrease on consumpiton failure on a resource.
	 */
	private static final int ENERGY_LOSS_ON_CONSUME_FAIL = 5;
	
	/**
	 * Copy constructor.
	 * 
	 * @param life
	 *            to copy
	 */
	public AbstactLife(final ALife life) {
		this.map = life.map;
		initBrain();
		
		orientation = ORIENTATION.UP;
		
		genes = new int[life.getGenes().length];
		
		energy = getGene(0);
		
		this.moveMemory = new ArrayList<Point>();
		
	}
	
	/**
	 * Creates life, creates GP/GA brain, set orientaion to up.
	 * 
	 * @param map
	 *            Used by life/brain when looking for food etc.
	 */
	public AbstactLife(final EnvironmentMap map) {
		this.map = map;
		initBrain();
		orientation = ORIENTATION.UP;
		moveMemory = new ArrayList<Point>();
	}
	
	/**
	 * Constructor using JGAP chromosome.
	 * 
	 * Converts parameter into a list of integers. Also setups logger and some
	 * basic variables.
	 * 
	 * @param chrome
	 *            used to create life from chromosone
	 * @param map
	 *            used for life for reference
	 * 
	 */
	public AbstactLife(final IChromosome chrome, final EnvironmentMap map) {
		this.map = map;
		initBrain();
		orientation = ORIENTATION.UP;
		final int len = chrome.getGenes().length;
		genes = new int[len];
		
		for ( int i = 0 ; i < len ; i++ ) {
			final Gene g = chrome.getGene(i);
			genes[i] = (Integer) g.getAllele();
		}
		
		energy = getGene(0);
		
		this.moveMemory = new ArrayList<Point>();
	}
	
	/**
	 * Used to setup 'brain'.
	 * 
	 * The decision making center is initiailised here.
	 */
	public abstract void initBrain();
	
	/**
	 * Constructor taking raw ints as gene values
	 * 
	 * Sets up some variables such as logger.
	 * 
	 * @param genes
	 *            used to generate life from genes
	 * @param map
	 *            used as reference for life
	 */
	public AbstactLife(final int[] genes, final EnvironmentMap map) {
		initBrain();
		this.map = map;
		this.genes = genes;
		orientation = ORIENTATION.UP;
		this.moveMemory = new ArrayList<Point>();
		// TODO make sure both place sit getGene(0)
		energy = getGene(0);
		
	}
	
	/**
	 * Create empty life instance.
	 * 
	 * Used to copy life
	 */
	@Deprecated
	protected AbstactLife() {
		
	}
	
	@Override
	public final void reset() {
		setEnergy(getStartEnergy());
		setOrientation(new Random().nextInt(ORIENTATION.values().length));
		moveMemory.clear();
	}
	
	/**
	 * For future implementation.
	 * 
	 * @Beta not yet used
	 * @return false due to not being fully implemented
	 */
	@Override
	@Beta
	public final boolean pickUpResource() {
		return false;
		
	}
	
	@Override
	public final void addMoveToMemory(final Point p) {
		
		if (moveMemory.size() > getMemoryLength()) {
			moveMemory.remove(0);
		}
		moveMemory.add(p);
		
	}
	
	@Override
	public final void moveForward() {
		
		moveCount++;
		logger.trace("Move forward");
		logger.trace("Orientation: " + orientation.toString());
		
		final Point p = getPositionAhead();
		final boolean moveValid = map.validPosition(p);
		logger.trace("Valid Move = " + moveValid);
		if (moveValid) {
			logger.trace(p);
			logger.trace(getPosition());
			setX(p.x);
			setY(p.y);
			energy -= ENERGY_LOSS_ON_CONSUME_FAIL;
			
		} else {
			logger.trace("Fail Move Forward");
			logger.trace("Position: " + getPosition());
			
			decrementEnegery(ENERGY_LOSS_ON_CONSUME);
		}
		addMoveToMemory(getPosition());
	}
	
	@Override
	public final Point getPositionAhead() {
		if (getOrientation() == ORIENTATION.UP) {
			return new Point(p.x, p.y - SimpleMap.STEP_SIZE);
		} else if (getOrientation() == ORIENTATION.RIGHT) {
			return new Point(p.x + SimpleMap.STEP_SIZE, p.y);
		} else if (getOrientation() == ORIENTATION.DOWN) {
			return new Point(p.x, p.y + SimpleMap.STEP_SIZE);
		} else {
			return new Point(p.x - SimpleMap.STEP_SIZE, p.y);
			
		}
	}
	
	@Override
	public final Point getPositionAhead(final int steps) {
		if (getOrientation() == ORIENTATION.UP) {
			return new Point(p.x, p.y - SimpleMap.STEP_SIZE * steps);
		} else if (getOrientation() == ORIENTATION.RIGHT) {
			return new Point(p.x + SimpleMap.STEP_SIZE * steps, p.y);
		} else if (getOrientation() == ORIENTATION.DOWN) {
			return new Point(p.x, p.y + SimpleMap.STEP_SIZE * steps);
		} else {
			return new Point(p.x - SimpleMap.STEP_SIZE * steps, p.y);
		}
	}
	
	// public boolean consumePosition(Point p) {
	// if (map.hasResource(p)) {
	// Resource r = map.getResource(p);
	// if (consumeResource(r)) {
	// map.removeResource(p);
	// return true;
	// }
	//
	// }
	// return false;
	// }
	
	@Override
	public final void doMove() {
		if (energy > 0) {
			brain.think();
			logger.trace("Energy After move: " + energy);
			logger.trace("Positon: " + getPosition());
			if (!hasMoveInMemory(getPosition())) {
				addMoveToMemory(getPosition());
				uniqueMoveCount++;
			}
		}
		
	}
	
	@Override
	public final boolean consumeResource(final Resource r) {
		if (r == null) {
			logger.error("NULLL RESOURCE");
			return false;
		}
		if (canConsumeResource(r)) {
			
			map.consumeResource(this);
			return true;
		}
		return false;
	}
	
	@Override
	public final boolean hasMoveInMemory(final Point position) {
		if (moveMemory.contains(position)) {
			return true;
		}
		return false;
	}
	
	@Override
	@Deprecated
	public final List<Point> getMoveMemory() {
		return moveMemory;
	}
	
	/**
	 * Returns image used to visualise life.
	 * 
	 * @return image of ALife
	 */
	protected final Image getImage() {
		
		final int scale = (int) (getRadius() * 3);
		
		final BufferedImage bi = new BufferedImage(scale, scale,
				BufferedImage.TYPE_INT_ARGB);
		
		final Graphics2D g2 = bi.createGraphics();
		g2.setColor(Color.GREEN);
		g2.fill(new Ellipse2D.Double(0, 0, scale, scale));
		// CHECKSTYLE.OFF: MagicNumber
		if (orientation == ORIENTATION.UP) {
			g2.setPaint(Color.BLACK);
			g2.fill(new Ellipse2D.Double(scale / 2 - (scale / 4), scale / 4,
					scale / 4, scale / 4));
			g2.fill(new Ellipse2D.Double(scale / 2 + (scale / 4), scale / 4,
					scale / 4, scale / 4));
		} else if (orientation == ORIENTATION.RIGHT) {
			g2.setPaint(Color.BLACK);
			g2.fill(new Ellipse2D.Double(scale / 2 + (scale / 4), scale / 2
					+ scale / 4, scale / 4, scale / 4));
			g2.fill(new Ellipse2D.Double(scale / 2 + scale / 4, scale / 4,
					scale / 4, scale / 4));
		} else if (orientation == ORIENTATION.DOWN) {
			g2.setPaint(Color.BLACK);
			g2.fill(new Ellipse2D.Double(scale / 2 + (scale / 4), scale / 2
					+ scale / 4, scale / 4, scale / 4));
			g2.fill(new Ellipse2D.Double(scale / 2 - (scale / 4), scale / 2
					+ scale / 4, scale / 4, scale / 4));
			
		} else {
			g2.setPaint(Color.BLACK);
			g2.fill(new Ellipse2D.Double(scale / 2 - (scale / 4), scale / 2
					+ scale / 4, scale / 4, scale / 4));
			g2.fill(new Ellipse2D.Double(scale / 2 - (scale / 4), scale / 4,
					scale / 4, scale / 4));
			// CHECKSTYLE.ON: MAGICNUMBER
		}
		
		return bi.getScaledInstance(scale, scale, Image.SCALE_SMOOTH);
		
	}
	
	@Override
	public final void turnLeft() {
		moveCount++;
		
		switch (orientation) {
			case UP:
				setOrientation(ORIENTATION.LEFT);
				break;
			case RIGHT:
				setOrientation(ORIENTATION.UP);
				break;
			case DOWN:
				setOrientation(ORIENTATION.RIGHT);
				break;
			case LEFT:
				setOrientation(ORIENTATION.DOWN);
				break;
			default:
				throw new IllegalStateException("Illegeal orientation");
		}
		decrementEnegery(4);
		
	}
	
	@Override
	public final void turnRight() {
		moveCount++;
		
		switch (orientation) {
			case UP:
				setOrientation(ORIENTATION.RIGHT);
				break;
			case RIGHT:
				setOrientation(ORIENTATION.DOWN);
				break;
			case DOWN:
				setOrientation(ORIENTATION.LEFT);
				break;
			case LEFT:
				setOrientation(ORIENTATION.UP);
				break;
			default:
				throw new IllegalStateException("Illegeal orientation");
		}
		decrementEnegery(4);
	}
	
	@Override
	public final boolean isHoldingResource() {
		return false;
	}
	
	@Override
	public final void draw(final Graphics2D g2) {
		g2.drawImage(getImage(), getX(), getY(), null);
	}
	
}
