package net.cammann.tom.fyp.gp.commands;

import java.awt.Point;

import net.cammann.tom.fyp.core.Commandable;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

public final class FoodAhead extends CommandGene {
	
	public FoodAhead(final GPConfiguration conf, final Class<?> returnType)
			throws InvalidConfigurationException {
		super(conf, 1, returnType);
		
	}
	
	@Override
	public String toString() {
		return "FoodAhead in &1";
	}
	
	@Override
	public double execute_double(final ProgramChromosome c, final int n,
			final Object[] args) {
		return isFoodAhead((Commandable) args[0], c.execute_double(n, 0, args));
		
	}
	
	private int isFoodAhead(final Commandable life, final double range) {
		
		// int foodRange = life.getGene(GENE_TYPE.SEE_FOOD_RANGE);
		
		for ( int i = 1 ; i < 10 ; i++ ) {
			final Point p = GPCommandUtil.getPositionAhead(life, i);
			
			if (life.getMap().hasResource(p)) {
				return i;
			}
		}
		
		return 0;
	}
	
}
