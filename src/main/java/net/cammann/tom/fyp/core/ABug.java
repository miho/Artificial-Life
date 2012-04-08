package net.cammann.tom.fyp.core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jgap.Gene;
import org.jgap.IChromosome;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public abstract class ABug extends ALife {

	static Logger logger = Logger.getLogger(ABug.class);

	// TODO make uMC private
	/**
	 * Tracks number of moves which are 'unique', moves that have are not in the
	 * move memory. Used in fitness function
	 */
	public int uniqueMoveCount = 0;

	/**
	 * Copy constructor
	 * 
	 * @param bug
	 */
	public ABug(ALife bug) {
		this.map = bug.map;
		initBrain();

		orientation = ORIENTATION.UP;

		genes = new int[bug.getGenes().length];

		energy = getGene(0);

		this.moveMemory = new ArrayList<Point>();

	}

	/**
	 * Constructor using JGAP chromosome.
	 * 
	 * Converts parameter into a list of integers. Also setups logger and some
	 * basic variables.
	 * 
	 * @param chrome
	 */
	public ABug(IChromosome chrome, EnvironmentMap map) {
		this.setMap(map);
		initBrain();

		orientation = ORIENTATION.UP;
		int len = chrome.getGenes().length;
		genes = new int[len];

		for (int i = 0; i < len; i++) {
			Gene g = chrome.getGene(i);
			genes[i] = (Integer) g.getAllele();
		}

		energy = getGene(0);

		this.moveMemory = new ArrayList<Point>();
	}

	public abstract void initBrain();

	/**
	 * Constructor taking raw ints as gene values
	 * 
	 * Sets up some variables such as logger.
	 * 
	 * @param genes
	 */
	public ABug(int[] genes, EnvironmentMap map) {
		initBrain();
		this.map = map;
		this.genes = genes;
		orientation = ORIENTATION.UP;
		this.moveMemory = new ArrayList<Point>();
		energy = getGene(0);

	}

	protected ABug() {

	}

	@Override
	public boolean pickUpResource() {
		return false;
	}

	@Override
	public void addMoveToMemory(Point p) {

		if (moveMemory.size() > getGene(GENE_TYPE.MEMORY_LENGTH)) {
			moveMemory.remove(0);
		}
		moveMemory.add(p);
	}

	@Override
	public void setMoveMemory(List<Point> pointList) {
		moveMemory = pointList;
	}

	@Override
	public void moveForward() {

		MOVE_COUNT++;

		logger.trace("Move forward");
		logger.trace("Orientation: " + orientation.toString());
		int STEP = 10;
		boolean failMove = false;
		switch (getOrientation()) {
		case UP:
			if ((getY() - STEP) >= 0) {
				setY(getY() - STEP);
			} else {
				failMove = true;
				// throw new IllegalStateException();
			}
			break;
		case RIGHT:
			if ((getX() + STEP) <= map.getWidth()) {
				setX(getX() + STEP);
			} else {
				failMove = true;
				// throw new IllegalStateException();
			}
			break;
		case DOWN:
			if ((getY() + STEP) <= map.getHeight()) {
				setY(getY() + STEP);
			} else {
				failMove = true;
				// throw new IllegalStateException();
			}
			break;

		case LEFT:
			if ((getX() - STEP) >= 0) {
				setX(getX() - STEP);
			} else {
				failMove = true;
				// throw new IllegalStateException();
			}
			break;
		default:
			failMove = true;
			throw new IllegalStateException("Illegeal orientation");

		}
		if (!failMove) {
			decrementEnegery(10);
		} else {
			logger.trace("Fail Move Forward");
			logger.trace("Position: " + getPosition());

			decrementEnegery(15);
		}
		addMoveToMemory(getPosition());
	}

	public boolean consumePosition(Point p) {
		if (map.hasResource(p)) {
			Resource r = map.getResource(p);
			if (consumeResource(r)) {
				map.removeResource(p);
				return true;
			}

		}
		return false;
	}

	@Override
	public void doMove() {
		brain.think();
		if (!hasMoveInMemory(getPosition())) {
			addMoveToMemory(getPosition());
			uniqueMoveCount++;
		}

	}

	@Override
	public boolean consumeResource(Resource r) {
		if (r == null) {
			logger.warn("NULLL RESOURCE");
			return false;
		}
		if (canConsumeResource(r)) {
			energy += r.getCalories();
			r.consume();
			return true;
		}
		return false;
	}

	@Override
	public boolean hasMoveInMemory(Point position) {
		if (moveMemory.contains(position)) {
			return true;
		}
		return false;
	}

	@Override
	public List<Point> getMoveMemory() {
		return moveMemory;
	}

	protected Image getImage() {
		BufferedImage bi = new BufferedImage(15, 15,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = bi.createGraphics();
		g2.setColor(Color.GREEN);
		g2.fill(new Ellipse2D.Double(0, 0, 15, 15));

		if (orientation == ORIENTATION.UP) {
			g2.setPaint(Color.BLACK);
			g2.fill(new Ellipse2D.Double(15 / 2 - (15 / 4), 15 / 4, 15 / 4,
					15 / 4));
			g2.fill(new Ellipse2D.Double(15 / 2 + (15 / 4), 15 / 4, 15 / 4,
					15 / 4));
		} else if (orientation == ORIENTATION.RIGHT) {
			g2.setPaint(Color.BLACK);
			g2.fill(new Ellipse2D.Double(15 / 2 + (15 / 4), 15 / 2 + 15 / 4,
					15 / 4, 15 / 4));
			g2.fill(new Ellipse2D.Double(15 / 2 + 15 / 4, 15 / 4, 15 / 4,
					15 / 4));
		} else if (orientation == ORIENTATION.DOWN) {
			g2.setPaint(Color.BLACK);
			g2.fill(new Ellipse2D.Double(15 / 2 + (15 / 4), 15 / 2 + 15 / 4,
					15 / 4, 15 / 4));
			g2.fill(new Ellipse2D.Double(15 / 2 - (15 / 4), 15 / 2 + 15 / 4,
					15 / 4, 15 / 4));

		} else {
			g2.setPaint(Color.BLACK);
			g2.fill(new Ellipse2D.Double(15 / 2 - (15 / 4), 15 / 2 + 15 / 4,
					15 / 4, 15 / 4));
			g2.fill(new Ellipse2D.Double(15 / 2 - (15 / 4), 15 / 4, 15 / 4,
					15 / 4));
		}
		return bi.getScaledInstance(15, 15, Image.SCALE_SMOOTH);

	}

	@Override
	public void turnLeft() {
		MOVE_COUNT++;

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
	public void turnRight() {
		MOVE_COUNT++;

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
	public boolean canConsumeResource(Resource r) {
		// EATS ANYTHING
		return true;
	}

	@Override
	public boolean dropResource() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHoldingResource() {
		return false;
	}

	@Override
	public void draw(Graphics2D g2) {
		g2.drawImage(getImage(), getX(), getY(), null);
	}

}
