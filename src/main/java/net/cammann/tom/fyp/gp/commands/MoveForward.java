package net.cammann.tom.fyp.gp.commands;

import net.cammann.tom.fyp.core.Commandable;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

public final class MoveForward extends CommandGene {
	
	public MoveForward(final GPConfiguration conf, final Class<?> returnType)
			throws InvalidConfigurationException {
		super(conf, 1, returnType);
	}
	
	@Override
	public String toString() {
		return "MoveForward &1";
	}
	
	@Override
	public void execute_void(final ProgramChromosome c, final int n,
			final Object[] args) {
		final Commandable life = (Commandable) args[0];
		
		final double x = c.execute_double(n, 0, args);
		
		for ( int i = 0 ; i < x ; i++ ) {
			life.moveForward();
		}
	}
	
	@Override
	public double execute_double(final ProgramChromosome c, final int n,
			final Object[] args) {
		final Commandable life = (Commandable) args[0];
		final double x = c.execute_double(n, 0, args);
		
		for ( int i = 0 ; i < x ; i++ ) {
			life.moveForward();
		}
		return 1;
	}
}
