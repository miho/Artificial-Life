package net.cammann.tom.fyp.gp.commands;

import java.awt.Point;

import net.cammann.tom.fyp.core.ALife;
import net.cammann.tom.fyp.core.Commandable;
import net.cammann.tom.fyp.core.Commandable.ORIENTATION;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

public class FoodAhead extends CommandGene {
	
	public FoodAhead(GPConfiguration a_conf, Class a_returnType)
			throws InvalidConfigurationException {
		super(a_conf, 0, a_returnType);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Cosume";
	}
	
	@Override
	public boolean execute_boolean(ProgramChromosome c, int n, Object[] args) {
		return isFoodAhead((ALife) args[0]);
	}
	
	@Override
	public double execute_double(ProgramChromosome c, int n, Object[] args) {
		if (isFoodAhead((Commandable) args[0])) {
			return 1;
		}
		return 0;
	}
	
	private boolean isFoodAhead(Commandable life) {
		
		int RANGE = 5;
		int STEP = 5;
		int x = life.getX();
		int y = life.getY();
		
		if (life.getOrientation() == ORIENTATION.UP) {
			for (int i = 1; i < RANGE; i++) {
				if (life.getMap().hasResource(x, y - i * STEP)) {
					return true;
				}
			}
		} else if (life.getOrientation() == ORIENTATION.RIGHT) {
			for (int i = 1; i < RANGE; i++) {
				if (life.getMap().hasResource(new Point(x + i * STEP, y))) {
					return true;
				}
			}
			
		} else if (life.getOrientation() == ORIENTATION.DOWN) {
			for (int i = 1; i < RANGE; i++) {
				if (life.getMap().hasResource(new Point(x, y + i * STEP))) {
					return true;
				}
			}
			
		} else {
			for (int i = 1; i < RANGE; i++) {
				if (life.getMap().hasResource(new Point(x - i * STEP, y))) {
					return true;
				}
			}
			
		}
		return false;
	}
	
}
