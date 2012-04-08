package net.cammann.tom.fyp.gp;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import net.cammann.tom.fyp.commands.LifeCommand;
import net.cammann.tom.fyp.core.ABug;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.Brain;
import net.cammann.tom.fyp.core.EnvironmentMap;

import org.apache.log4j.Logger;
import org.jgap.gp.IGPProgram;

public class ALifeGP extends ABug {
	
	static Logger logger = Logger.getLogger(ALifeGP.class);
	
	private final IGPProgram gp;
	
	private Object getThis() {
		return this;
	}
	
	public ALifeGP(IGPProgram gp, EnvironmentMap map) {
		this.setMap(map);
		initBrain();
		orientation = ORIENTATION.UP;
		
		energy = 1000;
		
		this.moveMemory = new ArrayList<Point>();
		
		this.gp = gp;
	}
	
	@Override
	public void addMoveToMemory(Point p) {
		moveMemory.add(p);
	}
	
	@Override
	public LifeCommand[] getCommandList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean consume() {
		// TODO FIXXXX
		// // MOVE_COUNT++;
		// // energy -= 5;
		// try {
		// consumeResource(map.getResource(getPosition()));
		// return true;
		// } catch (Exception e) {
		// // TODO: handle exception
		// }
		return false;
	}
	
	@Override
	public void initBrain() {
		setBrain(new Brain(this) {
			
			@Override
			public int think() {
				Object[] o = { getThis() };
				double x = gp.execute_double(0, o);
				if (x < 0) {
					moveForward();
				} else if (x > 0 && x < 10) {
					turnLeft();
				} else if (x > 10 && x < 20) {
					turnRight();
				}
				MOVE_COUNT++;
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
	public void reset() {
		setEnergy(1000);
		setOrientation(new Random().nextInt(4));
		setMoveMemory(new ArrayList<Point>());
	}
	
}
