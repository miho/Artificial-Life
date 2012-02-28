package net.cammann.tom.fyp.gp.commands;

import net.cammann.tom.fyp.core.Commandable;
import net.cammann.tom.fyp.core.Commandable.ORIENTATION;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

public class WallAhead extends CommandGene {
	
	public WallAhead(GPConfiguration a_conf, Class a_returnType)
			throws InvalidConfigurationException {
		super(a_conf, 0, a_returnType);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "IsOnResource";
	}
	
	@Override
	public boolean execute_boolean(ProgramChromosome c, int n, Object[] args) {
		return isWallAhead((Commandable) args[0]);
	}
	
	@Override
	public double execute_double(ProgramChromosome c, int n, Object[] args) {
		if (isWallAhead((Commandable) args[0])) {
			return 1;
		}
		return 0;
	}
	
	private boolean isWallAhead(Commandable life) {
		
		int STEP = 10;
		int RANGE = 5;
		int x = life.getX();
		int y = life.getY();
		
		if (life.getOrientation() == ORIENTATION.UP) {
			if (checkNewPosition(x, y - STEP * RANGE, life)) {
				return false;
			}
		} else if (life.getOrientation() == ORIENTATION.RIGHT) {
			if (checkNewPosition(x + STEP * RANGE, y, life)) {
				return false;
			}
		} else if (life.getOrientation() == ORIENTATION.DOWN) {
			if (checkNewPosition(x, y + STEP * RANGE, life)) {
				return false;
			}
		} else {
			if (checkNewPosition(x - STEP * RANGE, y, life)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkNewPosition(int x, int y, Commandable life) {
		if (x > life.getMap().getWidth() || x < 0) {
			return false;
		} else if (y > life.getMap().getHeight() || y < 0) {
			return false;
		}
		return true;
	}
}
