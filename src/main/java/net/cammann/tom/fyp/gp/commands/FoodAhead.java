package net.cammann.tom.fyp.gp.commands;

import java.awt.Point;

import net.cammann.tom.fyp.core.Commandable;
import net.cammann.tom.fyp.core.Commandable.ORIENTATION;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

public class FoodAhead extends CommandGene {
	
	public FoodAhead(GPConfiguration a_conf, Class a_returnType)
			throws InvalidConfigurationException {
		super(a_conf, 1, a_returnType);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "FoodAhead in &1";
	}
	
	@Override
	public double execute_double(ProgramChromosome c, int n, Object[] args) {
		return isFoodAhead((Commandable) args[0], c.execute_double(n, 0, args));
		
	}
	
	private int isFoodAhead(Commandable life, double range) {
		
		int RANGE = 10;
		int STEP = 10;
		int x = life.getX();
		int y = life.getY();
		
		if (life.getOrientation() == ORIENTATION.UP) {
			for (int i = 1; i < RANGE; i++) {
				if (life.getMap().hasResource(x, y - i * STEP)) {
					return i;
				}
			}
		} else if (life.getOrientation() == ORIENTATION.RIGHT) {
			for (int i = 1; i < RANGE; i++) {
				if (life.getMap().hasResource(new Point(x + i * STEP, y))) {
					return i;
				}
			}
			
		} else if (life.getOrientation() == ORIENTATION.DOWN) {
			for (int i = 1; i < RANGE; i++) {
				if (life.getMap().hasResource(new Point(x, y + i * STEP))) {
					return i;
				}
			}
			
		} else {
			for (int i = 1; i < RANGE; i++) {
				if (life.getMap().hasResource(new Point(x - i * STEP, y))) {
					return i;
				}
			}
			
		}
		return 0;
	}
	
}
