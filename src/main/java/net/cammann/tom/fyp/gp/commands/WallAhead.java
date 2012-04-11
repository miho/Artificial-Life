package net.cammann.tom.fyp.gp.commands;

import net.cammann.tom.fyp.core.Commandable;
import net.cammann.tom.fyp.core.Commandable.ORIENTATION;
import net.cammann.tom.fyp.core.SimpleMap;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

public final class WallAhead extends CommandGene {
	
	public WallAhead(final GPConfiguration conf, final Class<?> returnType)
			throws InvalidConfigurationException {
		super(conf, 0, returnType);
		
	}
	
	@Override
	public String toString() {
		return "IsOnResource";
	}
	
	@Override
	public boolean execute_boolean(final ProgramChromosome c, final int n,
			final Object[] args) {
		return isWallAhead((Commandable) args[0]);
	}
	
	@Override
	public double execute_double(final ProgramChromosome c, final int n,
			final Object[] args) {
		if (isWallAhead((Commandable) args[0])) {
			return 1;
		}
		return 0;
	}
	
	private boolean isWallAhead(final Commandable life) {
		
		final int RANGE = 5;
		final int x = life.getX();
		final int y = life.getY();
		
		if (life.getOrientation() == ORIENTATION.UP) {
			if (checkNewPosition(x, y - SimpleMap.STEP_SIZE * RANGE, life)) {
				return false;
			}
		} else if (life.getOrientation() == ORIENTATION.RIGHT) {
			if (checkNewPosition(x + SimpleMap.STEP_SIZE * RANGE, y, life)) {
				return false;
			}
		} else if (life.getOrientation() == ORIENTATION.DOWN) {
			if (checkNewPosition(x, y + SimpleMap.STEP_SIZE * RANGE, life)) {
				return false;
			}
		} else {
			if (checkNewPosition(x - SimpleMap.STEP_SIZE * RANGE, y, life)) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkNewPosition(final int x, final int y,
			final Commandable life) {
		if (x > life.getMap().getWidth() || x < 0) {
			return false;
		} else if (y > life.getMap().getHeight() || y < 0) {
			return false;
		}
		return true;
	}
}
