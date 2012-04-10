package net.cammann.tom.fyp.gp.commands;

import net.cammann.tom.fyp.core.Commandable;

import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.ProgramChromosome;

public class MoveForward extends CommandGene {
	
	public MoveForward(final GPConfiguration a_conf, final Class a_returnType)
			throws InvalidConfigurationException {
		super(a_conf, 1, a_returnType);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "MoveForward &1";
	}
	
	@Override
	public void execute_void(final ProgramChromosome c, final int n,
			final Object[] args) {
		final Commandable life = (Commandable) args[0];
		
		final double x = c.execute_double(n, 0, args);
		
		for (int i = 0; i < x; i++) {
			life.moveForward();
		}
	}
	
	@Override
	public double execute_double(final ProgramChromosome c, final int n,
			final Object[] args) {
		final Commandable life = (Commandable) args[0];
		final double x = c.execute_double(n, 0, args);
		
		for (int i = 0; i < x; i++) {
			life.moveForward();
		}
		return 1;
	}
}
