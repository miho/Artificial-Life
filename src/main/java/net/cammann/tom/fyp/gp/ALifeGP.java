package net.cammann.tom.fyp.gp;

import java.awt.Point;
import java.util.ArrayList;

import net.cammann.tom.fyp.commands.LifeCommand;
import net.cammann.tom.fyp.core.ABug;
import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.Brain;
import net.cammann.tom.fyp.core.EnvironmentMap;
import net.cammann.tom.fyp.utils.Logger;

import org.jgap.gp.IGPProgram;

public class ALifeGP extends ABug {
	
	private final IGPProgram gp;
	
	private Object getThis() {
		return this;
	}
	
	public ALifeGP(IGPProgram gp, EnvironmentMap map) {
		this.setMap(map);
		initBrain();
		log = new Logger("GP");
		orientation = ORIENTATION.UP;
		
		energy = 100;
		
		this.moveMemory = new ArrayList<Point>();
		
		this.gp = gp;
	}
	
	@Override
	public void addMoveToMemory(Point p) {
		moveMemory.add(p);
	}
	
	@Override
	public void setVerbosity(int level) {
		// TODO
	}
	
	@Override
	public LifeCommand[] getCommandList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean consume() {
		// TODO FIXXXX
		try {
			consumeResource(map.getResource(getPosition()));
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}
	
	@Override
	public void initBrain() {
		setBrain(new Brain(this) {
			
			@Override
			public int think() {
				Object[] o = { getThis() };
				gp.execute_double(0, o);
				return 1;
			}
		});
		
	}
	
	@Override
	public ALife clone() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
