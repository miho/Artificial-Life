package net.cammann.tom.fyp.core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author TC
 * @version 0.8
 * @since 31/01/2012
 * 
 */
public class BasicBrain extends Brain {
	
	public BasicBrain(ALife life) {
		super(life);
		// commands = life
	}
	
	private List<Point> resourcesInRange(int range) {
		
		int x = life.getX();
		int y = life.getY();
		List<Point> rList = new ArrayList<Point>();
		
		for (int i = -(range / 2); i < (range / 2); i++) {
			for (int j = -(range / 2); j < (range / 2); j++) {
				
				if (life.getMap().hasResource(x + i * STEP, y + i * STEP)) {
					rList.add(new Point(x + i * STEP, y + i * STEP));
				}
				
			}
		}
		return rList;
	}
	
	// private List<Point> consumableResourcesInRange(int range) {
	//
	// int x = life.getX();
	// int y = life.getY();
	// List<Point> rList = new ArrayList<Point>();
	// for (int i = -(range / 2); i < (range / 2); i++) {
	// for (int j = -(range / 2); j < (range / 2); j++) {
	//
	// if (life.getMap().hasResource(x + i * STEP, y + i * STEP)) {
	// rList.add(new Point(x + i * STEP, y + i * STEP));
	// }
	// }
	// }
	// return rList;
	// }
	
	private boolean canSeeLife() {
		
		int lifeRange = life.getGene(GENE_TYPE.SEE_LIFE_RANGE);
		
		for (int i = 1; i < lifeRange; i++) {
			Point p = life.getPositionAhead(i);
			if (!life.getMap().hasLife(p)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean canSeeObstacle() {
		
		int wallRange = life.getGene(GENE_TYPE.SEE_WALL_RANGE);
		
		for (int i = 1; i < wallRange; i++) {
			Point p = life.getPositionAhead(i);
			if (!life.getMap().validPosition(p)) {
				return true;
			}
		}
		
		return true;
		
	}
	
	private boolean canSeeResource() {
		
		// int foodRange = life.getGene(GENE_TYPE.SEE_FOOD_RANGE);
		int foodRange = 5;
		log.trace("Checking for resources");
		for (int i = 1; i < foodRange; i++) {
			Point p = life.getPositionAhead(i);
			
			if (life.getMap().hasResource(p)) {
				log.trace("Resource at: " + p);
				return true;
			}
		}
		return false;
		
	}
	
	// public List<LifeCommand> getCommandList() {
	
	private int moveByNum(int n) {
		life.getCommandList()[n].execute(life);
		log.trace("Move: " + n);
		return 0;
	}
	
	@Override
	public int think() {
		
		if (life.getMap().hasResource(life.getPosition())) {
			log.trace("Move - on food");
			return moveByNum(life.getGene(GENE_TYPE.ON_FOOD_ACTION));
			// life.consume();
			
		} else if (canSeeResource()) {
			log.trace("Move - can see food");
			return moveByNum(life.getGene(GENE_TYPE.SEE_FOOD_ACTION));
			// life.moveForward();
			
		} else if (resourcesInRange(life.getGene(GENE_TYPE.FOOD_NEARBY_RANGE))
				.size() > 0) {
			log.trace("Move - food nearby");
			return moveByNum(life.getGene(GENE_TYPE.FOOD_NEARBY_ACTION));
			// life.turnLeft();
			
		} else if (canSeeObstacle()) {
			log.trace("Move - can see obstacle");
			
			return moveByNum(life.getGene(GENE_TYPE.SEE_WALL_ACTION));
			
		} else if (canSeeLife()) {
			log.trace("Move - can see life");
			return moveByNum(life.getGene(GENE_TYPE.SEE_LIFE_ACTION));
		} else {
			log.trace("Move - other");
			// SEE NOTHING
			// if move in memory?
			// //
			
			/*
			 * THIS IS BAD! - at least it looks horrible
			 * 
			 * When there are no inputs, fall back to a predefined movement
			 * 
			 * It constructs all the possible variations of what could be in
			 * memory and calls a different gene for each of this different
			 * situations. ie. if left move and right move is memory, calls gene
			 * x...
			 */
			// gene reference number. completely static.
			// represents the offset in the chromosone. from 10 on wards are
			// these random move genes.
			int gRef = 12;
			Set<Integer> a = new HashSet<Integer>();
			for (int i = 0; i < 4; i++) {
				if (isMoveInMemory(i)) {
					a.add(i);
				}
			}
			if (a.size() == 0) {
				return moveByNum(life.getGene(gRef));
			}
			List<Set<Integer>> s2List = new ArrayList<Set<Integer>>();
			List<Set<Integer>> s3List = new ArrayList<Set<Integer>>();
			
			@SuppressWarnings("unused")
			int[][] s1 = { { 0 }, { 1 }, { 2 }, { 3 } };
			int[][] s2 = { { 0, 1 }, { 0, 2 }, { 0, 3 }, { 1, 2 }, { 1, 3 },
					{ 2, 3 } };
			int[][] s3 = { { 0, 1, 2 }, { 0, 1, 3 }, { 0, 2, 3 }, { 1, 2, 3 } };
			@SuppressWarnings("unused")
			int[][] s4 = { { 1, 2, 3, 4 } };
			
			for (int i = 0; i < 6; i++) {
				Set<Integer> s = new HashSet<Integer>();
				s.add(s2[i][0]);
				s.add(s2[i][1]);
				s2List.add(s);
			}
			
			for (int i = 0; i < 4; i++) {
				Set<Integer> s = new HashSet<Integer>();
				s.add(s3[i][0]);
				s.add(s3[i][1]);
				s.add(s3[i][2]);
				s3List.add(s);
			}
			
			if (a.size() == 1) {
				int k = a.iterator().next();
				return moveByNum(life.getGene(gRef + k));
			} else if (a.size() == 2) {
				int k = s2List.indexOf(a);
				return moveByNum(life.getGene(gRef + 4 + k));
				// return 4+k gene
			} else if (a.size() == 3) {
				int k = s3List.indexOf(a);
				return moveByNum(life.getGene(gRef + 10 + k));
				// return 10+k gene
			} else {
				return moveByNum(life.getGene(gRef + 15));
			}
			
		}
		
		// return moveByNum(4);
		
	}
	
	@SuppressWarnings("unused")
	private boolean moveInMemory(int x, int y) {
		return moveInMemory(new Point(x, y));
	}
	
	private boolean moveInMemory(Point p) {
		return life.getMoveMemory().contains(p);
	}
	
	@SuppressWarnings("unused")
	private void moveMemCheck() {
		
		String s0 = isMoveInMemory(0) ? "x" : "#";
		String s1 = isMoveInMemory(1) ? "x" : "#";
		String s2 = isMoveInMemory(2) ? "x" : "#";
		String s3 = isMoveInMemory(3) ? "x" : "#";
		
		log.trace(" - " + s0 + " -");
		log.trace(" " + s3 + " " + "o" + " " + s1 + "-");
		log.trace(" - " + s2 + " -");
		// - 0 -
		// 1 x 0
		// - 1
	}
	
	private boolean isMoveInMemory(int n) {
		int x = life.getX();
		int y = life.getY();
		
		if (n == 0) {
			return life.getMoveMemory().contains(new Point(x, y - STEP));
		} else if (n == 1) {
			return life.getMoveMemory().contains(new Point(x + STEP, y));
		} else if (n == 2) {
			return life.getMoveMemory().contains(new Point(x, y + STEP));
		} else if (n == 3) {
			return life.getMoveMemory().contains(new Point(x - STEP, y));
		} else {
			throw new IllegalArgumentException("CANT CHOOSE: " + n);
		}
	}
	
	@SuppressWarnings("unused")
	private List<Integer> movesAvaiable() {
		List<Integer> iList = new ArrayList<Integer>();
		int x = life.getX();
		int y = life.getY();
		
		if (life.getMap().validPosition(x, y - STEP)) {
			iList.add(0);
		}
		if (life.getMap().validPosition(x + STEP, y)) {
			iList.add(1);
		}
		if (life.getMap().validPosition(x, y + STEP)) {
			iList.add(2);
		}
		if (life.getMap().validPosition(x - STEP, y)) {
			iList.add(3);
		}
		return iList;
	}
	
}
