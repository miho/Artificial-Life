package net.cammann.tom.fyp.gp.commands;

import java.awt.Point;

import net.cammann.tom.fyp.core.Commandable;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

public class SmellResource extends CommandGene {
	
	public SmellResource(final GPConfiguration a_conf, final Class a_returnType)
			throws InvalidConfigurationException {
		super(a_conf, 1, a_returnType);
		
	}
	
	@Override
	public String toString() {
		return "Smell in &1";
	}
	
	@Override
	public double execute_double(final ProgramChromosome c, final int n,
			final Object[] args) {
		final Commandable life = (Commandable) args[0];
		final double range = c.execute_double(n, 0, args);
		if (consumableResourcesInRange(life, range)) {
			return 1;
		}
		return 0;
		
	}
	
	private boolean consumableResourcesInRange(final Commandable life,
			final double range) {
		
		final int STEP = 10;
		final int x = life.getX();
		final int y = life.getY();
		
		for (int i = (int) -(range / 2); i < (range / 2); i++) {
			for (int j = (int) -(range / 2); j < (range / 2); j++) {
				
				if (life.getMap().hasResource(
						new Point(x + i * STEP, y + i * STEP))) {
					
					return true;
				}
			}
		}
		return false;
	}
}
